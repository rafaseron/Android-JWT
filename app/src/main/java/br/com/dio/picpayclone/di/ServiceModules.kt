package br.com.dio.picpayclone.di

import br.com.dio.picpayclone.data.UsuarioLogado
import br.com.dio.picpayclone.services.ApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val serviceModule =
    module {
        single { provideRetrofit(get()) }
        single { provideOkHttpClient(get(), get()) }
        single { provideHttpLoggingInterceptor() }
        single { provideApiService(get()) }
        single { provideAuthInterceptor() }
    }

fun provideRetrofit(client: OkHttpClient): Retrofit =
    Retrofit
        .Builder()
        .baseUrl("https://yourapihere.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

fun provideOkHttpClient(interceptor: HttpLoggingInterceptor, authInterceptor: AuthInterceptor): OkHttpClient =
    OkHttpClient
        .Builder()
        .addInterceptor(interceptor)
        .addInterceptor(authInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

fun provideAuthInterceptor(): AuthInterceptor{
    return AuthInterceptor(UsuarioLogado.token)
}

class AuthInterceptor(private val token: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()

        val authenticatedRequest = request.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        return chain.proceed(authenticatedRequest)
    }

}
