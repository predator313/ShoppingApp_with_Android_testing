package com.aamirashraf.shoppingprojectwithandroidtesting.di

import android.content.Context
import androidx.room.Room
import com.aamirashraf.shoppingprojectwithandroidtesting.data.local.ShoppingItemDatabase
import com.aamirashraf.shoppingprojectwithandroidtesting.data.remote.PixelbayApi
import com.aamirashraf.shoppingprojectwithandroidtesting.others.Constants.BASE_URL
import com.aamirashraf.shoppingprojectwithandroidtesting.others.Constants.DATA_BASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)   //means the lifetime of this module is as long as the application lives
object AppModule {
    //where we do the work for both room and retrofit
    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context:Context
    ) = Room.databaseBuilder(context,ShoppingItemDatabase::class.java,DATA_BASE_NAME)

    //now for shopping dao
    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    //now for the retrofit
    @Singleton
    @Provides
    fun pixelbayApi():PixelbayApi{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixelbayApi::class.java)

    }
}