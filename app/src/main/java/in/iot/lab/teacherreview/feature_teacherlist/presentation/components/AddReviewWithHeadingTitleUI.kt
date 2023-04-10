package `in`.iot.lab.teacherreview.feature_teacherlist.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.teacherreview.R
import `in`.iot.lab.teacherreview.core.theme.CustomAppTheme

// This is the Preview function of the File
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreviewLoading() {
    CustomAppTheme {
        AddReviewWithHeadingTitleUI(
            headingTitle = R.string.overall_review,
            userInput = "",
            onUserInputChange = {}
        )
    }
}

/**
 * This function draws the Review heading Title along with a Text Field to input review
 *
 * @param modifier Default to pass modification from the parent function
 * @param headingTitle This is the title of the Add Review Component
 * @param userInput This is the Inputted value of the user
 * @param onUserInputChange This function is executed when the user changes the Input
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReviewWithHeadingTitleUI(
    modifier: Modifier = Modifier,
    headingTitle: Int,
    userInput: String,
    onUserInputChange: (String) -> Unit
) {

    // Parent Composable which contains the Components
    Column(
        modifier = modifier
    ) {

        // Heading Title to be written
        Text(
            text = stringResource(id = headingTitle),
            style = MaterialTheme.typography.titleMedium,
        )

        // Spacing of 4 dp
        Spacer(modifier = Modifier.height(4.dp))

        // This is the text Input where the user will give his Review
        OutlinedTextField(
            value = userInput,
            onValueChange = {
                onUserInputChange(it)
            },

            // This input field can contain more than 1 line
            singleLine = false,

            // Setting the max lines of the Text Field to 3
            maxLines = 3,

            // This text field fills width wise
            modifier = Modifier
                .fillMaxWidth(),

            // This is the Label of the input which is shown to the top left when selected
            label = {
                Text(
                    stringResource(id = R.string.add_your_review),
                    color = MaterialTheme.colorScheme.primary
                )
            },

            // Setting Custom Colors for the Outlined TextField
            colors = TextFieldDefaults.outlinedTextFieldColors(
                disabledTextColor = MaterialTheme.colorScheme.primary,
                disabledBorderColor = MaterialTheme.colorScheme.primaryContainer,
            ),

            // Shape of the TextField
            shape = MaterialTheme.shapes.medium,
        )
    }
}