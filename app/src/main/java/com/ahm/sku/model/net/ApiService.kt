package com.ahm.sku.model.net

import com.ahm.sku.model.AppRepository
import com.ahm.sku.model.local.chatData.ChatData
import com.ahm.sku.model.local.homeData.HomeData
import com.ahm.sku.model.local.toolsList.ToolsList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class ApiService {

    interface ApiService {

        // Get() =>
        @GET("home_data.json")
        fun getHomeData(): Call<List<HomeData>>

        @GET("chat_data.json")
        fun getChatData(): Call<List<ChatData>>

        @GET("tools_list.json")
        fun getToolsList(): Call<List<ToolsList>>

        @GET("uni_pics.json")
        fun getUniPics(): Call<List<String>>

        @GET("parent_data.json")
        fun getParentData(): Call<String>

    }

    fun getApi(dataFather: String): ApiService =
        Retrofit.Builder()
            .baseUrl(dataFather)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

    fun getParentApi(): ApiService =
        Retrofit.Builder()
            .baseUrl(AppRepository.PARENT_DATA)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)


}