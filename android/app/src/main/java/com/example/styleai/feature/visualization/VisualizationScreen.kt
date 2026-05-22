package com.example.styleai.feature.visualization

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styleai.domain.model.*
import com.example.styleai.core.localization.AppLocalization
import com.example.styleai.core.theme.SoftMoss

@Composable
fun VisualizationScreen(
    viewModel: VisualizationViewModel,
    onNavigateToPaywall: () -> Unit
) {
    val occasion by viewModel.selectedOccasion.collectAsState()
    val style by viewModel.selectedStyle.collectAsState()
    val season by viewModel.selectedSeason.collectAsState()
    val formality by viewModel.selectedFormality.collectAsState()
    val colorDirection by viewModel.selectedColorDirection.collectAsState()

    val outfitIdeas by viewModel.outfitIdeas.collectAsState()
    val balance by viewModel.CreditBalance.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val currentLanguage by viewModel.selectedLanguage.collectAsState()

    val strings = AppLocalization.getStrings(currentLanguage)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // Title banner
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = strings.visTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = strings.visSubtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            // Credits Tracker capsule
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = if (balance.isProUser) {
                        if (currentLanguage == AppLanguage.EN) "★ Pro" else "★ Pro"
                    } else {
                        "${strings.paywallSubtitle.take(0)} ${balance.totalCredits} Cr"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Safety warning label
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
        ) {
            Text(
                text = strings.visSafetyWarning,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(10.dp)
            )
        }

        // Controlled Selector Panels (Absolutely NO free text input)
        Text(
            text = "${strings.visOccasionLabel}:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
        RowScrollOccasionFilter(selected = occasion, onSelect = { viewModel.changeOccasion(it) })

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${strings.visStyleLabel}:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
        RowScrollStyleFilter(selected = style, onSelect = { viewModel.changeStyle(it) })

        Spacer(modifier = Modifier.height(4.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${strings.visSeasonLabel}:",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                RowScrollSeasonFilter(selected = season, onSelect = { viewModel.changeSeason(it) })
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${strings.visFormalityLabel}:",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                RowScrollFormalityFilter(selected = formality, onSelect = { viewModel.changeFormality(it) })
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${strings.visColorDirectionLabel}:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
        RowScrollColorDirectionFilter(selected = colorDirection, onSelect = { viewModel.changeColorDirection(it) })

        Spacer(modifier = Modifier.height(12.dp))

        // Synthesis Action Button
        Button(
            onClick = {
                viewModel.triggerControlledVisualization()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState !is VisualizerUiState.Generating,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                text = if (uiState is VisualizerUiState.Generating) {
                    if (currentLanguage == AppLanguage.EN) "Synthesizing safe layout..." else "Синтез безопасного макета..."
                } else strings.visIntroButton,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // UI state validation panels
        when (val state = uiState) {
            is VisualizerUiState.BannedWordDetected -> {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)) {
                    Text(
                        strings.visSafetyWarning,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.padding(10.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            is VisualizerUiState.InsufficientCredits -> {
                Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = if (currentLanguage == AppLanguage.EN) "Credit limit reached. Please purchase more." else "Лимит кредитов исчерпан. Пожалуйста, приобретите пакет.",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Button(onClick = onNavigateToPaywall) {
                            Text(strings.paywallButton, fontSize = 11.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            is VisualizerUiState.GenerationSuccess -> {
                Text(
                    text = if (currentLanguage == AppLanguage.EN) "✓ Outfit reference synthesized successfully!" else "✓ Спецификация образа успешно синтезирована!",
                    color = SoftMoss,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
            else -> {}
        }

        // Outfit matching ideas
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(outfitIdeas) { idea ->
                OutfitCard(
                    idea = idea,
                    onToggleSave = { viewModel.toggleSaveOutfit(idea.id) }
                )
            }
        }
    }
}

// 1. Selector scroll rows
@Composable
fun RowScrollOccasionFilter(selected: Occasion, onSelect: (Occasion) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Occasion.values().forEach { item ->
            val active = selected == item
            Box(
                modifier = Modifier
                    .background(
                        if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(6.dp)
                    )
                    .clickable { onSelect(item) }
                    .padding(horizontal = 8.dp, vertical = 5.dp)
            ) {
                Text(item.name, color = if (active) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
            }
        }
    }
}

@Composable
fun RowScrollStyleFilter(selected: StyleType, onSelect: (StyleType) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        StyleType.values().forEach { item ->
            val active = selected == item
            Box(
                modifier = Modifier
                    .background(
                        if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(6.dp)
                    )
                    .clickable { onSelect(item) }
                    .padding(horizontal = 8.dp, vertical = 5.dp)
            ) {
                Text(item.name.replace("_", " "), color = if (active) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 10.sp)
            }
        }
    }
}

@Composable
fun RowScrollSeasonFilter(selected: Season, onSelect: (Season) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Season.values().forEach { item ->
            val active = selected == item
            Box(
                modifier = Modifier
                    .background(
                        if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(6.dp)
                    )
                    .clickable { onSelect(item) }
                    .padding(horizontal = 6.dp, vertical = 5.dp)
            ) {
                Text(item.name, color = if (active) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 9.sp)
            }
        }
    }
}

@Composable
fun RowScrollFormalityFilter(selected: Formality, onSelect: (Formality) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Formality.values().forEach { item ->
            val active = selected == item
            Box(
                modifier = Modifier
                    .background(
                        if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(6.dp)
                    )
                    .clickable { onSelect(item) }
                    .padding(horizontal = 6.dp, vertical = 5.dp)
            ) {
                Text(item.name, color = if (active) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 9.sp)
            }
        }
    }
}

@Composable
fun RowScrollColorDirectionFilter(selected: ColorDirection, onSelect: (ColorDirection) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        ColorDirection.values().forEach { item ->
            val active = selected == item
            Box(
                modifier = Modifier
                    .background(
                        if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(6.dp)
                    )
                    .clickable { onSelect(item) }
                    .padding(horizontal = 6.dp, vertical = 5.dp)
            ) {
                Text(item.name, color = if (active) Color.White else MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 9.sp)
            }
        }
    }
}

@Composable
fun OutfitCard(
    idea: OutfitIdea,
    onToggleSave: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            // Visual Swatch/Box Placeholder instead of real image loading
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy((-4).dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    idea.colorPalette.forEach { hex ->
                        val color = try {
                            Color(android.graphics.Color.parseColor(hex))
                        } catch (e: Exception) {
                            MaterialTheme.colorScheme.primary
                        }
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(color)
                                .border(1.dp, Color.White, RoundedCornerShape(12.dp))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Title
            Text(
                text = idea.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Occasion / Season
            Text(
                text = "${idea.occasion} • ${idea.season}",
                style = MaterialTheme.typography.bodySmall,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Clothing items
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.weight(1f, fill = false)
            ) {
                idea.clothingItems.take(3).forEach { item ->
                    Text(
                        text = "• $item",
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 9.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        maxLines = 1
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Save/Saved Button
            Button(
                onClick = onToggleSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (idea.isSaved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = if (idea.isSaved) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text(
                    text = if (idea.isSaved) "✓ Saved" else "Save Lookout",
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
        }
    }
}
