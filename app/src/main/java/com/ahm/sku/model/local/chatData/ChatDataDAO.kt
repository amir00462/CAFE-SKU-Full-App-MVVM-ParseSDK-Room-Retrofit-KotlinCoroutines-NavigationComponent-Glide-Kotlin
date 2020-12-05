package com.ahm.sku.model.local.chatData

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ChatDataDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatData(chatData: ChatData): Long

    @Insert()
    suspend fun insertAllChatData(chatDatas: List<ChatData>)

    @Query("DELETE FROM chatData_data_table")
    suspend fun deleteAllChatData(): Int

    @Query("SELECT * FROM chatData_data_table ORDER BY timeInMillies DESC")
    fun getAllChatDataByTime(): LiveData<List<ChatData>>

    @Query("SELECT COUNT(timeInMillies) FROM chatData_data_table")
    fun getRowCountChatData(): LiveData<Int>



//    @Query("SELECT COUNT(nameWriter) FROM textTavalod_data_table where typeTabrik = :type")
//    fun getRowCountTextTabrikTavalod(type: String = SubscriberRepository.TYPE_TAVALOD): LiveData<Int>
//
//    @Query("SELECT COUNT(nameWriter) FROM textTavalod_data_table where typeTabrik = :type")
//    fun getRowCountTextTabrikSalgardEzdevaj(type: String = SubscriberRepository.TYPE_SALGARD_EZDEVAJ): LiveData<Int>
//
//    @Query("SELECT COUNT(nameWriter) FROM textTavalod_data_table")
//    fun getRowCountTextTabrikAll(type: String = SubscriberRepository.TYPE_TAVALOD): LiveData<Int>
//
//    @Query("SELECT * FROM textTavalod_data_table where typeTabrik = :type")
//    fun getTextTabrikTavalod(type: String = SubscriberRepository.TYPE_TAVALOD): LiveData<List<TextTabrik>>
//
//    @Query("SELECT * FROM textTavalod_data_table where typeTabrik = :type")
//    fun getTextTabrikSalgardEzdevaj(type: String = SubscriberRepository.TYPE_SALGARD_EZDEVAJ): LiveData<List<TextTabrik>>
//
//    @Query("SELECT * FROM textTavalod_data_table where id = :id")
//    fun getTextTabrikByID(id: Int): LiveData<TextTabrik>

}
