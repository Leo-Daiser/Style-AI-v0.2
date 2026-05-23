package com.example.styleai.domain.audit

import com.example.styleai.domain.model.AppLanguage

enum class AuditRecommendation {
    KEEP,
    REPLACE,
    DONATE_SELL,
    ARCHIVE,
    WISHLIST_SIMILAR
}

data class WardrobeAuditItem(
    val wardrobeItemId: String,
    val itemTitle: String,
    val recommendation: AuditRecommendation,
    val auditScore: Int,
    val reasons: List<String>,
    val risks: List<String>,
    val suggestedAction: String,
    val replacementHint: String?
)

fun AuditRecommendation.label(language: AppLanguage): String = when (this) {
    AuditRecommendation.KEEP -> if (language == AppLanguage.RU) "Оставить" else "Keep"
    AuditRecommendation.REPLACE -> if (language == AppLanguage.RU) "Заменить" else "Replace"
    AuditRecommendation.DONATE_SELL -> if (language == AppLanguage.RU) "Продать или отдать" else "Donate / Sell"
    AuditRecommendation.ARCHIVE -> if (language == AppLanguage.RU) "Архивировать" else "Archive"
    AuditRecommendation.WISHLIST_SIMILAR -> if (language == AppLanguage.RU) "Найти похожую" else "Wishlist similar"
}
