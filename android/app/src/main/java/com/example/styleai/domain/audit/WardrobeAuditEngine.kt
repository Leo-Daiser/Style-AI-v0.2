package com.example.styleai.domain.audit

import com.example.styleai.domain.model.AppLanguage
import com.example.styleai.domain.shopping.ShoppingSeason
import com.example.styleai.domain.wardrobe.CapsuleRole
import com.example.styleai.domain.wardrobe.WardrobeItem

object WardrobeAuditEngine {
    fun audit(
        items: List<WardrobeItem>,
        language: AppLanguage,
        hiddenItemIds: Set<String> = emptySet()
    ): WardrobeAuditResult {
        val auditItems = items
            .filterNot { hiddenItemIds.contains(it.id) }
            .map { auditItem(it, language) }
            .sortedBy { it.auditScore }

        return WardrobeAuditResult(
            totalItems = auditItems.size,
            keepCount = auditItems.count { it.recommendation == AuditRecommendation.KEEP },
            replaceCount = auditItems.count { it.recommendation == AuditRecommendation.REPLACE || it.recommendation == AuditRecommendation.WISHLIST_SIMILAR },
            donateSellCount = auditItems.count { it.recommendation == AuditRecommendation.DONATE_SELL },
            archiveCount = auditItems.count { it.recommendation == AuditRecommendation.ARCHIVE },
            averageVersatilityScore = items.map { it.versatilityScore }.average().takeIf { !it.isNaN() }?.toInt() ?: 0,
            auditItems = auditItems
        )
    }

    private fun auditItem(item: WardrobeItem, language: AppLanguage): WardrobeAuditItem {
        val score = auditScore(item)
        val styleMismatch = item.isWishlist || item.colorDirection.name == "BRIGHT"
        val recommendation = when {
            score >= 75 -> AuditRecommendation.KEEP
            score >= 55 && styleMismatch -> AuditRecommendation.WISHLIST_SIMILAR
            score >= 55 -> AuditRecommendation.KEEP
            score >= 35 -> AuditRecommendation.REPLACE
            score >= 20 -> AuditRecommendation.DONATE_SELL
            else -> AuditRecommendation.ARCHIVE
        }

        return WardrobeAuditItem(
            wardrobeItemId = item.id,
            itemTitle = item.title,
            recommendation = recommendation,
            auditScore = score,
            reasons = reasons(item, language),
            risks = risks(item, language),
            suggestedAction = suggestedAction(recommendation, language),
            replacementHint = replacementHint(item, recommendation, language)
        )
    }

    private fun auditScore(item: WardrobeItem): Int {
        var score = item.versatilityScore
        if (!item.isRarelyUsed) score += 10
        if (item.outfitCount >= 3) score += 15
        if (item.capsuleRole == CapsuleRole.CORE_BASIC) score += 10
        if (item.isWishlist) score += 0
        if (item.isRarelyUsed) score -= 20
        if (item.isDuplicateRisk) score -= 20
        if (item.outfitCount <= 1) score -= 15
        if (item.isWishlist) score -= 15
        if (!item.seasons.contains(ShoppingSeason.ALL_SEASON) && item.seasons.size == 1) score -= 5
        return score.coerceIn(0, 100)
    }

    private fun reasons(item: WardrobeItem, language: AppLanguage): List<String> {
        val reasons = mutableListOf<String>()
        if (item.outfitCount >= 3) {
            reasons += if (language == AppLanguage.RU) "Участвует в нескольких образах." else "Part of multiple outfits."
        }
        if (item.capsuleRole == CapsuleRole.CORE_BASIC) {
            reasons += if (language == AppLanguage.RU) "Работает как базовая вещь капсулы." else "Works as a core capsule piece."
        }
        if (!item.isRarelyUsed) {
            reasons += if (language == AppLanguage.RU) "Нет признаков редкого использования." else "No low-use signal is visible."
        }
        return reasons.ifEmpty {
            listOf(if (language == AppLanguage.RU) "Польза вещи требует проверки." else "Wardrobe value needs review.")
        }
    }

    private fun risks(item: WardrobeItem, language: AppLanguage): List<String> {
        val risks = mutableListOf<String>()
        if (item.isRarelyUsed) {
            risks += if (language == AppLanguage.RU) "Редко используется." else "Rarely used."
        }
        if (item.isDuplicateRisk) {
            risks += if (language == AppLanguage.RU) "Риск дублирования." else "Duplicate risk."
        }
        if (item.outfitCount <= 1) {
            risks += if (language == AppLanguage.RU) "Низкая польза для гардероба." else "Low wardrobe value."
        }
        if (item.isWishlist) {
            risks += if (language == AppLanguage.RU) "Пока не подтверждена как активная вещь." else "Not confirmed as an active wardrobe item."
        }
        return risks.ifEmpty {
            listOf(if (language == AppLanguage.RU) "Критичных рисков не найдено." else "No major risks found.")
        }
    }

    private fun suggestedAction(recommendation: AuditRecommendation, language: AppLanguage): String = when (recommendation) {
        AuditRecommendation.KEEP -> if (language == AppLanguage.RU) "Оставьте вещь в активной капсуле." else "Keep it in the active capsule."
        AuditRecommendation.REPLACE -> if (language == AppLanguage.RU) "Стоит заменить на более универсальный вариант." else "Consider replacing it with a more versatile option."
        AuditRecommendation.DONATE_SELL -> if (language == AppLanguage.RU) "Отложите как кандидат на продажу или передачу." else "Set aside as a donate or sell candidate."
        AuditRecommendation.ARCHIVE -> if (language == AppLanguage.RU) "Архивируйте локально и вернитесь позже." else "Archive it locally and review later."
        AuditRecommendation.WISHLIST_SIMILAR -> if (language == AppLanguage.RU) "Найдите похожую, но более полезную замену." else "Look for a similar but stronger replacement."
    }

    private fun replacementHint(item: WardrobeItem, recommendation: AuditRecommendation, language: AppLanguage): String? {
        if (recommendation == AuditRecommendation.KEEP) return null
        return if (language == AppLanguage.RU) {
            "Ищите ${item.category.name.lowercase()} в нейтральном цвете и с 3+ сценариями носки."
        } else {
            "Look for ${item.category.name.lowercase()} in a neutral color with 3+ use cases."
        }
    }
}
