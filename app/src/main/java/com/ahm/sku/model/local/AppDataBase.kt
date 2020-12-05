package com.ahm.sku.model.local

import android.content.Context
import androidx.room.*
import com.ahm.sku.model.local.chatData.ChatData
import com.ahm.sku.model.local.chatData.ChatDataDAO
import com.ahm.sku.model.local.homeData.HomeDataDAO
import com.ahm.sku.model.local.homeData.HomeData
import com.ahm.sku.model.local.toolsList.ToolsList
import com.ahm.sku.model.local.toolsList.ToolsListDAO

@Database(entities = [ HomeData::class , ChatData::class , ToolsList::class  ] , version = 1 , exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract val chatDataDAO: ChatDataDAO
    abstract val homeDataDAO: HomeDataDAO
    abstract val toolsListDAO: ToolsListDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            synchronized(this) {
                var instance =
                    INSTANCE

                if (instance == null) {

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "app_dataBase"
                    ).build()
                }

                return instance
            }
        }

    }

}