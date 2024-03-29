package ru.mirea.lukutin.networkstate

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        val networkLiveData: LiveData<String> = NetworkLiveData.getInstance(this)
        networkLiveData.observe(
            this
        ) { value -> textView.text = value }
    }
}