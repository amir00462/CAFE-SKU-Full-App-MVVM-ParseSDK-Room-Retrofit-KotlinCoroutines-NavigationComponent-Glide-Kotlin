package com.ahm.sku.ui.activity.homeDetailActivity

import androidx.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.ahm.sku.R
import com.ahm.sku.ext.BaseActivity
import com.ahm.sku.model.AppRepository
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_home_detail.*
import org.koin.android.ext.android.inject
import java.util.*

class HomeDetailActivity : BaseActivity() {

    private val glide: RequestManager by inject()
    private val homeDetailViewModelFactory: HomeDetailViewModelFactory by inject()
    private lateinit var homeDetailViewModel: HomeDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_detail)
        setSupportActionBar(toolbarHomeDetail)

        // support RTL =>
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL

        Objects.requireNonNull(supportActionBar)!!.setDisplayHomeAsUpEnabled(true)
        Objects.requireNonNull(supportActionBar)!!.setDisplayShowHomeEnabled(true)

        val data = intent.getStringExtra(AppRepository.CARD_ID)

        if (data != null) {
            initData(data.toInt())
        }


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


    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        homeDetailViewModel = ViewModelProviders.of(this, homeDetailViewModelFactory)
            .get(HomeDetailViewModel::class.java)

        return super.onCreateView(name, context, attrs)
    }

    private fun initData(id: Int) {

        val card = homeDetailViewModel.getHomeDataByCardId(id)
        card.observe(this, Observer {


            collapsingMain.title = it.homeText

            txt_HomeDetail.text = it.homeDetailText

            glide
                .load(it.homeDetailImageUrl)
                .fitCenter()
                .override(960, 640)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .error(R.drawable.img_error)
                .into(img_BigPic_HomeDetail)

            if (it.hasVideo) {
                fabVideo.visibility = View.VISIBLE

                fabVideo.setOnClickListener { _ ->
                    openWebsite(it.videoUrl)
                }

            } else {
                fabVideo.visibility = View.INVISIBLE
            }


            if (it.hasNavigate) {
                fabNavigate.visibility = View.VISIBLE

                fabNavigate.setOnClickListener { _ ->
                    openWebsite(it.navigateUri)
                }

            } else {
                fabNavigate.visibility = View.INVISIBLE
            }

            if (it.hasLinkAddress) {
                fabUrl.visibility = View.VISIBLE

                fabUrl.setOnClickListener { _ ->
                    openWebsite(it.linkUrl)
                }

            } else {
                fabUrl.visibility = View.INVISIBLE
            }


        })


    }

    private fun openWebsite(uri:String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }

}