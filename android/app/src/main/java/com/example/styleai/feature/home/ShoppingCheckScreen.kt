package com.example.styleai.feature.home

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.styleai.domain.decisions.SavedDecisionRepository
import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.wardrobe.WardrobeRepository
import com.example.styleai.domain.wishlist.WishlistRepository
import com.example.styleai.ui.components.LuxuryTopAppBar
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ShoppingCheckScreen(
    currentLanguageState: StateFlow<AppLanguage>,
    savedDecisionRepository: SavedDecisionRepository,
    wardrobeRepository: WardrobeRepository,
    wishlistRepository: WishlistRepository,
    onNavigateBack: () -> Unit,
    onViewDecisions: () -> Unit
) {
    val language by currentLanguageState.collectAsState()
    var hasSelectedPhoto by remember { mutableStateOf(false) }
    var optionalContext by remember { mutableStateOf("") }
    var isScanning by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = LuxuryBackground,
        topBar = {
            LuxuryTopAppBar(
                title = null,
                onBackClick = onNavigateBack,
                backgroundColor = LuxuryBackground
            )
        },
        bottomBar = {
            Surface(color = LuxuryBackground, tonalElevation = 0.dp, shadowElevation = 0.dp) {
                LuxuryPrimaryButton(
                    text = if (isScanning) {
                        if (language == AppLanguage.RU) "Анализируем..." else "Analyzing..."
                    } else {
                        if (language == AppLanguage.RU) "Проанализировать вещь" else "Analyze item"
                    },
                    enabled = hasSelectedPhoto && !isScanning,
                    onClick = { isScanning = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .imePadding()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LuxuryBackground)
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Header(language = language)

            PurchasePhotoFrame(
                language = language,
                hasSelectedPhoto = hasSelectedPhoto,
                isScanning = isScanning,
                onClick = {
                    if (!isScanning) {
                        hasSelectedPhoto = true
                    }
                }
            )

            OptionalContextField(
                value = optionalContext,
                language = language,
                enabled = !isScanning,
                onValueChange = { optionalContext = it }
            )

            if (isScanning) {
                ScanningState(language = language)
            }

            Spacer(modifier = Modifier.height(96.dp))
        }
    }
}

@Composable
private fun Header(language: AppLanguage) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = if (language == AppLanguage.RU) "Анализ покупки" else "Purchase Analysis",
            color = LuxuryPrimaryText,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = (-0.2).sp
        )
        Text(
            text = if (language == AppLanguage.RU) {
                "Загрузите фото вещи. ИИ проанализирует ваш гардероб, палитру и стиль, чтобы вынести вердикт."
            } else {
                "Upload an item photo. AI will compare your wardrobe, palette, and style before giving a verdict."
            },
            color = LuxurySecondaryText,
            fontSize = 14.sp,
            lineHeight = 21.sp
        )
    }
}

@Composable
private fun PurchasePhotoFrame(
    language: AppLanguage,
    hasSelectedPhoto: Boolean,
    isScanning: Boolean,
    onClick: () -> Unit
) {
    val transition = rememberInfiniteTransition(label = "purchaseFrameScan")
    val scanAlpha by transition.animateFloat(
        initialValue = 0.32f,
        targetValue = 0.72f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "purchaseFrameScanAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.8f)
            .background(Color.White)
            .border(BorderStroke(1.dp, LuxuryDivider), RectangleTwoDp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        if (isScanning) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = scanAlpha }
                    .background(LuxuryDivider)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(24.dp)
        ) {
            MinimalCameraIcon()
            Text(
                text = when {
                    isScanning -> if (language == AppLanguage.RU) "Сканируем фото" else "Scanning photo"
                    hasSelectedPhoto -> if (language == AppLanguage.RU) "Фото готово к анализу" else "Photo ready for analysis"
                    else -> if (language == AppLanguage.RU) "Загрузить фото или скриншот" else "Upload photo or screenshot"
                },
                color = LuxuryPrimaryText,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            if (!hasSelectedPhoto && !isScanning) {
                Text(
                    text = if (language == AppLanguage.RU) {
                        "Нажмите, чтобы добавить локальный пример"
                    } else {
                        "Tap to add a local sample"
                    },
                    color = LuxurySecondaryText,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun OptionalContextField(
    value: String,
    language: AppLanguage,
    enabled: Boolean,
    onValueChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = if (language == AppLanguage.RU) {
                "Ссылка на товар или бренд (опционально)"
            } else {
                "Product link or brand (optional)"
            },
            color = LuxurySecondaryText,
            fontSize = 12.sp,
            letterSpacing = 0.6.sp
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            singleLine = true,
            textStyle = TextStyle(
                color = LuxuryPrimaryText,
                fontSize = 15.sp,
                lineHeight = 21.sp
            ),
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        if (value.isEmpty()) {
                            Text(
                                text = if (language == AppLanguage.RU) "Например: COS, Uniqlo, ссылка магазина" else "Example: COS, Uniqlo, store link",
                                color = LuxurySecondaryText,
                                fontSize = 15.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        innerTextField()
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(LuxuryDivider)
                    )
                }
            }
        )
    }
}

@Composable
private fun ScanningState(language: AppLanguage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(1.dp, LuxuryDivider), RectangleTwoDp)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = if (language == AppLanguage.RU) "Локальный анализ запущен" else "Local analysis started",
            color = LuxuryPrimaryText,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = if (language == AppLanguage.RU) {
                "Это MVP-состояние сканирования. Следующий шаг — подключить локальный результат и сохранение решения."
            } else {
                "This is an MVP scanning state. Next step: connect a local result and decision saving."
            },
            color = LuxurySecondaryText,
            fontSize = 13.sp,
            lineHeight = 19.sp
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            LoadingLine(weight = 0.46f)
            LoadingLine(weight = 0.28f)
            LoadingLine(weight = 0.18f)
        }
    }
}

@Composable
private fun LoadingLine(weight: Float) {
    Box(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth(weight)
            .background(LuxuryPrimaryText)
    )
}

@Composable
private fun MinimalCameraIcon() {
    Canvas(modifier = Modifier.size(38.dp)) {
        val strokeWidth = 1.45.dp.toPx()
        val stroke = Stroke(width = strokeWidth, cap = StrokeCap.Square)
        drawRect(
            color = LuxuryPrimaryText,
            topLeft = Offset(size.width * 0.18f, size.height * 0.28f),
            size = androidx.compose.ui.geometry.Size(size.width * 0.64f, size.height * 0.48f),
            style = stroke
        )
        drawLine(
            color = LuxuryPrimaryText,
            start = Offset(size.width * 0.39f, size.height * 0.28f),
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
private fun LuxuryPrimaryButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(52.dp),
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
        Text(text = text, fontWeight = FontWeight.Medium, fontSize = 15.sp)
    }
}

private val LuxuryBackground = Color(0xFFF5F5F7)
private val LuxuryPrimaryText = Color(0xFF222222)
private val LuxurySecondaryText = Color(0xFF737373)
private val LuxuryDivider = Color(0xFFE5E5E5)
private val RectangleTwoDp = androidx.compose.foundation.shape.RoundedCornerShape(2.dp)
