package com.ahm.sku.customView.productView

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahm.sku.R
import com.ahm.sku.model.local.homeData.HomeData
import kotlinx.android.synthetic.main.custom_product_view.view.*

class ProductView(contextInstance: Context, attr: AttributeSet)
    : FrameLayout(contextInstance, attr) {

    private val txtTitle :TextView
    private val recycler :RecyclerView

    init {

        val mainView = View.inflate(context , R.layout.custom_product_view , this)
        txtTitle = mainView.txt_title
        recycler = mainView.recycler_main

        recycler.layoutManager = LinearLayoutManager(context , RecyclerView.HORIZONTAL , true)

        val typeArray = context.obtainStyledAttributes(attr , R.styleable.ProductView)
        val title = typeArray.getString(R.styleable.ProductView_productTitle) ?: "جدیدترین ها"
        typeArray.recycle()

        txtTitle.text = title


    }

    fun initRecycler(data :List<HomeData> , activity: Activity) {

        recycler.adapter = ProductAdapter(data , context , activity)

    }


}