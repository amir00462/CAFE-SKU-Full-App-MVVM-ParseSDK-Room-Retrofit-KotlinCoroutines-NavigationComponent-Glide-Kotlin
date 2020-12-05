package com.ahm.sku.model.local.toolsList

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ahm.sku.model.AppRepository

@Dao
interface ToolsListDAO {

    @Insert()
    suspend fun insertAllToolsList(toolsList: List<ToolsList>)

    @Query("DELETE FROM toolsList_data_table")
    suspend fun deleteAllToolsList(): Int

    @Query("SELECT * FROM toolsList_data_table where groupId = :id")
    fun getAllToolsListTelephone(id :Int = AppRepository.ID_DAFTARCHE_TELEPHONE): LiveData<List<ToolsList>>

    @Query("SELECT * FROM toolsList_data_table where groupId = :id")
    fun getAllToolsListForm(id :Int = AppRepository.ID_FORM): LiveData<List<ToolsList>>

    @Query("SELECT * FROM toolsList_data_table where groupId = :id")
    fun getAllToolsListChart(id :Int = AppRepository.ID_CHART_ENTEKHAB_VAHED): LiveData<List<ToolsList>>

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
