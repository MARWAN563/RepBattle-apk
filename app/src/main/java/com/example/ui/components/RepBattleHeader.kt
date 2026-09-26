package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.AccentViolet
import com.example.ui.theme.AccentVioletLight
import com.example.ui.theme.ArenaBorder
import com.example.ui.theme.ArenaSurface1
import com.example.ui.theme.ArenaSurface2
import com.example.ui.theme.SecondaryGreen
import com.example.ui.theme.TertiaryContainerOrange
import com.example.ui.theme.TertiaryOrange
import com.example.ui.theme.TextLight
import com.example.ui.theme.TextMuted

@Composable
fun RepBattleTabHeader(
    title: String = "Home",
    streakDays: Int = 4,
    onHtmlExplorerClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(ArenaSurface1.copy(alpha = 0.95f))
            .border(width = 0.5.dp, color = ArenaBorder)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Logo & Title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, AccentViolet.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            ) {
                AsyncImage(
                    model = AthleteAssets.LOGO_URL,
                    contentDescription = "RepBattle Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(36.dp)
                )
            }
            Column {
                Text(
                    text = "REPBATTLE",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-0.5).sp,
                    color = TextLight,
                    lineHeight = 18.sp
                )
                Text(
                    text = title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    lineHeight = 14.sp
                )
            }
        }

        // HTML Explorer, Streak & Profile
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // HTML Image Resolver Quick Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(AccentViolet.copy(alpha = 0.15f))
                    .border(1.dp, AccentViolet.copy(alpha = 0.4f), RoundedCornerShape(999.dp))
                    .clickable { onHtmlExplorerClick() }
                    .padding(horizontal = 8.dp, vertical = 5.dp)
                    .testTag("btn_header_html_explorer")
            ) {
                Text(
                    text = "HTML",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Black,
                    color = AccentVioletLight,
                    letterSpacing = 0.5.sp
                )
            }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(ArenaSurface2)
                    .border(1.dp, ArenaBorder, RoundedCornerShape(999.dp))
                    .padding(horizontal = 8.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocalFireDepartment,
                    contentDescription = "Streak Fire",
                    tint = TertiaryOrange,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "$streakDays D",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TertiaryOrange,
                    letterSpacing = 0.5.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, AccentViolet, CircleShape)
                    .clickable { onProfileClick() }
                    .testTag("header_profile_avatar")
            ) {
                AsyncImage(
                    model = AthleteAssets.MAROUANE_AVATAR,
                    contentDescription = "Athlete Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Composable
fun RepBattleStackHeader(
    title: String,
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(ArenaSurface1.copy(alpha = 0.95f))
            .border(width = 0.5.dp, color = ArenaBorder)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.testTag("stack_back_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = TextLight
                )
            }

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = AthleteAssets.LOGO_URL,
                    contentDescription = "RepBattle Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(32.dp)
                )
            }

            Text(
                text = title.uppercase(),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.3).sp,
                color = TextLight
            )
        }

        Box(
            modifier = Modifier
                .padding(end = 8.dp)
                .size(34.dp)
                .clip(CircleShape)
                .border(1.5.dp, AccentViolet, CircleShape)
        ) {
            AsyncImage(
                model = AthleteAssets.MAROUANE_AVATAR,
                contentDescription = "Athlete Profile",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(34.dp)
            )
        }
    }
}
