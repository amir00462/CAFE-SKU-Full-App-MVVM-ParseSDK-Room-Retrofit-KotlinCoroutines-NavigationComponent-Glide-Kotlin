package com.ahm.sku.model.local.chatData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chatData_data_table")
data class ChatData(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    val text: String,
    val imageUrl: String,

    val isImageMode: Boolean,
    val isImageTextMode: Boolean,
    val isTextMode: Boolean,
    val isNewDataExistedMode: Boolean ,

    val timeInMillies: Long,
    val timePersian: String

)