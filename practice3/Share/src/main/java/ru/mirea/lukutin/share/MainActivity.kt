package ru.mirea.lukutin.share

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent: Intent = Intent(Intent.ACTION_PICK)
        intent.type = "*/*"
        intent.putExtra(Intent.EXTRA_TEXT,"Mirea")
        //startActivityForResult(intent,1)
        startActivity(Intent.createChooser(intent,"Выбор за вами!"))
    }
}