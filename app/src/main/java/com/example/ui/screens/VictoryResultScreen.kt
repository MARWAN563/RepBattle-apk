package com.example.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
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
import com.example.ui.theme.BattleNumStyle
import com.example.ui.theme.SecondaryContainerGreen
import com.example.ui.theme.SecondaryGreen
import com.example.ui.theme.SuccessNeon
import com.example.ui.theme.TertiaryOrange
import com.example.ui.theme.TextLight
import com.example.ui.theme.TextMuted

@Composable
fun VictoryResultScreen(
    userScore: Int = 47,
    opponentScore: Int = 39,
    exerciseName: String = "Push-ups",
    onRematchClick: () -> Unit,
    onNewBattleClick: () -> Unit
) {
    val context = LocalContext.current
    val isWin = userScore >= opponentScore
    val scoreDelta = userScore - opponentScore

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ArenaBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Status Pill: BATTLE COMPLETE
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(ArenaSurfaceHigh)
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(SuccessNeon)
                )
                Text(
                    text = "BATTLE COMPLETE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    letterSpacing = 1.sp
                )
            }
        }

        // Hero Victory Announcement Badge
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(ArenaSurface2)
                .border(1.dp, ArenaBorder, RoundedCornerShape(999.dp))
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MilitaryTech,
                    contentDescription = null,
                    tint = TertiaryOrange,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = if (isWin) "YOU WON" else "DUEL FINISHED",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Black,
                    color = TextLight,
                    letterSpacing = 1.sp
                )
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = AccentVioletLight,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Competitors Face-Off & Huge Score
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(ArenaSurfaceContainer)
                .border(1.dp, ArenaBorder, RoundedCornerShape(18.dp))
                .padding(18.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Header with Competitor Avatars
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // You / Winner
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(modifier = Modifier.size(42.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, AccentViolet, CircleShape)
                            ) {
                                AsyncImage(
                                    model = AthleteAssets.MAROUANE_AVATAR,
                                    contentDescription = "You",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(42.dp)
                                )
                            }
                            if (isWin) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(SecondaryGreen)
                                        .align(Alignment.BottomEnd),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "W",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = ArenaBackground
                                    )
                                }
                            }
                        }

                        Column {
                            Text(
                                text = "YOU",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextLight
                            )
                            Text(
                                text = if (isWin) "WINNER" else "COMPLETED",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SecondaryGreen
                            )
                        }
                    }

                    Text(
                        text = "VS",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted
                    )

                    // Adam / Opponent
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "ADAM",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextLight
                            )
                            Text(
                                text = "Rank #14",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .border(1.dp, ArenaBorder, CircleShape)
                        ) {
                            AsyncImage(
                                model = AthleteAssets.ADAM_AVATAR,
                                contentDescription = "Adam",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(42.dp)
                            )
                        }
                    }
                }

                // Giant Score Split: 47 — 39
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "$userScore",
                        style = BattleNumStyle.copy(fontSize = 68.sp, lineHeight = 68.sp),
                        color = AccentVioletLight
                    )
                    Text(
                        text = "—",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Light,
                        color = TextMuted
                    )
                    Text(
                        text = "$opponentScore",
                        style = BattleNumStyle.copy(fontSize = 68.sp, lineHeight = 68.sp),
                        color = TextMuted.copy(alpha = 0.75f)
                    )
                }

                // Delta pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(SuccessNeon.copy(alpha = 0.15f))
                        .padding(horizontal = 14.dp, vertical = 5.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.TrendingUp,
                            contentDescription = null,
                            tint = SecondaryGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "+$scoreDelta REPS AHEAD",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Black,
                            color = SecondaryGreen,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                // Verified pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(ArenaSurface2)
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = SecondaryGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "50 $exerciseName · CAMERA VERIFIED",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextLight,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }

        // Match Highlights Strip (Pace, Form Score, Win Streak)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(ArenaSurface1)
                    .border(1.dp, ArenaBorder, RoundedCornerShape(12.dp))
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "PACE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                Text(text = "1.2s / rep", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextLight)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(ArenaSurface1)
                    .border(1.dp, ArenaBorder, RoundedCornerShape(12.dp))
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "FORM SCORE", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                Text(text = "98% Clean", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SecondaryGreen)
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(ArenaSurface1)
                    .border(1.dp, ArenaBorder, RoundedCornerShape(12.dp))
                    .padding(vertical = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "WIN STREAK", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextMuted)
                Text(text = "5 Days 🔥", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TertiaryOrange)
            }
        }

        // SQUARE 1:1 VIRAL SHARE CARD PREVIEW
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SHARE CARD PREVIEW",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    letterSpacing = 1.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = AccentVioletLight,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "Instant Brag",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentVioletLight
                    )
                }
            }

            // 1:1 Aspect Ratio Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(18.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(ArenaSurface2, ArenaSurfaceHigh, ArenaBackground)
                        )
                    )
                    .border(1.dp, ArenaBorder, RoundedCornerShape(18.dp))
                    .padding(18.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top Card Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "REPBATTLE",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Black,
                                color = TextLight
                            )
                            Box(
                                modifier = Modifier
                                    .size(4.dp)
                                    .clip(CircleShape)
                                    .background(TextMuted)
                            )
                            Text(
                                text = "VICTORY",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = AccentVioletLight
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(SuccessNeon.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "BATTLE WON",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black,
                                color = SecondaryGreen
                            )
                        }
                    }

                    // Center Final Tally
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "FINAL TALLY",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted,
                            letterSpacing = 1.sp
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "YOU",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = SecondaryGreen
                                )
                                Text(
                                    text = "$userScore",
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Black,
                                    color = TextLight
                                )
                            }
                            Text(
                                text = "—",
                                fontSize = 24.sp,
                                color = TextMuted
                            )
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "ADAM",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextMuted
                                )
                                Text(
                                    text = "$opponentScore",
                                    fontSize = 36.sp,
                                    fontWeight = FontWeight.Black,
                                    color = TextMuted
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(ArenaSurface1)
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = SecondaryGreen,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = "PUSH-UPS VERIFIED",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextLight
                                )
                            }
                        }
                    }

                    // Card Bottom CTA
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(ArenaSurface1.copy(alpha = 0.9f))
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "BEAT MY SCORE",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Black,
                                color = TextLight
                            )
                            Text(
                                text = "repbattle.app/b/8X29",
                                fontSize = 12.sp,
                                color = AccentVioletLight,
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(AccentVioletContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = TextLight,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }

        // DYNAMICALLY LINKED HTML BRAG CARD
        val dynamicHtmlSnippet = """
            <div class="repbattle-share-card">
              <img src="${AthleteAssets.LOGO_URL}" alt="RepBattle Official Emblem" />
              <img src="${AthleteAssets.MAROUANE_AVATAR}" alt="Winner: Athlete Marouane" />
            </div>
        """.trimIndent()

        com.example.ui.components.DynamicHtmlImageRenderer(
            htmlContent = dynamicHtmlSnippet,
            title = "Dynamic HTML Web Embed Assets",
            compact = false
        )

        // Action Buttons Stack (Hierarchy)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // PRIMARY: Share Result
            Button(
                onClick = {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "BATTLE WON! I crushed $userScore verified Push-ups against Adam ($opponentScore). Think you can beat me? https://repbattle.app/b/8X29"
                        )
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, "Share RepBattle Victory")
                    context.startActivity(shareIntent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_share_result"),
                shape = RoundedCornerShape(999.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentVioletContainer,
                    contentColor = TextLight
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "SHARE RESULT",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // SECONDARY: Rematch
            Button(
                onClick = onRematchClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_rematch"),
                shape = RoundedCornerShape(999.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ArenaSurface2,
                    contentColor = TextLight
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, ArenaBorder)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Rematch",
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "REMATCH",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // TERTIARY: New Battle
            TextButton(
                onClick = onNewBattleClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("btn_new_battle")
            ) {
                Text(
                    text = "NEW BATTLE",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    letterSpacing = 0.5.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}
