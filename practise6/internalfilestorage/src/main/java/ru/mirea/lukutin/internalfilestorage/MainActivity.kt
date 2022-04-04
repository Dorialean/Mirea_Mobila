package ru.mirea.lukutin.internalfilestorage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private lateinit var tv:TextView
    private val LOG_TAG = MainActivity::class.java.simpleName
    private val fileName = "mirea.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val string = "Hello mirea!"
        val outputStream: FileOutputStream
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
            outputStream.write(string.toByteArray())
            outputStream.close()
        } catch (e: Exception) { e.printStackTrace() }
        tv = findViewById(R.id.textView)
        Thread {
            try { Thread.sleep(5000) }
            catch (e: InterruptedException) { e.printStackTrace() }
            tv.post { tv.text = getTextFromFile() }
        }.start()
    }

    fun getTextFromFile(): String? {
        var fin: FileInputStream? = null
        try {
            fin = openFileInput(fileName)
            val bytes = ByteArray(fin.available())
            fin.read(bytes)
            val text = String(bytes)
            Log.d(LOG_TAG, text)
            return text
        } catch (ex: IOException) {
            Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
        } finally {
            try {
                fin?.close()
            } catch (ex: IOException) {
                Toast.makeText(this, ex.message, Toast.LENGTH_SHORT).show()
            }
        }
        return null
    }
}