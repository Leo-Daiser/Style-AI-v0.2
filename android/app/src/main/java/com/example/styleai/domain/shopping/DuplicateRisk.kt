package com.example.styleai.domain.shopping

import com.example.styleai.domain.model.AppLanguage

enum class DuplicateRiskLevel {
    LOW,
    MEDIUM,
    HIGH
}

data class DuplicateRisk(
    val level: DuplicateRiskLevel,
    val score: Int,
    val matchedItems: List<String>,
    val reason: String
)

fun DuplicateRiskLevel.label(language: AppLanguage): String = when (this) {
    DuplicateRiskLevel.LOW -> if (language == AppLanguage.RU) "Низкий" else "Low"
    DuplicateRiskLevel.MEDIUM -> if (language == AppLanguage.RU) "Средний" else "Medium"
    DuplicateRiskLevel.HIGH -> if (language == AppLanguage.RU) "Высокий" else "High"
}
