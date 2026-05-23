package com.example.styleai.domain.wardrobe

import kotlinx.coroutines.flow.Flow

interface WardrobeRepository {
    fun observeItems(): Flow<List<WardrobeItem>>
    suspend fun saveItem(item: WardrobeItem)
    suspend fun deleteItem(id: String)
    suspend fun clearItems()
    suspend fun getItemById(id: String): WardrobeItem?
}
