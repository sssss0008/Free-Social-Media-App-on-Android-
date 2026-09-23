package com.example.synapse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.synapse.model.Post
import com.example.synapse.model.PostType
import com.example.ui.theme.*

data class CreationOption(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val gradient: Brush,
    val postType: PostType
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCenterSheet(
    onDismiss: () -> Unit,
    onPublishPost: (PostType, String, String) -> Unit // type, caption, tags
) {
    var selectedOption by remember { mutableStateOf<CreationOption?>(null) }
    var captionText by remember { mutableStateOf("") }
    var tagsText by remember { mutableStateOf("") }
    var pollQuestionText by remember { mutableStateOf("") }

    val options = remember {
        listOf(
            CreationOption("Short Reel", "Immersive vertical video", Icons.Default.Bolt, Brush.linearGradient(listOf(Color(0xFF06B6D4), Color(0xFF3B82F6))), PostType.SHORT_VIDEO),
            CreationOption("Visual Post", "Photos & multi-carousels", Icons.Default.PhotoLibrary, Brush.linearGradient(listOf(Color(0xFF8B5CF6), Color(0xFFEC4899))), PostType.PHOTO),
            CreationOption("Thought / Thread", "Rich text & discourse", Icons.Default.Notes, Brush.linearGradient(listOf(Color(0xFF10B981), Color(0xFF06B6D4))), PostType.TEXT_THREAD),
            CreationOption("Interactive Poll", "Gather community consensus", Icons.Default.Poll, Brush.linearGradient(listOf(Color(0xFFF59E0B), Color(0xFFEF4444))), PostType.POLL),
            CreationOption("Go LIVE", "Stream to global audience", Icons.Default.Sensors, SynapseLiveGradient, PostType.LIVE),
            CreationOption("Long Video", "High-res deep dive episode", Icons.Default.PlayCircle, Brush.linearGradient(listOf(Color(0xFF6366F1), Color(0xFFA855F7))), PostType.LONG_VIDEO),
            CreationOption("Story", "24-hour visual moment", Icons.Default.HistoryToggleOff, SynapseStoryGradient, PostType.PHOTO),
            CreationOption("Visual Board", "Curate aesthetic collection", Icons.Default.DashboardCustomize, Brush.linearGradient(listOf(Color(0xFF0284C7), Color(0xFF7C3AED))), PostType.PHOTO)
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = Modifier.testTag("create_center_sheet")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = if (selectedOption == null) "Create & Broadcast" else "Compose ${selectedOption?.title}",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (selectedOption == null) "Choose format to publish to your Synapse channels" else "Reach your followers and topic communities",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                if (selectedOption != null) {
                    TextButton(onClick = { selectedOption = null }) {
                        Text("Change", color = SynapseVioletPrimary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedOption == null) {
                // Creation Options Grid
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(options) { option ->
                        CreationOptionCard(
                            option = option,
                            onClick = { selectedOption = option }
                        )
                    }
                }
            } else {
                // Composer View for Selected Option
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (selectedOption?.postType == PostType.POLL) {
                        OutlinedTextField(
                            value = pollQuestionText,
                            onValueChange = { pollQuestionText = it },
                            label = { Text("Poll Question") },
                            placeholder = { Text("Ask something insightful...") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp)
                        )
                    }

                    OutlinedTextField(
                        value = captionText,
                        onValueChange = { captionText = it },
                        label = { Text("Caption / Body") },
                        placeholder = { Text("Share what you're imagining, building or feeling...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .testTag("create_caption_input"),
                        shape = RoundedCornerShape(14.dp)
                    )

                    OutlinedTextField(
                        value = tagsText,
                        onValueChange = { tagsText = it },
                        label = { Text("Tags (comma separated)") },
                        placeholder = { Text("design, tech, motion, architecture") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )

                    // Publishing Button
                    Button(
                        onClick = {
                            selectedOption?.let {
                                onPublishPost(it.postType, captionText.ifEmpty { "New creative update!" }, tagsText)
                            }
                            onDismiss()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("publish_post_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SynapseVioletPrimary
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.RocketLaunch, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Publish to Synapse", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun CreationOptionCard(
    option: CreationOption,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .testTag("create_option_${option.title.replace(" ", "_")}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(option.gradient),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = option.icon,
                    contentDescription = option.title,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = option.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = option.subtitle,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1
                )
            }
        }
    }
}
