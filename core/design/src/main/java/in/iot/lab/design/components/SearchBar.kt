package `in`.iot.lab.design.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.theme.CustomAppTheme


// Preview Function
@Preview("Light")
@Preview(
    name = "Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
private fun DefaultPreview1() {
    CustomAppTheme {
        AppScreen {
            SearchBar(
                onValueChange = {},
                label = "Search a faculty"
            ) {}
        }
    }
}


/**
 * This composable function is a search bar used to search faculties from the database.
 *
 * @param onValueChange Called when the value of the search bar changes.
 * @param label The label to be displayed when the search bar is empty.
 * @param leadingIcon The leading icon to be displayed in the search bar.
 * @param trailingIcon The trailing icon to be displayed in the search bar.
 */
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onValueChange: ((String) -> Unit)? = null,
    label: String,
    leadingIcon: ImageVector = Icons.Outlined.Search,
    trailingIcon: ImageVector = Icons.Filled.Close,
    onClearClick: (() -> Unit)? = null,
    onSearchClicked: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current
    var search by remember { mutableStateOf("") }

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = search,
        onValueChange = {
            search = it
            if (onValueChange != null) onValueChange(it)
        },
        label = {
            Text(text = label)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                onSearchClicked(search)
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = leadingIcon,
                contentDescription = "leading_icon",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(32.dp)
            )
        },
        trailingIcon = {
            if (search.isNotEmpty()) {
                IconButton(
                    onClick = {
                        search = ""
                        if (onClearClick != null) onClearClick()
                    }
                ) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = "trailing_icon",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        },
        shape = CircleShape
    )
}


