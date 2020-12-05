package com.ahm.sku.ui.activity.imageGalleryActivity

import android.R.attr.*
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahm.sku.R
import com.ahm.sku.androidWrapper.G
import com.ahm.sku.ext.BaseActivity
import com.ahm.sku.model.AppRepository
import com.ahm.sku.ui.activity.homeDetailActivity.HomeDetailViewModel
import com.ahm.sku.ui.activity.homeDetailActivity.HomeDetailViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.activity_image_gallery.*
import org.koin.android.ext.android.inject


class ImageGalleryActivity : BaseActivity() , ImageGalleryAdapter.ClickyInterface {
    private val glide :RequestManager by inject()
    var data :ArrayList<String> = arrayListOf<String>()
    private lateinit var sharedPreferences: SharedPreferences

    private fun displayPhotos(position :Int , targetImage :ImageView) {

        Toast.makeText(this, "با کشیدن انگشت به طرفین عکس را عوض کنید", Toast.LENGTH_SHORT).show()

        StfalconImageViewer.Builder(this, data) { view, image ->
            glide
                .load(image)
                .error(R.drawable.img_error)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(view)
        }
            .withStartPosition(position)
            .withTransitionFrom(targetImage)
            .show()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_gallery)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

//        sharedPreferences = getSharedPreferences(
//            AppRepository.CODE_UNI_IMAGE_PARENT_URL,
//            Context.MODE_PRIVATE
//        )
//
//        sharedPreferences = getSharedPreferences(
//            AppRepository.CODE_UNI_IMAGE_PARENT_NUMBER,
//            Context.MODE_PRIVATE
//        )

        val urlBase = sharedPreferences.getString(AppRepository.CODE_UNI_IMAGE_PARENT_URL , AppRepository.PENDING_UNI_URL)!!
        val number = sharedPreferences.getString(AppRepository.CODE_UNI_IMAGE_PARENT_NUMBER , AppRepository.PENDING_UNI_NUMBER)!!.toInt()

        for(i in 0 until number)
            data.add( urlBase + (i + 1).toString() + ".jpeg" )

        onClickListeners()
        initSize()
        initImages()

    }

//    private fun foundSize() {
//        Rect rectangle = new Rect();
//        Window window = getWindow();
//        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
//        int statusBarHeight = rectangle.top;
//        int contentViewTop =
//        window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
//        int titleBarHeight= contentViewTop - statusBarHeight;
//    }

    private fun onClickListeners() {

        img_ImageGallery_big1.setOnClickListener {
            Toast.makeText(this, "با کشیدن انگشت به طرفین عکس را عوض کنید", Toast.LENGTH_SHORT).show()
            displayPhotos(0 , img_ImageGallery_big1)
        }

        img_ImageGallery_big2.setOnClickListener {
            Toast.makeText(this, "با کشیدن انگشت به طرفین عکس را عوض کنید", Toast.LENGTH_SHORT).show()
            displayPhotos(1 , img_ImageGallery_big2)


        }

        img_ImageGallery_small1.setOnClickListener {
            Toast.makeText(this, "با کشیدن انگشت به طرفین عکس را عوض کنید", Toast.LENGTH_SHORT).show()
            displayPhotos(2 , img_ImageGallery_small1)

        }

        img_ImageGallery_small2.setOnClickListener {
            Toast.makeText(this, "با کشیدن انگشت به طرفین عکس را عوض کنید", Toast.LENGTH_SHORT).show()
            displayPhotos(3 , img_ImageGallery_small2)

        }

        img_ImageGallery_small3.setOnClickListener {
            Toast.makeText(this, "با کشیدن انگشت به طرفین عکس را عوض کنید", Toast.LENGTH_SHORT).show()
            displayPhotos(4 , img_ImageGallery_small3)

        }

        img_ImageGallery_small4.setOnClickListener {
            Toast.makeText(this, "با کشیدن انگشت به طرفین عکس را عوض کنید", Toast.LENGTH_SHORT).show()
            displayPhotos(5 , img_ImageGallery_small4)

        }

    }

    private fun initSize() {

//      val displayMetrics: DisplayMetrics = this.resources.displayMetrics
//      val dpWidth = displayMetrics.widthPixels / displayMetrics.density
//

//      get phone Width =>
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        G.size = width

    }

    private fun initImages() {

        changeImagesSize()

        setDataFromNet()

    }

    private fun setDataFromNet() {

        glide
            .load(data[0])
            .skipMemoryCache(false)
            .error(R.drawable.img_error)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(img_ImageGallery_big1)

        glide
            .load(data[1])
            .skipMemoryCache(false)
            .error(R.drawable.img_error)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(img_ImageGallery_big2)

        glide
            .load(data[2])
            .skipMemoryCache(false)
            .error(R.drawable.img_error)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(img_ImageGallery_small1)

        glide
            .load(data[3])
            .skipMemoryCache(false)
            .error(R.drawable.img_error)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(img_ImageGallery_small2)

        glide
            .load(data[4])
            .skipMemoryCache(false)
            .error(R.drawable.img_error)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(img_ImageGallery_small3)

        glide
            .load(data[5])
            .skipMemoryCache(false)
            .error(R.drawable.img_error)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(img_ImageGallery_small4)


        // first recycler =>
        val data1 = getSubList(data , 6 , 11)!!
        val adapter1 = ImageGalleryAdapter(data1 , this , this)
        recycler_uniImages1.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        recycler_uniImages1.adapter = adapter1

        // second recycler =>
        val data2 = getSubList(data , 12 , data.count() - 1)!!
        val adapter2 = ImageGalleryAdapter(data2 , this , this)
        recycler_uniImages2.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        recycler_uniImages2.adapter = adapter2

    }

    private fun changeImagesSize() {


        val size2 = (G.size / 3) * 2
        val size3 = G.size / 3


        // big 1 =>
//        val params1 = RelativeLayout.LayoutParams(
//            RelativeLayout.LayoutParams.WRAP_CONTENT,
//            RelativeLayout.LayoutParams.WRAP_CONTENT
//        )
//        params1.setMargins(size3, 0, 0, 0)
//        img_ImageGallery_big1.layoutParams = params1
//
//        img_ImageGallery_big1.requestLayout()
//        img_ImageGallery_big1.layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT
//        img_ImageGallery_big1.layoutParams.width = size2


        // big 2 =>
//        val params2 = RelativeLayout.LayoutParams(
//            RelativeLayout.LayoutParams.WRAP_CONTENT,
//            RelativeLayout.LayoutParams.WRAP_CONTENT
//        )
//        params2.setMargins(0, 0, size3, 0)
//        img_ImageGallery_big2.layoutParams = params2
//
//        img_ImageGallery_big2.requestLayout()
//        img_ImageGallery_big2.layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT
//        img_ImageGallery_big2.layoutParams.width = size2


        img_ImageGallery_small1.requestLayout()
        img_ImageGallery_small1.layoutParams.height = size3
        img_ImageGallery_small1.layoutParams.width = size3

        img_ImageGallery_small2.requestLayout()
        img_ImageGallery_small2.layoutParams.height = size3
        img_ImageGallery_small2.layoutParams.width = size3

        img_ImageGallery_small3.requestLayout()
        img_ImageGallery_small3.layoutParams.height = size3
        img_ImageGallery_small3.layoutParams.width = size3

        img_ImageGallery_small4.requestLayout()
        img_ImageGallery_small4.layoutParams.height = size3
        img_ImageGallery_small4.layoutParams.width = size3


    }

    private fun <T> getSubList(list: List<T>, start: Int, end: Int): List<T>? {
        val subList: MutableList<T> = ArrayList()
        for (i in start..end) {
            subList.add(list[i])
        }
        return subList
    }

    override fun onImageClicked(position: String, targetImage: ImageView) {

        data.forEachIndexed { index, text ->

            if( text == position ) {
                displayPhotos(index , targetImage)
                return@forEachIndexed
            }

        }

    }


}