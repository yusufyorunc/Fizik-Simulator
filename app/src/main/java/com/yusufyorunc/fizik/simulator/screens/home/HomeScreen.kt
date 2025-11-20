package com.yusufyorunc.fizik.simulator.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yusufyorunc.fizik.simulator.R
import com.yusufyorunc.fizik.simulator.Screen
import com.yusufyorunc.fizik.simulator.ui.widgets.StatCard


data class CardData(
    val title: String,
    val value: String,
    val targetScreen: Screen
)

@Composable
fun HomeScreen(onNavigate: (Screen) -> Unit) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val cardHeight = (screenHeight - 64.dp) / 4

    val cardItems = listOf(
        CardData(
            title = stringResource(R.string.module_free_fall),
            value = stringResource(R.string.module_free_fall_desc),
            targetScreen = Screen.FREE_FALL
        ),
        CardData(
            title = stringResource(R.string.module_newton),
            value = stringResource(R.string.module_newton_desc),
            targetScreen = Screen.NEWTON
        ),
        CardData(
            title = stringResource(R.string.module_projectile),
            value = stringResource(R.string.module_projectile_desc),
            targetScreen = Screen.PROJECTILE
        ),
        CardData(
            title = stringResource(R.string.module_pendulum),
            value = stringResource(R.string.module_pendulum_desc),
            targetScreen = Screen.PENDULUM
        ),
        CardData(
            title = stringResource(R.string.module_energy),
            value = stringResource(R.string.module_energy_desc),
            targetScreen = Screen.ENERGY
        )
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
                    onClick = { onNavigate(cardData.targetScreen) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onNavigate = {})
}