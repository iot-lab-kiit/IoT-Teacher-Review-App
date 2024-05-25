package `in`.iot.lab.auth.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import `in`.iot.lab.auth.R


/**
 * This function is the OnBoarding UI above the Login with Google Button.
 *
 * @param modifier The modifier to be applied to this composable.
 */
@Composable
fun AuthOnBoarding(modifier: Modifier = Modifier) {

    Column(modifier = modifier.fillMaxWidth()) {

        // Onboarding Image
        Image(
            painter = painterResource(id = R.drawable.feedback),
            contentDescription = "Hero Image",
            contentScale = ContentScale.Fit,
            modifier = modifier
                .fillMaxWidth()
                .height(320.dp)
        )

        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // Title Text
            Text(
                text = "Find the right college professor for you",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 32.sp,
                ),
                textAlign = TextAlign.Center
            )

            // Caption Text
            Text(
                text = "Explore and share your experiences with teachers at our college to help others make informed decisions.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}