package `in`.iot.lab.design.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import `in`.iot.lab.design.theme.CustomAppTheme

@Preview
@Composable
private fun ButtonsPreview() {
    Column {
        CustomAppTheme {
            PrimaryButton(onClick = {}) {
                Text(text = "Button")
            }
            PrimaryButton(
                onClick = {},
                enabled = false
            ) {
                Text(text = "Button")
            }
            SecondaryButton(onClick = {}) {
                Text(text = "Button")
            }
            SecondaryButton(
                onClick = {},
                enabled = false
            ) {
                Text(text = "Button")
            }
            FAB(
                onClick = {}
            )

        }
    }
}

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shapes: RoundedCornerShape = RoundedCornerShape(percent = 50),
    color: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
        disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
    ),
    contentPadding: PaddingValues = PaddingValues(25.dp, 0.dp),
    content: @Composable (RowScope.() -> Unit)
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shapes,
        colors = color,
        contentPadding = contentPadding,
        content = content
    )
}


@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shapes: RoundedCornerShape = RoundedCornerShape(percent = 50),
    color: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.onPrimary,
        contentColor = MaterialTheme.colorScheme.onBackground,
        disabledContainerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
        disabledContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
    ),
    contentPadding: PaddingValues = PaddingValues(25.dp, 0.dp),
    content: @Composable (RowScope.() -> Unit)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shapes,
        colors = color,
        contentPadding = contentPadding,
        content = content
    )
}

@Composable
fun FAB(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(percent = 100),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    contentDescription :String = "ADD REVIEW BUTTON",
    icon:ImageVector=Icons.Filled.Add
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        elevation = elevation
        ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription)

    }
}