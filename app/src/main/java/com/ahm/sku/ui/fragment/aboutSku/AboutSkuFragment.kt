package com.ahm.sku.ui.fragment.aboutSku

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ahm.coronam.androidWrapper.NetWorkChecker
import com.ahm.sku.R
import com.ahm.sku.androidWrapper.G
import com.ahm.sku.ui.activity.imageGalleryActivity.ImageGalleryActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.fragment_about_sku.*
import org.koin.android.ext.android.inject


class AboutSkuFragment : Fragment() {
    private val glide :RequestManager by inject()
    private val aboutSkuViewModelFactory: AboutSkuViewModelFactory by inject()
    private lateinit var aboutSkuViewModel: AboutSkuViewModel

    // Main Thread =>
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        G.whereTo = -1

        btn_aboutSku.setOnClickListener {

            if(NetWorkChecker(requireContext()).checkNetwork) {
                val intent = Intent(context , ImageGalleryActivity::class.java)
                startActivity(intent)
                requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
            } else
                Toast.makeText(context, "ابتدا به اینترنت متصل شوید", Toast.LENGTH_SHORT).show()

        }

        img_aboutSku.setOnClickListener {

            val bitmap = img_aboutSku.drawable.toBitmap()
            Handler().postDelayed(80) {
                showFullScreenImage(bitmap , img_aboutSku)
            }

        }


        initPicture()


    }

    private fun showFullScreenImage(imageUri: Bitmap, imageView: ImageView) {

        StfalconImageViewer.Builder(context, listOf(imageUri)) { view, image ->
            Glide.with(this).load(image).into(view)
        }
            .withTransitionFrom(imageView)
            .show()

    }


    private fun initPicture() {

        val url = "https://www.kafe-it.ir/CafeSku/pic/uniSKU.jpg"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            img_aboutSku.clipToOutline = true
        }

        glide
            .load(url)
            .fitCenter()
            .override(960, 640)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .error(R.drawable.img_error)
            .into(img_aboutSku)

    }

    // create viewModel and viewModelFactory =>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        aboutSkuViewModel = ViewModelProvider(this , aboutSkuViewModelFactory)
            .get(AboutSkuViewModel::class.java)

//        aboutSkuViewModel = ViewModelProviders.of(this, aboutSkuViewModelFactory)
//            .get(AboutSkuViewModel::class.java)


        return inflater.inflate(R.layout.fragment_about_sku, container, false)
    }

}
