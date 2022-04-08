package ru.mirea.lukutin.viewmodel

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar = findViewById(R.id.progressBar)
        val viewModel = ViewModelProvider(this)[ProgressViewModel::class.java]
        viewModel.progressState.observe(
            this
        ) { isVisibleProgressBar ->
            if (isVisibleProgressBar) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
        viewModel.showProgress()
    }
}