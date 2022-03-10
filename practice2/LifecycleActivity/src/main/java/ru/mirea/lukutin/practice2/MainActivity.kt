package ru.mirea.lukutin.practice2

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG,"OnCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG,"onStart()")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG,"onSaveInstanceState()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG,"OnRestart()")
    }

    override fun onResume(){
        super.onResume()
        Log.i(TAG,"onResume()")
    }

    override fun onPause(){
        super.onPause()
        Log.i(TAG,"OnPause()")
    }

    override fun onStop(){
        super.onStop()
        Log.i(TAG,"OnStop()")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i(TAG,"onDestroy()")
    }
}