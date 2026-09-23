package com.example.synapse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.synapse.data.MockData
import com.example.synapse.model.Post
import com.example.synapse.model.User
import com.example.synapse.ui.components.formatCount
import com.example.ui.theme.SynapseBrandGradient
import com.example.ui.theme.SynapseCyanAccent
import com.example.ui.theme.SynapsePinkAccent
import com.example.ui.theme.SynapseVioletPrimary

@Composable
fun ProfileScreen(
    user: User = MockData.currentUser,
    posts: List<Post>,
    onOpenCreatorStudio: () -> Unit,
    onOpenSettings: () -> Unit,
    onPostClick: (Post) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = remember { listOf("Posts", "Reels", "Boards", "Saved") }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .testTag("profile_screen"),
        contentPadding = PaddingValues(bottom = 90.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        // Full Header Span
        item(span = { GridItemSpan(3) }) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Cover Image & Settings Shortcut
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                ) {
                    AsyncImage(
                        model = user.coverUrl,
                        contentDescription = "Profile Cover",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Settings Cog Icon Button (Top Right)
                    IconButton(
                        onClick = onOpenSettings,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .statusBarsPadding()
                            .padding(8.dp)
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .testTag("profile_settings_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }
                }

                // Avatar, Stats & Bio
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        // Avatar Overlapping Cover
                        Box(
                            modifier = Modifier
                                .offset(y = (-36).dp)
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(SynapseBrandGradient)
                                .padding(3.dp)
                        ) {
                            AsyncImage(
                                model = user.avatarUrl,
                                contentDescription = user.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .border(2.dp, MaterialTheme.colorScheme.background, CircleShape)
                            )
                        }

                        // Stats Summary
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            ProfileStatItem("Followers", formatCount(user.followerCount.toLong()))
                            ProfileStatItem("Following", "${user.followingCount}")
                            ProfileStatItem("Subscribers", formatCount(user.subscribersCount.toLong()))
                        }
                    }

                    // Name, Handle, Badge
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = user.name,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        if (user.isVerified) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Verified Creator",
                                tint = SynapseCyanAccent,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Text(
                        text = "${user.handle} • ${user.category}",
                        fontSize = 12.sp,
                        color = SynapseVioletPrimary,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = user.bio,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Link,
                            contentDescription = null,
                            tint = SynapseCyanAccent,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = user.website.removePrefix("https://"),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SynapseCyanAccent
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Action Buttons Row: Edit Profile, Creator Studio
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        OutlinedButton(
                            onClick = {},
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Edit Profile", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        Button(
                            onClick = onOpenCreatorStudio,
                            colors = ButtonDefaults.buttonColors(containerColor = SynapseVioletPrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("creator_studio_button")
                        ) {
                            Icon(Icons.Default.Analytics, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Studio", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Tab Row
                    TabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = SynapseVioletPrimary,
                        divider = {}
                    ) {
                        tabs.forEachIndexed { index, tabName ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                text = {
                                    Text(
                                        text = tabName,
                                        fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                                        fontSize = 13.sp
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }

        // Media Grid Items
        items(posts) { post ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clickable { onPostClick(post) }
            ) {
                AsyncImage(
                    model = post.mediaUrls.firstOrNull() ?: post.creator.avatarUrl,
                    contentDescription = post.caption,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Reel/Video Indicator
                if (post.type == com.example.synapse.model.PostType.SHORT_VIDEO || post.type == com.example.synapse.model.PostType.LONG_VIDEO) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Video",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileStatItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontWeight = FontWeight.Black,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = label,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
