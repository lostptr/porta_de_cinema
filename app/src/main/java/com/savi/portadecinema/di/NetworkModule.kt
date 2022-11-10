package com.savi.portadecinema.di

import com.google.gson.GsonBuilder
import com.savi.portadecinema.utils.LocalDateAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate

val networkModule = module {

    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor { msg ->
                println("LOG APP: $msg")
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).addNetworkInterceptor(HttpLoggingInterceptor { msg ->
                println("LOG NTW: $msg")
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()


    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder().registerTypeAdapter(
            LocalDate::class.java,
            LocalDateAdapter().nullSafe()
        ).create()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://api.themoviedb.org/3/")
            .client(okHttpClient)
            .build()
    }

    single { provideOkHttpClient() }
    single { provideRetrofitClient(okHttpClient = get()) }
}