package ru.mirea.lukutin.musicproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickStartMusic(view: View){
        startService(Intent(this,MusicService::class.java))
    }

    fun onClickStopMusic(view:View){
        stopService(Intent(this,MusicService::class.java))
    }
}