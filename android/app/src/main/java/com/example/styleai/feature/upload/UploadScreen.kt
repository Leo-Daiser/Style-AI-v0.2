package com.example.styleai.feature.upload

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styleai.core.localization.AppLocalization
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.model.UploadedPhoto
import com.example.styleai.ui.components.LuxuryTopAppBar

@Composable
fun UploadScreen(
    viewModel: UploadViewModel,
    onNavigateToReport: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val selfie by viewModel.selfiePhoto.collectAsState()
    val fullBody by viewModel.fullBodyPhoto.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val currentLanguage by viewModel.selectedLanguage.collectAsState()
    val strings = AppLocalization.getStrings(currentLanguage)
    val scrollState = rememberScrollState()

    LaunchedEffect(uiState) {
        if (uiState is UploadUiState.AnalysisSuccess) {
            onNavigateToReport()
        }
    }

    Surface(color = LuxuryBackground, modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is UploadUiState.Analyzing -> {
                AnalysisProgressView(
                    stepIndex = state.stepIndex,
                    stepText = when (state.stepIndex) {
                        0 -> strings.loadingStep1
                        1 -> strings.loadingStep2
                        2 -> strings.loadingStep3
                        3 -> strings.loadingStep4
                        4 -> strings.loadingStep5
                        5 -> strings.loadingStep6
                        else -> strings.loadingStep6
                    },
                    percentage = state.percentage,
                    currentLanguage = currentLanguage
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(LuxuryBackground)
                        .verticalScroll(scrollState)
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    LuxuryTopAppBar(
                        title = null,
                        onBackClick = onNavigateBack,
                        modifier = Modifier.fillMaxWidth()
                    )
                    UploadHeader(currentLanguage)
                    RecommendationsBlock(currentLanguage)

                    PhotoPickerBox(
                        title = if (currentLanguage == AppLanguage.EN) "Face photo" else "Фото лица",
                        uploadedPhoto = selfie,
                        aspectRatio = 1f,
                        onSelectMockUri = { viewModel.selectSelfie("internal_selfie_good.jpg") },
                        onSelectMockFailureUri = { viewModel.selectSelfie("selfie_dark_blurry.jpg") },
                        onSelectMockMinorUri = { viewModel.selectSelfie("selfie_minor_protected.jpg") },
                        currentLanguage = currentLanguage
                    )

                    PhotoPickerBox(
                        title = if (currentLanguage == AppLanguage.EN) "Silhouette" else "Силуэт",
                        uploadedPhoto = fullBody,
                        aspectRatio = 0.75f,
                        onSelectMockUri = { viewModel.selectFullBody("internal_fullbody_good.jpg") },
                        onSelectMockFailureUri = { viewModel.selectFullBody("fullbody_dark_blurry.jpg") },
                        onSelectMockMinorUri = { viewModel.selectFullBody("fullbody_crowd_people.jpg") },
                        currentLanguage = currentLanguage
                    )

                    if (state is UploadUiState.ValidationFailed) {
                        ValidationMessage(
                            reason = state.reason,
                            warnings = state.warnings,
                            currentLanguage = currentLanguage,
                            onClear = { viewModel.clearUploads() }
                        )
                    }

                    if (state is UploadUiState.Error) {
                        Text(
                            text = state.errorMessage,
                            color = LuxurySecondaryText,
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        )
                    }

                    LuxuryPrimaryButton(
                        text = strings.uploadButton,
                        onClick = { viewModel.startAnalysis() },
                        enabled = selfie != null && fullBody != null && state !is UploadUiState.ValidationFailed,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (selfie != null || fullBody != null) {
                        TextButton(
                            onClick = { viewModel.clearUploads() },
                            colors = ButtonDefaults.textButtonColors(contentColor = LuxurySecondaryText)
                        ) {
                            Text(
                                text = if (currentLanguage == AppLanguage.EN) "Reset photos" else "Сбросить фотографии",
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UploadHeader(language: AppLanguage) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = if (language == AppLanguage.EN) "Photo Upload" else "Загрузка фотографий",
            color = LuxuryPrimaryText,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-0.2).sp
        )
        Text(
            text = if (language == AppLanguage.EN) {
                "Upload a selfie and body silhouette for style analysis."
            } else {
                "Загрузите селфи и силуэт тела для анализа стиля."
            },
            color = LuxurySecondaryText,
            fontSize = 15.sp,
            lineHeight = 22.sp
        )
    }
}

@Composable
private fun RecommendationsBlock(language: AppLanguage) {
    val instructions = if (language == AppLanguage.EN) {
        listOf(
            "Use soft natural light, ideally near a window.",
            "Keep expression neutral and hands relaxed.",
            "Only one person should be visible in the frame.",
            "Use modest, everyday clothing without revealing styling.",
            "Avoid blur, group photos, strong filters, and distant framing."
        )
    } else {
        listOf(
            "Используйте мягкое естественное освещение, лучше у окна.",
            "Сохраняйте нейтральное выражение лица и расслабленные руки.",
            "В кадре должен быть только один человек.",
            "Выбирайте обычную закрытую одежду без откровенной стилизации.",
            "Избегайте размытия, групповых фото, сильных фильтров и дальнего кадра."
        )
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
        Text(
            text = if (language == AppLanguage.EN) "Recommendations:" else "Рекомендации:",
            color = LuxuryPrimaryText,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        instructions.forEach { item ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(4.dp)
                        .background(LuxurySecondaryText, RectangleTwoDp)
                )
                Text(
                    text = item,
                    color = LuxurySecondaryText,
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun PhotoPickerBox(
    title: String,
    uploadedPhoto: UploadedPhoto?,
    aspectRatio: Float,
    onSelectMockUri: () -> Unit,
    onSelectMockFailureUri: () -> Unit,
    onSelectMockMinorUri: () -> Unit,
    currentLanguage: AppLanguage
) {
    var showTestingSelectionMenu by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = title,
            color = LuxuryPrimaryText,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(aspectRatio)
                .border(BorderStroke(1.dp, LuxuryDivider), RectangleTwoDp)
                .background(Color.White)
                .clickable { showTestingSelectionMenu = !showTestingSelectionMenu },
            contentAlignment = Alignment.Center
        ) {
            if (uploadedPhoto == null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    MinimalCameraIcon()
                    Text(
                        text = if (currentLanguage == AppLanguage.EN) "Add photo" else "Добавить фото",
                        color = LuxuryPrimaryText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(18.dp)
                ) {
                    Text(
                        text = uploadedPhoto.uriString,
                        color = LuxuryPrimaryText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = if (currentLanguage == AppLanguage.EN) "Ready for style review" else "Готово к анализу стиля",
                        color = LuxurySecondaryText,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        if (showTestingSelectionMenu && uploadedPhoto == null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LuxuryOutlinedButton(
                    text = if (currentLanguage == AppLanguage.EN) "Use sample" else "Пример",
                    onClick = {
                        onSelectMockUri()
                        showTestingSelectionMenu = false
                    },
                    modifier = Modifier.weight(1f)
                )
                LuxuryOutlinedButton(
                    text = if (currentLanguage == AppLanguage.EN) "Low light" else "Темный кадр",
                    onClick = {
                        onSelectMockFailureUri()
                        showTestingSelectionMenu = false
                    },
                    modifier = Modifier.weight(1f),
                    secondary = true
                )
                LuxuryOutlinedButton(
                    text = if (currentLanguage == AppLanguage.EN) "Safety check" else "Проверка",
                    onClick = {
                        onSelectMockMinorUri()
                        showTestingSelectionMenu = false
                    },
                    modifier = Modifier.weight(1f),
                    secondary = true
                )
            }
        }
    }
}

@Composable
private fun MinimalCameraIcon() {
    Canvas(modifier = Modifier.size(34.dp)) {
        val strokeWidth = 1.4.dp.toPx()
        val stroke = Stroke(width = strokeWidth, cap = StrokeCap.Square)
        drawRect(
            color = LuxuryPrimaryText,
            topLeft = Offset(size.width * 0.18f, size.height * 0.28f),
            size = androidx.compose.ui.geometry.Size(size.width * 0.64f, size.height * 0.48f),
            style = stroke
        )
        drawLine(
            color = LuxuryPrimaryText,
            start = Offset(size.width * 0.38f, size.height * 0.28f),
            end = Offset(size.width * 0.44f, size.height * 0.18f),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = LuxuryPrimaryText,
            start = Offset(size.width * 0.44f, size.height * 0.18f),
            end = Offset(size.width * 0.62f, size.height * 0.18f),
            strokeWidth = strokeWidth
        )
        drawLine(
            color = LuxuryPrimaryText,
            start = Offset(size.width * 0.62f, size.height * 0.18f),
            end = Offset(size.width * 0.68f, size.height * 0.28f),
            strokeWidth = strokeWidth
        )
        drawCircle(
            color = LuxuryPrimaryText,
            radius = size.width * 0.12f,
            center = Offset(size.width * 0.5f, size.height * 0.52f),
            style = stroke
        )
    }
}

@Composable
private fun ValidationMessage(
    reason: String,
    warnings: List<String>,
    currentLanguage: AppLanguage,
    onClear: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shape = RectangleTwoDp,
        border = BorderStroke(1.dp, LuxuryDivider),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = if (currentLanguage == AppLanguage.EN) "Photo needs adjustment" else "Фото нужно заменить",
                color = LuxuryPrimaryText,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(reason, color = LuxurySecondaryText, fontSize = 13.sp, lineHeight = 19.sp)
            warnings.forEach { warning ->
                Text(warning, color = LuxurySecondaryText, fontSize = 13.sp, lineHeight = 19.sp)
            }
            TextButton(
                onClick = onClear,
                colors = ButtonDefaults.textButtonColors(contentColor = LuxuryPrimaryText)
            ) {
                Text(
                    text = if (currentLanguage == AppLanguage.EN) "Choose another photo" else "Выбрать другое фото",
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun AnalysisProgressView(
    stepIndex: Int,
    stepText: String,
    percentage: Int,
    currentLanguage: AppLanguage
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryBackground)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            progress = percentage / 100f,
            modifier = Modifier.size(72.dp),
            color = LuxuryPrimaryText,
            strokeWidth = 2.dp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = if (currentLanguage == AppLanguage.EN) "Creating profile... $percentage%" else "Создаем профиль... $percentage%",
            color = LuxuryPrimaryText,
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = stepText,
            color = LuxurySecondaryText,
            fontSize = 15.sp,
            lineHeight = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(28.dp))
        Divider(color = LuxuryDivider, thickness = 1.dp)
        Spacer(modifier = Modifier.height(18.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            val steps = if (currentLanguage == AppLanguage.EN) {
                listOf("Photo safety review", "Palette direction", "Silhouette notes", "Style profile ready")
            } else {
                listOf("Проверка фото", "Направление палитры", "Заметки по силуэту", "Стиль-профиль готов")
            }
            steps.forEachIndexed { index, label ->
                Text(
                    text = label,
                    color = if (index <= stepIndex / 2) LuxuryPrimaryText else LuxurySecondaryText,
                    fontSize = 14.sp,
                    fontWeight = if (index <= stepIndex / 2) FontWeight.Medium else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
private fun LuxuryPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(50.dp),
        shape = RectangleTwoDp,
        colors = ButtonDefaults.buttonColors(
            containerColor = LuxuryPrimaryText,
            contentColor = Color.White,
            disabledContainerColor = LuxuryDivider,
            disabledContentColor = LuxurySecondaryText
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
        modifier = modifier.height(42.dp),
        shape = RectangleTwoDp,
        border = BorderStroke(1.dp, LuxuryDivider),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = if (secondary) LuxurySecondaryText else LuxuryPrimaryText
        )
    ) {
        Text(text = text, fontSize = 12.sp, fontWeight = FontWeight.Medium, maxLines = 1, overflow = TextOverflow.Ellipsis)
    }
}

private val LuxuryBackground = Color(0xFFF5F5F7)
private val LuxuryPrimaryText = Color(0xFF222222)
private val LuxurySecondaryText = Color(0xFF737373)
private val LuxuryDivider = Color(0xFFE5E5E5)
private val RectangleTwoDp = RoundedCornerShape(2.dp)
