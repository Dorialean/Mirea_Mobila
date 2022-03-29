package ru.mirea.lukutin.mirea_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.mirea.lukutin.loadermanager.PlayerService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickPlayMusic(view: View){
        val intent = Intent(this, PlayerService::class.java)
        startService(intent)
    }

    fun onClickStopMusic(view:View){
        val intent = Intent(this,PlayerService::class.java)
        stopService(intent)
    }
}