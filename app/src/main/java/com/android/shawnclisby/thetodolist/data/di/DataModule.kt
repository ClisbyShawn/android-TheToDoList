package com.android.shawnclisby.thetodolist.data.di

import android.content.Context
import androidx.room.Room
import com.android.shawnclisby.thetodolist.data.local.CategoryDao
import com.android.shawnclisby.thetodolist.data.local.TaskDao
import com.android.shawnclisby.thetodolist.data.local.TheListDatabase
import com.android.shawnclisby.thetodolist.data.repository.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun providesTheListDatabase(
        @ApplicationContext context: Context
    ): TheListDatabase = Room.databaseBuilder(
        context,
        TheListDatabase::class.java,
        "the-list-database"
    ).build()


    @Singleton
    @Provides
    fun providesTaskDao(
        database: TheListDatabase
    ): TaskDao = database.taskDao()

    @Singleton
    @Provides
    fun providesCategoryDao(
        database: TheListDatabase
    ): CategoryDao = database.categoryDao()


    @Singleton
    @Provides
    fun providesCategoryRepository(
        categoryDao: CategoryDao
    ): CategoryRepository = CategoryRepository(categoryDao)
}