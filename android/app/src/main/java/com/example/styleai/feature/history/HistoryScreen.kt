package com.example.styleai.feature.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.styleai.domain.model.HistoryItem
import com.example.styleai.domain.model.SavedLook
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.core.localization.AppLocalization
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onNavigateBackToReport: () -> Unit
) {
    val histories by viewModel.historyItems.collectAsState()
    val looks by viewModel.savedLooks.collectAsState()
    val currentLanguage by viewModel.selectedLanguage.collectAsState()

    val strings = AppLocalization.getStrings(currentLanguage)
    var activeTabIdx by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = strings.historyTitle,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = strings.historySubtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Custom local Tab Indicator
        TabRow(selectedTabIndex = activeTabIdx) {
            Tab(selected = activeTabIdx == 0, onClick = { activeTabIdx = 0 }) {
                Text(
                    text = "${strings.historyActiveReports} (${histories.size})",
                    modifier = Modifier.padding(vertical = 12.dp),
                    fontSize = 11.sp,
                    fontWeight = if (activeTabIdx == 0) FontWeight.Bold else FontWeight.Normal
                )
            }
            Tab(selected = activeTabIdx == 1, onClick = { activeTabIdx = 1 }) {
                Text(
                    text = "${strings.historySavedLooks} (${looks.size})",
                    modifier = Modifier.padding(vertical = 12.dp),
                    fontSize = 11.sp,
                    fontWeight = if (activeTabIdx == 1) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (activeTabIdx == 0) {
            if (histories.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        text = strings.historyEmptyState,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(histories, key = { it.id }) { historyItem ->
                        HistoryItemCard(
                            item = historyItem,
                            onLoadReport = onNavigateBackToReport,
                            onDelete = { viewModel.deleteHistoryItem(historyItem.id) },
                            currentLanguage = currentLanguage
                        )
                    }
                }
            }
        } else {
            if (looks.isEmpty()) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    val noSavedLooksMsg = if (currentLanguage == AppLanguage.EN) {
                        "No saved looks yet. Highlight looks in visualization grids to check them out here."
                    } else {
                        "Сохраненные образы отсутствуют. Выберите образы на вкладке стилизации."
                    }
                    Text(
                        text = noSavedLooksMsg,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(looks, key = { it.id }) { savedLook ->
                        SavedLookItemCard(
                            look = savedLook,
                            onDelete = { viewModel.removeSavedLook(savedLook.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryItemCard(
    item: HistoryItem,
    onLoadReport: () -> Unit,
    onDelete: () -> Unit,
    currentLanguage: AppLanguage
) {
    val formatter = SimpleDateFormat("MMM dd, yyyy • HH:mm", Locale.getDefault())
    val dateString = formatter.format(Date(item.date))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (currentLanguage == AppLanguage.EN) "Style Report" else "Отчет по стилю",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge
                )
                TextButton(onClick = onDelete) {
                    Text(
                        text = if (currentLanguage == AppLanguage.EN) "Delete" else "Удалить",
                        color = Color(0xFFC76B6B),
                        fontSize = 11.sp
                    )
                }
            }
            Text(
                text = "${if (currentLanguage == AppLanguage.EN) "Date" else "Дата"}: $dateString",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = "${if (currentLanguage == AppLanguage.EN) "Color Palette" else "Палитра"}: ${item.paletteName}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${if (currentLanguage == AppLanguage.EN) "Saved Looks" else "Сохранено образов"}: ${item.numSavedLooks}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )
                Button(
                    onClick = onLoadReport,
                    modifier = Modifier.height(30.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = if (currentLanguage == AppLanguage.EN) "Load Report" else "Загрузить",
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SavedLookItemCard(
    look: SavedLook,
    onDelete: () -> Unit
) {
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val dateString = formatter.format(Date(look.savedAt))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Saved look image from drawable assets
            Image(
                painter = painterResource(id = getSavedLookDrawable(look.outfitId)),
                contentDescription = look.title,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(look.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
                Text("Added: $dateString", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            IconButton(onClick = onDelete) {
                Text("✕", color = Color.Gray, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

private fun getSavedLookDrawable(outfitId: String): Int {
    return when {
        outfitId.contains("outfit_1") -> com.example.styleai.R.drawable.autumn_layered_look
        outfitId.contains("outfit_2") -> com.example.styleai.R.drawable.date_night_classic
        outfitId.contains("outfit_3") -> com.example.styleai.R.drawable.weekend_minimal_casual
        outfitId.contains("outfit_4") -> com.example.styleai.R.drawable.travel_capsule_outfit
        outfitId.contains("outfit_5") -> com.example.styleai.R.drawable.smart_casual_everyday
        outfitId.contains("outfit_6") -> com.example.styleai.R.drawable.soft_office_capsule
        else -> com.example.styleai.R.drawable.soft_office_capsule
    }
}
