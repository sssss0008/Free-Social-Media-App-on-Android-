package com.example.synapse.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.synapse.data.MockData
import com.example.synapse.model.Post
import com.example.synapse.model.User
import com.example.ui.theme.SynapseCyanAccent
import com.example.ui.theme.SynapsePinkAccent
import com.example.ui.theme.SynapseVioletPrimary

data class LiveComment(
    val id: String,
    val user: User,
    val text: String
)

@Composable
fun LiveStreamModal(
    livePost: Post,
    onDismiss: () -> Unit
) {
    var viewerCount by remember { mutableStateOf(livePost.liveViewerCount.coerceAtLeast(3412)) }
    var comments by remember {
        mutableStateOf(
            listOf(
                LiveComment("1", MockData.creatorMaya, "The audio reactive filter in the background is amazing!! 🔥"),
                LiveComment("2", MockData.creatorKaelen, "How are you synchronizing the visual cues?"),
                LiveComment("3", MockData.creatorElena, "Love this live set so much ❤️❤️"),
                LiveComment("4", MockData.currentUser, "Sending good vibes from Tokyo! ✨")
            )
        )
    }
    var commentInput by remember { mutableStateOf("") }
    var showGiftSheet by remember { mutableStateOf(false) }
    var heartCount by remember { mutableStateOf(1420) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .testTag("live_stream_modal")
        ) {
            // Live Stream Background Video Preview
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(livePost.mediaUrls.firstOrNull() ?: "")
                    .crossfade(true)
                    .build(),
                contentDescription = "Live Broadcast",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Vignettes
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Black.copy(alpha = 0.7f),
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.85f)
                            )
                        )
                    )
            )

            // Top Header: Host info, LIVE badge, Viewer Count, Close button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Host Avatar + Name
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    AsyncImage(
                        model = livePost.creator.avatarUrl,
                        contentDescription = livePost.creator.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = livePost.creator.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFEF4444))
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "LIVE",
                                color = Color(0xFFEF4444),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }

                // Viewer Count & Close
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                        ) {
                            Icon(Icons.Default.Visibility, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("$viewerCount", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.5f))
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
                    }
                }
            }

            // Bottom Section: Floating Live Comments & Input Bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Floating Comments Stream (Semi-transparent overlay)
                val listState = rememberLazyListState()
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(180.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(comments, key = { it.id }) { comment ->
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color.Black.copy(alpha = 0.45f)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${comment.user.name}: ",
                                    color = SynapseCyanAccent,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = comment.text,
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Bottom Controls: Message Input, Gift, Heart
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = commentInput,
                        onValueChange = { commentInput = it },
                        placeholder = { Text("Comment live...", color = Color.White.copy(alpha = 0.6f), fontSize = 13.sp) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(20.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color.White.copy(alpha = 0.6f),
                            unfocusedBorderColor = Color.White.copy(alpha = 0.3f),
                            focusedContainerColor = Color.Black.copy(alpha = 0.5f),
                            unfocusedContainerColor = Color.Black.copy(alpha = 0.5f)
                        ),
                        trailingIcon = {
                            if (commentInput.isNotEmpty()) {
                                IconButton(onClick = {
                                    comments = comments + LiveComment(
                                        id = "${System.currentTimeMillis()}",
                                        user = MockData.currentUser,
                                        text = commentInput
                                    )
                                    commentInput = ""
                                }) {
                                    Icon(Icons.Default.Send, contentDescription = "Send", tint = SynapseVioletPrimary)
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Gift/Tip Button
                    IconButton(
                        onClick = { showGiftSheet = !showGiftSheet },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(SynapseVioletPrimary)
                    ) {
                        Icon(Icons.Default.CardGiftcard, contentDescription = "Send Gift", tint = Color.White)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Heart Pulse Button
                    IconButton(
                        onClick = { heartCount += 1 },
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(SynapsePinkAccent)
                    ) {
                        Icon(Icons.Default.Favorite, contentDescription = "Heart", tint = Color.White)
                    }
                }
            }

            // Quick Gift Dialog Overlay
            if (showGiftSheet) {
                Surface(
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Send Creator Appreciation", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            IconButton(onClick = { showGiftSheet = false }) {
                                Icon(Icons.Default.Close, contentDescription = null)
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            GiftOptionItem("Rose", "🌹", "10 coins") { showGiftSheet = false }
                            GiftOptionItem("Supernova", "💫", "50 coins") { showGiftSheet = false }
                            GiftOptionItem("Diamond", "💎", "200 coins") { showGiftSheet = false }
                            GiftOptionItem("Rocket", "🚀", "500 coins") { showGiftSheet = false }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun GiftOptionItem(
    name: String,
    emoji: String,
    coins: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Text(emoji, fontSize = 32.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(name, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        Text(coins, fontSize = 10.sp, color = SynapseCyanAccent)
    }
}
