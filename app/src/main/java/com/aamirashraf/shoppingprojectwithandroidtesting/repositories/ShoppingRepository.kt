package com.aamirashraf.shoppingprojectwithandroidtesting.repositories

import android.app.DownloadManager.Query
import androidx.lifecycle.LiveData
import com.aamirashraf.shoppingprojectwithandroidtesting.data.local.ShoppingItem
import com.aamirashraf.shoppingprojectwithandroidtesting.data.remote.response.ImageResponse
import com.aamirashraf.shoppingprojectwithandroidtesting.others.Resource
import retrofit2.Response

interface ShoppingRepository {
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItem():LiveData<List<ShoppingItem>>
    fun observeTotalPrice():LiveData<Float>

    suspend fun searchForImage(imageQuery:String):Resource<ImageResponse>
}