package com.ahm.sku.androidWrapper

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Typeface
import android.os.Build
import android.util.Log
import androidx.core.graphics.createBitmap
import com.ahm.sku.R
import com.ahm.sku.di.viewModule
import com.ahm.sku.model.AppRepository
import com.parse.Parse
import org.koin.android.ext.android.startKoin
import java.lang.reflect.Field


class G : Application() {

    companion object {
        var tmpFather = AppRepository.PARENT_DATA_PENDING
        var size: Int = 250
        var bitmap: Bitmap = createBitmap(200, 200)
        var whereTo = 0
        var forceUpdate = false

//        var HomeDataReceived :ArrayList<HomeData> = arrayListOf()
//        var ChatDataReceived :ArrayList<ChatData> = arrayListOf()
//        var ToolsListReceived :ArrayList<ToolsList> = arrayListOf()
//        var UniPicsReceived :ArrayList<String> = arrayListOf()
//        var FatherReceived :String = "  "
//        var errorTypeNet =
//            arrayListOf("0" , "0" , "0" ,"0" ,"0")
        // 0 => successfull     &&     1 => Error
        // [0] => homeData , [1] => ChatData , [2] => ToolsList , [3] => UniPics , [4] => FatherData


    }

    override fun onCreate() {
        super.onCreate()



//        TypefaceUtil.overrideFont(applicationContext, "SERIF", "fonts/iransansregular.ttf")



        // start Koin =>
        startKoin(
            applicationContext,
            listOf(viewModule)
        )



        Parse.initialize(
            Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        )

    }

}


//
//object TypefaceUtil {
//    /**
//     * Using reflection to override default typeface
//     * NOTICE: DO NOT FORGET TO SET TYPEFACE FOR APP THEME AS DEFAULT TYPEFACE WHICH WILL BE OVERRIDDEN
//     *
//     * @param context                    to work with assets
//     * @param defaultFontNameToOverride  for example "monospace"
//     * @param customFontFileNameInAssets file name of the font from assets
//     */
//    fun overrideFont(
//        context: Context,
//        defaultFontNameToOverride: String,
//        customFontFileNameInAssets: String
//    ) {
//        val customFontTypeface =
//            Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val newMap: MutableMap<String, Typeface> =
//                HashMap()
//            newMap["serif"] = customFontTypeface
//            try {
//                val staticField: Field = Typeface::class.java
//                    .getDeclaredField("sSystemFontMap")
//                staticField.isAccessible = true
//                staticField.set(null, newMap)
//            } catch (e: NoSuchFieldException) {
//                e.printStackTrace()
//            } catch (e: IllegalAccessException) {
//                e.printStackTrace()
//            }
//        } else {
//            try {
//                val defaultFontTypefaceField: Field =
//                    Typeface::class.java.getDeclaredField(defaultFontNameToOverride)
//                defaultFontTypefaceField.isAccessible = true
//                defaultFontTypefaceField.set(null, customFontTypeface)
//            } catch (e: Exception) {
//                Log.e(
//                    TypefaceUtil::class.java.simpleName,
//                    "Can not set custom font $customFontFileNameInAssets instead of $defaultFontNameToOverride"
//                )
//            }
//        }
//    }
//}