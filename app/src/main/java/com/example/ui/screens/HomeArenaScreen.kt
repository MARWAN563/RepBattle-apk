package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.material.icons.filled.SportsMartialArts
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.components.AthleteAssets
import com.example.ui.theme.AccentViolet
import com.example.ui.theme.AccentVioletContainer
import com.example.ui.theme.AccentVioletGlow
import com.example.ui.theme.AccentVioletLight
import com.example.ui.theme.ArenaBackground
import com.example.ui.theme.ArenaBorder
import com.example.ui.theme.ArenaSurface1
import com.example.ui.theme.ArenaSurface2
import com.example.ui.theme.ArenaSurfaceContainer
import com.example.ui.theme.ArenaSurfaceHigh
import com.example.ui.theme.BattleNumStyle
import com.example.ui.theme.DangerRed
import com.example.ui.theme.SecondaryContainerGreen
import com.example.ui.theme.SecondaryGreen
import com.example.ui.theme.SuccessNeon
import com.example.ui.theme.TertiaryContainerOrange
import com.example.ui.theme.TertiaryOrange
import com.example.ui.theme.TextLight
import com.example.ui.theme.TextMuted

@Composable
fun HomeArenaScreen(
    onContinueBattle: () -> Unit,
    onStartDuel: () -> Unit,
    onRematchRival: (String) -> Unit
) {
    var userLiveScore by remember { mutableIntStateOf(47) }
    var adamSent by remember { mutableStateOf(false) }
    var sarahSent by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ArenaBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // TOP HERO BANNER: STREAK CALENDAR & MOTIVATION
        CurrentRhythmCard()

        // ACTIVE LIVE BATTLE CARD (PUSH-UPS · LIVE BATTLE)
        LiveBattleCard(
            userScore = userLiveScore,
            onContinueBattle = onContinueBattle
        )

        // TODAY'S DUEL CARD (DAILY QUEST)
        DailyQuestCard(
            onStartDuel = onStartDuel
        )

        // CHALLENGE RIVALS SECTION
        ChallengeRivalsSection(
            adamSent = adamSent,
            sarahSent = sarahSent,
            onAdamRematch = {
                adamSent = true
                onRematchRival("Adam Miller")
            },
            onSarahInvite = {
                sarahSent = true
                onRematchRival("Sarah Chen")
            }
        )

        // DYNAMIC HTML MEDIA: Dynamically parsed and rendered HTML images
        val arenaHtmlEmbed = """
            <div class="active-duel-capsule">
              <img src="${AthleteAssets.LOGO_URL}" alt="RepBattle Engine Logo" />
              <img src="${AthleteAssets.ADAM_AVATAR}" alt="Rival: Adam Miller" />
              <img src="${AthleteAssets.SARAH_AVATAR}" alt="Challenger: Sarah Chen" />
            </div>
        """.trimIndent()

        com.example.ui.components.DynamicHtmlImageRenderer(
            htmlContent = arenaHtmlEmbed,
            title = "Dynamic HTML Arena Feed",
            compact = true
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun CurrentRhythmCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(ArenaSurface1)
            .border(1.dp, ArenaBorder, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(TertiaryContainerOrange.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = "Rhythm",
                            tint = TertiaryOrange,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "CURRENT RHYTHM",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "4 DAY STREAK",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            color = TextLight,
                            letterSpacing = (-0.5).sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(SuccessNeon.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "+50 XP TODAY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryGreen,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // 7-Day Consistency Track (M, T, W, T, F, S, S)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DayNode(day = "M", isDone = true)
                DayNode(day = "T", isDone = true)
                DayNode(day = "W", isDone = true)
                DayNode(day = "T", isDone = false, isCurrentActive = true)
                DayNode(day = "F", isDone = false)
                DayNode(day = "S", isDone = false)
                DayNode(day = "S", isDone = false)
            }
        }
    }
}

@Composable
private fun DayNode(
    day: String,
    isDone: Boolean,
    isCurrentActive: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = day,
            fontSize = 11.sp,
            fontWeight = if (isCurrentActive) FontWeight.Black else FontWeight.Bold,
            color = if (isCurrentActive) AccentVioletLight else TextMuted
        )
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(
                    when {
                        isDone -> SecondaryContainerGreen
                        isCurrentActive -> AccentVioletContainer
                        else -> ArenaSurface2
                    }
                )
                .border(
                    1.dp,
                    if (isCurrentActive) AccentViolet else ArenaBorder,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            when {
                isDone -> {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completed",
                        tint = TextLight,
                        modifier = Modifier.size(18.dp)
                    )
                }
                isCurrentActive -> {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = "Today Active",
                        tint = TextLight,
                        modifier = Modifier.size(18.dp)
                    )
                }
                else -> {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(ArenaBorder)
                    )
                }
            }
        }
    }
}

@Composable
private fun LiveBattleCard(
    userScore: Int,
    onContinueBattle: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(ArenaSurface1)
            .border(1.dp, ArenaBorder, RoundedCornerShape(16.dp))
            .padding(16.dp)
            .testTag("home_live_battle_card")
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Status Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(DangerRed)
                    )
                    Text(
                        text = "PUSH-UPS · LIVE BATTLE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = TextLight,
                        letterSpacing = 0.5.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(SuccessNeon.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                        contentDescription = "Ahead",
                        tint = SecondaryGreen,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "+8 AHEAD",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = SecondaryGreen,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // Competitors Face-Off
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // User Card
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(ArenaSurface2)
                        .border(1.dp, AccentViolet.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(AccentVioletContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "ME",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = TextLight
                            )
                        }
                        Text(
                            text = "YOU",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextLight
                        )
                    }
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "$userScore",
                            style = BattleNumStyle.copy(fontSize = 52.sp, lineHeight = 52.sp),
                            color = AccentVioletLight
                        )
                        Text(
                            text = "REPS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                }

                // Opponent Card (Adam)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(ArenaSurfaceHigh)
                        .border(1.dp, ArenaBorder, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(ArenaBorder),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "AD",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = TextMuted
                            )
                        }
                        Text(
                            text = "ADAM",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted
                        )
                    }
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "39",
                            style = BattleNumStyle.copy(fontSize = 52.sp, lineHeight = 52.sp),
                            color = TextMuted
                        )
                        Text(
                            text = "REPS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted.copy(alpha = 0.6f),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                }
            }

            // Differential Progress Meter
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(ArenaSurfaceHigh)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.55f)
                            .height(8.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(AccentViolet, AccentVioletContainer)
                                )
                            )
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(8.dp)
                            .background(ArenaBorder)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Target: First to 60 Reps",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextMuted
                    )
                    Text(
                        text = "Round ends in 02:40",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextMuted
                    )
                }
            }

            // Action Button: CONTINUE BATTLE
            Button(
                onClick = onContinueBattle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_continue_battle"),
                shape = RoundedCornerShape(999.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentVioletContainer,
                    contentColor = TextLight
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(TextLight)
                    )
                    Text(
                        text = "CONTINUE BATTLE",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Forward",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun DailyQuestCard(
    onStartDuel: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(ArenaSurface2)
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
                        imageVector = Icons.Default.SportsKabaddi,
                        contentDescription = "Trophy",
                        tint = SecondaryGreen,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "DAILY QUEST",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = SecondaryGreen,
                        letterSpacing = 1.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(TertiaryContainerOrange.copy(alpha = 0.25f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "Expires in 8h",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TertiaryOrange
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "50 Squats Duel",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        color = TextLight,
                        letterSpacing = (-0.5).sp
                    )
                    Text(
                        text = "Form tracking locked at 90° parallel.",
                        fontSize = 13.sp,
                        color = TextMuted,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(ArenaSurfaceContainer)
                        .border(1.dp, ArenaBorder, RoundedCornerShape(12.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "+150",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black,
                        color = TertiaryOrange
                    )
                    Text(
                        text = "POINTS",
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted
                    )
                }
            }

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
                        imageVector = Icons.Default.Verified,
                        contentDescription = "Verified",
                        tint = TextMuted,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Keeps 4-day streak intact",
                        fontSize = 12.sp,
                        color = TextMuted,
                        fontWeight = FontWeight.Medium
                    )
                }

                Button(
                    onClick = onStartDuel,
                    modifier = Modifier
                        .height(40.dp)
                        .testTag("btn_start_daily_duel"),
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SecondaryGreen,
                        contentColor = ArenaBackground
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "START",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        )
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = "Bolt",
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChallengeRivalsSection(
    adamSent: Boolean,
    sarahSent: Boolean,
    onAdamRematch: () -> Unit,
    onSarahInvite: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Challenge Rivals",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = TextLight,
                letterSpacing = (-0.3).sp
            )
            Text(
                text = "VIEW ALL (6)",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = AccentVioletLight,
                letterSpacing = 0.5.sp
            )
        }

        // Adam Miller Card
        RivalCard(
            name = "Adam Miller",
            subtitle = "Last duel: Yesterday (1-1)",
            avatarUrl = AthleteAssets.ADAM_AVATAR,
            actionLabel = if (adamSent) "SENT" else "REMATCH",
            actionSent = adamSent,
            icon = Icons.Default.SportsKabaddi,
            iconTint = AccentViolet,
            testTag = "rival_adam_btn",
            onClick = onAdamRematch
        )

        // Sarah Chen Card
        RivalCard(
            name = "Sarah Chen",
            subtitle = "Ready for Pull-Ups",
            avatarUrl = AthleteAssets.SARAH_AVATAR,
            actionLabel = if (sarahSent) "SENT" else "INVITE",
            actionSent = sarahSent,
            icon = Icons.Default.SportsMartialArts,
            iconTint = SecondaryGreen,
            testTag = "rival_sarah_btn",
            onClick = onSarahInvite
        )
    }
}

@Composable
private fun RivalCard(
    name: String,
    subtitle: String,
    avatarUrl: String,
    actionLabel: String,
    actionSent: Boolean,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    testTag: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(ArenaSurface1)
            .border(1.dp, ArenaBorder, RoundedCornerShape(14.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(modifier = Modifier.size(44.dp)) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .border(1.dp, ArenaBorder, CircleShape)
                ) {
                    AsyncImage(
                        model = avatarUrl,
                        contentDescription = name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(44.dp)
                    )
                }
                // Online dot
                Box(
                    modifier = Modifier
                        .size(11.dp)
                        .clip(CircleShape)
                        .background(SecondaryGreen)
                        .border(2.dp, ArenaSurface1, CircleShape)
                        .align(Alignment.BottomEnd)
                )
            }

            Column {
                Text(
                    text = name,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextLight
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }
        }

        Button(
            onClick = onClick,
            modifier = Modifier
                .height(36.dp)
                .testTag(testTag),
            shape = RoundedCornerShape(999.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (actionSent) ArenaSurfaceContainer else ArenaSurfaceHigh,
                contentColor = if (actionSent) SecondaryGreen else TextLight
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = if (actionSent) Icons.Default.Check else icon,
                    contentDescription = null,
                    tint = if (actionSent) SecondaryGreen else iconTint,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = actionLabel,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
