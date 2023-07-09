package com.aamirashraf.shoppingprojectwithandroidtesting.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aamirashraf.shoppingprojectwithandroidtesting.data.local.ShoppingItem
import com.aamirashraf.shoppingprojectwithandroidtesting.data.remote.response.ImageResponse
import com.aamirashraf.shoppingprojectwithandroidtesting.others.Resource


//we make this repository only to test the viewmodel
class FakeShoppingRepository:ShoppingRepository {
    private val shoppingItems = mutableListOf<ShoppingItem>()
    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()
    private var shouldReturnNetworkError = false

    private fun shouldReturnNetworkError(value:Boolean){
        shouldReturnNetworkError=value
    }
    private fun refreshLiveData(){
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }
    private fun getTotalPrice():Float{
        return shoppingItems.sumOf {
            it.price.toDouble()
        }.toFloat()
    }
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
       return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
       return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        if(shouldReturnNetworkError){
            return Resource.error("Error",null)
        }
        else return Resource.success(ImageResponse(listOf(),0,0))
    }
}