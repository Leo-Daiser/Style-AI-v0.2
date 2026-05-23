package com.example.styleai.feature.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.model.StyleReport
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateToUpload: () -> Unit,
    onNavigateBackToOnboarding: () -> Unit
) {
    val language by viewModel.selectedLanguage.collectAsState()
    val report by viewModel.activeReport.collectAsState()
    val message by viewModel.message.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryBackground)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(22.dp)
    ) {
        ProfileHeader(language = language)

        message?.let {
            MessageBlock(
                text = messageText(it, language),
                language = language,
                onDismiss = { viewModel.dismissMessage() }
            )
        }

        StyleIdentitySection(
            report = report,
            language = language,
            onCreateProfile = onNavigateToUpload
        )

        ProfileSection(title = if (language == AppLanguage.RU) "Язык" else "Language") {
            LanguageToggle(
                language = language,
                onSelect = { viewModel.changeLanguage(it) }
            )
        }

        ProfileSection(title = if (language == AppLanguage.RU) "Приватность" else "Privacy") {
            Text(
                text = if (language == AppLanguage.RU) {
                    "Абсолютная приватность. Ваши данные, стиль и изображения не покидают это устройство. Все обрабатывается локально."
                } else {
                    "Absolute Privacy. Your data, your style, and your images never leave this device. Everything is processed locally."
                },
                color = LuxurySecondaryText,
                fontSize = 14.sp,
                lineHeight = 21.sp
            )
        }

        ProfileSection(title = if (language == AppLanguage.RU) "Управление данными" else "Data Management") {
            LuxuryOutlinedButton(
                text = if (language == AppLanguage.RU) "Сбросить стиль-данные" else "Reset Style Data",
                onClick = { viewModel.deleteStyleData() },
                modifier = Modifier.fillMaxWidth()
            )
            LuxuryOutlinedButton(
                text = if (language == AppLanguage.RU) "Начать настройку заново" else "Restart Setup",
                onClick = { viewModel.resetOnboarding(onNavigateBackToOnboarding) },
                modifier = Modifier.fillMaxWidth()
            )
            LuxuryOutlinedButton(
                text = if (language == AppLanguage.RU) "Очистить локальную историю" else "Clear Local History",
                onClick = { viewModel.purgeAllLocalData(onNavigateBackToOnboarding) },
                modifier = Modifier.fillMaxWidth(),
                secondary = true
            )
        }
    }
}

@Composable
private fun ProfileHeader(language: AppLanguage) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = if (language == AppLanguage.RU) "Стиль и личное пространство" else "Style Identity",
            color = LuxuryPrimaryText,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-0.2).sp
        )
        Text(
            text = if (language == AppLanguage.RU) {
                "Палитра, правила гардероба и спокойное управление приватностью."
            } else {
                "Your palette, wardrobe rules, and calm privacy controls."
            },
            color = LuxurySecondaryText,
            fontSize = 15.sp,
            lineHeight = 22.sp
        )
    }
}

@Composable
private fun MessageBlock(text: String, language: AppLanguage, onDismiss: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RectangleTwoDp,
        border = BorderStroke(1.dp, LuxuryDivider),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text, modifier = Modifier.weight(1f), color = LuxurySecondaryText, fontSize = 13.sp, lineHeight = 19.sp)
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(contentColor = LuxuryPrimaryText)
            ) {
                Text(if (language == AppLanguage.RU) "Скрыть" else "Dismiss", fontWeight = FontWeight.Medium)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StyleIdentitySection(report: StyleReport?, language: AppLanguage, onCreateProfile: () -> Unit) {
    if (report == null) {
        ProfileSection(title = if (language == AppLanguage.RU) "Стиль-профиль" else "Style Profile") {
            Text(
                text = if (language == AppLanguage.RU) "Создайте необязательный стиль-профиль" else "Create your optional style profile",
                color = LuxuryPrimaryText,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = if (language == AppLanguage.RU) {
                    "Откройте палитру, силуэты и правила капсулы. Проверка покупок работает и без него."
                } else {
                    "Unlock palette, silhouettes, and capsule rules. Shopping checks still work without it."
                },
                color = LuxurySecondaryText,
                fontSize = 14.sp,
                lineHeight = 21.sp
            )
            LuxuryPrimaryButton(
                text = if (language == AppLanguage.RU) "Создать стиль-профиль" else "Create style profile",
                onClick = onCreateProfile
            )
        }
    } else {
        ProfileSection(title = if (language == AppLanguage.RU) "Стиль-профиль активен" else "Style Profile Active") {
            Text(
                text = report.colorPalette.name,
                color = LuxuryPrimaryText,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = report.styleDirections.joinToString { it.title },
                color = LuxurySecondaryText,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )
            if (report.colorPalette.swatchColors.isNotEmpty()) {
                FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    report.colorPalette.swatchColors.take(6).forEach { hex ->
                        Box(
                            modifier = Modifier
                                .width(34.dp)
                                .height(18.dp)
                                .background(parseHexColor(hex), RectangleTwoDp)
                                .border(BorderStroke(1.dp, LuxuryDivider), RectangleTwoDp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LanguageToggle(language: AppLanguage, onSelect: (AppLanguage) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Language:",
            color = LuxurySecondaryText,
            fontSize = 14.sp
        )
        LanguageChoice(text = "EN", selected = language == AppLanguage.EN, onClick = { onSelect(AppLanguage.EN) })
        Text("|", color = LuxuryDivider)
        LanguageChoice(text = "RU", selected = language == AppLanguage.RU, onClick = { onSelect(AppLanguage.RU) })
    }
}

@Composable
private fun LanguageChoice(text: String, selected: Boolean, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier.clickable(onClick = onClick),
        color = if (selected) LuxuryPrimaryText else LuxurySecondaryText,
        fontSize = 14.sp,
        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
        letterSpacing = 0.7.sp
    )
}

@Composable
private fun ProfileSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RectangleTwoDp,
        border = BorderStroke(1.dp, LuxuryDivider),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title.uppercase(Locale.getDefault()),
                color = LuxurySecondaryText,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 1.2.sp
            )
            Divider(color = LuxuryDivider, thickness = 1.dp)
            content()
        }
    }
}

@Composable
private fun LuxuryPrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RectangleTwoDp,
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
        Text(text = text, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

@Composable
private fun LuxuryOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    secondary: Boolean = false
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RectangleTwoDp,
        border = BorderStroke(1.dp, LuxuryDivider),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = if (secondary) LuxurySecondaryText else LuxuryPrimaryText
        )
    ) {
        Text(text = text, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

private fun parseHexColor(hex: String): Color {
    return runCatching { Color(android.graphics.Color.parseColor(hex)) }.getOrDefault(LuxuryDivider)
}

private fun messageText(message: String, language: AppLanguage): String {
    return if (message == "Style data deleted locally." && language == AppLanguage.RU) {
        "Локальные стиль-данные сброшены."
    } else if (message == "Style data deleted locally.") {
        "Local style data has been reset."
    } else {
        message
    }
}

private val LuxuryBackground = Color(0xFFF5F5F7)
private val LuxuryPrimaryText = Color(0xFF222222)
private val LuxurySecondaryText = Color(0xFF737373)
private val LuxuryDivider = Color(0xFFE5E5E5)
private val RectangleTwoDp = RoundedCornerShape(2.dp)
