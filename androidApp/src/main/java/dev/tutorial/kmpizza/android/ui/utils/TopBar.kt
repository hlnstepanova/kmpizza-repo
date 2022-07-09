package dev.tutorial.kmpizza.android.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.tutorial.kmpizza.android.R

@Composable
fun TopBar(upPress: () -> Unit) {
    Surface(elevation = 8.dp) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                modifier = Modifier.clickable(onClick = upPress)
                    .padding(16.dp),
                contentDescription = null
            )
        }
    }
}
