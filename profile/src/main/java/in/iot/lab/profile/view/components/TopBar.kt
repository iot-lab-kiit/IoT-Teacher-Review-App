package `in`.iot.lab.profile.view.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Profile",
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    )
}