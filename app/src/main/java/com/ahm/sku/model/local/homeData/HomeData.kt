package com.ahm.sku.model.local.homeData

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.security.acl.Acl
import java.util.*

@Entity(tableName = "homeData_data_table")
data class HomeData(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    val cardId: Int,

    val groupId: Int,

    val hasLinkAddress: Boolean,
    val hasNavigate: Boolean,
    val hasVideo: Boolean,

    val homeDetailImageUrl: String,
    val homeDetailText: String,

    val homeImageUrl: String,
    val homeText: String,

    val linkUrl: String,
    val navigateUri: String,
    val videoUrl: String

)