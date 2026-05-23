package com.example.styleai.feature.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styleai.R
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.ui.components.EditorialMetricUi
import com.example.styleai.ui.components.EditorialMetricsBlock

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateToShoppingCheck: () -> Unit,
    onNavigateToUpload: () -> Unit,
    onNavigateToReportDetail: () -> Unit,
    onNavigateToPaywall: () -> Unit,
    onSwitchTab: (Int) -> Unit
) {
    val language by viewModel.selectedLanguage.collectAsState()
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LuxuryBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(LuxuryBackground)
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            HeaderBlock(language = language)

            EditorialMetricsBlock(
                metrics = listOf(
                    EditorialMetricUi("10", if (language == AppLanguage.RU) "Вещей" else "Items"),
                    EditorialMetricUi("3", if (language == AppLanguage.RU) "Решения" else "Decisions"),
                    EditorialMetricUi("2", if (language == AppLanguage.RU) "Пропущено" else "Skipped")
                )
            )

            HeroOutfitSection(
                language = language,
                onBuildToday = { onSwitchTab(3) }
            )

            ShoppingDecisionSection(
                language = language,
                onCheckPurchase = onNavigateToShoppingCheck
            )

            QuickActionsSection(
                language = language,
                onOpenWardrobe = { onSwitchTab(1) },
                onCreateProfile = onNavigateToUpload,
                onOpenProfile = { onSwitchTab(4) }
            )

            Divider(color = LuxuryDivider, thickness = 1.dp)

            Text(
                text = if (language == AppLanguage.RU) {
                    "Проверка покупок работает без загрузки лица и тела."
                } else {
                    "Shopping checks work without uploading face/body photos."
                },
                color = LuxurySecondaryText,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
        }
    }
}

@Composable
private fun HeaderBlock(language: AppLanguage) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = "StyleAI",
            color = LuxuryPrimaryText,
            fontSize = 34.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-0.3).sp
        )
        Text(
            text = if (language == AppLanguage.RU) {
                "Принимайте лучшие решения для гардероба."
            } else {
                "Make better wardrobe decisions."
            },
            color = LuxurySecondaryText,
            fontSize = 16.sp,
            lineHeight = 23.sp
        )
    }
}

@Composable
private fun HeroOutfitSection(language: AppLanguage, onBuildToday: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Image(
            painter = painterResource(R.drawable.soft_office_capsule),
            contentDescription = if (language == AppLanguage.RU) "Капсульный образ" else "Soft office capsule",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.25f)
                .clip(RoundedCornerShape(2.dp)),
            contentScale = ContentScale.Crop
        )
        EditorialTextSection(
            title = if (language == AppLanguage.RU) "Что надеть сегодня?" else "What should I wear today?",
            body = if (language == AppLanguage.RU) {
                "Соберите 3 идеи образов на основе гардероба и правил стиля."
            } else {
                "Build 3 outfit ideas from your wardrobe and style rules."
            }
        )
        LuxuryPrimaryButton(
            text = if (language == AppLanguage.RU) "Подобрать образ" else "Build today's outfit",
            onClick = onBuildToday
        )
    }
}

@Composable
private fun ShoppingDecisionSection(language: AppLanguage, onCheckPurchase: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Divider(color = LuxuryDivider, thickness = 1.dp)
        EditorialTextSection(
            title = if (language == AppLanguage.RU) "Стоит ли покупать?" else "Should I buy this?",
            body = if (language == AppLanguage.RU) {
                "Проверьте вещь до покупки, чтобы она не стала очередной ненужной покупкой."
            } else {
                "Check if an item is worth buying before it becomes another unused piece."
            }
        )
        LuxuryPrimaryButton(
            text = if (language == AppLanguage.RU) "Проверить покупку" else "Check purchase",
            onClick = onCheckPurchase
        )
    }
}

@Composable
private fun EditorialTextSection(title: String, body: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = title,
            color = LuxuryPrimaryText,
            fontSize = 21.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 27.sp
        )
        Text(
            text = body,
            color = LuxurySecondaryText,
            fontSize = 15.sp,
            lineHeight = 22.sp
        )
    }
}

@Composable
private fun QuickActionsSection(
    language: AppLanguage,
    onOpenWardrobe: () -> Unit,
    onCreateProfile: () -> Unit,
    onOpenProfile: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            LuxurySecondaryButton(
                text = if (language == AppLanguage.RU) "Добавить вещь" else "Add item",
                modifier = Modifier.weight(1f),
                onClick = onOpenWardrobe
            )
            LuxurySecondaryButton(
                text = if (language == AppLanguage.RU) "Создать профиль" else "Create profile",
                modifier = Modifier.weight(1f),
                onClick = onCreateProfile
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            LuxurySecondaryButton(
                text = if (language == AppLanguage.RU) "Палитра" else "Palette",
                modifier = Modifier.weight(1f),
                onClick = onOpenProfile
            )
            LuxurySecondaryButton(
                text = if (language == AppLanguage.RU) "Редко носимое" else "Unused items",
                modifier = Modifier.weight(1f),
                onClick = onOpenWardrobe
            )
        }
    }
}

@Composable
private fun LuxuryPrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = LuxuryPrimaryText,
            contentColor = Color.White
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            focusedElevation = 0.dp,
            hoveredElevation = 0.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(text = text, fontWeight = FontWeight.Medium, fontSize = 15.sp)
    }
}

@Composable
private fun LuxurySecondaryButton(text: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(2.dp),
        border = BorderStroke(1.dp, LuxuryDivider),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = LuxuryBackground,
            contentColor = LuxuryPrimaryText
        )
    ) {
        Text(text = text, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    }
}

private val LuxuryBackground = Color(0xFFF5F5F7)
private val LuxuryPrimaryText = Color(0xFF222222)
private val LuxurySecondaryText = Color(0xFF737373)
private val LuxuryDivider = Color(0xFFE5E5E5)
