package com.yusufyorunc.fizik.simulator.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yusufyorunc.fizik.simulator.ui.widgets.StatCard

@Composable
fun HomeScreen() {

    val speedFloat = 12.345f

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
                    height = 110.dp
                )
                Spacer(modifier = Modifier.width(12.dp))
                StatCard(
                    title = stringResource(id = com.yusufyorunc.fizik.simulator.R.string.force),
                    value = "24 N",
                    width = 160.dp,
                    height = 110.dp
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
                height = 120.dp
            )
        }
    }
}
