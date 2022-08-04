package com.jwdfhi.meal_up.di

import com.jwdfhi.meal_up.services.MealService
import com.jwdfhi.meal_up.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideMealService(): MealService {
        return Retrofit.Builder()
            .baseUrl(Constant.MEAL_SERVICE_BASE_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealService::class.java)
    }

}