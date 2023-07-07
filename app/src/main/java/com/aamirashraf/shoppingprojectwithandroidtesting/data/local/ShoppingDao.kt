package com.aamirashraf.shoppingprojectwithandroidtesting.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShoppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItems(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItems(shoppingItem: ShoppingItem)

    //now two query
    @Query("select * from shopping_items")
    fun observeAllShoppingItem():LiveData<List<ShoppingItem>>
    @Query("select sum(price*amount) from shopping_items")
    fun observeTotalPrice():LiveData<Float>
}