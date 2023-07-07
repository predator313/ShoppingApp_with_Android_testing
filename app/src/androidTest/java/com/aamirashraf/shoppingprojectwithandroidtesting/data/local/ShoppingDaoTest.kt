package com.aamirashraf.shoppingprojectwithandroidtesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {
    @get:Rule  //by this we tell rule to the junit and test case will run not fails
    var instantTaskExecutorRule=InstantTaskExecutorRule()  //we need this to run every things in serial manner
    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao
    @Before
    fun setup(){
        //inMemoryDatabaseBuilder()should be use because it is not a real db,
        //means it hold the database only inside the ram not inside the app persistence database
        database=Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        //we should use .allowMainThreadQueries() because we want all action one after another in serial manner
        ).allowMainThreadQueries().build()
        dao=database.shoppingDao()
    }

    @After
    fun teardown(){
        database.close()
    }

    //since we don't want concurrency aka multithreading in test case so we need to use run blocking here
    //run blocking is the way of executing coroutines in the main thread
    //but here we are going to use runblockingtest which is optimize for the testcase vvi
    //run blocking test will skips the delay function
    @Test
    fun insertShoppingItems() = runBlockingTest {
        val shoppingItem=ShoppingItem("name",1,1f,"url",1)
        dao.insertShoppingItems(shoppingItem)
        val allShoppingItem=dao.observeAllShoppingItem().getOrAwaitValue()  //but this return the live data and
        //we know that the live data is asynchronous
        //but luckily google provide a super helpful class  to solve this problem
        //so doing .geOrAwaitValue() return the list not the livedata
        assertThat(allShoppingItem).contains(shoppingItem)  //means in the of all Shopping item
        //the new shopping item should be present
    }
    @Test
    fun deleteShoppingItems() = runBlockingTest{
        val shoppingItem=ShoppingItem("name",1,1f,"url",1)
        //before deleting we must first insert the item
        //because if item is not present it can't be deleted
        dao.insertShoppingItems(shoppingItem)
        dao.deleteShoppingItems(shoppingItem)
        val allShoppingItem=dao.observeAllShoppingItem().getOrAwaitValue()
        assertThat(allShoppingItem).doesNotContain(shoppingItem)
    }
    @Test
    fun observeTotalPriceSum()=runBlockingTest {
        val shoppingItem1=ShoppingItem("mango",1,10f,"url",1)
        val shoppingItem2=ShoppingItem("apple",4,5f,"url",2)
        val shoppingItem3=ShoppingItem("orange",10,2f,"url",3)
        val shoppingItem4=ShoppingItem("tomato",15,1f,"url",4)
        dao.insertShoppingItems(shoppingItem1)
        dao.insertShoppingItems(shoppingItem2)
        dao.insertShoppingItems(shoppingItem3)
        dao.insertShoppingItems(shoppingItem4)
        val totalPriceSum=dao.observeTotalPrice().getOrAwaitValue()
        assertThat(totalPriceSum).isEqualTo(1*10f+4*5f+10*2f+15*1f)
    }



}