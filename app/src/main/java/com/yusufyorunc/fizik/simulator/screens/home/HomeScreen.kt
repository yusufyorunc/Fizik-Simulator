package com.yusufyorunc.fizik.simulator.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yusufyorunc.fizik.simulator.ui.widgets.StatCard
import com.yusufyorunc.fizik.simulator.NativeLibrary


@Composable
fun HomeScreen() {

    val speedFloat = 12.345f
    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogContent by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // İki yan yana kart
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatCard(
                    title = stringResource(id = com.yusufyorunc.fizik.simulator.R.string.speed),
                    value = stringResource(
                        id = com.yusufyorunc.fizik.simulator.R.string.speed_value,
                        speedFloat
                    ),
                    width = 160.dp,
                    height = 110.dp,
                    onClick = {
                        try {
                            val result = NativeLibrary.onSpeedCardClicked()
                            dialogTitle = "Hız Analizi"
                            dialogContent = result
                            showDialog = true
                        } catch (e: Exception) {
                            dialogTitle = "Hata"
                            dialogContent = "Hesaplama hatası: ${e.message}"
                            showDialog = true
                        }
                    }

                )
                Spacer(modifier = Modifier.width(12.dp))
                StatCard(
                    title = stringResource(id = com.yusufyorunc.fizik.simulator.R.string.force),
                    value = "24 N",
                    width = 160.dp,
                    height = 110.dp,
                    onClick = {
                        try {
                            val result = NativeLibrary.onForceCardClicked()
                            dialogTitle = "Kuvvet Analizi"
                            dialogContent = result
                            showDialog = true
                        } catch (e: Exception) {
                            dialogTitle = "Hata"
                            dialogContent = "Hesaplama hatası: ${e.message}"
                            showDialog = true
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Alta sıralı geniş kart
            StatCard(
                title = stringResource(id = com.yusufyorunc.fizik.simulator.R.string.description),
                value = stringResource(
                    id = com.yusufyorunc.fizik.simulator.R.string.system_status,
                    "normal"
                ),
                width = 340.dp,
                height = 120.dp,
                onClick = {
                    try {
                        val result = NativeLibrary.onDescriptionCardClicked()
                        dialogTitle = "Sistem Durumu"
                        dialogContent = result
                        showDialog = true
                    } catch (e: Exception) {
                        dialogTitle = "Hata"
                        dialogContent = "Sistem analizi hatası: ${e.message}"
                        showDialog = true
                    }
                }
            )
        }
    }

    // Sonuçları gösteren dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = dialogTitle,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    text = dialogContent,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("Tamam")
                }
            }
        )
    }
}
