package com.ahm.sku.receiver

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.ahm.sku.model.AppRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject


class DeviceBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context , intent: Intent) {

        if (intent.action == "android.intent.action.BOOT_COMPLETED") {

            startAlarmAgain(context)


        }

    }

    private fun startAlarmAgain(context: Context) {

        val intent = Intent(context, AlarmReceiver::class.java)

        val pending = PendingIntent.getBroadcast(
            context,
            AppRepository.REQ_ALARM,
            intent,
            0
        )

        val currentTime = System.currentTimeMillis() + AppRepository.TWELVE_HOURS_MILLISECONDS

        val alarm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarm.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            currentTime, // every Playing // for the first time
            AppRepository.FIVE_DAYS_MILLIES,
            pending
        )

    }

}