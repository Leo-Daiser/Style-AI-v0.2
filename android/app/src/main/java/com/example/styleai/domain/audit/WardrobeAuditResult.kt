package com.example.styleai.domain.audit

data class WardrobeAuditResult(
    val totalItems: Int,
    val keepCount: Int,
    val replaceCount: Int,
    val donateSellCount: Int,
    val archiveCount: Int,
    val averageVersatilityScore: Int,
    val auditItems: List<WardrobeAuditItem>
)
