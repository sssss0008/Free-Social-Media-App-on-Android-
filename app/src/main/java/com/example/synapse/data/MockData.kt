package com.example.synapse.data

import com.example.synapse.model.*

object MockData {

    val currentUser = User(
        id = "u_current",
        name = "Aiden Vance",
        handle = "@aidenvance",
        avatarUrl = "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=400&q=80",
        coverUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?auto=format&fit=crop&w=1200&q=80",
        isVerified = true,
        isCreator = true,
        bio = "Synthesizing design, generative motion & soundscapes. Founder @SynapseLab ✨",
        category = "Creative Technologist",
        website = "https://aidenvance.design",
        location = "Tokyo • San Francisco",
        followerCount = 42800,
        followingCount = 684,
        subscribersCount = 2840,
        totalViews = 1850000L
    )

    val creatorElena = User(
        id = "u_elena",
        name = "Elena Rostova",
        handle = "@elenarostova",
        avatarUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=400&q=80",
        coverUrl = "https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=1200&q=80",
        isVerified = true,
        isCreator = true,
        bio = "Cinematic documentary filmmaker & visual explorer 🎬 Exploring quiet corners of Earth.",
        category = "Filmmaker",
        followerCount = 184500,
        followingCount = 420,
        subscribersCount = 14200,
        totalViews = 8900000L,
        isFollowing = true
    )

    val creatorKaelen = User(
        id = "u_kaelen",
        name = "Kaelen Cross",
        handle = "@kaelencross",
        avatarUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=400&q=80",
        isVerified = true,
        isCreator = true,
        bio = "Hardware hacker & ambient modular sound designer 🎛️ Building real-time audio devices.",
        category = "Audio Engineer",
        followerCount = 68200,
        followingCount = 310,
        subscribersCount = 3900,
        isFollowing = false
    )

    val creatorMaya = User(
        id = "u_maya",
        name = "Maya Lin",
        handle = "@mayacodes",
        avatarUrl = "https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&w=400&q=80",
        isVerified = true,
        isCreator = true,
        bio = "Building intelligent systems & micro-interactions. Kotlin & Compose lover 🚀",
        category = "Software Architect",
        followerCount = 95400,
        followingCount = 512,
        isFollowing = true
    )

    val creatorTheo = User(
        id = "u_theo",
        name = "Theo Wright",
        handle = "@theowright",
        avatarUrl = "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&w=400&q=80",
        isVerified = false,
        bio = "Urban brutalism, architecture archiving & analogue photography 📷",
        category = "Photographer",
        followerCount = 21400,
        followingCount = 180,
        isFollowing = true
    )

    val stories = listOf(
        Story(
            id = "s_own",
            user = currentUser,
            mediaUrl = "https://images.unsplash.com/photo-1518770660439-4636190af475?auto=format&fit=crop&w=800&q=80",
            isSeen = false,
            isOwnStory = true,
            timestamp = "Just now",
            caption = "Studio session live setup"
        ),
        Story(
            id = "s_1",
            user = creatorElena,
            mediaUrl = "https://images.unsplash.com/photo-1469474968028-56623f02e42e?auto=format&fit=crop&w=800&q=80",
            isSeen = false,
            timestamp = "15m ago",
            caption = "Dawn over the northern fjords"
        ),
        Story(
            id = "s_2",
            user = creatorMaya,
            mediaUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?auto=format&fit=crop&w=800&q=80",
            isSeen = false,
            timestamp = "1h ago",
            caption = "New generative shader experiment running at 120fps!"
        ),
        Story(
            id = "s_3",
            user = creatorKaelen,
            mediaUrl = "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?auto=format&fit=crop&w=800&q=80",
            isSeen = true,
            timestamp = "3h ago",
            caption = "Patch cables everywhere"
        ),
        Story(
            id = "s_4",
            user = creatorTheo,
            mediaUrl = "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?auto=format&fit=crop&w=800&q=80",
            isSeen = true,
            timestamp = "6h ago",
            caption = "Shadows in Shinjuku"
        )
    )

    val samplePosts = listOf(
        // 1. Short Form Video Post
        Post(
            id = "p_short_1",
            creator = creatorElena,
            type = PostType.SHORT_VIDEO,
            caption = "Capturing the midnight auroras over Tromsø with the new anamorphic rig. Turn sound on! 🌌✨",
            tags = listOf("cinematography", "norway", "auroraborealis", "visuals"),
            location = "Tromsø, Norway",
            timestamp = "2h ago",
            mediaUrls = listOf("https://images.unsplash.com/photo-1531366936337-7c912a4589a7?auto=format&fit=crop&w=1080&q=80"),
            videoDuration = "0:38",
            audioTrack = AudioTrack("a_1", "Nordic Reverie (Ambient Mix)", "Elena Rostova", "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?auto=format&fit=crop&w=200&q=80"),
            likesCount = 38420,
            commentsCount = 1420,
            repostsCount = 890,
            viewsCount = 142000L,
            isLiked = true
        ),
        // 2. Interactive Poll Post
        Post(
            id = "p_poll_1",
            creator = creatorMaya,
            type = PostType.POLL,
            caption = "Quick poll for creative engineers: When designing future generative UI interfaces, which navigation paradigm gives the most fluid sensory flow?",
            tags = listOf("design", "futuretech", "ux", "mobile"),
            timestamp = "4h ago",
            pollQuestion = "Which navigation pattern feels most natural for multi-modal apps?",
            pollOptions = listOf(
                PollOption(1, "Radial Gesture Hub (Thumb wheel)", 420),
                PollOption(2, "Adaptive Context Dock (M3 bottom rail)", 810),
                PollOption(3, "Spatial Pan & Zoom Canvas", 290)
            ),
            userVotedOptionIndex = 1,
            likesCount = 2840,
            commentsCount = 312,
            repostsCount = 142
        ),
        // 3. Photo Carousel Post
        Post(
            id = "p_carousel_1",
            creator = creatorTheo,
            type = PostType.CAROUSEL,
            caption = "Monolithic geometries from Berlin to Seoul. 35mm film scans, unedited grain and natural daylight contrast. Swipe to see the interior cathedral curves. 🏛️",
            tags = listOf("architecture", "minimalism", "35mm", "monolith"),
            location = "Berlin Modernist Archive",
            timestamp = "6h ago",
            mediaUrls = listOf(
                "https://images.unsplash.com/photo-1513694203232-719a280e022f?auto=format&fit=crop&w=1080&q=80",
                "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?auto=format&fit=crop&w=1080&q=80",
                "https://images.unsplash.com/photo-1506157786151-b8491531f063?auto=format&fit=crop&w=1080&q=80"
            ),
            likesCount = 15920,
            commentsCount = 428,
            repostsCount = 210,
            isSaved = true
        ),
        // 4. Long Form Video Post
        Post(
            id = "p_long_1",
            creator = creatorKaelen,
            type = PostType.LONG_VIDEO,
            caption = "A complete walkthrough of building a custom FPGA synthesizer with algorithmic physical modeling. Schematics & DSP code linked below.",
            tags = listOf("hardware", "synthesizer", "audioengineering", "tutorial"),
            timestamp = "12h ago",
            videoTitle = "Designing an Algorithmic DSP Synth from Raw Silicon",
            videoDuration = "24:18",
            mediaUrls = listOf("https://images.unsplash.com/photo-1598488035139-bdbb2231ce04?auto=format&fit=crop&w=1280&q=80"),
            viewsCount = 74500L,
            likesCount = 8920,
            commentsCount = 645,
            repostsCount = 412
        ),
        // 5. Rich Text Thread Post
        Post(
            id = "p_thread_1",
            creator = currentUser,
            type = PostType.TEXT_THREAD,
            caption = "The artificial divide between 'video apps', 'photo feeds', and 'discussion threads' was always an accident of early smartphone bandwidth.\n\nIn Synapse, your voice, your visuals, your boards, and your code live in one continuous neural canvas. When tools adapt to your flow instead of siloed algorithms, real community emerges. What are you building today?",
            tags = listOf("manifesto", "synapse", "futureofsocial", "indieweb"),
            timestamp = "1d ago",
            likesCount = 4520,
            commentsCount = 512,
            repostsCount = 630,
            isLiked = false
        ),
        // 6. Live Broadcast Stream Post
        Post(
            id = "p_live_1",
            creator = creatorElena,
            type = PostType.LIVE,
            caption = "LIVE: Editing the 8K footage from Svalbard expedition with live sound design collaboration with @kaelencross! Join chat & drop questions.",
            tags = listOf("live", "svalbard", "colorgrading", "editing"),
            timestamp = "Happening now",
            isLive = true,
            liveViewerCount = 4180,
            mediaUrls = listOf("https://images.unsplash.com/photo-1518770660439-4636190af475?auto=format&fit=crop&w=1080&q=80"),
            likesCount = 19400,
            commentsCount = 2810
        )
    )

    val sampleComments = listOf(
        Comment(
            id = "c_1",
            user = creatorMaya,
            text = "The dynamic range and color separation in the low-light shots are breathtaking Elena! Which LUT was your base?",
            timestamp = "1h ago",
            likesCount = 142,
            isLiked = true,
            isPinned = true,
            isCreatorBadge = false,
            replies = listOf(
                Comment(
                    id = "c_1_1",
                    user = creatorElena,
                    text = "Thank you Maya! Custom Kodak 5219 emulation with a slight teal shift in the shadows. Sending you the cube file!",
                    timestamp = "45m ago",
                    likesCount = 88,
                    isLiked = false,
                    isCreatorBadge = true
                )
            )
        ),
        Comment(
            id = "c_2",
            user = creatorKaelen,
            text = "The audio mastering pairs with the motion so gracefully. That sub-bass pulse at 0:24 is immaculate.",
            timestamp = "30m ago",
            likesCount = 64
        ),
        Comment(
            id = "c_3",
            user = creatorTheo,
            text = "Obsessed with that frame composition through the icy archway. Frame worthy.",
            timestamp = "15m ago",
            likesCount = 29
        )
    )

    val visualPins = listOf(
        Pin(
            id = "pin_1",
            title = "Cybernetic Bioluminescent Terrarium Architecture",
            imageUrl = "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=800&q=80",
            creator = creatorMaya,
            heightRatio = 1.45f,
            savesCount = 1420,
            tags = listOf("concept", "3d", "architecture")
        ),
        Pin(
            id = "pin_2",
            title = "Brutalist Concrete Horizon & Minimal Shadowplay",
            imageUrl = "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?auto=format&fit=crop&w=800&q=80",
            creator = creatorTheo,
            heightRatio = 1.15f,
            savesCount = 890,
            tags = listOf("brutalism", "minimal")
        ),
        Pin(
            id = "pin_3",
            title = "Vintage Analogue Oscilloscope Harmonic Patterns",
            imageUrl = "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?auto=format&fit=crop&w=800&q=80",
            creator = creatorKaelen,
            heightRatio = 1.6f,
            savesCount = 2150,
            tags = listOf("synth", "oscilloscope", "waves")
        ),
        Pin(
            id = "pin_4",
            title = "Emerald Aurora Reflections Across Arctic Ice",
            imageUrl = "https://images.unsplash.com/photo-1531366936337-7c912a4589a7?auto=format&fit=crop&w=800&q=80",
            creator = creatorElena,
            heightRatio = 1.3f,
            savesCount = 3840,
            tags = listOf("nature", "aurora", "night")
        ),
        Pin(
            id = "pin_5",
            title = "Futuristic Workspace with Custom Mechanical Keyboard",
            imageUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?auto=format&fit=crop&w=800&q=80",
            creator = currentUser,
            heightRatio = 1.25f,
            savesCount = 1920,
            tags = listOf("workspace", "minimal", "tech")
        ),
        Pin(
            id = "pin_6",
            title = "Tokyo Neon Rain Alleyways in 35mm",
            imageUrl = "https://images.unsplash.com/photo-1503899036084-c55cdd92da26?auto=format&fit=crop&w=800&q=80",
            creator = creatorTheo,
            heightRatio = 1.55f,
            savesCount = 4210,
            tags = listOf("tokyo", "street", "cyberpunk")
        )
    )

    val sampleBoards = listOf(
        Board(
            id = "b_1",
            title = "Generative Aesthetics & Audio",
            description = "Collection of spatial algorithms, shader math & acoustic models",
            coverUrl = "https://images.unsplash.com/photo-1518770660439-4636190af475?auto=format&fit=crop&w=800&q=80",
            pinCount = 48,
            owner = currentUser,
            collaboratorAvatars = listOf(
                creatorMaya.avatarUrl,
                creatorKaelen.avatarUrl
            )
        ),
        Board(
            id = "b_2",
            title = "Nordic Expedition Palettes",
            description = "Color grades, moody skies, and high-latitude lighting references",
            coverUrl = "https://images.unsplash.com/photo-1469474968028-56623f02e42e?auto=format&fit=crop&w=800&q=80",
            pinCount = 124,
            owner = creatorElena
        ),
        Board(
            id = "b_3",
            title = "Monolithic Brutalism",
            description = "Raw concrete structures and monumental architectural forms worldwide",
            coverUrl = "https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?auto=format&fit=crop&w=800&q=80",
            pinCount = 86,
            owner = creatorTheo
        )
    )

    val sampleCommunities = listOf(
        Community(
            id = "comm_1",
            name = "Creative Technologists",
            handle = "c/creativetech",
            category = "Technology & Design",
            description = "Where engineering meets high art. Shaders, physical computing, Kotlin multiplatform and sensory interfaces.",
            bannerUrl = "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?auto=format&fit=crop&w=1000&q=80",
            iconUrl = "https://images.unsplash.com/photo-1518770660439-4636190af475?auto=format&fit=crop&w=200&q=80",
            memberCount = 142800,
            onlineCount = 3840,
            isJoined = true,
            rules = listOf(
                "Share source code or visual breakdown when posting work",
                "Constructive technical feedback only",
                "No promotional spam or unsolicited pitches"
            )
        ),
        Community(
            id = "comm_2",
            name = "Cinematic Visualists",
            handle = "c/cinematography",
            category = "Film & Media",
            description = "An open guild of directors, colorists, and DP explorers dissecting lens choices, lighting rigs and raw storytelling.",
            bannerUrl = "https://images.unsplash.com/photo-1485846234645-a62644f84728?auto=format&fit=crop&w=1000&q=80",
            iconUrl = "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=200&q=80",
            memberCount = 89400,
            onlineCount = 1920,
            isJoined = true,
            rules = listOf(
                "Include camera, lens, and lighting setup details",
                "Respect original artist attribution"
            )
        ),
        Community(
            id = "comm_3",
            name = "Sound & Synthesis",
            handle = "c/synthesizers",
            category = "Audio Production",
            description = "Eurorack enthusiasts, DSP coders, and sound sculptors pushing frequencies into unexplored acoustic territories.",
            bannerUrl = "https://images.unsplash.com/photo-1598488035139-bdbb2231ce04?auto=format&fit=crop&w=1000&q=80",
            iconUrl = "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=200&q=80",
            memberCount = 61200,
            onlineCount = 1140,
            isJoined = false
        )
    )

    val sampleCommunityPosts = listOf(
        CommunityPost(
            id = "cp_1",
            community = sampleCommunities[0],
            author = creatorMaya,
            title = "Rendering 60k particles in Jetpack Compose Canvas with sub-millisecond frame times",
            content = "Sharing our latest benchmark. By bypassing allocation inside DrawScope and caching FloatBuffer transforms on native memory, we achieved zero frame-drops even during intense user pinch gestures. Complete GitHub repo linked in comments!",
            imageUrl = "https://images.unsplash.com/photo-1550745165-9bc0b252726f?auto=format&fit=crop&w=800&q=80",
            upvotes = 542,
            commentsCount = 68,
            timestamp = "3h ago",
            hasUpvoted = true
        ),
        CommunityPost(
            id = "cp_2",
            community = sampleCommunities[1],
            author = creatorElena,
            title = "How we protected our camera sensors at -38°C in northern Norway",
            content = "Standard lithium batteries shut down within 12 minutes in arctic cold. Here is our custom heated V-mount harness that kept dual RED Komodo rigs powered for 8 uninterrupted hours under the northern lights.",
            imageUrl = "https://images.unsplash.com/photo-1531366936337-7c912a4589a7?auto=format&fit=crop&w=800&q=80",
            upvotes = 890,
            commentsCount = 114,
            timestamp = "5h ago",
            hasUpvoted = false
        )
    )

    val sampleConversations = listOf(
        Conversation(
            id = "conv_1",
            participant = creatorElena,
            lastMessage = "Sent you the 8K proxy clips via Synapse Cloud! Let me know if the color grading matches.",
            timestamp = "12:45 PM",
            unreadCount = 2,
            isOnline = true
        ),
        Conversation(
            id = "conv_2",
            participant = creatorMaya,
            lastMessage = "The particle shader repo is live. Check out line 142 for the vector math!",
            timestamp = "10:15 AM",
            unreadCount = 0,
            isOnline = true
        ),
        Conversation(
            id = "conv_3",
            participant = creatorKaelen,
            lastMessage = "Voice message (0:42)",
            timestamp = "Yesterday",
            unreadCount = 0,
            isOnline = false
        ),
        Conversation(
            id = "conv_4",
            participant = creatorTheo,
            lastMessage = "Let's do the gallery walk in Ginza next Thursday.",
            timestamp = "Oct 12",
            unreadCount = 0,
            isOnline = false
        )
    )

    val sampleChatMessages = listOf(
        ChatMessage(
            id = "m_1",
            sender = creatorElena,
            text = "Hey Aiden! Did you get a chance to see the Tromsø midnight sun test sequence?",
            timestamp = "12:30 PM",
            isMe = false
        ),
        ChatMessage(
            id = "m_2",
            sender = currentUser,
            text = "Elena, the dynamic range is staggering. The glow around the ice shelf looks almost like sci-fi concept art!",
            timestamp = "12:35 PM",
            isMe = true
        ),
        ChatMessage(
            id = "m_3",
            sender = creatorElena,
            text = "I recorded a quick audio note breaking down the lens filter we used on the anamorphic body:",
            timestamp = "12:40 PM",
            isMe = false,
            isVoiceNote = true,
            voiceDurationSeconds = 38
        ),
        ChatMessage(
            id = "m_4",
            sender = creatorElena,
            text = "Sent you the 8K proxy clips via Synapse Cloud! Let me know if the color grading matches.",
            timestamp = "12:45 PM",
            isMe = false,
            reactions = listOf("❤️", "🔥")
        )
    )

    val sampleNotifications = listOf(
        NotificationItem(
            id = "n_1",
            category = NotificationCategory.LIKES,
            actor = creatorMaya,
            message = "liked your post 'The artificial divide between video apps...'",
            timestamp = "5m ago",
            isRead = false,
            actionLabel = "View"
        ),
        NotificationItem(
            id = "n_2",
            category = NotificationCategory.COMMENTS,
            actor = creatorElena,
            message = "replied: 'Custom Kodak 5219 emulation with a slight teal shift...'",
            timestamp = "25m ago",
            isRead = false,
            actionLabel = "Reply"
        ),
        NotificationItem(
            id = "n_3",
            category = NotificationCategory.LIVE,
            actor = creatorElena,
            message = "started a live broadcast: 'LIVE: Editing the 8K footage from Svalbard...'",
            timestamp = "1h ago",
            isRead = false,
            actionLabel = "Watch"
        ),
        NotificationItem(
            id = "n_4",
            category = NotificationCategory.FOLLOW,
            actor = creatorKaelen,
            message = "started following your creator profile and boards.",
            timestamp = "3h ago",
            isRead = true,
            actionLabel = "Follow back"
        ),
        NotificationItem(
            id = "n_5",
            category = NotificationCategory.SYSTEM,
            actor = currentUser,
            message = "Synapse Creator Milestone: You surpassed 1.8M total content views this month!",
            timestamp = "1d ago",
            isRead = true,
            actionLabel = "Studio"
        )
    )

    val creatorMetrics = listOf(
        CreatorMetric("Total Impressions", "1.85M", "+24.8%", true),
        CreatorMetric("Watch Hours", "48.6K", "+18.2%", true),
        CreatorMetric("New Subscribers", "2,840", "+32.1%", true),
        CreatorMetric("Est. Net Revenue", "$3,842", "+41.5%", true)
    )

    val sampleStories: List<Story> get() = stories
    val samplePins: List<Pin> get() = visualPins
}
