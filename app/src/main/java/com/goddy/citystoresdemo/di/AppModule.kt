package com.goddy.citystoresdemo.di

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.goddy.citystoresdemo.BuildConfig
import com.goddy.citystoreslibrary.data.LiveDataCallAdapterFactory
import com.goddy.citystoreslibrary.data.api.ApiService
import com.goddy.citystoreslibrary.data.db.AppDatabase
import com.goddy.citystoreslibrary.data.db.CityDao
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .addInterceptor(Interceptor { chain ->
                        val requestBuilder = chain.request().newBuilder()
                        return@Interceptor chain.proceed(requestBuilder.build())

                    })
                    .build()

    @Singleton
    @Provides
    fun provideAppService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(ApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase {
        return Room
                .databaseBuilder(app, AppDatabase::class.java, "androidtrends.db")
                .fallbackToDestructiveMigration()
                .build()
    }


    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): CityDao {
        return db.cityDao()
    }
}
