package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Synapse Brand Colors - Obsidian & Neon Cyber-Elegance
val SynapseObsidian = Color(0xFF090A10)
val SynapseDarkSurface = Color(0xFF11131E)
val SynapseDarkCard = Color(0xFF181B2A)
val SynapseDarkElevated = Color(0xFF22263B)
val SynapseDarkBorder = Color(0xFF2E3450)

// Vibrant Primary Accents
val SynapseVioletPrimary = Color(0xFF8B5CF6)
val SynapseVioletLight = Color(0xFFA78BFA)
val SynapseVioletDark = Color(0xFF6D28D9)

// Secondary & Accent Gradients
val SynapseCyanAccent = Color(0xFF06B6D4)
val SynapseCyanLight = Color(0xFF22D3EE)
val SynapsePinkAccent = Color(0xFFEC4899)
val SynapseRoseAccent = Color(0xFFF43F5E)
val SynapseAmberLive = Color(0xFFF59E0B)
val SynapseEmeraldSuccess = Color(0xFF10B981)

// Neutral Text & Tones
val SynapseTextPrimaryDark = Color(0xFFF8FAFC)
val SynapseTextSecondaryDark = Color(0xFF94A3B8)
val SynapseTextMutedDark = Color(0xFF64748B)

// Light Theme Palette
val SynapseLightBg = Color(0xFFF8FAFC)
val SynapseLightSurface = Color(0xFFFFFFFF)
val SynapseLightCard = Color(0xFFF1F5F9)
val SynapseLightBorder = Color(0xFFE2E8F0)
val SynapseTextPrimaryLight = Color(0xFF0F172A)
val SynapseTextSecondaryLight = Color(0xFF475569)

// Signature Gradients
val SynapseBrandGradient = Brush.linearGradient(
    colors = listOf(SynapseCyanAccent, SynapseVioletPrimary, SynapsePinkAccent)
)

val SynapseLiveGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFEF4444), Color(0xFFF97316))
)

val SynapseStoryGradient = Brush.sweepGradient(
    colors = listOf(
        SynapseCyanAccent,
        SynapseVioletPrimary,
        SynapsePinkAccent,
        SynapseAmberLive,
        SynapseCyanAccent
    )
)

val SynapseDarkCardGradient = Brush.verticalGradient(
    colors = listOf(SynapseDarkCard, SynapseDarkElevated)
)
