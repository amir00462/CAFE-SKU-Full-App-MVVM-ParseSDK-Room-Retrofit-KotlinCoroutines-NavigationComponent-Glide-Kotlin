package com.ahm.sku.ui.activity

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ahm.sku.R
import com.ahm.sku.androidWrapper.G
import com.ahm.sku.ext.BaseActivity
import com.ahm.sku.model.AppRepository
import com.ahm.sku.receiver.AlarmReceiver
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.parse.ParseUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_main_activity.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var sharedPreferences: SharedPreferences
    private var appBarConfiguration: AppBarConfiguration? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbarMain)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

//        // mehman checker =>
//        sharedPreferences = getSharedPreferences(
//            AppRepository.MEHMAN_USER,
//            Context.MODE_PRIVATE
//        )
//
//        // first use =>
//        sharedPreferences = getSharedPreferences(
//            AppRepository.FIRST_USE,
//            Context.MODE_PRIVATE
//        )

        // support RTL =>
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL

        // first Use =>
//        ParseInstallation.getCurrentInstallation().saveInBackground()

        // creating the bottom navigation =>
        val navView: BottomNavigationView = findViewById(R.id.nav_view_bottomnav)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_about_sku,
                R.id.navigation_news,
                R.id.navigation_profile,
                R.id.navigation_tools
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration!!)
        navView.setupWithNavController(navController)

        // navigation =>
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbarMain, 0, 0
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view_navigation.setNavigationItemSelectedListener(this)


//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//
//                 }
//            }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when (requestCode) {

            AppRepository.STORAGE_PERMISSION_CODE -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "اجازه داده نشد !", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp(appBarConfiguration!!) || super.onSupportNavigateUp()
//    }

    override fun onBackPressed() {

        if (G.whereTo == 0)
            finishAffinity()
        else
            super.onBackPressed()

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_anjomanPage -> {
                val intent = Intent(this, AboutAnjomanActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_programmerPage -> {
                val intent = Intent(this, AboutProgrammerActivity::class.java)
                startActivity(intent)
            }
            R.id.menu_hemayatAzMa -> {
                showDialogStar()
            }
            R.id.menu_EshterakGozari -> {
                showDialogShare()
            }
            R.id.menu_signOut -> {
                signOut()
            }
            R.id.menu_helpUs0 -> {
                openPoshtibany()
            }
            R.id.menu_helpUs1 -> {
                openPoshtibany()
            }
            R.id.menu_helpUs3 -> {
                openPoshtibany()
            }
            R.id.menu_Sess -> {
                openWebsite("https://sess.sku.ac.ir/")
            }
            R.id.menu_UniversitySite -> {
                openWebsite("https://www.sku.ac.ir")
            }
            R.id.menu_SamaneEmtehanat -> {
                openWebsite("http://exam.sku.ac.ir/Default.aspx")
            }
            R.id.menu_NahadShahrekord -> {
                openWebsite("https://ec.nahad.ir/index.php")
            }
            R.id.menu_SandoghRefahDaneshgooyan -> {
                openWebsite("https://bp.swf.ir/")
            }
            R.id.menu_SajjadSite -> {
                openWebsite("https://portal.saorg.ir/")
            }
            R.id.menu_SabtDore -> {
                openWebsite("https://cesku.ir/home/course/")
            }
            R.id.menu_App_Page -> {
                openWebsite("https://cesku.ir/home/course/cafesku/")
            }


        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true

    }

    private fun showDialogShare() {
        val customView = View.inflate(this, R.layout.dialog_eshterak_gozari, null) as LinearLayout
        val alertDialog = SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
        alertDialog.setCustomView(customView)
        alertDialog.confirmText = "اشتراک گذاری"
        alertDialog.showCancelButton(false)
        alertDialog.setCancelable(true)
        alertDialog.setConfirmClickListener {

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "کافه sku")
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "اگه دانشجوی دانشگاه شهرکردی و هنوز اپ جدید دانشگاه رو نداری نصف عمرت به فنا رفته :/\nاگه ورودی جدیدی که دیگه ای وای :( \n یه سر بهمون بزن!\n https://cafebazaar.ir/app/com.ahm.sku"
            )
            startActivity(Intent.createChooser(intent, "choose one"))
        }
        alertDialog.show()
    }

    private fun showDialogStar() {
        val customView = View.inflate(this, R.layout.dialog_hemayat_az_ma, null) as LinearLayout
        val alertDialog = SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
        alertDialog.setCustomImage(R.drawable.img_star)
        alertDialog.setCustomView(customView)
        alertDialog.confirmText = "حمایت از ما"
        alertDialog.showCancelButton(false)
        alertDialog.setCancelable(true)
        alertDialog.setConfirmClickListener {
            openWebsite("https://cafebazaar.ir/app/com.ahm.sku")
        }
        alertDialog.show()
    }


    private fun signOut() {

        Toast.makeText(this, "با آرزوی موفقیت برای شما به امید دیدار", Toast.LENGTH_SHORT).show()

        val edit = sharedPreferences.edit()
        edit.putBoolean(AppRepository.MEHMAN_USER, false)
        edit.putBoolean(AppRepository.FIRST_USE, true)
        edit.apply()

        ParseUser.logOutInBackground()

        val intent = Intent(this, NewUserActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }

    private fun openPoshtibany() {

        val alertDialog = SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
        alertDialog.confirmText = "پیام به پشتیبانی"
        alertDialog.contentText = "ما را آگاه سازید!"
        alertDialog.setConfirmClickListener {

            val number = "+989900668721"
            val url = "https://api.whatsapp.com/send?phone=$number"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)

            alertDialog.dismiss()

        }
        alertDialog.showCancelButton(false)
        alertDialog.setCancelable(true)
        alertDialog.show()


    }

    private fun openWebsite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }



}