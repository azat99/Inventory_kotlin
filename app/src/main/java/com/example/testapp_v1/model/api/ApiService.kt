package com.example.testapp_v1.model.api

import com.example.testapp_v1.model.branchEntity.BranchListResponse
import com.example.testapp_v1.model.filialsEntity.FilialDataResponse
import com.example.testapp_v1.model.tdsDataEntity.TdsResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


const val BASE_URL = "url"

interface ApiService {

    @GET("firmList")
    fun getFilialList(): Deferred<FilialDataResponse>

    @GET("branchList")
    fun getBranchList(): Deferred<BranchListResponse>

    @GET("tdsData")
    fun getTdsList():Deferred<TdsResponse>

    companion object{

        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): ApiService {

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
            okHttpClient.addInterceptor(connectivityInterceptor)
            okHttpClient.readTimeout(60, TimeUnit.SECONDS)
            okHttpClient.connectTimeout(60, TimeUnit.SECONDS)
            okHttpClient.writeTimeout(60,TimeUnit.SECONDS)
            okHttpClient.addInterceptor(logging)



            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient.build())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

    }

}
