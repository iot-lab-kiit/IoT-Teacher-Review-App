package `in`.iot.lab.design.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.theme.backgroundColor

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SearchBar(
        value = "",
        onValueChange = {},
        label = "Search a faculty",
        keyboardActions = KeyboardActions(
            onSearch = {}
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        leadingIcon = Icons.Outlined.Search
    )
}

/**
 * This composable function is a search bar used to search faculties from the database.
 *
 * @param value The current value of the search bar.
 * @param onValueChange Called when the value of the search bar changes.
 * @param label The label to be displayed when the search bar is empty.
 * @param keyboardActions The keyboard actions, such as - onSearch - to be used by the search bar.
 * @param keyboardOptions The keyboard options, such as - imeAction - to be used by the search bar.
 * @param leadingIcon The leading icon to be displayed in the search bar.
 * @param trailingIcon The trailing icon to be displayed in the search bar.
 */

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    leadingIcon: ImageVector,
    trailingIcon: ImageVector ?= null
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(0.dp)
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(50.dp)

            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {


            OutlinedTextField(
                modifier = Modifier
                    .background(color = backgroundColor)
                    .padding(end = 2.dp)
                    .fillMaxWidth(1f),
                value = value,
                onValueChange = onValueChange,
                label = {
                    if (value.isEmpty()) {
                        Text(text = label)
                    }
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    cursorColor = Color.White,
                    focusedLabelColor = backgroundColor,
                    unfocusedLabelColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                leadingIcon = {
                    FloatingActionButton(
                        onClick = {

                        },
                        containerColor = Color.Transparent,
                        contentColor = Color.White,
                        elevation = FloatingActionButtonDefaults.elevation(0.dp)
                    ) {
                        Icon(
                            imageVector = leadingIcon,
                            contentDescription = "leading_icon",
                            modifier = Modifier
                                .background(color = Color.Transparent)
                                .size(40.dp)
                                .padding(start = 10.dp)
                        )
                    }
                },
                trailingIcon = {
                    FloatingActionButton(
                        onClick = {

                        },
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary,
                        elevation = FloatingActionButtonDefaults.elevation(0.dp)
                    ) {
                        if (trailingIcon != null) {
                            Icon(
                                imageVector = trailingIcon,
                                contentDescription = "trailing_icon",
                                modifier = Modifier
                                    .background(color = Color.Transparent)
                                    .size(40.dp)
                                    .padding(end = 10.dp)
                            )
                        }
                    }
                })
        }

    }
}

