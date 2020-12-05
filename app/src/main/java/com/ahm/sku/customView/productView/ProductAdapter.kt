package com.ahm.sku.customView.productView

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ahm.sku.R
import com.ahm.sku.model.AppRepository
import com.ahm.sku.model.local.homeData.HomeData
import com.ahm.sku.ui.activity.homeDetailActivity.HomeDetailActivity
import com.ahm.sku.ui.activity.toolsList.ToolsListActivity
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ProductAdapter(
    private val data: List<HomeData>,
    private val context: Context,
    private val activity: Activity
) :
    RecyclerView.Adapter<ProductAdapter.CustomViewHolder>(), KoinComponent {

    private val glide: RequestManager by inject()

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var productCard: CardView = itemView.findViewById<CardView>(R.id.itemCardView)
        var productName: TextView = itemView.findViewById<TextView>(R.id.txt_product_name)
        val productImage: ImageView = itemView.findViewById<ImageView>(R.id.img_product_show)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_view, parent, false)
        )
    }

    override fun getItemCount(): Int = data.count()

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.productName.text = data[position].homeText

        glide
            .load(data[position].homeImageUrl)
            .fitCenter()
            .override(640, 640)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .error(R.drawable.img_error)
            .into(holder.productImage)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.productImage.clipToOutline = true
        }

        if (position == 0) {
            val layoutParamsCustom = holder.productCard.layoutParams as RecyclerView.LayoutParams
            layoutParamsCustom.setMargins(
                context.resources.getDimension(R.dimen.margin_card_normal).toInt(),
                context.resources.getDimension(R.dimen.margin_card_normal).toInt(),
                context.resources.getDimension(R.dimen.margin_card_first_right).toInt(),
                context.resources.getDimension(R.dimen.margin_card_normal).toInt()
            )
            holder.productCard.layoutParams = layoutParamsCustom
            holder.productCard.requestLayout()
        } else {
            val layoutParamsCustom = holder.productCard.layoutParams as RecyclerView.LayoutParams
            layoutParamsCustom.setMargins(
                context.resources.getDimension(R.dimen.margin_card_normal).toInt(),
                context.resources.getDimension(R.dimen.margin_card_normal).toInt(),
                context.resources.getDimension(R.dimen.margin_card_normal_right).toInt(),
                context.resources.getDimension(R.dimen.margin_card_normal).toInt()
            )

            holder.productCard.layoutParams = layoutParamsCustom
            holder.productCard.requestLayout()
            holder.productCard.requestLayout()
        }

        holder.productImage.setOnClickListener {
            clicked( data[position].cardId.toString() )
        }
        holder.productCard.setOnClickListener {
            clicked( data[position].cardId.toString() )
        }


    }

    private fun clicked(cardId:String) {
        val intent = Intent(context , HomeDetailActivity::class.java)
        intent.putExtra(AppRepository.CARD_ID , cardId)
        context.startActivity(intent)
        activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

}