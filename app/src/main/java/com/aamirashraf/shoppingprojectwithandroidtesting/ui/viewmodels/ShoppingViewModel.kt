package com.aamirashraf.shoppingprojectwithandroidtesting.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aamirashraf.shoppingprojectwithandroidtesting.data.local.ShoppingItem
import com.aamirashraf.shoppingprojectwithandroidtesting.data.remote.response.ImageResponse
import com.aamirashraf.shoppingprojectwithandroidtesting.others.Event
import com.aamirashraf.shoppingprojectwithandroidtesting.others.Resource
import com.aamirashraf.shoppingprojectwithandroidtesting.repositories.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ShoppingRepository
):ViewModel() {
    val shoppingItem = repository.observeAllShoppingItem()
    val totalPrice=repository.observeTotalPrice()
    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images:LiveData<Event<Resource<ImageResponse>>> = _images  //means we can't post
    //any thing to images from fragment this is very important concept

    private val _currImageUrl = MutableLiveData<String>()
    val currImageUrl:LiveData<String> = _currImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus:LiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus

    fun setCurImgUrl(url:String){
        _currImageUrl.postValue(url)
    }
    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }
    fun deleteShoppingItemFromDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }
    fun insertShoppingItem(name:String,amount:String,price:String){
        //function for user input validate
    }
    fun searchForImage(imgQuery:String){

    }
}