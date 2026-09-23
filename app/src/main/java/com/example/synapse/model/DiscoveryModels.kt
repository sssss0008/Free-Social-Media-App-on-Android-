package com.example.synapse.model

data class Pin(
    val id: String,
    val title: String,
    val imageUrl: String,
    val boardId: String = "",
    val creator: User,
    val heightRatio: Float = 1.3f, // For masonry layout
    val savesCount: Int = 120,
    val tags: List<String> = emptyList()
)

data class Board(
    val id: String,
    val title: String,
    val description: String,
    val coverUrl: String,
    val pinCount: Int,
    val isPrivate: Boolean = false,
    val owner: User,
    val collaboratorAvatars: List<String> = emptyList()
)

data class Community(
    val id: String,
    val name: String,
    val handle: String,
    val category: String,
    val description: String,
    val bannerUrl: String,
    val iconUrl: String,
    val memberCount: Int,
    val onlineCount: Int,
    val isJoined: Boolean = false,
    val rules: List<String> = emptyList()
)

data class CommunityPost(
    val id: String,
    val community: Community,
    val author: User,
    val title: String,
    val content: String,
    val imageUrl: String = "",
    val upvotes: Int = 0,
    val commentsCount: Int = 0,
    val timestamp: String = "4h ago",
    val hasUpvoted: Boolean = false
)

data class ChatMessage(
    val id: String,
    val sender: User,
    val text: String,
    val timestamp: String,
    val isMe: Boolean = false,
    val isVoiceNote: Boolean = false,
    val voiceDurationSeconds: Int = 0,
    val mediaUrl: String = "",
    val reactions: List<String> = emptyList()
)

data class Conversation(
    val id: String,
    val participant: User,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int = 0,
    val isOnline: Boolean = false,
    val isGroup: Boolean = false,
    val groupName: String = "",
    val groupMembersCount: Int = 0
)

enum class NotificationCategory {
    ALL,
    LIKES,
    COMMENTS,
    MENTIONS,
    REPOSTS,
    LIVE,
    FOLLOW,
    SYSTEM
}

data class NotificationItem(
    val id: String,
    val category: NotificationCategory,
    val actor: User,
    val message: String,
    val timestamp: String,
    val targetThumbnail: String = "",
    val isRead: Boolean = false,
    val actionLabel: String = ""
)

data class CreatorMetric(
    val label: String,
    val value: String,
    val changePercent: String,
    val isPositive: Boolean
)

data class CreatorAnalytics(
    val totalViews: String = "1.42M",
    val watchHours: String = "48.6K",
    val followersGained: String = "+12,480",
    val netRevenue: String = "$3,842.50",
    val viewsChartData: List<Float> = listOf(22f, 35f, 45f, 40f, 65f, 85f, 92f, 110f, 130f, 125f, 142f),
    val revenueBreakdown: Map<String, Float> = mapOf(
        "Subscriptions" to 45f,
        "Tips & Gifts" to 25f,
        "Brand Deals" to 20f,
        "Marketplace" to 10f
    )
)
