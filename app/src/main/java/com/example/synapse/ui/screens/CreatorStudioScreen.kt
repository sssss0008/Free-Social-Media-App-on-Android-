package com.example.synapse.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.synapse.data.MockData
import com.example.synapse.model.CreatorMetric
import com.example.ui.theme.*

@Composable
fun CreatorStudioScreen(
    onBack: () -> Unit
) {
    var selectedPeriod by remember { mutableStateOf("28 Days") }
    val periods = remember { listOf("7 Days", "28 Days", "90 Days", "1 Year") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .testTag("creator_studio_screen")
    ) {
        // Studio Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onBackground)
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(
                        text = "Creator Studio",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Channel Analytics & Monetization Hub",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = SynapseVioletPrimary.copy(alpha = 0.15f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.Stars, contentDescription = null, tint = SynapseVioletPrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("PRO", color = SynapseVioletPrimary, fontWeight = FontWeight.Black, fontSize = 11.sp)
                }
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Period Selector
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    periods.forEach { period ->
                        val isSelected = period == selectedPeriod
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = if (isSelected) SynapseVioletPrimary else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier
                                .clickable { selectedPeriod = period }
                                .padding(vertical = 2.dp)
                        ) {
                            Text(
                                text = period,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            // Metrics Cards Grid (Impressions, Watch Hours, Subscribers, Revenue)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard(
                        metric = MockData.creatorMetrics[0],
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        metric = MockData.creatorMetrics[1],
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard(
                        metric = MockData.creatorMetrics[2],
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        metric = MockData.creatorMetrics[3],
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Real-Time Views Graph Card
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Audience Reach & Growth",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "142.8K views / day average",
                                    fontSize = 11.sp,
                                    color = SynapseCyanAccent
                                )
                            }
                            Icon(Icons.Default.TrendingUp, contentDescription = null, tint = SynapseEmeraldSuccess)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Custom Smooth Curve Chart in Canvas
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                        ) {
                            val data = listOf(20f, 32f, 48f, 42f, 65f, 78f, 72f, 95f, 110f, 128f, 142f)
                            val max = data.maxOrNull() ?: 1f
                            val stepX = size.width / (data.size - 1)

                            val path = Path()
                            val fillPath = Path()

                            data.forEachIndexed { i, value ->
                                val x = i * stepX
                                val y = size.height - (value / max * (size.height * 0.85f))
                                if (i == 0) {
                                    path.moveTo(x, y)
                                    fillPath.moveTo(x, size.height)
                                    fillPath.lineTo(x, y)
                                } else {
                                    path.lineTo(x, y)
                                    fillPath.lineTo(x, y)
                                }
                            }
                            fillPath.lineTo(size.width, size.height)
                            fillPath.close()

                            // Fill gradient
                            drawPath(
                                path = fillPath,
                                brush = Brush.verticalGradient(
                                    listOf(SynapseVioletPrimary.copy(alpha = 0.4f), Color.Transparent)
                                )
                            )

                            // Stroke line
                            drawPath(
                                path = path,
                                brush = SynapseBrandGradient,
                                style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                            )
                        }
                    }
                }
            }

            // Monetization & Payout Center
            item {
                Text(
                    text = "Monetization Streams",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            item {
                MonetizationItem(
                    title = "Creator Memberships",
                    description = "2,840 active monthly supporters ($4.99/mo)",
                    earnings = "$2,140.00",
                    icon = Icons.Default.CardMembership
                )
            }

            item {
                MonetizationItem(
                    title = "Live Gifts & Tips",
                    description = "Direct stream appreciation from 420 viewers",
                    earnings = "$980.50",
                    icon = Icons.Default.VolunteerActivism
                )
            }

            item {
                MonetizationItem(
                    title = "Brand Collaborations",
                    description = "Sponsored creative showcase with LensCraft Pro",
                    earnings = "$722.00",
                    icon = Icons.Default.Handshake
                )
            }
        }
    }
}

@Composable
private fun MetricCard(
    metric: CreatorMetric,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = metric.label,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = metric.value,
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = if (metric.isPositive) SynapseEmeraldSuccess.copy(alpha = 0.15f) else Color(0xFFEF4444).copy(alpha = 0.15f)
            ) {
                Text(
                    text = metric.changePercent,
                    color = if (metric.isPositive) SynapseEmeraldSuccess else Color(0xFFEF4444),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun MonetizationItem(
    title: String,
    description: String,
    earnings: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(SynapseVioletPrimary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = SynapseVioletPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = description,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                text = earnings,
                fontWeight = FontWeight.Black,
                fontSize = 15.sp,
                color = SynapseEmeraldSuccess
            )
        }
    }
}
