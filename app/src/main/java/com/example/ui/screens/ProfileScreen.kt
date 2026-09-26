package com.example.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SlowMotionVideo
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.local.BattleEntity
import com.example.data.repository.BattleRepository
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
import com.example.ui.theme.DangerRed
import com.example.ui.theme.SecondaryContainerGreen
import com.example.ui.theme.SecondaryGreen
import com.example.ui.theme.SuccessNeon
import com.example.ui.theme.TertiaryContainerOrange
import com.example.ui.theme.TertiaryOrange
import com.example.ui.theme.TextLight
import com.example.ui.theme.TextMuted

enum class BattleFilter {
    ALL,
    WINS,
    LOSSES
}

@Composable
fun ProfileScreen(
    repository: BattleRepository,
    onRematchBattle: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedFilter by remember { mutableStateOf(BattleFilter.ALL) }
    var showProDialog by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }

    val allBattles by repository.allBattles.collectAsState(initial = emptyList())
    val wonBattles by repository.wonBattles.collectAsState(initial = emptyList())
    val lostBattles by repository.lostBattles.collectAsState(initial = emptyList())

    val displayedBattles = when (selectedFilter) {
        BattleFilter.ALL -> allBattles
        BattleFilter.WINS -> wonBattles
        BattleFilter.LOSSES -> lostBattles
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ArenaBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // PROFILE HEADER HERO
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(ArenaSurfaceContainer)
                .border(1.dp, ArenaBorder, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    // Avatar with verified badge
                    Box(modifier = Modifier.size(70.dp)) {
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .border(2.dp, AccentViolet, CircleShape)
                        ) {
                            AsyncImage(
                                model = AthleteAssets.MAROUANE_AVATAR,
                                contentDescription = "Marouane",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(70.dp)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .clip(CircleShape)
                                .background(SecondaryGreen)
                                .border(2.dp, ArenaSurfaceContainer, CircleShape)
                                .align(Alignment.BottomEnd),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verified",
                                tint = ArenaBackground,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "MAROUANE",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = TextLight,
                            letterSpacing = (-0.5).sp
                        )
                        Text(
                            text = "@marouane_fit",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(AccentVioletContainer)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.MilitaryTech,
                                        contentDescription = null,
                                        tint = TextLight,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Text(
                                        text = "PRO MEMBER",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TextLight
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(ArenaSurface2)
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Bolt,
                                        contentDescription = null,
                                        tint = TertiaryOrange,
                                        modifier = Modifier.size(12.dp)
                                    )
                                    Text(
                                        text = "Lvl 14",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = TertiaryOrange
                                    )
                                }
                            }
                        }
                    }
                }

                IconButton(
                    onClick = { showSettingsDialog = true },
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(ArenaSurfaceHigh)
                        .testTag("btn_profile_settings")
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Settings",
                        tint = TextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // CORE STAT GRID (2x2)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Streak Card
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(ArenaSurfaceContainer)
                    .border(1.dp, ArenaBorder, RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ACTIVE STREAK",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted,
                            letterSpacing = 0.5.sp
                        )
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = null,
                            tint = TertiaryOrange,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "4",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            color = TextLight
                        )
                        Text(
                            text = "DAYS",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TertiaryOrange,
                            modifier = Modifier.padding(bottom = 3.dp)
                        )
                    }

                    // Mini consistency nodes
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(ArenaSurfaceHigh)
                            )
                        }
                        repeat(3) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(SecondaryGreen)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .clip(CircleShape)
                                .background(AccentViolet),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = TextLight,
                                modifier = Modifier.size(8.dp)
                            )
                        }
                    }
                }
            }

            // Battles Card
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(ArenaSurfaceContainer)
                    .border(1.dp, ArenaBorder, RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "BATTLES",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted,
                            letterSpacing = 0.5.sp
                        )
                        Icon(
                            imageVector = Icons.Default.MilitaryTech,
                            contentDescription = null,
                            tint = AccentViolet,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "18",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            color = TextLight
                        )
                        Text(
                            text = "61% WR",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = SecondaryGreen,
                            modifier = Modifier.padding(bottom = 3.dp)
                        )
                    }

                    Text(
                        text = "11W • 7L",
                        fontSize = 11.sp,
                        color = TextMuted
                    )

                    // Win Ratio bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(ArenaSurfaceHigh)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.61f)
                                .height(4.dp)
                                .background(SecondaryGreen)
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Total Verified Reps
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(ArenaSurfaceContainer)
                    .border(1.dp, ArenaBorder, RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "VERIFIED REPS",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted,
                            letterSpacing = 0.5.sp
                        )
                        Icon(
                            imageVector = Icons.Default.TaskAlt,
                            contentDescription = null,
                            tint = SecondaryGreen,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Text(
                        text = "1,420",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        color = TextLight
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = null,
                            tint = AccentVioletLight,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "Vision AI Tracked",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }
            }

            // Best Record
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(ArenaSurfaceContainer)
                    .border(1.dp, ArenaBorder, RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "BEST RECORD",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted,
                            letterSpacing = 0.5.sp
                        )
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = null,
                            tint = TertiaryOrange,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "72",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            color = TextLight
                        )
                        Text(
                            text = "PUSH-UPS",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted,
                            modifier = Modifier.padding(bottom = 3.dp)
                        )
                    }

                    Text(
                        text = "99% Clean Strict",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SecondaryGreen
                    )
                }
            }
        }

        // PERFORMANCE & FORM SCORE BREAKDOWN
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(ArenaSurfaceContainer)
                .border(1.dp, ArenaBorder, RoundedCornerShape(14.dp))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "KINETIC FORM SCORE",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextLight,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Pose Landmark Analysis",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "96.4%",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            color = SecondaryGreen
                        )
                        Text(
                            text = "Precision Avg",
                            fontSize = 10.sp,
                            color = TextMuted
                        )
                    }
                }

                // Favorite Discipline split
                Text(
                    text = "Favorite Discipline: Push-ups (62% of battles)",
                    fontSize = 12.sp,
                    color = TextMuted
                )

                // Tri-color distribution bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(ArenaSurfaceHigh)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.62f)
                            .height(6.dp)
                            .background(AccentViolet)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.68f)
                            .height(6.dp)
                            .background(SecondaryGreen)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .background(TertiaryOrange)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LegendItem(color = AccentViolet, label = "Push-ups (62%)")
                    LegendItem(color = SecondaryGreen, label = "Squats (26%)")
                    LegendItem(color = TertiaryOrange, label = "Pull-ups (12%)")
                }
            }
        }

        // REPBATTLE PRO TEASER BANNER
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
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "REPBATTLE",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Black,
                                color = TextLight
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(AccentVioletContainer)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "PRO",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Black,
                                    color = TextLight
                                )
                            }
                        }

                        Text(
                            text = "Level up with unlimited ghost duels, customized battle rules & frame-by-frame deep CV biomechanics.",
                            fontSize = 12.sp,
                            color = TextMuted,
                            lineHeight = 16.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(AccentVioletContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ElectricBolt,
                            contentDescription = null,
                            tint = TextLight,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Button(
                    onClick = { showProDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("btn_explore_pro"),
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentVioletContainer,
                        contentColor = TextLight
                    )
                ) {
                    Text(
                        text = "EXPLORE PRO ARSENAL",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }
        }

        // BATTLE LOG (MATCH HISTORY SECTION)
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
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
                        text = "BATTLE LOG",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextLight,
                        letterSpacing = 0.5.sp
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(999.dp))
                            .background(ArenaSurfaceHigh)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "${displayedBattles.size}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted
                        )
                    }
                }

                Text(
                    text = "Export CSV ↗",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AccentVioletLight,
                    modifier = Modifier.clickable {
                        Toast.makeText(context, "Exporting 18 battles to CSV...", Toast.LENGTH_SHORT).show()
                    }
                )
            }

            // Filter Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChipItem(
                    label = "All (${allBattles.size})",
                    isSelected = selectedFilter == BattleFilter.ALL,
                    onClick = { selectedFilter = BattleFilter.ALL }
                )
                FilterChipItem(
                    label = "Wins (${wonBattles.size})",
                    isSelected = selectedFilter == BattleFilter.WINS,
                    onClick = { selectedFilter = BattleFilter.WINS }
                )
                FilterChipItem(
                    label = "Losses (${lostBattles.size})",
                    isSelected = selectedFilter == BattleFilter.LOSSES,
                    onClick = { selectedFilter = BattleFilter.LOSSES }
                )
            }

            // List of Battle Cards
            displayedBattles.forEach { battle ->
                BattleLogCard(
                    battle = battle,
                    onShareClick = {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "RepBattle Duel Result: ${if (battle.isWin) "WIN" else "DUEL"} ${battle.userScore} vs ${battle.opponentScore} in ${battle.exerciseName}! https://repbattle.app/b/${battle.shareCode}"
                            )
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendIntent, "Share Result"))
                    },
                    onRematchClick = {
                        onRematchBattle(battle.opponentName)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }

    // PRO Perks Dialog
    if (showProDialog) {
        AlertDialog(
            onDismissRequest = { showProDialog = false },
            containerColor = ArenaSurface1,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = Icons.Default.ElectricBolt, contentDescription = null, tint = AccentVioletLight)
                    Text("RepBattle PRO Arsenal", color = TextLight, fontWeight = FontWeight.Black)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    ProPerkItem("Unlimited Ghost Duels with Global Top 50")
                    ProPerkItem("Frame-by-frame joint angle & depth analysis")
                    ProPerkItem("Instant exportable 4K brag video clips")
                    ProPerkItem("Custom battle wagering rules & Century modes")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showProDialog = false
                        Toast.makeText(context, "Pro activated for athlete Marouane!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentViolet)
                ) {
                    Text("Unlock ($4.99/mo)", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showProDialog = false }) {
                    Text("Close", color = TextMuted)
                }
            }
        )
    }

    // Settings Dialog
    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            containerColor = ArenaSurface1,
            title = {
                Text("Athlete Preferences", color = TextLight, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Connected: Google Fit & Health Connect", color = TextMuted, fontSize = 13.sp)
                    Text("Vision Model: MediaPipe BlazePose v2.4 (High Accuracy)", color = TextMuted, fontSize = 13.sp)
                    Text("Anticheat Engine: Active (Zero half-reps)", color = SecondaryGreen, fontSize = 13.sp)
                }
            },
            confirmButton = {
                Button(
                    onClick = { showSettingsDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentViolet)
                ) {
                    Text("Done")
                }
            }
        )
    }
}

@Composable
private fun LegendItem(color: Color, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(text = label, fontSize = 10.sp, color = TextMuted)
    }
}

@Composable
private fun FilterChipItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(if (isSelected) AccentViolet else ArenaSurfaceContainer)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 6.dp)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
            color = if (isSelected) TextLight else TextMuted
        )
    }
}

@Composable
private fun BattleLogCard(
    battle: BattleEntity,
    onShareClick: () -> Unit,
    onRematchClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(ArenaSurfaceContainer)
            .border(1.dp, ArenaBorder, RoundedCornerShape(14.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            // Header Row: Status badge & timestamp
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
                            .clip(RoundedCornerShape(999.dp))
                            .background(
                                if (battle.isWin) SuccessNeon.copy(alpha = 0.2f) else DangerRed.copy(alpha = 0.2f)
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (battle.isWin) "WIN 🏆" else "LOSS",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = if (battle.isWin) SecondaryGreen else DangerRed
                        )
                    }
                    Text(
                        text = battle.timestampFormatted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = null,
                        tint = SecondaryGreen,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = "Form ${battle.formScorePercent}%",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryGreen
                    )
                }
            }

            // Score and Competitors Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User Side
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .border(1.dp, AccentViolet, CircleShape)
                    ) {
                        AsyncImage(
                            model = AthleteAssets.MAROUANE_AVATAR,
                            contentDescription = "You",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                    Column {
                        Text(text = "You", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextLight)
                        Text(text = battle.exerciseName, fontSize = 11.sp, color = TextMuted)
                    }
                }

                // Scores Arena Display
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "${battle.userScore}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = if (battle.isWin) SecondaryGreen else DangerRed
                    )
                    Text(text = "—", fontSize = 14.sp, color = TextMuted, fontWeight = FontWeight.Bold)
                    Text(
                        text = "${battle.opponentScore}",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = TextMuted
                    )
                }

                // Opponent Side
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = battle.opponentName, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextLight)
                        Text(text = battle.opponentHandle, fontSize = 11.sp, color = TextMuted)
                    }
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(ArenaSurfaceHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        if (battle.opponentAvatarUrl.isNotEmpty()) {
                            AsyncImage(
                                model = battle.opponentAvatarUrl,
                                contentDescription = battle.opponentName,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(34.dp)
                            )
                        } else {
                            Text(
                                text = battle.opponentInitials,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextMuted
                            )
                        }
                    }
                }
            }

            // Actions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onShareClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(34.dp),
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ArenaSurface2,
                        contentColor = TextLight
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Share",
                            tint = AccentVioletLight,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(text = "SHARE CARD", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Button(
                    onClick = onRematchClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(34.dp),
                    shape = RoundedCornerShape(999.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AccentVioletContainer,
                        contentColor = TextLight
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Rematch",
                            modifier = Modifier.size(14.dp)
                        )
                        Text(text = "REMATCH", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun ProPerkItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = SecondaryGreen, modifier = Modifier.size(16.dp))
        Text(text = text, color = TextLight, fontSize = 13.sp)
    }
}
