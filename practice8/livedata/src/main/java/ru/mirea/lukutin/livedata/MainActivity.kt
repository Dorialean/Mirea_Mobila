package ru.mirea.lukutin.livedata

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ru.mirea.lukutin.livedata.TimeLiveData.setTime

class MainActivity : AppCompatActivity(), Observer<Long> {
    private lateinit var networkNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TimeLiveData.time.observe(this,this)
        val handler = Handler()
        handler.postDelayed({ setTime() }, 5000)
    }

    override fun onChanged(t: Long?) {
        Log.d(MainActivity::class.java.simpleName, t.toString())
        networkNameTextView.text = t.toString()
    }
}