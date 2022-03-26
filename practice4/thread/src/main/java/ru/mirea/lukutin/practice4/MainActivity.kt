package ru.mirea.lukutin.practice4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var infoTextView:TextView = findViewById(R.id.resultView)
        val mainThread:Thread = Thread.currentThread()
        infoTextView.text = "Текущий поток:" + mainThread.name
        mainThread.name = "MireaThread"
        infoTextView.append("\n Новое имя потока:" + mainThread.name)
    }

    fun onClickCountClasses(view: View){
        val runnable:Runnable = Runnable {
            fun run(){
                var numberThread = counter++
                Log.i("ThreadProject","Запущен поток №" + numberThread)
                val endTime:Long = System.currentTimeMillis() + 20 * 1000
                while(System.currentTimeMillis() < endTime){
                    synchronized(this){
                        try {
                            Thread.sleep(endTime - System.currentTimeMillis())
                        } catch(e:Exception){ }
                    }
                }
                Log.i("ThreadProject","Выполнен поток №" + numberThread)
            }
        }
        val thread:Thread = Thread(runnable)
        thread.start()
    }
}



