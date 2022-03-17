package ru.mirea.lukutin.practice3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startNewActivity(view: View){
        val dateInMillis:Long = System.currentTimeMillis()
        val format:String = "yyyy-MM-dd HH:mm:ss"
        val sdf:SimpleDateFormat = SimpleDateFormat(format)
        val dateString:String = sdf.format(Date(dateInMillis))
        val intentForActivity2 = Intent(this, MainActivity2::class.java)
        intentForActivity2.putExtra(MainActivity2.time, dateString)

        startActivity(intentForActivity2)
    }
}