package com.ahm.sku.model.local.toolsList

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "toolsList_data_table")
data class ToolsList
    (

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    val groupId :Int ,

    val name :String ,

    val optionInfo :String

)
