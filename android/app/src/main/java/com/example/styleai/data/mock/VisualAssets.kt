package com.example.styleai.data.mock

import androidx.annotation.DrawableRes
import com.example.styleai.R

data class WardrobeAssetItem(
    val id: String,
    val titleEn: String,
    val titleRu: String,
    val category: String,
    val colorDirection: String,
    val season: String,
    val versatilityScore: Int,
    val outfitCount: Int,
    @DrawableRes val drawableRes: Int
)

data class OutfitBoardAsset(
    val id: String,
    val titleEn: String,
    val titleRu: String,
    val occasion: String,
    val season: String,
    val formality: String,
    val itemIds: List<String>,
    @DrawableRes val drawableRes: Int,
    val creditCost: Int
)

data class EmptyStateAsset(
    val id: String,
    val titleEn: String,
    val titleRu: String,
    @DrawableRes val drawableRes: Int,
    val usage: String
)

object VisualAssets {
    val wardrobeItems = listOf(
        WardrobeAssetItem(
            id = "cream-structured-blazer",
            titleEn = "Cream structured blazer",
            titleRu = "Кремовый структурированный жакет",
            category = "Outerwear",
            colorDirection = "Warm neutral",
            season = "Spring / Autumn",
            versatilityScore = 86,
            outfitCount = 6,
            drawableRes = R.drawable.cream_structured_blazer
        ),
        WardrobeAssetItem(
            id = "white-cotton-shirt",
            titleEn = "White cotton shirt",
            titleRu = "Белая хлопковая рубашка",
            category = "Tops",
            colorDirection = "Neutral",
            season = "All season",
            versatilityScore = 92,
            outfitCount = 8,
            drawableRes = R.drawable.white_cotton_shirt
        ),
        WardrobeAssetItem(
            id = "dark-blue-straight-jeans",
            titleEn = "Dark blue straight jeans",
            titleRu = "Темно-синие прямые джинсы",
            category = "Bottoms",
            colorDirection = "Neutral",
            season = "All season",
            versatilityScore = 89,
            outfitCount = 7,
            drawableRes = R.drawable.dark_blue_straight_jeans
        ),
        WardrobeAssetItem(
            id = "beige-tailored-trousers",
            titleEn = "Beige tailored trousers",
            titleRu = "Бежевые классические брюки",
            category = "Bottoms",
            colorDirection = "Warm neutral",
            season = "Spring / Autumn / Summer",
            versatilityScore = 82,
            outfitCount = 5,
            drawableRes = R.drawable.beige_tailored_trousers
        ),
        WardrobeAssetItem(
            id = "olive-midi-dress",
            titleEn = "Olive midi dress",
            titleRu = "Оливковое платье-миди",
            category = "Dresses",
            colorDirection = "Muted olive",
            season = "Spring / Summer / Autumn",
            versatilityScore = 75,
            outfitCount = 4,
            drawableRes = R.drawable.olive_midi_dress
        ),
        WardrobeAssetItem(
            id = "oatmeal-merino-knit",
            titleEn = "Oatmeal merino knit",
            titleRu = "Овсяный трикотажный пуловер",
            category = "Tops",
            colorDirection = "Warm neutral",
            season = "Autumn / Winter / Spring",
            versatilityScore = 88,
            outfitCount = 6,
            drawableRes = R.drawable.oatmeal_merino_knit
        ),
        WardrobeAssetItem(
            id = "camel-trench-coat",
            titleEn = "Camel trench coat",
            titleRu = "Песочный тренч",
            category = "Outerwear",
            colorDirection = "Warm neutral",
            season = "Spring / Autumn",
            versatilityScore = 85,
            outfitCount = 5,
            drawableRes = R.drawable.camel_trench_coat
        ),
        WardrobeAssetItem(
            id = "white-leather-sneakers",
            titleEn = "White leather sneakers",
            titleRu = "Белые кожаные кеды",
            category = "Shoes",
            colorDirection = "Neutral",
            season = "Spring / Summer / Autumn",
            versatilityScore = 95,
            outfitCount = 10,
            drawableRes = R.drawable.white_leather_sneakers
        ),
        WardrobeAssetItem(
            id = "black-leather-loafers",
            titleEn = "Black leather loafers",
            titleRu = "Черные кожаные лоферы",
            category = "Shoes",
            colorDirection = "Neutral",
            season = "Spring / Autumn",
            versatilityScore = 84,
            outfitCount = 6,
            drawableRes = R.drawable.black_leather_loafers
        ),
        WardrobeAssetItem(
            id = "tan-ankle-boots",
            titleEn = "Tan ankle boots",
            titleRu = "Рыжие ботильоны",
            category = "Shoes",
            colorDirection = "Warm neutral",
            season = "Autumn / Spring",
            versatilityScore = 78,
            outfitCount = 4,
            drawableRes = R.drawable.tan_ankle_boots
        ),
        WardrobeAssetItem(
            id = "brown-leather-handbag",
            titleEn = "Brown leather handbag",
            titleRu = "Коричневая кожаная сумка",
            category = "Accessories",
            colorDirection = "Warm neutral",
            season = "All season",
            versatilityScore = 90,
            outfitCount = 9,
            drawableRes = R.drawable.brown_leather_handbag
        ),
        WardrobeAssetItem(
            id = "brown-leather-belt",
            titleEn = "Brown leather belt",
            titleRu = "Коричневый кожаный ремень",
            category = "Accessories",
            colorDirection = "Warm neutral",
            season = "All season",
            versatilityScore = 94,
            outfitCount = 12,
            drawableRes = R.drawable.brown_leather_belt
        )
    )

    val outfitBoards = listOf(
        OutfitBoardAsset(
            id = "soft-office-capsule",
            titleEn = "Soft Office Capsule",
            titleRu = "Мягкая офисная капсула",
            occasion = "Office",
            season = "All season",
            formality = "Polished",
            itemIds = listOf(
                "cream-structured-blazer",
                "white-cotton-shirt",
                "dark-blue-straight-jeans",
                "black-leather-loafers",
                "brown-leather-handbag"
            ),
            drawableRes = R.drawable.soft_office_capsule,
            creditCost = 1
        ),
        OutfitBoardAsset(
            id = "weekend-minimal-casual",
            titleEn = "Weekend Minimal Casual",
            titleRu = "Минималистичный кэжуал выходного дня",
            occasion = "Weekend",
            season = "Spring / Summer",
            formality = "Relaxed",
            itemIds = listOf(
                "white-cotton-shirt",
                "dark-blue-straight-jeans",
                "white-leather-sneakers",
                "brown-leather-belt",
                "brown-leather-handbag"
            ),
            drawableRes = R.drawable.weekend_minimal_casual,
            creditCost = 1
        ),
        OutfitBoardAsset(
            id = "autumn-layered-look",
            titleEn = "Autumn Layered Look",
            titleRu = "Осенний многослойный образ",
            occasion = "City Walk",
            season = "Autumn",
            formality = "Smart Casual",
            itemIds = listOf(
                "camel-trench-coat",
                "oatmeal-merino-knit",
                "dark-blue-straight-jeans",
                "tan-ankle-boots",
                "brown-leather-handbag"
            ),
            drawableRes = R.drawable.autumn_layered_look,
            creditCost = 1
        ),
        OutfitBoardAsset(
            id = "date-night-classic",
            titleEn = "Date Night Classic",
            titleRu = "Классический образ для свидания",
            occasion = "Dinner",
            season = "Spring / Autumn",
            formality = "Elevated",
            itemIds = listOf(
                "cream-structured-blazer",
                "olive-midi-dress",
                "black-leather-loafers",
                "brown-leather-handbag"
            ),
            drawableRes = R.drawable.date_night_classic,
            creditCost = 1
        ),
        OutfitBoardAsset(
            id = "travel-capsule-outfit",
            titleEn = "Travel Capsule Outfit",
            titleRu = "Образ для путешествий",
            occasion = "Travel",
            season = "All season",
            formality = "Comfortable",
            itemIds = listOf(
                "camel-trench-coat",
                "white-cotton-shirt",
                "beige-tailored-trousers",
                "white-leather-sneakers",
                "brown-leather-handbag"
            ),
            drawableRes = R.drawable.travel_capsule_outfit,
            creditCost = 1
        ),
        OutfitBoardAsset(
            id = "smart-casual-everyday",
            titleEn = "Smart Casual Everyday",
            titleRu = "Повседневный смарт-кэжуал",
            occasion = "Everyday",
            season = "All season",
            formality = "Smart Casual",
            itemIds = listOf(
                "cream-structured-blazer",
                "oatmeal-merino-knit",
                "beige-tailored-trousers",
                "black-leather-loafers",
                "brown-leather-handbag"
            ),
            drawableRes = R.drawable.smart_casual_everyday,
            creditCost = 1
        ),
        OutfitBoardAsset(
            id = "creative-workspace-blend",
            titleEn = "Creative Workspace Blend",
            titleRu = "Креативный офисный стиль",
            occasion = "Office / Creative",
            season = "All season",
            formality = "Smart Casual",
            itemIds = listOf(
                "white-cotton-shirt",
                "dark-blue-straight-jeans",
                "white-leather-sneakers",
                "brown-leather-belt"
            ),
            drawableRes = R.drawable.creative_workspace_blend,
            creditCost = 1
        ),
        OutfitBoardAsset(
            id = "gallery-evening-look",
            titleEn = "Gallery Evening Look",
            titleRu = "Вечерний образ для галереи",
            occasion = "Gallery / Dinner",
            season = "Autumn / Winter",
            formality = "Polished",
            itemIds = listOf(
                "cream-structured-blazer",
                "oatmeal-merino-knit",
                "beige-tailored-trousers",
                "black-leather-loafers",
                "brown-leather-handbag"
            ),
            drawableRes = R.drawable.gallery_evening_look,
            creditCost = 1
        )
    )

    val emptyStateAssets = listOf(
        EmptyStateAsset(
            id = "empty-wardrobe",
            titleEn = "No items in wardrobe",
            titleRu = "В гардеробе пока нет вещей",
            drawableRes = R.drawable.empty_wardrobe,
            usage = "Displayed on Wardrobe Screen when no items have been added."
        ),
        EmptyStateAsset(
            id = "empty-looks",
            titleEn = "No saved outfits yet",
            titleRu = "У вас пока нет сохраненных образов",
            drawableRes = R.drawable.empty_looks,
            usage = "Displayed on Looks Screen when the user has not generated/saved any outfits."
        ),
        EmptyStateAsset(
            id = "empty-decisions",
            titleEn = "No shopping decisions made yet",
            titleRu = "Решения о покупках еще не приняты",
            drawableRes = R.drawable.empty_looks, // Fallback since empty-decisions is missing
            usage = "Displayed on Decisions Screen when the user has no history or saved item shopping checks."
        )
    )
}
