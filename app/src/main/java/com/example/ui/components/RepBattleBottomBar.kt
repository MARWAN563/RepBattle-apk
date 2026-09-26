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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QueryStats
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentViolet
import com.example.ui.theme.AccentVioletContainer
import com.example.ui.theme.ArenaBorder
import com.example.ui.theme.ArenaSurface1
import com.example.ui.theme.TextLight
import com.example.ui.theme.TextMuted

enum class NavTab {
    HOME,
    BATTLES,
    HISTORY,
    PROFILE
}

@Composable
fun RepBattleBottomBar(
    currentTab: NavTab,
    onTabSelected: (NavTab) -> Unit,
    onChallengeClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(82.dp)
            .background(ArenaSurface1.copy(alpha = 0.95f))
            .border(0.5.dp, ArenaBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home
            NavItem(
                icon = Icons.Default.FitnessCenter,
                label = "Home",
                selected = currentTab == NavTab.HOME,
                testTag = "nav_home",
                onClick = { onTabSelected(NavTab.HOME) }
            )

            // Battles
            NavItem(
                icon = Icons.Default.MilitaryTech,
                label = "Battles",
                selected = currentTab == NavTab.BATTLES,
                testTag = "nav_battles",
                onClick = { onTabSelected(NavTab.BATTLES) }
            )

            // Center Spacer for elevated Challenge button
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clickable { onChallengeClick() }
            )

            // History
            NavItem(
                icon = Icons.Default.QueryStats,
                label = "History",
                selected = currentTab == NavTab.HISTORY,
                testTag = "nav_history",
                onClick = { onTabSelected(NavTab.HISTORY) }
            )

            // Profile
            NavItem(
                icon = Icons.Default.Person,
                label = "Profile",
                selected = currentTab == NavTab.PROFILE,
                testTag = "nav_profile",
                onClick = { onTabSelected(NavTab.PROFILE) }
            )
        }

        // Floating Elevated "+ Challenge" Button in the center
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-14).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .shadow(12.dp, CircleShape, spotColor = AccentViolet)
                    .clip(CircleShape)
                    .background(AccentVioletContainer)
                    .border(2.dp, AccentViolet, CircleShape)
                    .clickable { onChallengeClick() }
                    .testTag("nav_center_challenge"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Challenge",
                    tint = TextLight,
                    modifier = Modifier.size(32.dp)
                )
            }
            Text(
                text = "Challenge",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextLight,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
private fun NavItem(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    testTag: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .size(54.dp)
            .clickable { onClick() }
            .testTag(testTag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (selected) AccentViolet else TextMuted,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) AccentViolet else TextMuted,
            modifier = Modifier.padding(top = 3.dp)
        )
    }
}
