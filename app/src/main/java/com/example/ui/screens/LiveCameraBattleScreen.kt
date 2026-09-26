package com.example.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
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
import kotlinx.coroutines.delay

@Composable
fun LiveCameraBattleScreen(
    exerciseType: ExerciseType = ExerciseType.PUSH_UPS,
    ruleType: RuleType = RuleType.FIRST_TO_50,
    strictForm: Boolean = true,
    ghostEnabled: Boolean = true,
    onEndSet: (userScore: Int, opponentScore: Int, formScore: Int, pace: Float) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Workout telemetry state
    var userScore by remember { mutableIntStateOf(47) }
    var opponentScore by remember { mutableIntStateOf(39) }
    var secondsElapsed by remember { mutableLongStateOf(38L) }
    var isAudioMuted by remember { mutableStateOf(false) }
    var showRepFlash by remember { mutableStateOf(false) }
    var currentDepthPercent by remember { mutableFloatStateOf(0.94f) }

    // Live Timer
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            secondsElapsed++
            // Occasional ghost progression
            if (secondsElapsed % 4L == 0L && opponentScore < ruleType.targetReps) {
                opponentScore++
            }
        }
    }

    fun triggerValidRep() {
        userScore++
        showRepFlash = true
        // Haptic feedback
        try {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vm?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator?.vibrate(VibrationEffect.createOneShot(45, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(45)
            }
        } catch (_: Exception) {}
    }

    LaunchedEffect(showRepFlash) {
        if (showRepFlash) {
            delay(600)
            showRepFlash = false
        }
    }

    // Infinite animation for skeleton joint motion simulation
    val infiniteTransition = rememberInfiniteTransition(label = "skeletonMotion")
    val jointOffset by infiniteTransition.animateFloat(
        initialValue = -8f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "jointMotion"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ArenaBackground)
    ) {
        // LAYER 1: CAMERA PREVIEW OR ATMOSPHERIC WORKOUT FEED
        if (hasCameraPermission) {
            AndroidView(
                factory = { ctx ->
                    val previewView = PreviewView(ctx)
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also {
                            it.surfaceProvider = previewView.surfaceProvider
                        }
                        val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                        try {
                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview)
                        } catch (_: Exception) {
                            // Fallback to back camera if front camera unavailable
                            try {
                                cameraProvider.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview)
                            } catch (_: Exception) {}
                        }
                    }, ContextCompat.getMainExecutor(ctx))
                    previewView
                },
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // High fidelity athletic gym background
            AsyncImage(
                model = AthleteAssets.GYM_BG,
                contentDescription = "Workout Arena",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .background(ArenaBackground)
            )
        }

        // Dark Vignette & Grid Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            ArenaBackground.copy(alpha = 0.90f),
                            Color.Transparent,
                            ArenaBackground.copy(alpha = 0.95f)
                        )
                    )
                )
        )

        // LAYER 2: AI POSE SKELETON VECTOR OVERLAY
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasW = size.width
            val canvasH = size.height

            val centerX = canvasW * 0.5f
            val spineTopY = canvasH * 0.32f + jointOffset
            val shoulderL = Offset(centerX - canvasW * 0.16f, spineTopY + 20f)
            val shoulderR = Offset(centerX + canvasW * 0.16f, spineTopY + 20f)
            val elbowL = Offset(centerX - canvasW * 0.24f, spineTopY + 80f - jointOffset)
            val elbowR = Offset(centerX + canvasW * 0.24f, spineTopY + 80f - jointOffset)
            val wristL = Offset(centerX - canvasW * 0.26f, spineTopY + 140f)
            val wristR = Offset(centerX + canvasW * 0.26f, spineTopY + 140f)

            val pelvisY = spineTopY + 150f
            val hipL = Offset(centerX - canvasW * 0.10f, pelvisY)
            val hipR = Offset(centerX + canvasW * 0.10f, pelvisY)
            val kneeL = Offset(centerX - canvasW * 0.14f, pelvisY + 110f + jointOffset)
            val kneeR = Offset(centerX + canvasW * 0.14f, pelvisY + 110f + jointOffset)
            val ankleL = Offset(centerX - canvasW * 0.15f, pelvisY + 210f)
            val ankleR = Offset(centerX + canvasW * 0.15f, pelvisY + 210f)

            // Bones (violet 35% opacity)
            val boneColor = AccentVioletContainer.copy(alpha = 0.40f)
            val boneStroke = Stroke(width = 4f)

            // Spine
            drawLine(boneColor, Offset(centerX, spineTopY), Offset(centerX, pelvisY), strokeWidth = 5f)
            // Clavicle
            drawLine(boneColor, shoulderL, shoulderR, strokeWidth = 5f)
            // Arms
            drawLine(boneColor, shoulderL, elbowL, strokeWidth = 4f)
            drawLine(boneColor, elbowL, wristL, strokeWidth = 4f)
            drawLine(boneColor, shoulderR, elbowR, strokeWidth = 4f)
            drawLine(boneColor, elbowR, wristR, strokeWidth = 4f)
            // Pelvis
            drawLine(boneColor, hipL, hipR, strokeWidth = 5f)
            // Legs
            drawLine(boneColor, hipL, kneeL, strokeWidth = 4f)
            drawLine(boneColor, kneeL, ankleL, strokeWidth = 4f)
            drawLine(boneColor, hipR, kneeR, strokeWidth = 4f)
            drawLine(boneColor, kneeR, ankleR, strokeWidth = 4f)

            // Head node
            drawCircle(AccentVioletLight, radius = 10f, center = Offset(centerX, spineTopY - 15f))
            drawCircle(AccentViolet.copy(alpha = 0.3f), radius = 22f, center = Offset(centerX, spineTopY - 15f))

            // Body nodes
            val nodes = listOf(shoulderL, shoulderR, elbowL, elbowR, hipL, hipR, ankleL, ankleR)
            nodes.forEach { pt ->
                drawCircle(AccentVioletLight.copy(alpha = 0.8f), radius = 7f, center = pt)
            }

            // Valid flex tracked nodes: Wrists & Knees glow green
            val greenNodes = listOf(wristL, wristR, kneeL, kneeR)
            greenNodes.forEach { pt ->
                drawCircle(SuccessNeon, radius = 9f, center = pt)
                drawCircle(SuccessNeon.copy(alpha = 0.25f), radius = 18f, center = pt)
            }
        }

        // LAYER 3: TOP SECTION SCOREBOARD HUD
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Live Status Ribbon & Audio Cue Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .background(ArenaSurfaceContainer.copy(alpha = 0.85f))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
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
                        text = "CV TRACKING ACTIVE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Black,
                        color = SecondaryGreen,
                        letterSpacing = 0.5.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = { isAudioMuted = !isAudioMuted },
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(ArenaSurfaceContainer.copy(alpha = 0.85f))
                            .testTag("btn_audio_toggle")
                    ) {
                        Icon(
                            imageVector = if (isAudioMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                            contentDescription = "Audio Cue",
                            tint = if (isAudioMuted) TextMuted else TextLight,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(ArenaSurfaceContainer.copy(alpha = 0.85f))
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "60 FPS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted
                        )
                    }
                }
            }

            // MAIN SPLIT SCOREBOARD (YOU vs ADAM)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(ArenaSurface1.copy(alpha = 0.92f))
                    .border(1.dp, ArenaBorder, RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // User Column
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier.clickable { triggerValidRep() }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = "YOU",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Black,
                                    color = AccentVioletLight,
                                    letterSpacing = 1.sp
                                )
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(AccentVioletContainer)
                                )
                            }
                            Text(
                                text = "$userScore",
                                style = BattleNumStyle.copy(fontSize = 58.sp, lineHeight = 58.sp),
                                color = TextLight,
                                modifier = Modifier.testTag("live_user_score")
                            )
                            Text(
                                text = "TARGET: 60S MAX",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextMuted
                            )
                        }

                        // Floating Advantage Badge in the center
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(999.dp))
                                .background(SecondaryContainerGreen)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropUp,
                                    contentDescription = null,
                                    tint = TextLight,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "YOU +${userScore - opponentScore}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = TextLight
                                )
                            }
                        }

                        // Opponent Column (Adam)
                        Column(horizontalAlignment = Alignment.End) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(TextMuted)
                                )
                                Text(
                                    text = "ADAM",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextMuted,
                                    letterSpacing = 1.sp
                                )
                            }
                            Text(
                                text = "$opponentScore",
                                style = BattleNumStyle.copy(fontSize = 58.sp, lineHeight = 58.sp),
                                color = TextMuted
                            )
                            Text(
                                text = "LIVE GHOST",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextMuted
                            )
                        }
                    }

                    // Comparative Split Progress Bar
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(999.dp))
                                .background(ArenaSurfaceHigh)
                        ) {
                            val userRatio = (userScore.toFloat() / (userScore + opponentScore).coerceAtLeast(1)).coerceIn(0.1f, 0.9f)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(userRatio)
                                    .height(6.dp)
                                    .background(AccentVioletContainer)
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(6.dp)
                                    .background(ArenaBorder)
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Pace: 1.28s / rep",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AccentVioletLight
                            )
                            Text(
                                text = "Opponent: 1.54s",
                                fontSize = 11.sp,
                                color = TextMuted
                            )
                        }
                    }
                }
            }
        }

        // LAYER 4: CENTER COACHING & CV TOKENS
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Interactive +1 VALID REP FLASH BADGE (Tap to count reps!)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(SecondaryContainerGreen)
                    .clickable { triggerValidRep() }
                    .padding(horizontal = 18.dp, vertical = 10.dp)
                    .testTag("btn_valid_rep_trigger")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Valid Rep",
                        tint = TextLight,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = "+1 VALID REP",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        color = TextLight,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // Real-time Biomechanics Feedback HUD
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(ArenaSurface1.copy(alpha = 0.92f))
                    .border(1.dp, ArenaBorder, RoundedCornerShape(14.dp))
                    .padding(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
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
                                    .background(SuccessNeon)
                            )
                            Text(
                                text = "FORM: GOOD",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = TextLight
                            )
                        }
                        Text(
                            text = "DEPTH 94%",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextMuted
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "GO DEEPER",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = TertiaryOrange,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "TARGET THRESHOLD",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextLight
                        )
                    }

                    // Depth progress gauge
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(ArenaSurfaceHigh)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(currentDepthPercent)
                                .height(8.dp)
                                .background(TertiaryContainerOrange)
                        )
                    }
                }
            }
        }

        // LAYER 5: BOTTOM METRICS STRIP & EMERGENCY END SET BUTTON
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Live Metrics Strip (Timer, Exercise, Accuracy)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricPill(
                    modifier = Modifier.weight(1f),
                    label = "TIMER",
                    value = String.format("%02d:%02d", secondsElapsed / 60, secondsElapsed % 60),
                    valueColor = TextLight
                )
                MetricPill(
                    modifier = Modifier.weight(1f),
                    label = "EXERCISE",
                    value = exerciseType.title.uppercase(),
                    valueColor = AccentVioletLight
                )
                MetricPill(
                    modifier = Modifier.weight(1f),
                    label = "ACCURACY",
                    value = "98.2%",
                    valueColor = SecondaryGreen
                )
            }

            // High-Contrast Emergency Safety Termination Button
            Button(
                onClick = {
                    onEndSet(userScore, opponentScore, 98, 1.2f)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("btn_end_set"),
                shape = RoundedCornerShape(999.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ArenaSurfaceHigh,
                    contentColor = DangerRed
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, DangerRed.copy(alpha = 0.6f))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.StopCircle,
                        contentDescription = "Stop",
                        tint = DangerRed,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "END SET",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricPill(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    valueColor: Color
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(ArenaSurface1.copy(alpha = 0.90f))
            .border(1.dp, ArenaBorder, RoundedCornerShape(12.dp))
            .padding(vertical = 10.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = TextMuted,
            letterSpacing = 0.5.sp
        )
        Text(
            text = value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Black,
            color = valueColor,
            maxLines = 1
        )
    }
}
