package com.ahm.sku.ui.activity

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ahm.coronam.androidWrapper.NetWorkChecker
import com.ahm.sku.R
import com.ahm.sku.ext.BaseActivity
import com.ahm.sku.model.AppRepository
import com.ahm.sku.receiver.AlarmReceiver
import com.parse.ParseInstallation

class NewUserActivity : BaseActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)


//        // mehman checker =>
//        sharedPreferences = getSharedPreferences(
//            AppRepository.MEHMAN_USER,
//            Context.MODE_PRIVATE
//        )


        // the first installation of app =>
//        ParseInstallation.getCurrentInstallation().saveInBackground()

    }

    fun onClickNewUser(view: View) {
        when (view.id) {
            R.id.btn_signIn -> {
                signInIntent()
            }
            R.id.btn_signUp -> {
                signUpIntent()
            }
            R.id.mehmanUser -> {
                mehmanUserDialog()
            }
        }
    }

    private fun mehmanUserDialog() {

        val view = View.inflate(this, R.layout.dialog_mehman, null) as LinearLayout
        val alertDialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        alertDialog.setCustomView(view)
        alertDialog.showCancelButton(false)
        alertDialog.confirmText = "بزن بریم"
        alertDialog.setConfirmClickListener {


            if(NetWorkChecker(this).checkNetwork) {

                val edit = sharedPreferences.edit()
                edit.putBoolean(AppRepository.MEHMAN_USER , true)
                edit.apply()

                Toast.makeText(this, "خوش آمدید", Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            } else
                Toast.makeText(this, "ابتدا به اینترنت متصل شوید", Toast.LENGTH_SHORT).show()



        }
        alertDialog.setCancelable(true)
        alertDialog.show()


    }

    private fun signUpIntent() {
        val intent = Intent(this, SignUpActivity::class.java)
        overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )
        startActivity(intent)
    }

    private fun signInIntent() {
        val intent = Intent(this, SignInActivity::class.java)
        overridePendingTransition(
            R.anim.fade_in,
            R.anim.fade_out
        )
        startActivity(intent)
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

    // Option Controller =>


}
