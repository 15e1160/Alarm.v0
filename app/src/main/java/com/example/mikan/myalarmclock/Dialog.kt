package com.example.mikan.myalarmclock

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import android.widget.TimePicker
import org.jetbrains.anko.AnkoAsyncContext
import org.jetbrains.anko.toast
import java.util.*


class SimpleAlertDialog : DialogFragment() {


    interface OnClickListener {
        fun onPositiveClick()
        fun onNegativeClick()
    }

    private  lateinit var listener: OnClickListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is SimpleAlertDialog.OnClickListener) {
            listener = context
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = context
        if (context == null)
            return super.onCreateDialog(savedInstanceState)
        val builder = AlertDialog.Builder(context).apply {
            setMessage("時間になりました！　")
            setPositiveButton("起きる") { dialog, which ->
                listener.onPositiveClick()
                context.toast("起きるがクリックされました")
            }
            setNegativeButton("あと５分") { dialog, which ->
                listener.onNegativeClick()
                context.toast("あと５分がクリックされました")
            }
        }
        return builder.create()
    }
}

class DatePickerFragment : DialogFragment(),
        DatePickerDialog.OnDateSetListener{

    interface OnDateSelectedListner{
        fun onSelected(year: Int, month: Int, date:Int)
    }

    private  lateinit var listner: OnDateSelectedListner

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is OnDateSelectedListner){
            listner =context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val date = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(context, this ,year,month,date)
    }

    override fun onDateSet(view:DatePicker,year:
    Int, month: Int, date: Int){
        listner.onSelected(year, month, date)
    }
}

class TimePickerFragment : DialogFragment(),
        TimePickerDialog.OnTimeSetListener{

    interface OnTimeSelectedListner{
        fun onSelected(hourOfDay: Int, minute: Int)
    }
    private lateinit var listner: OnTimeSelectedListner

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is TimePickerFragment.OnTimeSelectedListner){
            listner=context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute =c.get(Calendar.MINUTE)
        return TimePickerDialog(context,this,hour,minute,true)
    }

    override fun onTimeSet(view: TimePicker,hourOfDay: Int,minute: Int){
        listner.onSelected(hourOfDay,minute)
    }
}