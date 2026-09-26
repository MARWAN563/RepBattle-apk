package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.components.AthleteAssets
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
fun BattlesScreen(
    onCreateBattleClick: () -> Unit,
    onJoinDuelClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ArenaBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Active Battles Card
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
                            imageVector = Icons.Default.MilitaryTech,
                            contentDescription = null,
                            tint = AccentVioletLight,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "LIVE ARENA QUEUE",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = AccentVioletLight,
                            letterSpacing = 1.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(SuccessNeon.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "3 ACTIVE DUELS",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = SecondaryGreen
                        )
                    }
                }

                Text(
                    text = "Asynchronous Ghost Duels",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    color = TextLight,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "Compete against rival wireframes recorded in real-time. Anticheat verifies strict range of motion.",
                    fontSize = 13.sp,
                    color = TextMuted,
                    lineHeight = 18.sp
                )

                Button(
                    onClick = onCreateBattleClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_create_duel_from_battles"),
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentViolet)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Bolt, contentDescription = null)
                        Text(text = "CREATE NEW DUEL", fontWeight = FontWeight.Black, fontSize = 13.sp)
                    }
                }
            }
        }

        // Rival Duel Queue List
        Text(
            text = "Active Ghost Matchups",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextLight
        )

        DuelQueueItem(
            name = "Adam Miller",
            handle = "@miller_a",
            avatarUrl = AthleteAssets.ADAM_AVATAR,
            exercise = "Push-ups",
            rule = "First to 50",
            status = "Your Turn",
            onAction = { onJoinDuelClick("Adam Miller") }
        )

        DuelQueueItem(
            name = "Sarah Chen",
            handle = "@sarahc",
            avatarUrl = AthleteAssets.SARAH_AVATAR,
            exercise = "Deep Squats",
            rule = "Century (100)",
            status = "Waiting for Sarah",
            onAction = { onJoinDuelClick("Sarah Chen") }
        )

        DuelQueueItem(
            name = "David K.",
            handle = "@david_k",
            avatarUrl = "",
            initials = "DK",
            exercise = "Pull-ups",
            rule = "Most in 5 Min",
            status = "Ready to Duel",
            onAction = { onJoinDuelClick("David K.") }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun DuelQueueItem(
    name: String,
    handle: String,
    avatarUrl: String,
    initials: String = "",
    exercise: String,
    rule: String,
    status: String,
    onAction: () -> Unit
) {
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
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(ArenaSurfaceHigh),
                contentAlignment = Alignment.Center
            ) {
                if (avatarUrl.isNotEmpty()) {
                    AsyncImage(
                        model = avatarUrl,
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(44.dp)
                    )
                } else {
                    Text(text = initials, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                }
            }

            Column {
                Text(text = name, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextLight)
                Text(text = "$exercise · $rule", fontSize = 12.sp, color = TextMuted)
                Text(text = status, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = AccentVioletLight)
            }
        }

        Button(
            onClick = onAction,
            modifier = Modifier.height(36.dp),
            shape = RoundedCornerShape(999.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ArenaSurface2, contentColor = TextLight),
            border = androidx.compose.foundation.BorderStroke(1.dp, ArenaBorder)
        ) {
            Text(text = "ENTER", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}
