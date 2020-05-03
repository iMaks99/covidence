package com.sr.covidence.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkService {
    private var mRetrofit: Retrofit? = null

    val registrationEndpoint: RegistrationEndpoint?
        get() = mRetrofit?.create(RegistrationEndpoint::class.java)

    init {

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("content-type", "application/json")
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(3, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()


        mRetrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    companion object {
        private const val BASE_URL = "https://google.com"

        var instance: NetworkService? = null
            get() {
                if (field == null) {
                    field = NetworkService()
                }
                return field
            }
    }
}