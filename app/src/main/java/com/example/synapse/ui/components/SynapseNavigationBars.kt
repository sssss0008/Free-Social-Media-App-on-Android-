package com.example.synapse.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

enum class NavDestination {
    HOME,
    DISCOVER,
    CREATE,
    NOTIFICATIONS,
    PROFILE
}

@Composable
fun SynapseTopBar(
    title: String = "SYNAPSE",
    activeCategory: String = "For You",
    unreadMessageCount: Int = 2,
    onCategorySelected: (String) -> Unit = {},
    onSearchClick: () -> Unit = {},
    onMessagesClick: () -> Unit = {},
    onLiveClick: () -> Unit = {},
    isDarkTheme: Boolean = true,
    onToggleTheme: () -> Unit = {}
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Main Top Bar Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Synapse Brand Logo & Text
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onCategorySelected("For You") }
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(SynapseBrandGradient),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AllInclusive,
                            contentDescription = "Synapse Logo",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                // Action buttons: Live, Theme, Search, Messages
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // LIVE Pulsing Badge Button
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFEF4444).copy(alpha = 0.15f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.6f)),
                        modifier = Modifier
                            .testTag("live_button")
                            .clickable { onLiveClick() }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(7.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFEF4444))
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "LIVE",
                                color = Color(0xFFEF4444),
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }

                    // Theme toggle
                    IconButton(
                        onClick = onToggleTheme,
                        modifier = Modifier.testTag("theme_toggle")
                    ) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Outlined.LightMode else Icons.Outlined.DarkMode,
                            contentDescription = "Toggle Theme",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Search Button
                    IconButton(
                        onClick = onSearchClick,
                        modifier = Modifier.testTag("search_button")
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Direct Messages with Badge
                    IconButton(
                        onClick = onMessagesClick,
                        modifier = Modifier.testTag("messages_button")
                    ) {
                        BadgedBox(
                            badge = {
                                if (unreadMessageCount > 0) {
                                    Badge(
                                        containerColor = SynapsePinkAccent,
                                        contentColor = Color.White
                                    ) {
                                        Text("$unreadMessageCount", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ChatBubbleOutline,
                                contentDescription = "Messages",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SynapseBottomBar(
    currentDestination: NavDestination,
    unreadNotificationsCount: Int = 3,
    onNavigate: (NavDestination) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SynapseNavItem(
                icon = if (currentDestination == NavDestination.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                label = "Home",
                isSelected = currentDestination == NavDestination.HOME,
                testTag = "nav_home",
                onClick = { onNavigate(NavDestination.HOME) }
            )

            SynapseNavItem(
                icon = if (currentDestination == NavDestination.DISCOVER) Icons.Filled.Explore else Icons.Outlined.Explore,
                label = "Discover",
                isSelected = currentDestination == NavDestination.DISCOVER,
                testTag = "nav_discover",
                onClick = { onNavigate(NavDestination.DISCOVER) }
            )

            // Prominent Glowing Center "Create" FAB Button
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .shadow(12.dp, CircleShape, spotColor = SynapseVioletPrimary)
                    .clip(CircleShape)
                    .background(SynapseBrandGradient)
                    .clickable { onNavigate(NavDestination.CREATE) }
                    .testTag("nav_create"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Create Content",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            SynapseNavItem(
                icon = if (currentDestination == NavDestination.NOTIFICATIONS) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                label = "Alerts",
                isSelected = currentDestination == NavDestination.NOTIFICATIONS,
                badgeCount = unreadNotificationsCount,
                testTag = "nav_notifications",
                onClick = { onNavigate(NavDestination.NOTIFICATIONS) }
            )

            SynapseNavItem(
                icon = if (currentDestination == NavDestination.PROFILE) Icons.Filled.Person else Icons.Outlined.Person,
                label = "Profile",
                isSelected = currentDestination == NavDestination.PROFILE,
                testTag = "nav_profile",
                onClick = { onNavigate(NavDestination.PROFILE) }
            )
        }
    }
}

@Composable
private fun SynapseNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    badgeCount: Int = 0,
    testTag: String,
    onClick: () -> Unit
) {
    val activeColor by animateColorAsState(
        targetValue = if (isSelected) SynapseVioletPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "nav_color"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .testTag(testTag)
    ) {
        BadgedBox(
            badge = {
                if (badgeCount > 0) {
                    Badge(
                        containerColor = SynapsePinkAccent,
                        contentColor = Color.White
                    ) {
                        Text("$badgeCount", fontSize = 9.sp)
                    }
                }
            }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = activeColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = activeColor
        )
    }
}
