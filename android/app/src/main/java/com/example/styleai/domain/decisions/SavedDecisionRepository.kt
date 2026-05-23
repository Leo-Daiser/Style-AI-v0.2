package com.example.styleai.domain.decisions

import kotlinx.coroutines.flow.Flow

interface SavedDecisionRepository {
    fun observeDecisions(): Flow<List<SavedDecision>>
    suspend fun saveDecision(decision: SavedDecision)
    suspend fun deleteDecision(id: String)
    suspend fun clearDecisions()
    suspend fun getDecisionById(id: String): SavedDecision?
}
