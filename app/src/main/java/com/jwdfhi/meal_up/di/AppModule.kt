package com.jwdfhi.meal_up.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.jwdfhi.meal_up.databases.MealDao
import com.jwdfhi.meal_up.databases.MealDatabase
import com.jwdfhi.meal_up.services.MealService
import com.jwdfhi.meal_up.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideMealService(): MealService {
        return Retrofit.Builder()
            .baseUrl(Constant.MEAL_SERVICE_BASE_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealService::class.java)
    }

    @Provides
    @Singleton
    fun provideMealDatabase(@ApplicationContext context: Context): MealDatabase {
        return Room
            .databaseBuilder(
                context,
                MealDatabase::class.java,
                "meal_database"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMealDao(mealDatabase: MealDatabase): MealDao {
        return mealDatabase.mealDao()
    }

}