package `in`.iot.lab.kritique

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint
import `in`.iot.lab.design.theme.CustomAppTheme
import `in`.iot.lab.design.theme.GradientTop
import `in`.iot.lab.kritique.domain.repository.UserRepo
import `in`.iot.lab.kritique.view.navigation.MainNavGraph
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // User Repo
    @Inject
    lateinit var user: UserRepo

    // --- Google Play In-App Update ---
    private lateinit var appUpdateManager: AppUpdateManager
    private lateinit var updateLauncher: ActivityResultLauncher<IntentSenderRequest>

    // Becomes true once a FLEXIBLE update has finished downloading and is ready to install.
    private val updateDownloaded = mutableStateOf(false)

    // Tracks the flexible update's install state (download → downloaded → installed / failed).
    private val installStateListener = InstallStateUpdatedListener { state ->
        when (state.installStatus()) {
            InstallStatus.DOWNLOADED -> updateDownloaded.value = true   // "updated" — ready to apply
            InstallStatus.INSTALLED -> updateDownloaded.value = false   // applied successfully
            InstallStatus.FAILED ->
                Toast.makeText(this, "Update failed. Please try again later.", Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = GradientTop.toArgb()

        appUpdateManager = AppUpdateManagerFactory.create(this)
        appUpdateManager.registerListener(installStateListener)

        // Result of the Play update dialog (flexible consent) / update screen (immediate).
        updateLauncher = registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()
        ) { result ->
            if (result.resultCode != RESULT_OK) {
                Log.w(TAG, "In-app update flow cancelled or failed (code=${result.resultCode}).")
            }
        }

        checkForUpdate()

        setContent {
            CustomAppTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                val isDownloaded by updateDownloaded

                // When the flexible update is downloaded, prompt the user to restart & apply it.
                LaunchedEffect(isDownloaded) {
                    if (isDownloaded) {
                        val result = snackbarHostState.showSnackbar(
                            message = "An update has just been downloaded.",
                            actionLabel = "RESTART",
                            duration = SnackbarDuration.Indefinite
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            appUpdateManager.completeUpdate()
                        }
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    MainNavGraph(
                        isUserLoggedIn = user.isUserLoggedIn(),
                        navHostController = navController
                    )
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }

    /** "Update available" check — runs at launch. */
    private fun checkForUpdate() {
        appUpdateManager.appUpdateInfo
            .addOnSuccessListener { info ->
                if (info.updateAvailability() != UpdateAvailability.UPDATE_AVAILABLE) {
                    return@addOnSuccessListener
                }
                // Prefer a non-blocking flexible update; fall back to immediate if that's all that's allowed.
                when {
                    info.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) ->
                        startUpdate(info, AppUpdateType.FLEXIBLE)

                    info.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) ->
                        startUpdate(info, AppUpdateType.IMMEDIATE)
                }
            }
            .addOnFailureListener { Log.e(TAG, "Failed to check for updates", it) }
    }

    /** "Update" trigger — launches the Play in-app update flow. */
    private fun startUpdate(info: AppUpdateInfo, @AppUpdateType type: Int) {
        runCatching {
            appUpdateManager.startUpdateFlowForResult(
                info,
                updateLauncher,
                AppUpdateOptions.newBuilder(type).build()
            )
        }.onFailure { Log.e(TAG, "Failed to start the update flow", it) }
    }

    override fun onResume() {
        super.onResume()
        // Resume any update that was in progress while the app was backgrounded.
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            // A flexible update finished downloading in the background → prompt to install.
            if (info.installStatus() == InstallStatus.DOWNLOADED) {
                updateDownloaded.value = true
            }
            // An immediate update was interrupted → resume it.
            if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                startUpdate(info, AppUpdateType.IMMEDIATE)
            }
        }
    }

    override fun onDestroy() {
        appUpdateManager.unregisterListener(installStateListener)
        super.onDestroy()
    }

    private companion object {
        const val TAG = "InAppUpdate"
    }
}
