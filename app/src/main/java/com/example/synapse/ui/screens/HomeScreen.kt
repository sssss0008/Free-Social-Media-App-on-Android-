package com.example.synapse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.synapse.model.Post
import com.example.synapse.model.PostType
import com.example.synapse.model.Story
import com.example.synapse.ui.components.PostCard
import com.example.synapse.ui.components.StoryTray
import com.example.ui.theme.SynapseVioletPrimary

@Composable
fun HomeScreen(
    posts: List<Post>,
    stories: List<Story>,
    selectedCategory: String,
    onCategoryChange: (String) -> Unit,
    onStoryClick: (Story) -> Unit,
    onAddStoryClick: () -> Unit,
    onLikeToggle: (String) -> Unit,
    onSaveToggle: (String) -> Unit,
    onCommentClick: (Post) -> Unit,
    onShareClick: (Post) -> Unit,
    onCreatorClick: (String) -> Unit,
    onWatchVideoClick: (Post) -> Unit,
    onPollVote: (String, Int) -> Unit
) {
    val categories = remember {
        listOf("For You", "Following", "Friends", "Live", "Design", "Tech", "Cinematography", "Audio")
    }

    val filteredPosts = remember(posts, selectedCategory) {
        when (selectedCategory) {
            "For You" -> posts
            "Following" -> posts.filter { it.creator.isFollowing }
            "Live" -> posts.filter { it.isLive || it.type == PostType.LIVE }
            "Design" -> posts.filter { it.tags.any { t -> t.contains("design") || t.contains("architecture") } }
            "Tech" -> posts.filter { it.tags.any { t -> t.contains("tech") || t.contains("hardware") } }
            "Cinematography" -> posts.filter { it.tags.any { t -> t.contains("cinema") || t.contains("norway") } }
            "Audio" -> posts.filter { it.tags.any { t -> t.contains("audio") || t.contains("synth") } }
            else -> posts
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("home_feed_list"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Top Story Tray
        item(key = "story_tray") {
            StoryTray(
                stories = stories,
                onStoryClick = onStoryClick,
                onAddStoryClick = onAddStoryClick
            )
        }

        // Horizontal Category Pill Bar
        item(key = "category_bar") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    val isSelected = category == selectedCategory
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) SynapseVioletPrimary else MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clickable { onCategoryChange(category) }
                            .testTag("category_pill_$category")
                    ) {
                        Text(
                            text = category,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                        )
                    }
                }
            }
        }

        // Feed Posts
        items(filteredPosts, key = { it.id }) { post ->
            PostCard(
                post = post,
                onLikeToggle = onLikeToggle,
                onSaveToggle = onSaveToggle,
                onCommentClick = onCommentClick,
                onShareClick = onShareClick,
                onCreatorClick = onCreatorClick,
                onWatchVideoClick = onWatchVideoClick,
                onPollVote = onPollVote
            )
        }
    }
}
