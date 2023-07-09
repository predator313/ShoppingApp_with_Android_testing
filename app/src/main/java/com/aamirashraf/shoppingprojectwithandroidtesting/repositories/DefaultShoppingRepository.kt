package com.aamirashraf.shoppingprojectwithandroidtesting.repositories

import androidx.lifecycle.LiveData
import com.aamirashraf.shoppingprojectwithandroidtesting.data.local.ShoppingDao
import com.aamirashraf.shoppingprojectwithandroidtesting.data.local.ShoppingItem
import com.aamirashraf.shoppingprojectwithandroidtesting.data.remote.PixelbayApi
import com.aamirashraf.shoppingprojectwithandroidtesting.data.remote.response.ImageResponse
import com.aamirashraf.shoppingprojectwithandroidtesting.others.Resource
import retrofit2.Response
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixelbayApi: PixelbayApi
) :ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItems(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
//        shoppingDao.insertShoppingItems(shoppingItem)
        shoppingDao.deleteShoppingItems(shoppingItem)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItem()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        //we need to do some error handling here
        return try {
            val response = pixelbayApi.searchForImage(imageQuery)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?:return Resource.error("unknown error",null)
            }else return Resource.error("unknown error",null)
        }catch (e:Exception){
            Resource.error("couldn't reach the server please check your internet",null)
        }
    }
}