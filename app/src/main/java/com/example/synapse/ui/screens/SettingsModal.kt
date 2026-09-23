package com.example.synapse.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.SynapseCyanAccent
import com.example.ui.theme.SynapsePinkAccent
import com.example.ui.theme.SynapseVioletPrimary

@Composable
fun SettingsModal(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onDismiss: () -> Unit
) {
    var privateAccount by remember { mutableStateOf(false) }
    var autoplayVideo by remember { mutableStateOf(true) }
    var highQualityMedia by remember { mutableStateOf(true) }
    var pushNotifications by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .testTag("settings_modal"),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
            ) {
                // Settings Top Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Close Settings")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Settings & Privacy",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }

                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))

                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Account Section
                    item {
                        SettingsSectionHeader("Account & Security")
                    }
                    item {
                        SettingsNavigationItem("Edit Profile & Links", Icons.Outlined.Person) {}
                        SettingsNavigationItem("Security & 2-Factor Authentication", Icons.Outlined.Security) {}
                        SettingsNavigationItem("Monetization & Payout Methods", Icons.Outlined.Payment) {}
                    }

                    // Preferences & Experience
                    item {
                        SettingsSectionHeader("Content & Display")
                    }
                    item {
                        SettingsToggleItem(
                            title = "Dark Theme",
                            subtitle = if (isDarkTheme) "Deep obsidian mode enabled" else "Crisp modern light mode",
                            icon = Icons.Outlined.DarkMode,
                            isChecked = isDarkTheme,
                            onToggle = { onToggleTheme() }
                        )
                        SettingsToggleItem(
                            title = "Autoplay Video Feeds",
                            subtitle = "Play short reels and video cards smoothly while scrolling",
                            icon = Icons.Outlined.PlayCircle,
                            isChecked = autoplayVideo,
                            onToggle = { autoplayVideo = !autoplayVideo }
                        )
                        SettingsToggleItem(
                            title = "Stream in Ultra HD (4K / 60fps)",
                            subtitle = "Highest visual fidelity for cinematic long videos and pins",
                            icon = Icons.Outlined.HighQuality,
                            isChecked = highQualityMedia,
                            onToggle = { highQualityMedia = !highQualityMedia }
                        )
                    }

                    // Privacy & Safety
                    item {
                        SettingsSectionHeader("Privacy & Permissions")
                    }
                    item {
                        SettingsToggleItem(
                            title = "Private Synapse Profile",
                            subtitle = "Only approved followers can view your boards, stories and reels",
                            icon = Icons.Outlined.Lock,
                            isChecked = privateAccount,
                            onToggle = { privateAccount = !privateAccount }
                        )
                        SettingsToggleItem(
                            title = "Push & Live Notifications",
                            subtitle = "Alerts when creators you follow go live or reply",
                            icon = Icons.Outlined.Notifications,
                            isChecked = pushNotifications,
                            onToggle = { pushNotifications = !pushNotifications }
                        )
                    }

                    // Storage & System
                    item {
                        SettingsSectionHeader("Data & Cache")
                    }
                    item {
                        SettingsNavigationItem("Clear Offline Media Cache (184 MB)", Icons.Outlined.CleaningServices) {}
                        SettingsNavigationItem("About Synapse v1.0 • Terms & Safety", Icons.Outlined.Info) {}
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        color = SynapseVioletPrimary,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}

@Composable
private fun SettingsNavigationItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(title, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
        }
    }
}

@Composable
private fun SettingsToggleItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    isChecked: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    Text(subtitle, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Switch(
                checked = isChecked,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = SynapseVioletPrimary
                )
            )
        }
    }
}
