package com.ahm.sku.receiver

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.widget.Toast
import com.ahm.sku.R
import com.ahm.sku.model.AppRepository
import com.ahm.sku.ui.activity.SplashActivity
import kotlin.random.Random


class AlarmReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {

        setNotification(context)

    }

    private fun setNotification(context: Context) {

        val intent = Intent(context, SplashActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            context,
            AppRepository.REQ_PENDING_INTENT,
            intent,
            0
        )

        val data = arrayOf(
            "بی معرفت یه سر به ما بزن!" ,
            "یوهو عکسای جدید رسید!" ,
            "وااای اخبار جدیدو!" ,
            "می دونی چی شده؟!" ,
            "عکسای جدیدو دیدی؟!" ,
            "یه سر به رفیقت بزن!"
        )
        val random = (0..5).random()
        val image = BitmapFactory.decodeResource( context.resources  , R.drawable.imt)

        val notification = Notification.Builder(context)
            .setContentTitle("Cafe SKU")
            .setContentText(data[random])
            .setLargeIcon(image)
            .setContentIntent(pendingIntent)
            .build()
        notification.flags = Notification.FLAG_AUTO_CANCEL

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(
            AppRepository.REQ_NOTIFICATION,
            notification
        )

    }

}