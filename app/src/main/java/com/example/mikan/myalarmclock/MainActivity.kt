package com.example.mikan.myalarmclock

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager.LayoutParams.*
import android.widget.AdapterView
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.text.DateFormat
import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity()
,SimpleAlertDialog.OnClickListener
,DatePickerFragment.OnDateSelectedListner
,TimePickerFragment.OnTimeSelectedListner{

    private lateinit var soundPool: SoundPool
    private  var soundResId = 0

    override fun onSelected(year: Int, month: Int, date: Int) {
        val c = Calendar.getInstance()
        c.set(year,month,date)
        dateText.text =android.text.format.DateFormat.format("yyyy/MM/dd",c)

    }

    override fun onSelected(hourOfDay: Int, minute: Int) {
        timeText.text= "%1$02d:%2$02d".format(hourOfDay,minute)
    }


    override fun onPositiveClick(){
        finish()
    }

    override fun onNegativeClick() {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.MINUTE, 5)
        setAlarmManager(calendar)
        finish()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent?.getBooleanExtra("onReceive",false)== true) {

            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ->
                    window.addFlags(FLAG_TURN_SCREEN_ON or
                            FLAG_SHOW_WHEN_LOCKED)

                else ->
                    window.addFlags(FLAG_TURN_SCREEN_ON or
                    FLAG_SHOW_WHEN_LOCKED or FLAG_DISMISS_KEYGUARD)
            }

            soundPool = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                SoundPool(2, AudioManager.STREAM_ALARM, 0)
            } else {
                val audioAttributes = AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
                SoundPool.Builder()
                        .setMaxStreams(1)
                        .setAudioAttributes(audioAttributes)
                        .build()
            }
            soundResId = soundPool.load(this, R.raw.a1, 1)
            soundPool.setOnLoadCompleteListener{
                soundPool, sampleId, status ->
                soundPool.play(soundResId, 1.0f, 1.0f, 0, 1, 1.0f)
            }

            val dialog = SimpleAlertDialog()
            dialog.setSound(soundPool, soundResId)
            dialog.show(supportFragmentManager, "alert_dialog")


        }
        setContentView(R.layout.activity_main)




        setAlarm.setOnClickListener{
            val date = "${dateText.text} ${timeText.text}".toDate()
            when {
                date != null -> {
                    val calendar = Calendar.getInstance()
                    calendar.time = date
                    setAlarmManager(calendar)
                    toast("アラームをセットしました")

                }
                else -> {
                    toast("日付の選択が正しくありません")
                }
            }

        }
        cancelAlarm.setOnClickListener{cancelAlarmManager()
        }
        dateText.setOnClickListener {
            val dialog = DatePickerFragment()
            dialog.show(supportFragmentManager, "date_dialog")

        }
        timeText.setOnClickListener {
            val dialog =TimePickerFragment()
            dialog.show(supportFragmentManager, "time_dialog")
        }

    }

    private fun cancelAlarmManager() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    @TargetApi(LOLLIPOP)
    private fun setAlarmManager(calender: Calendar) {
        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,AlarmBroadcastReceiver::class.java)
        val pending = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                0
        )
        am.cancel(pending)

        when {
            Build.VERSION.SDK_INT  >= LOLLIPOP  -> {
             val info = AlarmManager.AlarmClockInfo(
                     calender.timeInMillis, null)
            am.setAlarmClock(info, pending)
        }
         Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT  ->{
            am.setExact(AlarmManager.RTC_WAKEUP,
                    calender.timeInMillis, pending)
        }
        else  ->  {
            am.set(AlarmManager.RTC_WAKEUP,
                    calender.timeInMillis,pending)
        }
    }


    }
   fun String.toDate(patten:String="yyyy/MM/dd HH:mm"):Date?{
       val sdFormat =try {
           SimpleDateFormat(patten)
       }catch (e:IllegalArgumentException){
           null
       }
       val date =sdFormat?.let {
           try {
               it.parse(this)
           }catch (e:ParseException){
               null
           }
       }
       return date
   }

}

