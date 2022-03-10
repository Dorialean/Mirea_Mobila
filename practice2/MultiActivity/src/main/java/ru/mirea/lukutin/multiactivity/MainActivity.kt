package ru.mirea.lukutin.multiactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickNewActivity(view:View){
        var intent:Intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("key","MIREA - ЛАКУТИН ДМИТРИЙ АЛЕКСЕЕВИЧ")
        startActivity(intent)
    }
}