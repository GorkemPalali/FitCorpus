package com.example.fitcorpus.di

import com.example.fitcorpus.BuildConfig
import com.example.fitcorpus.core.auth.AuthInterceptor
import com.example.fitcorpus.core.auth.TokenAuthenticator
import com.example.fitcorpus.core.security.CertificatePinner
import com.example.fitcorpus.data.remote.api.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val BASE_URL = BuildConfig.API_BASE_URL
    private const val HOSTNAME = "api.fitcorpus.com"
    
    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
    
    @Provides
    @Singleton
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        
        val builder = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(tokenAuthenticator)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        val certificatePinner = CertificatePinner.create(HOSTNAME, BuildConfig.DEBUG)
        certificatePinner?.let {
            builder.certificatePinner(it)
        }
        
        return builder.build()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }
    
    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideTrainerApi(retrofit: Retrofit): TrainerApi {
        return retrofit.create(TrainerApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideWorkoutApi(retrofit: Retrofit): WorkoutApi {
        return retrofit.create(WorkoutApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideDietApi(retrofit: Retrofit): DietApi {
        return retrofit.create(DietApi::class.java)
    }
    
    @Provides
    @Singleton
    fun providePurchaseApi(retrofit: Retrofit): PurchaseApi {
        return retrofit.create(PurchaseApi::class.java)
    }
    
    @Provides
    @Singleton
    fun providePlanApi(retrofit: Retrofit): PlanApi {
        return retrofit.create(PlanApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideStudentApi(retrofit: Retrofit): StudentApi {
        return retrofit.create(StudentApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideCodeApi(retrofit: Retrofit): CodeApi {
        return retrofit.create(CodeApi::class.java)
    }
}