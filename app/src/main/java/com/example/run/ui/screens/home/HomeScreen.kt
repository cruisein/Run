package com.example.run.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.run.R
import com.example.run.data.entities.Duration
import com.example.run.data.entities.Session
import com.example.run.data.pickQuote
import com.example.run.ui.theme.RunTheme
import kotlinx.coroutines.delay
import java.math.RoundingMode

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val runDurations = viewModel.durations.collectAsState(initial = emptyList()).value
    val sessions = viewModel.sessions.collectAsState(initial = emptyList()).value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.good_morning),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.paddingFromBaseline(bottom = 16.dp),
        )
        Quote()
        Spacer(modifier = Modifier.height(16.dp))
        StatsTable(sessions = sessions)
        Spacer(modifier = Modifier.height(16.dp))
        SetUp(
            timerValue = uiState.timerValue,
            isTimerRunning = uiState.isTimerRunning,
            runDurations = runDurations.sortedBy {
                it.seconds
            },
            onSetUpTimer = viewModel::onSetUpTimer,
            onAddDurationEvent = viewModel::onAddDuration,
        )
        Timer(
            timerValue = uiState.timerValue,
            isTimerRunning = uiState.isTimerRunning,
            onClickButton = viewModel::onClickTimerButton,
            onFinishSession = viewModel::onFinishSession,
            updateTimer = viewModel::onUpdateTimer,
        )
    }
}

@Composable
fun Quote(
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.today_quote),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.paddingFromBaseline(bottom = 8.dp),
            )
            Text(
                text = pickQuote(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
fun StatsTable(
    sessions: List<Session>,
    modifier: Modifier = Modifier
) {
    var minutes = 0
    sessions.forEach { session ->
        minutes += session.duration
    }
    val hours = (minutes / 60.0).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()

    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StatsElement(
                label = stringResource(id = R.string.sessions_done),
                value = sessions.size.toString()
            )
            StatsElement(
                label = stringResource(id = R.string.hours_ran),
                value = hours.toString()
            )
        }
    }
}

@Composable
fun StatsElement(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.height(48.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = if (isSystemInDarkTheme()) {
                Color.White
            } else {
                Color.Black
            },
        )
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
fun SetUp(
    timerValue: Int,
    onAddDurationEvent: (Int) -> Unit,
    isTimerRunning: Boolean,
    runDurations: List<Duration>,
    onSetUpTimer: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var minutesValue by remember { mutableStateOf(0f) }

    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.pick_a_duration).uppercase(),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = stringResource(id = R.string.durations),
                style = MaterialTheme.typography.labelLarge,
                color = if (isSystemInDarkTheme()) {
                    Color.White
                } else {
                    Color.Black
                },
                modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(runDurations) { duration ->
                    Button(
                        onClick = { onSetUpTimer(duration.seconds) },
                        enabled = (!isTimerRunning || timerValue == 0),
                    ) {
                        Text(text = stringResource(id = R.string.minutes, (duration.seconds / 60)))
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = minutesValue.toInt().toString() + " minutes:",
                    modifier = Modifier.width(96.dp)
                )
                Slider(
                    value = minutesValue,
                    onValueChange = { minutesValue = it },
                    valueRange = 0f..60f,
                    steps = 60,
                )
            }

            IconButton(
                onClick = { onAddDurationEvent(minutesValue.toInt()) },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = !isTimerRunning,
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    }
}

@Composable
fun Timer(
    timerValue: Int,
    isTimerRunning: Boolean,
    updateTimer: (Int) -> Unit,
    onFinishSession: () -> Unit,
    onClickButton: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    if (timerValue == 0 && isTimerRunning) {
        run { onFinishSession }
    }

    val hourValue = if (timerValue / 3600 < 10) {
        "0" + (timerValue / 3600).toString()
    } else {
        (timerValue / 3600).toString()
    }

    val minuteValue = if (timerValue / 60 < 10) {
        "0" + (timerValue / 60).toString()
    } else {
        (timerValue / 60).toString()
    }

    val secondValue = if (timerValue % 60 < 10) {
        "0" + (timerValue % 60).toString()
    } else {
        (timerValue % 60).toString()
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "$hourValue:$minuteValue:$secondValue",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        TextButton(onClick = { updateTimer(0) }, enabled = !isTimerRunning) {
            Text(text = stringResource(id = R.string.reset))
        }
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(
            shape = CircleShape,
            onClick = { onClickButton(!isTimerRunning) },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.size(80.dp),
            enabled = timerValue != 0,
        ) {
            Icon(
                imageVector = if (isTimerRunning) {
                    Icons.Default.Pause
                } else {
                    Icons.Default.PlayArrow
                }, contentDescription = null
            )
        }
    }
    LaunchedEffect(key1 = timerValue, key2 = isTimerRunning) {
        if (timerValue > 0 && isTimerRunning) {
            delay(1000L)
            updateTimer(timerValue - 1)
        }
    }
}
