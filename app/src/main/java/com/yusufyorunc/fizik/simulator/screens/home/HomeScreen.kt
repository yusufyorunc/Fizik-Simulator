package com.yusufyorunc.fizik.simulator.screens.home

import android.annotation.SuppressLint
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yusufyorunc.fizik.simulator.Screen
import com.yusufyorunc.fizik.simulator.ui.widgets.StatCard


data class CardData(
    val title: String,
    val value: String,
    val targetScreen: Screen
)

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun HomeScreen(onNavigate: (Screen) -> Unit) {

    val configuration = LocalConfiguration.current
    val cardHeight = configuration.screenHeightDp.dp * 0.20f

    val cardItems = listOf(
        CardData(
            title = "Serbest Düşüş",
            value = "Yerçekimi Analizi",
            targetScreen = Screen.FREE_FALL
        ),
        CardData(
            title = "Newton'un 2. Yasası",
            value = "Kuvvet & İvme",
            targetScreen = Screen.NEWTON
        ),
        CardData(
            title = "Eğik Atış",
            value = "Yörünge Hareketi",
            targetScreen = Screen.PROJECTILE
        ),
        CardData(
            title = "Basit Sarkaç",
            value = "Harmonik Hareket",
            targetScreen = Screen.PENDULUM
        ),
        CardData(
            title = "Enerji Dönüşümü",
            value = "KE & PE",
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