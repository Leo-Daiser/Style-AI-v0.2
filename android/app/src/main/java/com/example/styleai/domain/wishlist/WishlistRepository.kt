package com.example.styleai.domain.wishlist

import kotlinx.coroutines.flow.Flow

interface WishlistRepository {
    fun observeWishlist(): Flow<List<WishlistItem>>
    suspend fun addWishlistItem(item: WishlistItem)
    suspend fun updateWishlistItem(item: WishlistItem)
    suspend fun archiveWishlistItem(id: String)
    suspend fun deleteWishlistItem(id: String)
    suspend fun clearWishlist()
}
