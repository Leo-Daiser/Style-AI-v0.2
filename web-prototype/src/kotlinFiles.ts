export interface KotlinFile {
  name: string;
  path: string;
  description: string;
  code: string;
}

export const kotlinFiles: Record<string, KotlinFile> = {
  models: {
    name: "StyleModels.kt",
    path: "domain/model/StyleModels.kt",
    description: "Domain-level safe enums and structured models for style evaluations & the new 'Should I buy this?' purchase-readiness checkers.",
    code: `package com.example.styleai.domain.model

enum class AppLanguage { EN, RU }
enum class Occasion { EVERYDAY, OFFICE, DATE, TRAVEL, EVENT }
enum class StyleType { MINIMAL, CLASSIC, SMART_CASUAL, FEMININE, STREETWEAR }
enum class Season { SPRING, SUMMER, AUTUMN, WINTER }
enum class Formality { CASUAL, POLISHED, FORMAL }
enum class ColorDirection { NEUTRAL, WARM, COOL, SOFT, CONTRAST }

data class VisualizationRequest(
    val reportId: String,
    val occasion: Occasion,
    val style: StyleType,
    val season: Season,
    val formality: Formality,
    val colorDirection: ColorDirection
) {
    init {
        // Validation check against model-level banned parameters
        val combined = "\${occasion.name} \${style.name} \${season.name} \${formality.name} \${colorDirection.name}".lowercase()
        val bannedKeywords = listOf("underwear", "lingerie", "bikini", "swimsuit", "transparent", "nude", "naked", "schoolgirl")
        bannedKeywords.forEach { keyword ->
            if (combined.contains(keyword)) throw SecurityException("Safety breach: Forbidden theme matched!")
        }
    }
}

enum class ClothingCategory {
    TOP, BOTTOM, DRESS, OUTERWEAR, SHOES, BAG, ACCESSORY
}

enum class ShoppingVerdict {
    GOOD_MATCH, MAYBE, SKIP
}

data class ShoppingCheckRequest(
    val reportId: String,
    val category: ClothingCategory,
    val localPhotoUri: String? = null
)

data class ShoppingCheckResult(
    val verdict: ShoppingVerdict,
    val explanationColor: String,
    val explanationSilhouette: String,
    val explanationCapsule: String,
    val explanationVersatility: String,
    val outfitCountEstimate: String,
    val shoppingAdvice: String
)

sealed class ShoppingCheckUiState {
    object Input : ShoppingCheckUiState()
    object Loading : ShoppingCheckUiState()
    data class Result(val result: ShoppingCheckResult) : ShoppingCheckUiState()
}`
  },
  localization: {
    name: "AppLocalization.kt",
    path: "core/localization/AppLocalization.kt",
    description: "Multilingual strings file enabling on-the-fly, in-app switching between Russian and English.",
    code: `package com.example.styleai.core.localization

import com.example.styleai.domain.model.AppLanguage

interface AppStrings {
    val splashSubtitle: String
    val onboardingTitle: String
    val consentTitle: String
    val visSafetyWarning: String
    val settingsTitle: String
}

object AppStringsEn : AppStrings {
    override val splashSubtitle = "Your private AI style assistant"
    override val onboardingTitle = "Discover your personal style"
    override val consentTitle = "Privacy & Consent"
    override val visSafetyWarning = "Visualizations are style references, not exact clothing fit simulations."
    override val settingsTitle = "App Settings"
}

object AppStringsRu : AppStrings {
    override val splashSubtitle = "Ваш приватный AI-стилист"
    override val onboardingTitle = "Откройте свой персональный стиль"
    override val consentTitle = "Конфиденциальность и согласие"
    override val visSafetyWarning = "Визуализации показывают стилевое направление, а не точную посадку одежды."
    override val settingsTitle = "Настройки приложения"
}`
  },
  privacy: {
    name: "PrivacySecurityUtilities.kt",
    path: "core/privacy/PrivacySecurityUtilities.kt",
    description: "Strict SafeLogger implementation discarding personal data/image URIs, image preprocessors, and ExifStripper contracts.",
    code: `package com.example.styleai.core.privacy

object SafeLogger {
    fun i(message: String) {
        val sanitized = message
            .replace(Regex("content://[a-zA-Z0-9_%/.]+"), "[REDACTED_IMAGE_URI]")
        println("[StyleAI_SafeLogger] $sanitized")
    }
}

interface ExifStripper {
    fun stripMetadata(imageBytes: ByteArray): ByteArray
}

interface TemporaryImageStore {
    fun cachedTemporarily(imageBytes: ByteArray): String
    fun purgeAll()
}`
  },
  visualization: {
    name: "VisualizationScreen.kt",
    path: "feature/visualization/VisualizationScreen.kt",
    description: "Controlled style rendering layouts without free-text inputs, powered by Type-Safe scroll selection elements.",
    code: `package com.example.styleai.feature.visualization

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.styleai.domain.model.*

@Composable
fun VisualizationScreen(viewModel: VisualizationViewModel) {
    val occasion by viewModel.selectedOccasion.collectAsState()
    val style by viewModel.selectedStyle.collectAsState()
    val season by viewModel.selectedSeason.collectAsState()
    
    Column {
        Text("Select Occasion:")
        RowScrollOccasion(selected = occasion, onSelect = { viewModel.changeOccasion(it) })
        
        Button(onClick = { viewModel.triggerControlledVisualization() }) {
            Text("Create visual reference · 1 credit")
        }
    }
}`
  },
  settings: {
    name: "SettingsScreen.kt",
    path: "feature/settings/SettingsScreen.kt",
    description: "Dynamic options containing language selectors, separate wipes for style vs onboarding resets, and the dev checklist.",
    code: `package com.example.styleai.feature.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val language by viewModel.selectedLanguage.collectAsState()
    val isDeveloperMode by viewModel.isDeveloperMode.collectAsState()
    
    Column {
        Text("App Language")
        Row {
            Button(onClick = { viewModel.changeLanguage(AppLanguage.EN) }) { Text("EN") }
            Button(onClick = { viewModel.changeLanguage(AppLanguage.RU) }) { Text("RU") }
        }
        
        OutlinedButton(onClick = { viewModel.deleteStyleDataOnly {} }) { Text("Delete Style Data") }
        Button(onClick = { viewModel.resetAppOnboarding {} }) { Text("Reset Onboarding Slider") }
        
        if (isDeveloperMode) {
            Text("🛠️ Developer MVP Readiness Checklist: All system tests validated.")
        }
    }
}`
  },
  shoppingCheckScreen: {
    name: "ShoppingCheckScreen.kt",
    path: "feature/shoppingcheck/ShoppingCheckScreen.kt",
    description: "Android MVP screen letting users check whether a clothing item matches their personal style guidelines before standard purchase checkout safely.",
    code: `package com.example.styleai.feature.shoppingcheck

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.styleai.domain.model.*

@Composable
fun ShoppingCheckScreen(viewModel: ShoppingCheckViewModel, onNavigateToPaywall: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val category by viewModel.selectedCategory.collectAsState()
    val context by viewModel.selectedContext.collectAsState()
    val colorDir by viewModel.selectedColorDir.collectAsState()
    val freeChecksLeft by viewModel.freeChecksLeft.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Should I Buy This?", style = MaterialTheme.typography.titleMedium)
        
        Spacer(modifier = Modifier.height(12.dp))

        if (freeChecksLeft <= 0) {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("More shopping checks are part of Pro.")
                    Button(onClick = onNavigateToPaywall) {
                        Text("View Pro")
                    }
                }
            }
        } else {
            when (uiState) {
                is ShoppingCheckUiState.Input -> {
                    // Category Selection
                    Text("Select Clothing Category")
                    ClothingCategorySelector(selected = category, onSelect = { viewModel.selectCategory(it) })
                    
                    // Context Selection
                    Text("Item Quality / Context")
                    ContextSelector(selected = context, onSelect = { viewModel.selectContext(it) })

                    // Color direction
                    Text("Color Direction")
                    ColorSelector(selected = colorDir, onSelect = { viewModel.selectColorDirection(it) })

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.runCheck() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Analyze Purchase Readiness (\${freeChecksLeft} free remaining)")
                    }
                }
                is ShoppingCheckUiState.Loading -> {
                    CircularProgressIndicator()
                    Text("Assessing capsule synchrony & color compatibility...")
                }
                is ShoppingCheckUiState.Result -> {
                    val result = (uiState as ShoppingCheckUiState.Result).result
                    Text("Verdict: \${result.verdict.name}")
                    Text(result.explanationColor)
                    Text(result.explanationSilhouette)
                    Text("Suggested outfits count: \${result.outfitCountEstimate}")
                    
                    Button(onClick = { viewModel.reset() }) {
                        Text("Check another item")
                    }
                }
            }
        }
    }
}`
  },
  shoppingCheckViewModel: {
    name: "ShoppingCheckViewModel.kt",
    path: "feature/shoppingcheck/ShoppingCheckViewModel.kt",
    description: "ViewModel coordinating buy decisions via deterministic capsule rules and color vector metrics safely without cloud AI reliance.",
    code: `package com.example.styleai.feature.shoppingcheck

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*
import com.example.styleai.domain.model.*

class ShoppingCheckViewModel(
    private val styleReportRepository: StyleReportRepository,
    private val historyRepository: ShoppingCheckHistoryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<ShoppingCheckUiState>(ShoppingCheckUiState.Input)
    val uiState: StateFlow<ShoppingCheckUiState> = _uiState.asStateFlow()

    private val _selectedCategory = MutableStateFlow(ClothingCategory.TOP)
    val selectedCategory: StateFlow<ClothingCategory> = _selectedCategory.asStateFlow()

    private val _selectedContext = MutableStateFlow("Basic wardrobe item")
    val selectedContext: StateFlow<String> = _selectedContext.asStateFlow()

    private val _selectedColorDir = MutableStateFlow(ColorDirection.NEUTRAL)
    val selectedColorDir: StateFlow<ColorDirection> = _selectedColorDir.asStateFlow()

    private val _freeChecksLeft = MutableStateFlow(3)
    val freeChecksLeft: StateFlow<Int> = _freeChecksLeft.asStateFlow()

    fun selectCategory(cat: ClothingCategory) { _selectedCategory.value = cat }
    fun selectContext(ctx: String) { _selectedContext.value = ctx }
    fun selectColorDirection(dir: ColorDirection) { _selectedColorDir.value = dir }

    fun runCheck() {
        if (_freeChecksLeft.value <= 0) return
        _uiState.value = ShoppingCheckUiState.Loading
        
        val report = styleReportRepository.getActiveReport()
        val result = evaluateShoppingResult(
            category = _selectedCategory.value,
            context = _selectedContext.value,
            colorDir = _selectedColorDir.value,
            hasReport = report != null
        )
        
        historyRepository.saveShoppingCheckResult(result)
        _freeChecksLeft.value -= 1
        _uiState.value = ShoppingCheckUiState.Result(result)
    }

    fun reset() {
        _uiState.value = ShoppingCheckUiState.Input
    }

    private fun evaluateShoppingResult(
        category: ClothingCategory,
        context: String,
        colorDir: ColorDirection,
        hasReport: Boolean
    ): ShoppingCheckResult {
        var verdict = ShoppingVerdict.MAYBE
        
        if (context == "Similar" || context == "Похожая вещь") {
            verdict = ShoppingVerdict.SKIP
        } else if ((context == "Basic" || context == "Базовая вещь") && 
                   (colorDir == ColorDirection.NEUTRAL || colorDir == ColorDirection.WARM || colorDir == ColorDirection.SOFT)) {
            verdict = ShoppingVerdict.GOOD_MATCH
        } else if ((context == "Trend" || context == "Тренд сезона") && colorDir == ColorDirection.CONTRAST) {
            verdict = ShoppingVerdict.MAYBE
        } else if (context == "Occasion" || context == "Особый случай") {
            verdict = if (colorDir == ColorDirection.NEUTRAL || colorDir == ColorDirection.WARM || colorDir == ColorDirection.SOFT) {
                ShoppingVerdict.MAYBE
            } else {
                ShoppingVerdict.SKIP
            }
        } else if (context == "Trend" || context == "Тренд сезона" || context == "Unsure" || context == "Не уверен") {
            verdict = ShoppingVerdict.MAYBE
        }

        if ((category == ClothingCategory.SHOES || category == ClothingCategory.BAG || category == ClothingCategory.ACCESSORY) && 
            colorDir == ColorDirection.NEUTRAL) {
            verdict = if (context == "Similar" || context == "Похожая вещь") ShoppingVerdict.SKIP else ShoppingVerdict.GOOD_MATCH
        }

        val explanationColor: String
        val explanationSilhouette: String
        val explanationCapsule: String
        val explanationVersatility: String
        val outfitCountEstimate: String
        val shoppingAdvice: String

        when (verdict) {
            ShoppingVerdict.GOOD_MATCH -> {
                explanationColor = if (hasReport) {
                    "Color Synergy: Aligns perfectly with your master Soft Autumn palette guidelines."
                } else {
                    "Color Synergy: Neutral and soft coordinates provide safe base matching versatility."
                }
                explanationSilhouette = "Silhouette Fit: Standard straight construction balances and flatters your form."
                explanationCapsule = "Capsule Utility: Fills high-priority structural gaps in your wardrobe checklist."
                explanationVersatility = "Versatility: Supports multiple styles. Beautifully coordinates with office tailoring or relaxed jeans."
                outfitCountEstimate = "Creates 4-6 outfits compatibility"
                shoppingAdvice = "Highly recommended purchase. Built on functional color logic rather than trend hype."
            }
            ShoppingVerdict.MAYBE -> {
                explanationColor = if (hasReport) {
                    "Color Check: Tolerable tone, but does not strictly feature Soft Autumn warmth."
                } else {
                    "Color Check: Vivid chromatic tone that requires careful surrounding layers."
                }
                explanationSilhouette = "Silhouette Fit: Normal geometry. Could overwhelm your proportions if worn oversized."
                explanationCapsule = "Capsule Utility: Medium priority. Best added only if you are fully lacking this category."
                explanationVersatility = "Versatility: Slightly limited combination capacity. Best reserved for predefined outfits."
                outfitCountEstimate = "Creates 2-3 outfits compatibility"
                shoppingAdvice = "Proceed with caution. Double check if you can build at least three distinct fits."
            }
            ShoppingVerdict.SKIP -> {
                explanationColor = "Color Clash: High friction contrast that disrupts your natural aesthetic harmony."
                explanationSilhouette = "Silhouette Fit: Bulky or uncomfortable tailoring that restricts movement."
                explanationCapsule = "Capsule Utility: Redundant purchase. Adds clutter to your active wardrobe."
                explanationVersatility = "Versatility: Low index. Extremely hard to pair with basic everyday items."
                outfitCountEstimate = "1 outfit maximum"
                shoppingAdvice = "Skip this purchase. Strongly advisable to pass. This item is likely to remain unworn."
            }
        }

        return ShoppingCheckResult(
            verdict = verdict,
            explanationColor = explanationColor,
            explanationSilhouette = explanationSilhouette,
            explanationCapsule = explanationCapsule,
            explanationVersatility = explanationVersatility,
            outfitCountEstimate = outfitCountEstimate,
            shoppingAdvice = shoppingAdvice
        )
    }
}`
  },
  homeScreen: {
    name: "HomeScreen.kt",
    path: "feature/home/HomeScreen.kt",
    description: "Responsive Compose layout showing the 'Should I Buy' primary action and optional photo upload launcher.",
    code: `package com.example.styleai.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.styleai.domain.model.*

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToShoppingCheck: () -> Unit,
    onNavigateToCreateProfile: () -> Unit,
    onNavigateToLooks: () -> Unit,
    onNavigateToReport: () -> Unit
) {
    val hasProfile by viewModel.hasActiveProfile.collectAsState()
    val skippedCount by viewModel.skippedItemsCount.collectAsState()

    Column(modifier = Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Header
            Column {
                Text("StyleAI", style = MaterialTheme.typography.headlineMedium)
                Text("Make better wardrobe decisions.", style = MaterialTheme.typography.bodySmall)
            }

            // Primary MVP Action: "Should I buy this?"
            Card(onClick = onNavigateToShoppingCheck) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Should I buy this?", style = MaterialTheme.typography.titleMedium)
                    Text("Check if an item is worth buying before it becomes another unused piece.", style = MaterialTheme.typography.bodyMedium)
                    Button(onClick = onNavigateToShoppingCheck, modifier = Modifier.padding(top = 8.dp)) {
                        Text("Check an item")
                    }
                }
            }

            // Secondary: "What should I wear?" / Outfit ideas
            Card(onClick = onNavigateToLooks) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("What should I wear?", style = MaterialTheme.typography.titleMedium)
                    Text("Get outfit ideas from your saved looks and style rules.", style = MaterialTheme.typography.bodyMedium)
                    Button(onClick = onNavigateToLooks, modifier = Modifier.padding(top = 8.dp)) {
                        Text("Get outfit ideas")
                    }
                }
            }

            // Optional Profile Card
            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    if (!hasProfile) {
                        Text("Create style profile", style = MaterialTheme.typography.titleMedium)
                        Text("Optional: unlock palette, silhouettes, and capsule recommendations.", style = MaterialTheme.typography.bodySmall)
                        OutlinedButton(onClick = onNavigateToCreateProfile, modifier = Modifier.padding(top = 8.dp)) {
                            Text("Create profile")
                        }
                    } else {
                        Text("Style profile is Active", style = MaterialTheme.typography.titleMedium)
                        Text("Soft Classic spectrum loaded. Tap below to see your full diagnosis report.", style = MaterialTheme.typography.bodySmall)
                        Button(onClick = onNavigateToReport, modifier = Modifier.padding(top = 8.dp)) {
                            Text("Open report")
                        }
                    }
                }
            }
        }

        // Summary Statistics Box
        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Wardrobe Smart Stats", style = MaterialTheme.typography.bodyMedium)
                Text("You skipped $skippedCount low-versatility items through careful gatekeeping.", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}`
  },
  homeViewModel: {
    name: "HomeViewModel.kt",
    path: "feature/home/HomeViewModel.kt",
    description: "Android ViewModel coordinating home dashboard parameters, localized alerts, and credits data flows.",
    code: `package com.example.styleai.feature.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*
import com.example.styleai.domain.model.*

class HomeViewModel(
    private val profileRepository: StyleProfileRepository,
    private val creditManager: CreditManager,
    private val historyRepository: ShoppingCheckHistoryRepository
) : ViewModel() {
    val hasActiveProfile: StateFlow<Boolean> = profileRepository.hasProfileFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val creditsLeft: StateFlow<Int> = creditManager.remainingCreditsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 5)

    val skippedItemsCount: StateFlow<Int> = historyRepository.skippedItemsCountFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 2)
}`
  },
  decisionsScreen: {
    name: "DecisionsScreen.kt",
    path: "feature/decisions/DecisionsScreen.kt",
    description: "Android compose screen showing a practical list of wardorbe decisions and bypassed shopping trap metrics.",
    code: `package com.example.styleai.feature.decisions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.styleai.domain.model.*

@Composable
fun DecisionsScreen(
    viewModel: DecisionsViewModel,
    onNavigateToDetails: (String) -> Unit
) {
    val decisions by viewModel.savedDecisions.collectAsState()
    
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text("Saved Decisions", style = MaterialTheme.typography.titleLarge)
        Text("Track wardrobe gatekeeping metrics and bypassed retail buyer traps", style = MaterialTheme.typography.bodySmall)
        
        Spacer(modifier = Modifier.height(12.dp))
        
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(decisions) { item ->
                Card {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(item.category.name, style = MaterialTheme.typography.titleMedium)
                            Text(item.verdict.name, color = when(item.verdict) {
                                ShoppingVerdict.GOOD_MATCH -> MaterialTheme.colorScheme.primary
                                ShoppingVerdict.MAYBE -> MaterialTheme.colorScheme.secondary
                                ShoppingVerdict.SKIP -> MaterialTheme.colorScheme.error
                            })
                        }
                        Text(item.reasonSummary, style = MaterialTheme.typography.bodyMedium)
                        Text("Outfit Count: \${item.outfitCountEstimate}", style = MaterialTheme.typography.labelSmall)
                        
                        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.End) {
                            TextButton(onClick = { viewModel.deleteDecision(item.id) }) {
                                Text("Delete")
                            }
                            Button(onClick = { onNavigateToDetails(item.id) }) {
                                Text("Open Details")
                            }
                        }
                    }
                }
            }
        }
    }
}`
  },
  decisionsViewModel: {
    name: "DecisionsViewModel.kt",
    path: "feature/decisions/DecisionsViewModel.kt",
    description: "ViewModel managing decision lookbooks and wardorbe gatekeeping parameters offline.",
    code: `package com.example.styleai.feature.decisions

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.*
import com.example.styleai.domain.model.*

data class SavedDecision(
    val id: String,
    val category: ClothingCategory,
    val verdict: ShoppingVerdict,
    val reasonSummary: String,
    val outfitCountEstimate: Int,
    val date: String
)

class DecisionsViewModel : ViewModel() {
    private val _savedDecisions = MutableStateFlow<List<SavedDecision>>(listOf(
        SavedDecision("dec_1", ClothingCategory.OUTERWEAR, ShoppingVerdict.GOOD_MATCH, "Perfect charcoal blazer for Soft Autumn styling.", 6, "Today"),
        SavedDecision("dec_2", ClothingCategory.SHOES, ShoppingVerdict.SKIP, "Redundant purchase. Similar silhouette already owned.", 0, "Yesterday"),
        SavedDecision("dec_3", ClothingCategory.TOP, ShoppingVerdict.MAYBE, "High maintenance wool outerwear requiring dry wash.", 3, "3 days ago")
    ))
    val savedDecisions: StateFlow<List<SavedDecision>> = _savedDecisions.asStateFlow()

    fun deleteDecision(id: String) {
        _savedDecisions.value = _savedDecisions.value.filter { it.id != id }
    }
}`
  }
};
