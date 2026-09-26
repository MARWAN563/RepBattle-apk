package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.BattleRepository
import com.example.ui.theme.AccentViolet
import com.example.ui.theme.AccentVioletContainer
import com.example.ui.theme.AccentVioletLight
import com.example.ui.theme.ArenaBackground
import com.example.ui.theme.ArenaBorder
import com.example.ui.theme.ArenaSurface1
import com.example.ui.theme.ArenaSurface2
import com.example.ui.theme.ArenaSurfaceContainer
import com.example.ui.theme.ArenaSurfaceHigh
import com.example.ui.theme.SecondaryGreen
import com.example.ui.theme.SuccessNeon
import com.example.ui.theme.TertiaryOrange
import com.example.ui.theme.TextLight
import com.example.ui.theme.TextMuted

@Composable
fun HistoryScreen(
    repository: BattleRepository
) {
    val battles by repository.allBattles.collectAsState(initial = emptyList())
    val winCount by repository.winCount.collectAsState(initial = 0)
    val totalCount by repository.battleCount.collectAsState(initial = 0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ArenaBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Analytics Summary Header Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(ArenaSurfaceContainer)
                .border(1.dp, ArenaBorder, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.QueryStats,
                            contentDescription = null,
                            tint = SecondaryGreen,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "ATHLETIC TELEMETRY",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SecondaryGreen,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(
                        text = "Last 30 Days",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "1,420", fontSize = 28.sp, fontWeight = FontWeight.Black, color = TextLight)
                        Text(text = "Verified Reps", fontSize = 11.sp, color = TextMuted)
                    }
                    Column {
                        Text(text = "96.4%", fontSize = 28.sp, fontWeight = FontWeight.Black, color = SecondaryGreen)
                        Text(text = "Form Precision", fontSize = 11.sp, color = TextMuted)
                    }
                    Column {
                        Text(text = "1.28s", fontSize = 28.sp, fontWeight = FontWeight.Black, color = AccentVioletLight)
                        Text(text = "Avg Tempo", fontSize = 11.sp, color = TextMuted)
                    }
                }

                // Volume Bar Chart Visualization
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "Weekly Rep Output", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextLight)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val weeklyHeights = listOf(0.4f, 0.7f, 0.5f, 0.9f, 0.85f, 0.3f, 0.6f)
                        val days = listOf("M", "T", "W", "T", "F", "S", "S")
                        weeklyHeights.zip(days).forEach { (ratio, day) ->
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(width = 24.dp, height = (44 * ratio).dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (ratio > 0.8f) AccentVioletContainer else ArenaSurfaceHigh)
                                )
                                Text(text = day, fontSize = 10.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }

        // Recent Verifications
        Text(
            text = "Completed Vision Sessions",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextLight
        )

        battles.forEach { battle ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(ArenaSurface1)
                    .border(1.dp, ArenaBorder, RoundedCornerShape(14.dp))
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (battle.isWin) SuccessNeon.copy(alpha = 0.2f) else AccentViolet.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = if (battle.isWin) SecondaryGreen else AccentVioletLight,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Column {
                        Text(text = "${battle.exerciseName} vs ${battle.opponentName}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextLight)
                        Text(text = "${battle.timestampFormatted} · Form ${battle.formScorePercent}%", fontSize = 11.sp, color = TextMuted)
                    }
                }

                Text(
                    text = "${battle.userScore} reps",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Black,
                    color = if (battle.isWin) SecondaryGreen else TextLight
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
