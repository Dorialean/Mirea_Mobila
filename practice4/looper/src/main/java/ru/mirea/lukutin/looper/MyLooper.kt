package ru.mirea.lukutin.looper

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log

class MyLooper : Thread() {

    var number:Int = 0
    lateinit var handler: Handler

    @SuppressLint("HandlerLeak")
    override fun run(){
        Log.d("MyLooper","Run")
        Looper.prepare()
        handler = object : Handler(){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                Log.d("MyLooper", number.toString() + ":" + msg.data.getString("KEY"))
                sleep(msg.data.getString("AGE")?.toLong() ?: 500)
                Log.d("My age",":" + msg.data.getString("AGE"))
                Log.d("Work", ":" + msg.data.getString("WORK"))
                number++
            }
        }
        Looper.loop()
    }
}