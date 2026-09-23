package com.example.synapse.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.synapse.model.Post
import com.example.synapse.ui.components.formatCount
import com.example.ui.theme.*

@Composable
fun ShortVideoScreen(
    shortVideos: List<Post>,
    onBack: () -> Unit,
    onCommentClick: (Post) -> Unit,
    onShareClick: (Post) -> Unit,
    onCreatorClick: (String) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { shortVideos.size })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .testTag("short_video_screen")
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val post = shortVideos[page]
            SingleShortVideoPlayer(
                post = post,
                onCommentClick = { onCommentClick(post) },
                onShareClick = { onShareClick(post) },
                onCreatorClick = { onCreatorClick(post.creator.id) }
            )
        }

        // Top Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.4f))
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Following",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "For You",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.4f))
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun SingleShortVideoPlayer(
    post: Post,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    onCreatorClick: () -> Unit
) {
    var isLiked by remember { mutableStateOf(post.isLiked) }
    var likesCount by remember { mutableStateOf(post.likesCount) }
    var isSaved by remember { mutableStateOf(post.isSaved) }
    var showHeartBurst by remember { mutableStateOf(false) }
    var isPaused by remember { mutableStateOf(false) }

    // Vinyl record spin animation
    val infiniteTransition = rememberInfiniteTransition(label = "vinyl_spin")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        if (!isLiked) {
                            isLiked = true
                            likesCount += 1
                        }
                        showHeartBurst = true
                    },
                    onTap = {
                        isPaused = !isPaused
                    }
                )
            }
    ) {
        // Video Preview / Simulated Player
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(post.mediaUrls.firstOrNull() ?: "")
                .crossfade(true)
                .build(),
            contentDescription = "Short Video",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Gradient Vignette
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = 0.5f),
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.8f)
                        )
                    )
                )
        )

        // Paused Indicator
        if (isPaused) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = "Paused",
                    tint = Color.White,
                    modifier = Modifier.size(44.dp)
                )
            }
        }

        // Animated Double-Tap Heart Burst
        LaunchedEffect(showHeartBurst) {
            if (showHeartBurst) {
                kotlinx.coroutines.delay(800L)
                showHeartBurst = false
            }
        }

        AnimatedVisibility(
            visible = showHeartBurst,
            enter = scaleIn(spring(stiffness = Spring.StiffnessMediumLow)) + fadeIn(),
            exit = scaleOut() + fadeOut(),
            modifier = Modifier.align(Alignment.Center)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = SynapsePinkAccent,
                modifier = Modifier.size(100.dp)
            )
        }

        // Right-Side Interaction Stack
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 90.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Creator Avatar with Follow Button
            Box(contentAlignment = Alignment.BottomCenter) {
                AsyncImage(
                    model = post.creator.avatarUrl,
                    contentDescription = post.creator.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                        .clickable(onClick = onCreatorClick)
                )
                Box(
                    modifier = Modifier
                        .offset(y = 8.dp)
                        .size(18.dp)
                        .clip(CircleShape)
                        .background(SynapsePinkAccent),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Follow",
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            // Like
            ActionColumnItem(
                icon = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                label = formatCount(likesCount.toLong()),
                tint = if (isLiked) SynapsePinkAccent else Color.White,
                onClick = {
                    isLiked = !isLiked
                    likesCount += if (isLiked) 1 else -1
                }
            )

            // Comments
            ActionColumnItem(
                icon = Icons.Outlined.ChatBubbleOutline,
                label = formatCount(post.commentsCount.toLong()),
                onClick = onCommentClick
            )

            // Bookmark/Save
            ActionColumnItem(
                icon = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                label = "Save",
                tint = if (isSaved) SynapseVioletPrimary else Color.White,
                onClick = { isSaved = !isSaved }
            )

            // Share
            ActionColumnItem(
                icon = Icons.Outlined.Share,
                label = "Share",
                onClick = onShareClick
            )

            // Spinning Vinyl Audio Record
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.8f))
                    .border(2.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                    .rotate(rotation),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = post.audioTrack?.coverUrl ?: post.creator.avatarUrl,
                    contentDescription = "Audio Disc",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                )
            }
        }

        // Bottom Left Creator Info & Music Marquee
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(0.78f)
                .padding(start = 16.dp, bottom = 80.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(onClick = onCreatorClick)
            ) {
                Text(
                    text = post.creator.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                if (post.creator.isVerified) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Verified",
                        tint = SynapseCyanAccent,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = post.caption,
                color = Color.White,
                fontSize = 13.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Audio Track Marquee Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MusicNote,
                    contentDescription = null,
                    tint = SynapseCyanAccent,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${post.audioTrack?.title ?: "Original Audio"} • ${post.audioTrack?.artist ?: post.creator.name}",
                    color = Color.White,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun ActionColumnItem(
    imageVector: ImageVector? = null,
    icon: ImageVector = imageVector ?: Icons.Default.Favorite,
    label: String,
    tint: Color = Color.White,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
