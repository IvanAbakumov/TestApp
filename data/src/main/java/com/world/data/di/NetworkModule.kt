package com.world.data.di

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.world.data.NETWORK
import com.world.data.SERVER_ENDPOINT
import com.world.data.call_adapter.FlowCallAdapterFactory
import com.world.data.converter.asConverterFactory
import com.world.data.di.quialifiers.InterceptorLogTag
import com.world.data.di.quialifiers.OkhttpWithoutAuth
import com.world.data.di.quialifiers.ServerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    private val json by lazy {
        Json {
            encodeDefaults = false
            ignoreUnknownKeys = true
            isLenient = true
            allowSpecialFloatingPointValues = false
            allowStructuredMapKeys = true
            prettyPrint = false
            coerceInputValues = true
            useArrayPolymorphism = false
        }
    }

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    @ServerApi
    internal fun createRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(SERVER_ENDPOINT)//BuildConfig.ENDPOINT)
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaTypeOrNull()!!)
            )
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory.create())

        builder.client(okHttpClient)

        return builder.build()
    }

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    //@DynamicApi
    internal fun createRetrofitDynamic(
        @OkhttpWithoutAuth okHttpClient: OkHttpClient
    ): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl(SERVER_ENDPOINT) //BuildConfig.ENDPOINT)
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaTypeOrNull()!!)
            )
            .addCallAdapterFactory(FlowCallAdapterFactory.create())

        builder.client(okHttpClient)

        return builder.build()
    }

    @Provides
    fun provideRequestInterceptor(
        // accountManager: AccountManager,
    ) = Interceptor {
        val builder = it.request().newBuilder().url(it.request().url)
        builder.addHeader("Content-Type","application/json")
//        accountManager.getToken()?.let { token ->
//            builder.header(AUTH, token.toBearer())
//        }
        it.proceed(builder.build())
    }

    @Singleton
    @Provides
    @InterceptorLogTag
    internal fun logTag(): String = NETWORK

    @Provides
    internal fun chuckInterceptor(@ApplicationContext context: Context): ChuckerInterceptor =
        ChuckerInterceptor(context)

    @Provides
    internal fun loggingInterceptor(@InterceptorLogTag logTag: String): LoggingInterceptor {
        return LoggingInterceptor.Builder()
            .setLevel(Level.BASIC ) //if (isLogsEnabled()) Level.BASIC else Level.NONE)
            .log(Log.INFO)
            .request(logTag)
            .response(logTag)
            .build()
    }

    @ExperimentalSerializationApi
    @Provides
    @OkhttpWithoutAuth
    internal fun okHttpClientWithoutAuth(
        logging: LoggingInterceptor,
        chuck: ChuckerInterceptor,
    ): OkHttpClient {
        val requestInterceptor =
            Interceptor {
                val builder1 = it.request().newBuilder().url(it.request().url)
                builder1.addHeader("Content-Type","application/json")
                it.proceed(builder1.build())
            }

        val builder = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(chuck)
            .addInterceptor(requestInterceptor)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        return builder.build()
    }

    @ExperimentalSerializationApi
    @Provides
    internal fun okHttpClientBase(
        logging: LoggingInterceptor,
        chuck: ChuckerInterceptor,
        requestInterceptor: Interceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(chuck)
            .addInterceptor(requestInterceptor)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        return builder.build()
    }

    companion object {
        private const val TIME_OUT = 60L
    }
}

val nullOnEmptyConverterFactory = object : Converter.Factory() {
    fun converterFactory() = this
    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object :
        Converter<ResponseBody, Any?> {
        val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
        override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
    }
}