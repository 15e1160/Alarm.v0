package com.example.mikan.myalarmclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.toast

class AlarmBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val next_intent = Intent(context,MainActivity::class.java)
                .putExtra("onReceive",true)
                .putExtra("alarm_name", intent?.getStringExtra("alarm_name"))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(next_intent)

    }
}