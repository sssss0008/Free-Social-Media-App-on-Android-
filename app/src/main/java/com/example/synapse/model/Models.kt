package com.example.synapse.model

data class User(
    val id: String,
    val name: String,
    val handle: String,
    val avatarUrl: String,
    val coverUrl: String = "",
    val isVerified: Boolean = false,
    val isCreator: Boolean = false,
    val bio: String = "",
    val category: String = "Digital Creator",
    val website: String = "",
    val location: String = "",
    val followerCount: Int = 0,
    val followingCount: Int = 0,
    val subscribersCount: Int = 0,
    val totalViews: Long = 0L,
    val isFollowing: Boolean = false,
    val isSubscribed: Boolean = false
)

enum class PostType {
    SHORT_VIDEO,
    LONG_VIDEO,
    PHOTO,
    CAROUSEL,
    TEXT_THREAD,
    POLL,
    LINK,
    LIVE
}

data class PollOption(
    val id: Int,
    val text: String,
    val votes: Int
)

data class AudioTrack(
    val id: String,
    val title: String,
    val artist: String,
    val coverUrl: String = ""
)

data class Post(
    val id: String,
    val creator: User,
    val type: PostType,
    val caption: String,
    val tags: List<String> = emptyList(),
    val location: String = "",
    val timestamp: String,
    val mediaUrls: List<String> = emptyList(),
    val videoDuration: String = "",
    val videoTitle: String = "",
    val audioTrack: AudioTrack? = null,
    val pollQuestion: String = "",
    val pollOptions: List<PollOption> = emptyList(),
    val userVotedOptionIndex: Int? = null,
    val linkUrl: String = "",
    val linkTitle: String = "",
    val linkDomain: String = "",
    val linkThumbnail: String = "",
    val isLive: Boolean = false,
    val liveViewerCount: Int = 0,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val comments: List<Comment> = emptyList(),
    val repostsCount: Int = 0,
    val viewsCount: Long = 0L,
    val isLiked: Boolean = false,
    val isSaved: Boolean = false,
    val isReposted: Boolean = false
)

data class Story(
    val id: String,
    val user: User,
    val mediaUrl: String,
    val isSeen: Boolean = false,
    val isOwnStory: Boolean = false,
    val timestamp: String = "2h ago",
    val caption: String = ""
)

data class Comment(
    val id: String,
    val user: User,
    val text: String,
    val timestamp: String,
    val likesCount: Int = 0,
    val isLiked: Boolean = false,
    val isPinned: Boolean = false,
    val isCreatorBadge: Boolean = false,
    val replies: List<Comment> = emptyList()
)
