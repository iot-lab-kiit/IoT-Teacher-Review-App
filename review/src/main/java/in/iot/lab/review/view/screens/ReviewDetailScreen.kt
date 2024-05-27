package `in`.iot.lab.review.view.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import `in`.iot.lab.design.components.AppScreen


@Composable
fun ReviewDetailScreenControl(
    id: String
) {
    AppScreen {
        Text(text = "Teacher Id $id")
    }
}