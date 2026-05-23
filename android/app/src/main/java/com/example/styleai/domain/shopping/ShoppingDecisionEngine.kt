package com.example.styleai.domain.shopping

import kotlin.math.max
import kotlin.math.min

object ShoppingDecisionEngine {
    fun evaluate(draft: ShoppingItemDraft): ShoppingDecisionResult {
        val score = calculateScore(draft)
        val verdict = when {
            score >= 72 -> ShoppingVerdict.BUY
            score >= 45 -> ShoppingVerdict.MAYBE
            else -> ShoppingVerdict.SKIP
        }

        return ShoppingDecisionResult(
            score = score,
            verdict = verdict,
            mainReason = mainReason(verdict),
            positiveReasons = positiveReasons(draft),
            risks = risks(draft),
            estimatedOutfitCount = estimatedOutfitCount(score, draft),
            capsuleImpact = capsuleImpact(score, draft),
            recommendation = recommendation(verdict)
        )
    }

    private fun calculateScore(draft: ShoppingItemDraft): Int {
        var score = 50

        score += when (draft.wardrobeGap) {
            WardrobeGapStatus.FILLS_CLEAR_GAP -> 25
            WardrobeGapStatus.USEFUL_NOT_URGENT -> 10
            WardrobeGapStatus.DUPLICATE -> -20
            WardrobeGapStatus.UNSURE -> -5
        }
        score += when (draft.similarItemsOwned) {
            SimilarItemsOwned.NONE -> 10
            SimilarItemsOwned.ONE -> 0
            SimilarItemsOwned.TWO_OR_MORE -> -15
        }
        score += when (draft.expectedWearFrequency) {
            ExpectedWearFrequency.WEEKLY -> 20
            ExpectedWearFrequency.MONTHLY -> 8
            ExpectedWearFrequency.RARELY -> -15
            ExpectedWearFrequency.ONE_TIME -> -25
        }
        score += when (draft.priceLevel) {
            ShoppingPriceLevel.LOW -> 8
            ShoppingPriceLevel.MEDIUM -> 3
            ShoppingPriceLevel.HIGH -> -8
            ShoppingPriceLevel.VERY_HIGH -> -18
        }
        score += when (draft.careComplexity) {
            CareComplexity.EASY -> 8
            CareComplexity.MEDIUM -> 0
            CareComplexity.DIFFICULT -> -10
        }
        score += when (draft.confidence) {
            DecisionConfidence.HIGH -> 10
            DecisionConfidence.MEDIUM -> 0
            DecisionConfidence.LOW -> -15
        }
        score += when (draft.colorDirection) {
            ShoppingColorDirection.NEUTRAL,
            ShoppingColorDirection.WARM_NEUTRAL,
            ShoppingColorDirection.COOL_NEUTRAL -> 8
            ShoppingColorDirection.ACCENT -> 2
            ShoppingColorDirection.BRIGHT -> -5
            ShoppingColorDirection.DARK,
            ShoppingColorDirection.LIGHT -> 3
        }

        return min(100, max(0, score))
    }

    private fun mainReason(verdict: ShoppingVerdict): ShoppingReason = when (verdict) {
        ShoppingVerdict.BUY -> ShoppingReason(
            "This item fills a clear wardrobe need and should work across multiple outfits.",
            "Эта вещь закрывает понятную потребность гардероба и должна работать в нескольких образах."
        )
        ShoppingVerdict.MAYBE -> ShoppingReason(
            "This item can work, but the wardrobe value is not strong enough yet.",
            "Эта вещь может подойти, но польза для гардероба пока недостаточно сильная."
        )
        ShoppingVerdict.SKIP -> ShoppingReason(
            "This item is likely to become an underused purchase.",
            "Эта вещь с высокой вероятностью станет редко используемой покупкой."
        )
    }

    private fun positiveReasons(draft: ShoppingItemDraft): List<ShoppingReason> {
        val reasons = mutableListOf<ShoppingReason>()
        if (draft.wardrobeGap == WardrobeGapStatus.FILLS_CLEAR_GAP) {
            reasons += ShoppingReason(
                "It fills a clear wardrobe gap.",
                "Она закрывает явный пробел в гардеробе."
            )
        }
        if (draft.expectedWearFrequency == ExpectedWearFrequency.WEEKLY) {
            reasons += ShoppingReason(
                "Weekly wear makes the cost and storage space easier to justify.",
                "Еженедельная носка лучше оправдывает цену и место в гардеробе."
            )
        }
        if (draft.similarItemsOwned == SimilarItemsOwned.NONE) {
            reasons += ShoppingReason(
                "You do not already own a close duplicate.",
                "У вас нет близкого дубля этой вещи."
            )
        }
        if (draft.careComplexity == CareComplexity.EASY) {
            reasons += ShoppingReason(
                "Easy care increases the chance that you will actually wear it.",
                "Простой уход повышает шанс, что вещь действительно будет носиться."
            )
        }
        if (draft.colorDirection in listOf(
                ShoppingColorDirection.NEUTRAL,
                ShoppingColorDirection.WARM_NEUTRAL,
                ShoppingColorDirection.COOL_NEUTRAL
            )
        ) {
            reasons += ShoppingReason(
                "The color direction is easy to combine in a capsule.",
                "Цветовое направление легко встроить в капсулу."
            )
        }
        return reasons.ifEmpty {
            listOf(
                ShoppingReason(
                    "The item has at least some styling potential.",
                    "У вещи есть базовый потенциал для стилизации."
                )
            )
        }
    }

    private fun risks(draft: ShoppingItemDraft): List<ShoppingRisk> {
        val risks = mutableListOf<ShoppingRisk>()
        if (draft.wardrobeGap == WardrobeGapStatus.DUPLICATE) {
            risks += ShoppingRisk(
                "It duplicates something you already own.",
                "Она дублирует вещь, которая уже есть."
            )
        }
        if (draft.similarItemsOwned == SimilarItemsOwned.TWO_OR_MORE) {
            risks += ShoppingRisk(
                "Two or more similar items reduce the real wardrobe value.",
                "Две или больше похожих вещей снижают реальную пользу покупки."
            )
        }
        if (draft.expectedWearFrequency in listOf(ExpectedWearFrequency.RARELY, ExpectedWearFrequency.ONE_TIME)) {
            risks += ShoppingRisk(
                "Low expected wear frequency makes it likely to sit unused.",
                "Низкая ожидаемая частота носки повышает риск, что вещь будет лежать без дела."
            )
        }
        if (draft.priceLevel in listOf(ShoppingPriceLevel.HIGH, ShoppingPriceLevel.VERY_HIGH)) {
            risks += ShoppingRisk(
                "The price level requires stronger wardrobe value.",
                "Высокая цена требует более сильной пользы для гардероба."
            )
        }
        if (draft.careComplexity == CareComplexity.DIFFICULT) {
            risks += ShoppingRisk(
                "Difficult care can reduce actual wear.",
                "Сложный уход может снизить реальную частоту носки."
            )
        }
        if (draft.confidence == DecisionConfidence.LOW) {
            risks += ShoppingRisk(
                "Low confidence is a signal to pause before buying.",
                "Низкая уверенность — сигнал остановиться перед покупкой."
            )
        }
        if (draft.colorDirection == ShoppingColorDirection.BRIGHT) {
            risks += ShoppingRisk(
                "Bright color may be harder to repeat across outfits.",
                "Яркий цвет может быть сложнее регулярно сочетать."
            )
        }
        return risks.ifEmpty {
            listOf(
                ShoppingRisk(
                    "No major risk is visible from the selected inputs.",
                    "По выбранным параметрам нет выраженного риска."
                )
            )
        }
    }

    private fun estimatedOutfitCount(score: Int, draft: ShoppingItemDraft): ShoppingReason = when {
        score >= 82 -> ShoppingReason("7-9 outfits", "7-9 образов")
        score >= 72 -> ShoppingReason("5-7 outfits", "5-7 образов")
        score >= 55 -> ShoppingReason("3-4 outfits", "3-4 образа")
        draft.expectedWearFrequency == ExpectedWearFrequency.ONE_TIME -> ShoppingReason("1 event outfit", "1 образ для события")
        else -> ShoppingReason("0-2 outfits", "0-2 образа")
    }

    private fun capsuleImpact(score: Int, draft: ShoppingItemDraft): ShoppingReason = when {
        draft.wardrobeGap == WardrobeGapStatus.FILLS_CLEAR_GAP && score >= 72 -> ShoppingReason(
            "Strong capsule upgrade",
            "Сильное усиление капсулы"
        )
        draft.wardrobeGap == WardrobeGapStatus.DUPLICATE -> ShoppingReason(
            "Low capsule impact: likely duplication",
            "Низкое влияние на капсулу: вероятный дубль"
        )
        score >= 45 -> ShoppingReason(
            "Moderate capsule impact",
            "Среднее влияние на капсулу"
        )
        else -> ShoppingReason(
            "Weak capsule fit",
            "Слабая совместимость с капсулой"
        )
    }

    private fun recommendation(verdict: ShoppingVerdict): ShoppingReason = when (verdict) {
        ShoppingVerdict.BUY -> ShoppingReason(
            "Buy if the fit and fabric quality are acceptable in person.",
            "Покупайте, если посадка и качество ткани вживую устраивают."
        )
        ShoppingVerdict.MAYBE -> ShoppingReason(
            "Pause and compare it with your current wardrobe before buying.",
            "Сделайте паузу и сравните вещь с текущим гардеробом перед покупкой."
        )
        ShoppingVerdict.SKIP -> ShoppingReason(
            "Skip this item and look for a higher-use alternative.",
            "Пропустите эту вещь и ищите более полезную альтернативу."
        )
    }
}
