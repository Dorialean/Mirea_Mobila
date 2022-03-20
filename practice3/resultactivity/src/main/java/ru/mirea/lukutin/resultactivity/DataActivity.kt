package ru.mirea.lukutin.resultactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class DataActivity : AppCompatActivity() {

    private var universityEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data)
        universityEditText = findViewById(R.id.editTextTextPersonName)
    }

    public fun sendResultOnMainActivityOnClick(view: View){
        val intent:Intent = Intent()
        intent.putExtra("name", universityEditText?.text.toString())
        setResult(RESULT_OK,intent)
        finish()
    }
}