package com.example.alarmpractice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import android.widget.ToggleButton
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var alarmToogle: ToggleButton

    val NOTIFICATION_ID = 0
    val PRIMARY_CHANNEL_ID = "primary_notification_channel"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notificationManager =
            ContextCompat.getSystemService(
                this,
                NotificationManager::class.java
            ) as NotificationManager


        alarmToogle = findViewById(R.id.alarmToggle)

        alarmToogle.setOnCheckedChangeListener { compoundButton, isChecked ->
            var toastMessage = ""
            if(isChecked){
                deliverNotification(this)
                toastMessage = getString(R.string.alarm_on_toast)
            }else{
                toastMessage = getString(R.string.alarm_off_toast)
            }
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
        }

        //ToogleButton
    }

    fun createNotificationChannel() {
        val notificationManager =
            ContextCompat.getSystemService(
                this,
                NotificationManager::class.java
            ) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_ID.toString(),
                PRIMARY_CHANNEL_ID,
                // TODO: Step 2.4 change importance
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every 15 minutes to stand up and walk")

            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private fun deliverNotification(context: Context) {
        val contentIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context,NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder = NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stand_up)
            .setContentTitle("Stand Up Alert")
            .setContentText("You should stand up and walk around now!")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL);

        val notificationManager =
            ContextCompat.getSystemService(
                this,
                NotificationManager::class.java
            ) as NotificationManager

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}