package com.ahm.sku.model.local.homeData

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface HomeDataDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHomeData(homeData: HomeData): Long

    @Insert()
    suspend fun insertAllHomeData(homeData: List<HomeData>)

    @Update()
    suspend fun updateHomeData(homeData: HomeData) :Int

    @Update()
    suspend fun updateAllHomeData(homeData: List<HomeData>)

    @Query("DELETE FROM homeData_data_table")
    suspend fun deleteAllHomeData(): Int

    @Query("SELECT * FROM homedata_data_table")
    fun getAllHomeData(): LiveData<List<HomeData>>

    @Query("SELECT * FROM homedata_data_table where groupId = :id")
    fun getAllHomeDataByGroupId(id :Int): LiveData<List<HomeData>>

    @Query("SELECT * FROM homedata_data_table where cardId = :id")
    fun getHomeDataByCardId(id: Int): LiveData<HomeData>


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
