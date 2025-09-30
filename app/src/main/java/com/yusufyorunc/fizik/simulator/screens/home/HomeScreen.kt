package com.yusufyorunc.fizik.simulator.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yusufyorunc.fizik.simulator.NativeLibrary
import com.yusufyorunc.fizik.simulator.R
import com.yusufyorunc.fizik.simulator.ui.widgets.StatCard


data class CardData(
    val title: String,
    val value: String,
    val dialogTitle: String,
    val nativeCall: () -> String
)

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun HomeScreen() {

    val speedFloat = 12.345f
    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogContent by remember { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val cardHeight = configuration.screenHeightDp.dp * 0.25f

    val cardItems = listOf(
        CardData(
            title = stringResource(id = R.string.speed),
            value = stringResource(id = R.string.speed_value, speedFloat),
            dialogTitle = stringResource(id = R.string.speed_analysis),
            nativeCall = { NativeLibrary.safeOnSpeedCardClicked() }
        ),
        CardData(
            title = stringResource(id = R.string.force),
            value = "24 N",
            dialogTitle = stringResource(id = R.string.force_analysis),
            nativeCall = { NativeLibrary.safeOnForceCardClicked() }
        ),
        CardData(
            title = stringResource(id = R.string.description),
            value = stringResource(id = R.string.system_status, "normal"),
            dialogTitle = stringResource(id = R.string.system_status_analysis),
            nativeCall = { NativeLibrary.safeOnDescriptionCardClicked() }
        ),
        CardData(
            title = stringResource(id = R.string.energy),
            value = "120 J",
            dialogTitle = stringResource(id = R.string.energy_analysis),
            nativeCall = { NativeLibrary.safeOnEnergyCardClicked() }
        ),
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cardItems) { cardData ->
                StatCard(
                    modifier = Modifier.height(cardHeight),
                    title = cardData.title,
                    value = cardData.value,
                    onClick = {
                        dialogContent = cardData.nativeCall()
                        dialogTitle = cardData.dialogTitle
                        showDialog = true
                    }
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = dialogTitle, style = MaterialTheme.typography.headlineSmall) },
            text = { Text(text = dialogContent, style = MaterialTheme.typography.bodyMedium) },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(stringResource(id = R.string.ok))
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}