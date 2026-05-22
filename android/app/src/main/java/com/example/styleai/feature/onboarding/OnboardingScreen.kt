package com.example.styleai.feature.onboarding

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.styleai.core.localization.AppLocalization
import com.example.styleai.domain.model.AppLanguage

/**
 * Animated entry splash screen.
 */
@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    var animateLogo by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        animateLogo = true
        delay(1800) // Cinematic transition buffer
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = animateLogo,
                enter = fadeIn() + expandVertically()
            ) {
                Text(
                    text = "StyleAI",
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your private AI style assistant",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Onboarding Carousel displaying key product tenets.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    onOnboardingFinished: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()
    val currentLanguage by viewModel.selectedLanguage.collectAsState()
    val strings = AppLocalization.getStrings(currentLanguage)

    val pages = remember(currentLanguage) {
        listOf(
            OnboardingPageData(
                title = strings.onboardingTitle1,
                description = strings.onboardingDesc1
            ),
            OnboardingPageData(
                title = strings.onboardingTitle2,
                description = strings.onboardingDesc2
            ),
            OnboardingPageData(
                title = strings.onboardingTitle3,
                description = strings.onboardingDesc3
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Skip header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = { viewModel.finishOnboarding(onOnboardingFinished) }) {
                Text(strings.onboardingSkip, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            val page = pages[index]
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = page.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = page.description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Stepper indicator dots and CTA button
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(3) { index ->
                    val color = if (pagerState.currentPage == index) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
                    }
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(if (pagerState.currentPage == index) 10.dp else 8.dp)
                            .background(color, shape = MaterialTheme.shapes.small)
                    )
                }
            }

            Button(
                onClick = {
                    if (pagerState.currentPage < 2) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    } else {
                        viewModel.finishOnboarding(onOnboardingFinished)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (pagerState.currentPage == 2) strings.onboardingFinish else strings.onboardingContinue)
            }
        }
    }
}

/**
 * Consent Screen.
 * Must be verified and approved before proceeding to uploading steps.
 */
@Composable
fun ConsentScreen(
    viewModel: OnboardingViewModel,
    onConsentApproved: () -> Unit
) {
    val state by viewModel.consentState.collectAsState()
    val currentLanguage by viewModel.selectedLanguage.collectAsState()
    val strings = AppLocalization.getStrings(currentLanguage)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = strings.consentTitle,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = strings.consentSubtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Dynamic Checkboxes
            CheckboxRow(
                text = strings.consentCheck1,
                checked = state.hasPhotoPermission,
                onCheckedChange = { checked -> viewModel.updateConsent(photoPermission = checked) }
            )

            CheckboxRow(
                text = strings.consentCheck2,
                checked = state.understandsDisclaimer,
                onCheckedChange = { checked -> viewModel.updateConsent(understandsDisclaimer = checked) }
            )

            CheckboxRow(
                text = strings.consentCheck3,
                checked = state.rawPhotosNotStored,
                onCheckedChange = { checked -> viewModel.updateConsent(rawPhotosNotStored = checked) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Crucial safety warning message
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (currentLanguage == AppLanguage.EN) "Safety Note" else "Безопасность использования",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (currentLanguage == AppLanguage.EN) "Unsafe or non-consensual image use is not allowed. The app does not support nudity, underwear, erotic content, minors, or attempts to alter someone without consent." else "Использование некорректных фотографий или обработка без согласия строго запрещены. Приложение не поддерживает раздевание, интимные фото, эротику, изображения несовершеннолетних разработок.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }
        }

        Button(
            onClick = {
                viewModel.submitConsent(onConsentApproved)
            },
            enabled = state.isFullyConsented,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(strings.consentButton)
        }
    }
}

@Composable
fun CheckboxRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

data class OnboardingPageData(
    val title: String,
    val description: String
)
