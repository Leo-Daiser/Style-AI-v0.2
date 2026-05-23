package com.example.styleai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgument
import androidx.navigation.compose.*
import com.example.styleai.data.repository.BillingRepositoryImpl
import com.example.styleai.data.repository.SavedDecisionDataStoreRepository
import com.example.styleai.data.repository.StyleRepositoryImpl
import com.example.styleai.data.repository.WardrobeDataStoreRepository
import com.example.styleai.data.repository.WishlistDataStoreRepository
import com.example.styleai.domain.decisions.SavedDecisionRepository
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.model.StyleReport
import com.example.styleai.domain.repository.BillingRepository
import com.example.styleai.domain.repository.StyleRepository
import com.example.styleai.domain.wardrobe.WardrobeRepository
import com.example.styleai.domain.wishlist.WishlistRepository
import com.example.styleai.feature.decisions.DecisionsScreen
import com.example.styleai.feature.decisions.DecisionsViewModel
import com.example.styleai.feature.onboarding.*
import com.example.styleai.feature.paywall.PaywallScreen
import com.example.styleai.feature.paywall.PaywallViewModel
import com.example.styleai.feature.profile.ProfileScreen
import com.example.styleai.feature.profile.ProfileViewModel
import com.example.styleai.feature.report.ReportScreen
import com.example.styleai.feature.upload.UploadScreen
import com.example.styleai.feature.upload.UploadViewModel
import com.example.styleai.feature.visualization.VisualizationScreen
import com.example.styleai.feature.visualization.VisualizationViewModel
import com.example.styleai.feature.home.HomeScreen
import com.example.styleai.feature.home.HomeViewModel
import com.example.styleai.feature.home.ShoppingCheckScreen
import com.example.styleai.feature.wardrobe.WardrobeScreen
import com.example.styleai.feature.wardrobe.WardrobeViewModel
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var styleRepository: StyleRepository
    private lateinit var billingRepository: BillingRepository
    private lateinit var savedDecisionRepository: SavedDecisionRepository
    private lateinit var wardrobeRepository: WardrobeRepository
    private lateinit var wishlistRepository: WishlistRepository

    private lateinit var activeReportState: StateFlow<StyleReport?>
    private lateinit var selectedLanguageState: StateFlow<AppLanguage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize our persistent standard repositories with App Context
        styleRepository = StyleRepositoryImpl(applicationContext)
        billingRepository = BillingRepositoryImpl(applicationContext)
        savedDecisionRepository = SavedDecisionDataStoreRepository(applicationContext)
        wardrobeRepository = WardrobeDataStoreRepository(applicationContext)
        wishlistRepository = WishlistDataStoreRepository(applicationContext)

        // State flows from repositories compiled tightly under lifecycleScope
        activeReportState = styleRepository.getActiveReport().stateIn(
            lifecycleScope,
            SharingStarted.Eagerly,
            null
        )
        selectedLanguageState = styleRepository.getSelectedLanguage().stateIn(
            lifecycleScope,
            SharingStarted.Eagerly,
            AppLanguage.EN
        )

        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "splash") {
                    composable("splash") {
                        SplashScreen(
                            onSplashFinished = {
                                lifecycleScope.launch {
                                    val onboardingCompleted = styleRepository.isOnboardingCompleted().first()
                                    if (!onboardingCompleted) {
                                        navController.navigate("onboarding") {
                                            popUpTo("splash") { inclusive = true }
                                        }
                                    } else {
                                        val consent = styleRepository.getConsentState().first()
                                        if (!consent.isFullyConsented) {
                                            navController.navigate("consent") {
                                                popUpTo("splash") { inclusive = true }
                                            }
                                        } else {
                                            navController.navigate("main") {
                                                popUpTo("splash") { inclusive = true }
                                            }
                                        }
                                    }
                                }
                            }
                        )
                    }

                    composable("onboarding") {
                        val onboardingViewModel = remember { OnboardingViewModel(styleRepository) }
                        OnboardingScreen(
                            viewModel = onboardingViewModel,
                            onOnboardingFinished = {
                                navController.navigate("consent") {
                                    popUpTo("onboarding") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("consent") {
                        val onboardingViewModel = remember { OnboardingViewModel(styleRepository) }
                        ConsentScreen(
                            viewModel = onboardingViewModel,
                            onConsentApproved = {
                                navController.navigate("main") {
                                    popUpTo("consent") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("upload") {
                        val uploadViewModel = remember { UploadViewModel(styleRepository) }
                        UploadScreen(
                            viewModel = uploadViewModel,
                            onNavigateToReport = {
                                navController.navigate("report_detail") {
                                    popUpTo("upload") { inclusive = true }
                                }
                            },
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }

                    composable(
                        route = "main?tab={tab}",
                        arguments = listOf(navArgument("tab") { defaultValue = "home" })
                    ) { backStackEntry ->
                        val initialTab = when (backStackEntry.arguments?.getString("tab")) {
                            "wardrobe" -> 1
                            "decisions" -> 2
                            "looks" -> 3
                            "profile" -> 4
                            else -> 0
                        }
                        DashboardHostScreen(
                            styleRepository = styleRepository,
                            billingRepository = billingRepository,
                            savedDecisionRepository = savedDecisionRepository,
                            wardrobeRepository = wardrobeRepository,
                            wishlistRepository = wishlistRepository,
                            activeReportState = activeReportState,
                            selectedLanguageState = selectedLanguageState,
                            initialTab = initialTab,
                            onNavigateToPaywall = {
                                navController.navigate("paywall")
                            },
                            onResetOnboarding = {
                                navController.navigate("onboarding") {
                                    popUpTo("main") { inclusive = true }
                                }
                            },
                            onNavigateToUpload = {
                                navController.navigate("upload")
                            },
                            onNavigateToShoppingCheck = {
                                navController.navigate("shopping_check")
                            },
                            onNavigateToReportDetail = {
                                navController.navigate("report_detail")
                            }
                        )
                    }

                    composable("report_detail") {
                        ReportScreen(
                            reportState = activeReportState,
                            selectedLanguage = selectedLanguageState,
                            onToggleGapCompleted = { gapId -> },
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }

                    composable("shopping_check") {
                        ShoppingCheckScreen(
                            currentLanguageState = selectedLanguageState,
                            savedDecisionRepository = savedDecisionRepository,
                            wardrobeRepository = wardrobeRepository,
                            wishlistRepository = wishlistRepository,
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            onViewDecisions = {
                                navController.navigate("main?tab=decisions") {
                                    popUpTo("shopping_check") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("paywall") {
                        val paywallViewModel = remember { PaywallViewModel(billingRepository, styleRepository) }
                        PaywallScreen(
                            viewModel = paywallViewModel,
                            onDismiss = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardHostScreen(
    styleRepository: StyleRepository,
    billingRepository: BillingRepository,
    savedDecisionRepository: SavedDecisionRepository,
    wardrobeRepository: WardrobeRepository,
    wishlistRepository: WishlistRepository,
    activeReportState: StateFlow<StyleReport?>,
    selectedLanguageState: StateFlow<AppLanguage>,
    initialTab: Int,
    onNavigateToPaywall: () -> Unit,
    onResetOnboarding: () -> Unit,
    onNavigateToUpload: () -> Unit,
    onNavigateToShoppingCheck: () -> Unit,
    onNavigateToReportDetail: () -> Unit
) {
    var activeTab by remember { mutableStateOf(initialTab) }
    LaunchedEffect(initialTab) {
        activeTab = initialTab
    }
    val currentLanguage by selectedLanguageState.collectAsState()
    val tabs = if (currentLanguage == AppLanguage.RU) {
        listOf("Главная", "Гардероб", "Решения", "Образы", "Профиль")
    } else {
        listOf("Home", "Wardrobe", "Decisions", "Looks", "Profile")
    }
    Scaffold(
        containerColor = Color(0xFFF5F5F7),
        bottomBar = {
            Column {
                Divider(color = Color(0xFFE5E5E5), thickness = 1.dp)
                NavigationBar(
                    containerColor = Color(0xFFF5F5F7),
                    tonalElevation = 0.dp
                ) {
                    tabs.forEachIndexed { index, label ->
                        val selected = activeTab == index
                        NavigationBarItem(
                            selected = selected,
                            onClick = { activeTab = index },
                            icon = {},
                            label = {
                                Text(
                                    text = label,
                                    color = if (selected) Color(0xFF222222) else Color(0xFF737373),
                                    fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF222222),
                                selectedTextColor = Color(0xFF222222),
                                unselectedIconColor = Color(0xFF737373),
                                unselectedTextColor = Color(0xFF737373),
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF5F5F7))
        ) {
            when (activeTab) {
                0 -> {
                    val homeViewModel = remember {
                        HomeViewModel(styleRepository, billingRepository)
                    }
                    HomeScreen(
                        viewModel = homeViewModel,
                        onNavigateToShoppingCheck = onNavigateToShoppingCheck,
                        onNavigateToUpload = onNavigateToUpload,
                        onNavigateToReportDetail = onNavigateToReportDetail,
                        onNavigateToPaywall = onNavigateToPaywall,
                        onSwitchTab = { targetTab -> activeTab = targetTab }
                    )
                }
                1 -> {
                    val wardrobeViewModel = remember { WardrobeViewModel(styleRepository, wardrobeRepository, wishlistRepository) }
                    WardrobeScreen(
                        viewModel = wardrobeViewModel,
                        onCheckSimilarItem = onNavigateToShoppingCheck
                    )
                }
                2 -> {
                    val decisionsViewModel = remember {
                        DecisionsViewModel(styleRepository, savedDecisionRepository, wardrobeRepository, wishlistRepository)
                    }
                    DecisionsScreen(
                        viewModel = decisionsViewModel,
                        onCheckItem = onNavigateToShoppingCheck
                    )
                }
                3 -> {
                    val visualizationViewModel = remember {
                        VisualizationViewModel(styleRepository, billingRepository)
                    }
                    VisualizationScreen(
                        viewModel = visualizationViewModel,
                        onNavigateToPaywall = onNavigateToPaywall
                    )
                }
                4 -> {
                    val profileViewModel = remember { ProfileViewModel(styleRepository) }
                    ProfileScreen(
                        viewModel = profileViewModel,
                        onNavigateToUpload = onNavigateToUpload,
                        onNavigateBackToOnboarding = onResetOnboarding
                    )
                }
            }
        }
    }
}
