package com.ahm.sku.ui.activity.imageGalleryActivity

import android.app.Activity
import android.app.DownloadManager
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.ahm.coronam.androidWrapper.NetWorkChecker
import com.ahm.sku.R
import com.ahm.sku.androidWrapper.G
import com.ahm.sku.model.AppRepository
import com.ahm.sku.model.local.toolsList.ToolsList
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.activity_image_gallery.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.io.File

class ImageGalleryAdapter(
    private val data: List<String>,
    private val context: Context ,
    private val clicky :ClickyInterface
) :
    RecyclerView.Adapter<ImageGalleryAdapter.CustomViewHolder>(), KoinComponent {

    private val glide :RequestManager by inject()
    val size3 = G.size / 3

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var parent: FrameLayout = itemView.findViewById<FrameLayout>(R.id.itemUniImageFather)
        var image: ImageView = itemView.findViewById<ImageView>(R.id.imgUniImageSon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_uni_images, parent, false)
        )
    }

    override fun getItemCount(): Int = data.count()

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.image.requestLayout()
        holder.image.layoutParams.height = size3
        holder.image.layoutParams.width = size3

        glide
            .load(data[position])
            .error(R.drawable.img_error)
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(holder.image)

        holder.parent.setOnClickListener {
            clicky.onImageClicked(data[position] , holder.image)
        }


    }

    interface ClickyInterface {
        fun onImageClicked(position :String , targetImage :ImageView)
    }

}
