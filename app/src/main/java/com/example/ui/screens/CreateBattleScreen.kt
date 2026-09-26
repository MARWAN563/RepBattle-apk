package com.example.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DownhillSkiing
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material.icons.filled.SportsKabaddi
import androidx.compose.material.icons.filled.Timer3
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.model.ExerciseType
import com.example.data.model.RuleType
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
import com.example.ui.theme.SecondaryGreen
import com.example.ui.theme.SuccessNeon
import com.example.ui.theme.TextLight
import com.example.ui.theme.TextMuted

@Composable
fun CreateBattleScreen(
    onInitiateBattle: (ExerciseType, RuleType, Boolean, Boolean) -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current

    var selectedExercise by remember { mutableStateOf(ExerciseType.PUSH_UPS) }
    var selectedRule by remember { mutableStateOf(RuleType.FIRST_TO_50) }
    var strictFormLock by remember { mutableStateOf(true) }
    var liveGhostMode by remember { mutableStateOf(true) }
    var linkCopied by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ArenaBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Step Tracker Header
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
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
                            .clip(RoundedCornerShape(999.dp))
                            .background(AccentViolet.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "SETUP ARENA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = AccentVioletLight,
                            letterSpacing = 0.5.sp
                        )
                    }
                    Text(
                        text = "STEP 1 OF 3",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextMuted,
                        letterSpacing = 0.5.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Sensors,
                        contentDescription = "Sensors",
                        tint = SecondaryGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "AI TRACKER READY",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryGreen,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // Step Progress Bars (3 segments)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(5.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(AccentViolet)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(5.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(ArenaSurfaceHigh)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(5.dp)
                        .clip(RoundedCornerShape(999.dp))
                        .background(ArenaSurfaceHigh)
                )
            }

            Text(
                text = "Create Battle ⚔",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = TextLight,
                letterSpacing = (-0.5).sp,
                modifier = Modifier.padding(top = 6.dp)
            )
            Text(
                text = "Select movement, competitive parameters, and validation constraints.",
                fontSize = 14.sp,
                color = TextMuted
            )
        }

        // 1. SELECT EXERCISE
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
                    Icon(
                        imageVector = Icons.Default.FitnessCenter,
                        contentDescription = null,
                        tint = AccentViolet,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "1. SELECT EXERCISE",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextLight,
                        letterSpacing = 0.5.sp
                    )
                }
                Text(
                    text = "Pose Model v2.4",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }

            ExerciseSelectionCard(
                title = "Push-ups",
                subtitle = "Chest & Triceps · Camera Verified",
                detail = "100% Rec",
                icon = Icons.Default.SportsGymnastics,
                isSelected = selectedExercise == ExerciseType.PUSH_UPS,
                onClick = { selectedExercise = ExerciseType.PUSH_UPS },
                testTag = "exercise_pushups"
            )

            ExerciseSelectionCard(
                title = "Deep Squats",
                subtitle = "Legs & Glutes · Depth Tracking 90°",
                detail = "Hip > Knee",
                icon = Icons.Default.AccessibilityNew,
                isSelected = selectedExercise == ExerciseType.DEEP_SQUATS,
                onClick = { selectedExercise = ExerciseType.DEEP_SQUATS },
                testTag = "exercise_squats"
            )

            ExerciseSelectionCard(
                title = "Pull-ups",
                subtitle = "Back & Biceps · Bar Detection",
                detail = "Chin Clear",
                icon = Icons.Default.DirectionsRun,
                isSelected = selectedExercise == ExerciseType.PULL_UPS,
                onClick = { selectedExercise = ExerciseType.PULL_UPS },
                testTag = "exercise_pullups"
            )

            ExerciseSelectionCard(
                title = "Walking Lunges",
                subtitle = "Legs · Stride & Knee Angle",
                detail = "Stride Lock",
                icon = Icons.Default.DownhillSkiing,
                isSelected = selectedExercise == ExerciseType.WALKING_LUNGES,
                onClick = { selectedExercise = ExerciseType.WALKING_LUNGES },
                testTag = "exercise_lunges"
            )
        }

        // 2. BATTLE VICTORY RULE
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
                    Icon(
                        imageVector = Icons.Default.Gavel,
                        contentDescription = null,
                        tint = AccentViolet,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "2. BATTLE VICTORY RULE",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextLight,
                        letterSpacing = 0.5.sp
                    )
                }
                Text(
                    text = "Paced Duel",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentVioletLight
                )
            }

            // 2x2 Grid of Victory Rules
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                RuleSelectionBox(
                    modifier = Modifier.weight(1f),
                    title = "First to 50",
                    subtitle = "Pace sprint · Locked camera",
                    icon = Icons.Default.Timer3,
                    isSelected = selectedRule == RuleType.FIRST_TO_50,
                    onClick = { selectedRule = RuleType.FIRST_TO_50 },
                    testTag = "rule_first_50"
                )
                RuleSelectionBox(
                    modifier = Modifier.weight(1f),
                    title = "Most in 5 Min",
                    subtitle = "Endurance grit duel",
                    icon = Icons.Default.HourglassTop,
                    isSelected = selectedRule == RuleType.MOST_IN_5_MIN,
                    onClick = { selectedRule = RuleType.MOST_IN_5_MIN },
                    testTag = "rule_5_min"
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                RuleSelectionBox(
                    modifier = Modifier.weight(1f),
                    title = "Century (100)",
                    subtitle = "The ultimate stamina test",
                    icon = Icons.Default.LocalFireDepartment,
                    isSelected = selectedRule == RuleType.CENTURY_100,
                    onClick = { selectedRule = RuleType.CENTURY_100 },
                    testTag = "rule_century_100"
                )
                RuleSelectionBox(
                    modifier = Modifier.weight(1f),
                    title = "Daily Duel",
                    subtitle = "Sync streak defender",
                    icon = Icons.Default.SportsKabaddi,
                    isSelected = selectedRule == RuleType.DAILY_DUEL,
                    onClick = { selectedRule = RuleType.DAILY_DUEL },
                    testTag = "rule_daily_duel"
                )
            }
        }

        // 3. STRICTNESS & HUD SETTINGS
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
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        tint = AccentViolet,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "3. STRICTNESS & HUD SETTINGS",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextLight,
                        letterSpacing = 0.5.sp
                    )
                }
                Text(
                    text = "ANTICHEAT ENGAGED",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = SecondaryGreen,
                    letterSpacing = 0.5.sp
                )
            }

            // Strict Form Lock Toggle Card
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
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(DangerRed.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = null,
                            tint = DangerRed,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Strict Form Lock",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextLight
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(DangerRed.copy(alpha = 0.25f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Hard",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DangerRed
                                )
                            }
                        }
                        Text(
                            text = "Only exact 90° elbow depth reps are verified. Half reps score 0.",
                            fontSize = 12.sp,
                            color = TextMuted,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                Switch(
                    checked = strictFormLock,
                    onCheckedChange = { strictFormLock = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = TextLight,
                        checkedTrackColor = AccentViolet,
                        uncheckedTrackColor = ArenaSurfaceHigh
                    ),
                    modifier = Modifier.testTag("toggle_strict_form")
                )
            }

            // Live Ghost Projection Toggle Card
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
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(AccentViolet.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ViewInAr,
                            contentDescription = null,
                            tint = AccentViolet,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Live Ghost Projection",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextLight
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(AccentViolet.copy(alpha = 0.25f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "Realtime",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AccentVioletLight
                                )
                            }
                        }
                        Text(
                            text = "Project opponent's live wireframe skeleton alongside your screen.",
                            fontSize = 12.sp,
                            color = TextMuted,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                Switch(
                    checked = liveGhostMode,
                    onCheckedChange = { liveGhostMode = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = TextLight,
                        checkedTrackColor = AccentViolet,
                        uncheckedTrackColor = ArenaSurfaceHigh
                    ),
                    modifier = Modifier.testTag("toggle_ghost_mode")
                )
            }
        }

        // 4. TARGET OPPONENT
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
                    Icon(
                        imageVector = Icons.Default.PersonSearch,
                        contentDescription = null,
                        tint = AccentViolet,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "4. TARGET OPPONENT",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextLight,
                        letterSpacing = 0.5.sp
                    )
                }
                Text(
                    text = "Instant Dispatch",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }

            // Adam Miller Selected Opponent Card
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
                                model = AthleteAssets.ADAM_AVATAR,
                                contentDescription = "Adam Miller",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(44.dp)
                            )
                        }
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Adam Miller",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextLight
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(ArenaSurfaceHigh)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "#14 Global",
                                    fontSize = 10.sp,
                                    color = TextMuted
                                )
                            }
                        }
                        Text(
                            text = "Record: 1 Win · 1 Loss (Rivalry Due)",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(AccentViolet.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = "SELECTED",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentVioletLight,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // Shareable URL Box
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(ArenaSurfaceContainer)
                    .border(1.dp, ArenaBorder, RoundedCornerShape(12.dp))
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(ArenaSurfaceHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Or invite anyone via URL:",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                        Text(
                            text = "repbattle.app/c/new?mode=50p",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AccentVioletLight,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                IconButton(
                    onClick = {
                        clipboardManager.setText(AnnotatedString("https://repbattle.app/c/new?mode=50p"))
                        linkCopied = true
                        Toast.makeText(context, "Challenge link copied!", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.testTag("btn_copy_challenge_link")
                ) {
                    Icon(
                        imageVector = if (linkCopied) Icons.Default.Check else Icons.Default.ContentCopy,
                        contentDescription = "Copy Link",
                        tint = if (linkCopied) SecondaryGreen else TextMuted
                    )
                }
            }
        }

        // BOTTOM ACTION: INITIATE BATTLE ARENA
        Column(
            modifier = Modifier.padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    onInitiateBattle(
                        selectedExercise,
                        selectedRule,
                        strictFormLock,
                        liveGhostMode
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("btn_initiate_battle"),
                shape = RoundedCornerShape(999.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentViolet,
                    contentColor = TextLight
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Initiate Battle Arena",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Forward",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Videocam,
                    contentDescription = null,
                    tint = SecondaryGreen,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = "Front camera calibrates immediately upon tapping initiate",
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
private fun ExerciseSelectionCard(
    title: String,
    subtitle: String,
    detail: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(if (isSelected) ArenaSurface2 else ArenaSurface1)
            .border(
                1.5.dp,
                if (isSelected) AccentViolet else ArenaBorder,
                RoundedCornerShape(14.dp)
            )
            .clickable { onClick() }
            .padding(14.dp)
            .testTag(testTag),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) ArenaSurface1 else ArenaSurfaceHigh),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = if (isSelected) AccentViolet else TextMuted,
                    modifier = Modifier.size(26.dp)
                )
            }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextLight
                    )
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(AccentViolet)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "ACTIVE",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                color = TextLight
                            )
                        }
                    }
                }
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.CheckCircle,
                contentDescription = null,
                tint = if (isSelected) AccentViolet else ArenaBorder,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = detail,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) SecondaryGreen else TextMuted
            )
        }
    }
}

@Composable
private fun RuleSelectionBox(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(if (isSelected) ArenaSurface2 else ArenaSurface1)
            .border(
                1.5.dp,
                if (isSelected) AccentViolet else ArenaBorder,
                RoundedCornerShape(14.dp)
            )
            .clickable { onClick() }
            .padding(14.dp)
            .testTag(testTag),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) AccentViolet.copy(alpha = 0.2f) else ArenaSurfaceHigh),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) AccentViolet else TextMuted,
                    modifier = Modifier.size(18.dp)
                )
            }

            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = if (isSelected) AccentViolet else ArenaBorder,
                modifier = Modifier.size(20.dp)
            )
        }

        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextLight
            )
            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = TextMuted,
                modifier = Modifier.padding(top = 2.dp),
                lineHeight = 14.sp
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(999.dp))
                .background(if (isSelected) AccentViolet else ArenaSurfaceHigh)
        )
    }
}
