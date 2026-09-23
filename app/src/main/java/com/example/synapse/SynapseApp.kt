package com.example.synapse

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.synapse.data.MockData
import com.example.synapse.model.*
import com.example.synapse.ui.components.*
import com.example.synapse.ui.screens.*

@Composable
fun SynapseApp(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    // Navigation State
    var currentDestination by remember { mutableStateOf(NavDestination.HOME) }
    var selectedCategory by remember { mutableStateOf("For You") }

    // Feeds & Data State
    var posts by remember { mutableStateOf<List<Post>>(MockData.samplePosts) }
    var stories by remember { mutableStateOf<List<Story>>(MockData.stories) }
    var pins by remember { mutableStateOf<List<Pin>>(MockData.visualPins) }
    var boards by remember { mutableStateOf<List<Board>>(MockData.sampleBoards) }
    var conversations by remember { mutableStateOf<List<Conversation>>(MockData.sampleConversations) }
    var notifications by remember { mutableStateOf<List<NotificationItem>>(MockData.sampleNotifications) }
    var communities by remember { mutableStateOf<List<Community>>(MockData.sampleCommunities) }
    var communityPosts by remember { mutableStateOf<List<CommunityPost>>(MockData.sampleCommunityPosts) }

    // Modal / Fullscreen Views State
    var activeStory by remember { mutableStateOf<Story?>(null) }
    var activeShortVideoViewer by remember { mutableStateOf(false) }
    var activeLongVideo by remember { mutableStateOf<Post?>(null) }
    var activeLiveStream by remember { mutableStateOf<Post?>(null) }
    var showCreateSheet by remember { mutableStateOf(false) }
    var activeCommentsPost by remember { mutableStateOf<Post?>(null) }
    var showMessaging by remember { mutableStateOf(false) }
    var showCreatorStudio by remember { mutableStateOf(false) }
    var showCommunities by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("synapse_root_scaffold"),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            // Show top bar only when on main destinations and not inside full-screen overlays
            if (!showMessaging && !showCreatorStudio && !showCommunities && activeLongVideo == null && !activeShortVideoViewer) {
                SynapseTopBar(
                    title = "SYNAPSE",
                    activeCategory = selectedCategory,
                    unreadMessageCount = conversations.sumOf { it.unreadCount },
                    onCategorySelected = { selectedCategory = it },
                    onSearchClick = { currentDestination = NavDestination.DISCOVER },
                    onMessagesClick = { showMessaging = true },
                    onLiveClick = {
                        val livePost = posts.firstOrNull { it.isLive || it.type == PostType.LIVE }
                        if (livePost != null) {
                            activeLiveStream = livePost
                        }
                    },
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = onToggleTheme
                )
            }
        },
        bottomBar = {
            // Bottom bar shown on main tabs
            if (!showMessaging && !showCreatorStudio && !showCommunities && activeLongVideo == null && !activeShortVideoViewer) {
                SynapseBottomBar(
                    currentDestination = currentDestination,
                    unreadNotificationsCount = notifications.count { !it.isRead },
                    onNavigate = { dest ->
                        if (dest == NavDestination.CREATE) {
                            showCreateSheet = true
                        } else {
                            currentDestination = dest
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Main Destination Routing
            when (currentDestination) {
                NavDestination.HOME -> {
                    HomeScreen(
                        posts = posts,
                        stories = stories,
                        selectedCategory = selectedCategory,
                        onCategoryChange = { selectedCategory = it },
                        onStoryClick = { story -> activeStory = story },
                        onAddStoryClick = { showCreateSheet = true },
                        onLikeToggle = { postId ->
                            posts = posts.map { p ->
                                if (p.id == postId) {
                                    val isNowLiked = !p.isLiked
                                    p.copy(
                                        isLiked = isNowLiked,
                                        likesCount = p.likesCount + if (isNowLiked) 1 else -1
                                    )
                                } else p
                            }
                        },
                        onSaveToggle = { postId ->
                            posts = posts.map { p ->
                                if (p.id == postId) p.copy(isSaved = !p.isSaved) else p
                            }
                        },
                        onCommentClick = { post -> activeCommentsPost = post },
                        onShareClick = { },
                        onCreatorClick = { currentDestination = NavDestination.PROFILE },
                        onWatchVideoClick = { post ->
                            if (post.type == PostType.SHORT_VIDEO) {
                                activeShortVideoViewer = true
                            } else if (post.type == PostType.LIVE || post.isLive) {
                                activeLiveStream = post
                            } else {
                                activeLongVideo = post
                            }
                        },
                        onPollVote = { postId, optionIndex ->
                            posts = posts.map { p ->
                                if (p.id == postId) {
                                    val updatedOptions = p.pollOptions.mapIndexed { idx, opt ->
                                        if (idx == optionIndex) opt.copy(votes = opt.votes + 1) else opt
                                    }
                                    p.copy(pollOptions = updatedOptions, userVotedOptionIndex = optionIndex)
                                } else p
                            }
                        }
                    )
                }
                NavDestination.DISCOVER -> {
                    DiscoverScreen(
                        pins = pins,
                        boards = boards,
                        onPinClick = {},
                        onBoardClick = {},
                        onSearchSubmit = {}
                    )
                }
                NavDestination.NOTIFICATIONS -> {
                    NotificationsScreen(
                        notifications = notifications,
                        onActionClick = { notif ->
                            if (notif.category == NotificationCategory.LIVE) {
                                val livePost = posts.firstOrNull { it.isLive }
                                if (livePost != null) activeLiveStream = livePost
                            }
                        }
                    )
                }
                NavDestination.PROFILE -> {
                    ProfileScreen(
                        posts = posts,
                        onOpenCreatorStudio = { showCreatorStudio = true },
                        onOpenSettings = { showSettings = true },
                        onPostClick = { post ->
                            if (post.type == PostType.SHORT_VIDEO) activeShortVideoViewer = true
                            else if (post.type == PostType.LONG_VIDEO) activeLongVideo = post
                        }
                    )
                }
                NavDestination.CREATE -> {
                    // Handled via modal sheet
                }
            }

            // Fullscreen Screens & Subviews

            // Direct Messaging Screen
            AnimatedVisibility(
                visible = showMessaging,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                MessagingScreen(
                    conversations = conversations,
                    onBack = { showMessaging = false }
                )
            }

            // Creator Studio Screen
            AnimatedVisibility(
                visible = showCreatorStudio,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                CreatorStudioScreen(
                    onBack = { showCreatorStudio = false }
                )
            }

            // Communities Screen
            AnimatedVisibility(
                visible = showCommunities,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
            ) {
                CommunitiesScreen(
                    communities = communities,
                    communityPosts = communityPosts,
                    onBack = { showCommunities = false }
                )
            }

            // Long Video Detail Screen
            if (activeLongVideo != null) {
                LongVideoScreen(
                    videoPost = activeLongVideo!!,
                    relatedVideos = posts.filter { it.type == PostType.LONG_VIDEO },
                    onBack = { activeLongVideo = null },
                    onSelectVideo = { nextVideo -> activeLongVideo = nextVideo },
                    onOpenComments = { activeCommentsPost = activeLongVideo }
                )
            }

            // Short Vertical Video Screen
            if (activeShortVideoViewer) {
                ShortVideoScreen(
                    shortVideos = posts.filter { it.type == PostType.SHORT_VIDEO },
                    onBack = { activeShortVideoViewer = false },
                    onCommentClick = { post -> activeCommentsPost = post },
                    onShareClick = {},
                    onCreatorClick = {
                        activeShortVideoViewer = false
                        currentDestination = NavDestination.PROFILE
                    }
                )
            }
        }
    }

    // Story Viewer Modal Dialog
    activeStory?.let { story ->
        StoryViewerModal(
            story = story,
            onDismiss = { activeStory = null },
            onNext = {
                val nextIndex = stories.indexOfFirst { it.id == story.id } + 1
                if (nextIndex < stories.size) {
                    activeStory = stories[nextIndex]
                } else {
                    activeStory = null
                }
            },
            onPrevious = {
                val prevIndex = stories.indexOfFirst { it.id == story.id } - 1
                if (prevIndex >= 0) {
                    activeStory = stories[prevIndex]
                }
            }
        )
    }

    // Live Stream Modal Dialog
    activeLiveStream?.let { livePost ->
        LiveStreamModal(
            livePost = livePost,
            onDismiss = { activeLiveStream = null }
        )
    }

    // Create Center Modal BottomSheet
    if (showCreateSheet) {
        CreateCenterSheet(
            onDismiss = { showCreateSheet = false },
            onPublishPost = { type, caption, tagsStr ->
                val newPost = Post(
                    id = "post_${System.currentTimeMillis()}",
                    creator = MockData.currentUser,
                    type = type,
                    caption = caption,
                    mediaUrls = listOf("https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=800&auto=format&fit=crop"),
                    tags = tagsStr.split(",").map { it.trim() }.filter { it.isNotEmpty() },
                    timestamp = "Just now",
                    likesCount = 1,
                    isLiked = true,
                    commentsCount = 0,
                    repostsCount = 0
                )
                posts = listOf(newPost) + posts
            }
        )
    }

    // Comments Sheet
    activeCommentsPost?.let { post ->
        CommentsBottomSheet(
            comments = if (post.comments.isNotEmpty()) post.comments else MockData.sampleComments,
            onDismiss = { activeCommentsPost = null },
            onAddComment = { commentText ->
                val newComment = Comment(
                    id = "c_${System.currentTimeMillis()}",
                    user = MockData.currentUser,
                    text = commentText,
                    timestamp = "Just now"
                )
                posts = posts.map { p ->
                    if (p.id == post.id) {
                        p.copy(
                            comments = listOf(newComment) + p.comments,
                            commentsCount = p.commentsCount + 1
                        )
                    } else p
                }
            }
        )
    }

    // Settings Modal
    if (showSettings) {
        SettingsModal(
            isDarkTheme = isDarkTheme,
            onToggleTheme = onToggleTheme,
            onDismiss = { showSettings = false }
        )
    }
}
