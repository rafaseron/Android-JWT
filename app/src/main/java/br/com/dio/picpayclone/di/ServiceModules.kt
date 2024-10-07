package br.com.dio.picpayclone.di

import br.com.dio.picpayclone.services.ApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val serviceModule =
    module {
        single { provideRetrofit(get()) }
        single { provideOkHttpClient(get()) }
        single { provideHttpLoggingInterceptor() }
        single { provideApiService(get()) }
    }

fun provideRetrofit(client: OkHttpClient): Retrofit =
    Retrofit
        .Builder()
        .baseUrl("http://10.0.0.112:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .build()

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
