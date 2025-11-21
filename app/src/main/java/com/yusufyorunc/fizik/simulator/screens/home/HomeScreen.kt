package com.yusufyorunc.fizik.simulator.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yusufyorunc.fizik.simulator.R
import com.yusufyorunc.fizik.simulator.Screen
import com.yusufyorunc.fizik.simulator.ui.theme.ThemeMode
import com.yusufyorunc.fizik.simulator.ui.theme.ThemeState
import com.yusufyorunc.fizik.simulator.ui.widgets.StatCard

data class CardData(
    val title: String,
    val value: String,
    val targetScreen: Screen
)

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun HomeScreen(
    onNavigate: (Screen) -> Unit,
    themeState: ThemeState
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val cardHeight = (screenHeight - 120.dp) / 4

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.theme_mode),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Button(
                        onClick = { themeState.toggleTheme() }
                    ) {
                        val themeText = when (themeState.themeMode) {
                            ThemeMode.LIGHT -> "☀️"
                            ThemeMode.DARK -> "🌙"
                            ThemeMode.SYSTEM -> "⚙️"
                        }
                        Text(text = themeText, style = MaterialTheme.typography.titleLarge)
                    }
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),
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
}