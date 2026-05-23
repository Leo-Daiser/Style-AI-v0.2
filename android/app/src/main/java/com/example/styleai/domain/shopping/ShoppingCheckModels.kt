package com.example.styleai.domain.shopping

import com.example.styleai.domain.model.AppLanguage

enum class ShoppingCategory {
    TOP, BOTTOM, DRESS, OUTERWEAR, SHOES, BAG, ACCESSORY
}

enum class ShoppingColorDirection {
    NEUTRAL, WARM_NEUTRAL, COOL_NEUTRAL, ACCENT, BRIGHT, DARK, LIGHT
}

enum class ShoppingOccasion {
    EVERYDAY, OFFICE, DATE, TRAVEL, EVENT, SPORT, HOME
}

enum class ShoppingSeason {
    ALL_SEASON, SPRING, SUMMER, AUTUMN, WINTER
}

enum class ShoppingPriceLevel {
    LOW, MEDIUM, HIGH, VERY_HIGH
}

enum class WardrobeGapStatus {
    FILLS_CLEAR_GAP, USEFUL_NOT_URGENT, DUPLICATE, UNSURE
}

enum class SimilarItemsOwned {
    NONE, ONE, TWO_OR_MORE
}

enum class ExpectedWearFrequency {
    WEEKLY, MONTHLY, RARELY, ONE_TIME
}

enum class CareComplexity {
    EASY, MEDIUM, DIFFICULT
}

enum class DecisionConfidence {
    HIGH, MEDIUM, LOW
}

enum class ShoppingVerdict {
    BUY, MAYBE, SKIP
}

data class ShoppingItemDraft(
    val category: ShoppingCategory,
    val colorDirection: ShoppingColorDirection,
    val occasion: ShoppingOccasion,
    val season: ShoppingSeason,
    val priceLevel: ShoppingPriceLevel,
    val wardrobeGap: WardrobeGapStatus,
    val similarItemsOwned: SimilarItemsOwned,
    val expectedWearFrequency: ExpectedWearFrequency,
    val careComplexity: CareComplexity,
    val confidence: DecisionConfidence
)

data class ShoppingReason(
    val textEn: String,
    val textRu: String
) {
    fun text(language: AppLanguage): String = if (language == AppLanguage.RU) textRu else textEn
}

data class ShoppingRisk(
    val textEn: String,
    val textRu: String
) {
    fun text(language: AppLanguage): String = if (language == AppLanguage.RU) textRu else textEn
}

data class ShoppingDecisionResult(
    val score: Int,
    val verdict: ShoppingVerdict,
    val mainReason: ShoppingReason,
    val positiveReasons: List<ShoppingReason>,
    val risks: List<ShoppingRisk>,
    val estimatedOutfitCount: ShoppingReason,
    val capsuleImpact: ShoppingReason,
    val recommendation: ShoppingReason
)

data class SavedShoppingDecision(
    val id: String,
    val draft: ShoppingItemDraft,
    val result: ShoppingDecisionResult,
    val createdDateLabel: String
)

fun ShoppingCategory.label(language: AppLanguage): String = when (this) {
    ShoppingCategory.TOP -> if (language == AppLanguage.RU) "Верх" else "Top"
    ShoppingCategory.BOTTOM -> if (language == AppLanguage.RU) "Низ" else "Bottom"
    ShoppingCategory.DRESS -> if (language == AppLanguage.RU) "Платье" else "Dress"
    ShoppingCategory.OUTERWEAR -> if (language == AppLanguage.RU) "Верхняя одежда" else "Outerwear"
    ShoppingCategory.SHOES -> if (language == AppLanguage.RU) "Обувь" else "Shoes"
    ShoppingCategory.BAG -> if (language == AppLanguage.RU) "Сумка" else "Bag"
    ShoppingCategory.ACCESSORY -> if (language == AppLanguage.RU) "Аксессуар" else "Accessory"
}

fun ShoppingColorDirection.label(language: AppLanguage): String = when (this) {
    ShoppingColorDirection.NEUTRAL -> if (language == AppLanguage.RU) "Нейтральный" else "Neutral"
    ShoppingColorDirection.WARM_NEUTRAL -> if (language == AppLanguage.RU) "Теплый нейтральный" else "Warm neutral"
    ShoppingColorDirection.COOL_NEUTRAL -> if (language == AppLanguage.RU) "Холодный нейтральный" else "Cool neutral"
    ShoppingColorDirection.ACCENT -> if (language == AppLanguage.RU) "Акцентный" else "Accent"
    ShoppingColorDirection.BRIGHT -> if (language == AppLanguage.RU) "Яркий" else "Bright"
    ShoppingColorDirection.DARK -> if (language == AppLanguage.RU) "Темный" else "Dark"
    ShoppingColorDirection.LIGHT -> if (language == AppLanguage.RU) "Светлый" else "Light"
}

fun ShoppingOccasion.label(language: AppLanguage): String = when (this) {
    ShoppingOccasion.EVERYDAY -> if (language == AppLanguage.RU) "Каждый день" else "Everyday"
    ShoppingOccasion.OFFICE -> if (language == AppLanguage.RU) "Офис" else "Office"
    ShoppingOccasion.DATE -> if (language == AppLanguage.RU) "Свидание" else "Date"
    ShoppingOccasion.TRAVEL -> if (language == AppLanguage.RU) "Поездка" else "Travel"
    ShoppingOccasion.EVENT -> if (language == AppLanguage.RU) "Мероприятие" else "Event"
    ShoppingOccasion.SPORT -> if (language == AppLanguage.RU) "Спорт" else "Sport"
    ShoppingOccasion.HOME -> if (language == AppLanguage.RU) "Дом" else "Home"
}

fun ShoppingSeason.label(language: AppLanguage): String = when (this) {
    ShoppingSeason.ALL_SEASON -> if (language == AppLanguage.RU) "Всесезонная" else "All season"
    ShoppingSeason.SPRING -> if (language == AppLanguage.RU) "Весна" else "Spring"
    ShoppingSeason.SUMMER -> if (language == AppLanguage.RU) "Лето" else "Summer"
    ShoppingSeason.AUTUMN -> if (language == AppLanguage.RU) "Осень" else "Autumn"
    ShoppingSeason.WINTER -> if (language == AppLanguage.RU) "Зима" else "Winter"
}

fun ShoppingPriceLevel.label(language: AppLanguage): String = when (this) {
    ShoppingPriceLevel.LOW -> if (language == AppLanguage.RU) "Низкая" else "Low"
    ShoppingPriceLevel.MEDIUM -> if (language == AppLanguage.RU) "Средняя" else "Medium"
    ShoppingPriceLevel.HIGH -> if (language == AppLanguage.RU) "Высокая" else "High"
    ShoppingPriceLevel.VERY_HIGH -> if (language == AppLanguage.RU) "Очень высокая" else "Very high"
}

fun WardrobeGapStatus.label(language: AppLanguage): String = when (this) {
    WardrobeGapStatus.FILLS_CLEAR_GAP -> if (language == AppLanguage.RU) "Закрывает явный пробел" else "Fills clear gap"
    WardrobeGapStatus.USEFUL_NOT_URGENT -> if (language == AppLanguage.RU) "Полезно, но не срочно" else "Useful but not urgent"
    WardrobeGapStatus.DUPLICATE -> if (language == AppLanguage.RU) "Дублирует вещь" else "Duplicate"
    WardrobeGapStatus.UNSURE -> if (language == AppLanguage.RU) "Не уверен" else "Unsure"
}

fun SimilarItemsOwned.label(language: AppLanguage): String = when (this) {
    SimilarItemsOwned.NONE -> if (language == AppLanguage.RU) "Нет" else "None"
    SimilarItemsOwned.ONE -> if (language == AppLanguage.RU) "Одна" else "One"
    SimilarItemsOwned.TWO_OR_MORE -> if (language == AppLanguage.RU) "Две или больше" else "Two or more"
}

fun ExpectedWearFrequency.label(language: AppLanguage): String = when (this) {
    ExpectedWearFrequency.WEEKLY -> if (language == AppLanguage.RU) "Каждую неделю" else "Weekly"
    ExpectedWearFrequency.MONTHLY -> if (language == AppLanguage.RU) "Раз в месяц" else "Monthly"
    ExpectedWearFrequency.RARELY -> if (language == AppLanguage.RU) "Редко" else "Rarely"
    ExpectedWearFrequency.ONE_TIME -> if (language == AppLanguage.RU) "Один раз" else "One-time"
}

fun CareComplexity.label(language: AppLanguage): String = when (this) {
    CareComplexity.EASY -> if (language == AppLanguage.RU) "Простая" else "Easy"
    CareComplexity.MEDIUM -> if (language == AppLanguage.RU) "Средняя" else "Medium"
    CareComplexity.DIFFICULT -> if (language == AppLanguage.RU) "Сложная" else "Difficult"
}

fun DecisionConfidence.label(language: AppLanguage): String = when (this) {
    DecisionConfidence.HIGH -> if (language == AppLanguage.RU) "Высокая" else "High"
    DecisionConfidence.MEDIUM -> if (language == AppLanguage.RU) "Средняя" else "Medium"
    DecisionConfidence.LOW -> if (language == AppLanguage.RU) "Низкая" else "Low"
}

fun ShoppingVerdict.label(language: AppLanguage): String = when (this) {
    ShoppingVerdict.BUY -> if (language == AppLanguage.RU) "Купить" else "Buy"
    ShoppingVerdict.MAYBE -> if (language == AppLanguage.RU) "Подумать" else "Maybe"
    ShoppingVerdict.SKIP -> if (language == AppLanguage.RU) "Пропустить" else "Skip"
}
