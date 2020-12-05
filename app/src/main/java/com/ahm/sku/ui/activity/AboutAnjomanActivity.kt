package com.ahm.sku.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.ahm.sku.R
import com.ahm.sku.ext.BaseActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.activity_about_anjoman.*
import kotlinx.android.synthetic.main.include_main_activity.*
import org.koin.android.ext.android.inject
import java.util.*

class AboutAnjomanActivity : BaseActivity() {
    private val glide :RequestManager by inject()
    val url = "https://www.kafe-it.ir/CafeSku/pic/Anjoman.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_anjoman)
        setSupportActionBar(toolbarAboutAnjoman)

        // support RTL =>
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL

        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        Objects.requireNonNull(supportActionBar)!!.setDisplayShowHomeEnabled(true)

        clickListeners()
        initPicture()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            android.R.id.home -> {

                onBackPressed()
                return true

            }

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

    }

    private fun clickListeners() {

        anjomanTelegram.setOnClickListener {
            openProgrammerTelegram()
        }


        anjomanInstagram.setOnClickListener {
            openProgrammerInstagram()
        }

        anjomanSite.setOnClickListener {
            openProgrammerWebsite()
        }

        btn_textInWatsApp.setOnClickListener {

            val number = "+989900668721"
            val url = "https://api.whatsapp.com/send?phone=$number"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)

        }


    }

    private fun openProgrammerTelegram() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/cessasku"))
        startActivity(intent)
    }

    private fun openProgrammerWebsite() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://cesku.ir/"))
        startActivity(intent)
    }

    private fun openProgrammerInstagram() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/cessa_sku"))
        startActivity(intent)
    }

    private fun initPicture() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            imgAnjomanPic.clipToOutline = true

        glide
            .load(url)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .error(R.drawable.img_error)
            .into(imgAnjomanPic)

        imgAnjomanPic.setOnClickListener {
            displayPhoto()
        }

    }

    private fun displayPhoto() {

        StfalconImageViewer.Builder(this, listOf(url)) { view, image ->
            Glide.with(this).load(image).into(view)
        }
            .withTransitionFrom(imgAnjomanPic)
            .show()

    }

}