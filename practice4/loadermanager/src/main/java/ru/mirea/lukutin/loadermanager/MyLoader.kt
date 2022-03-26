package ru.mirea.lukutin.loadermanager

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import androidx.loader.content.AsyncTaskLoader

class MyLoader(context: Context, args: Bundle?) : AsyncTaskLoader<String>(context) {
    private lateinit var firstName:String
    val ARG_WORD:String = "word"

    init {
        if(args != null) firstName = args.getString(ARG_WORD).toString()
    }


    override fun loadInBackground(): String? {
        SystemClock.sleep(5000)
        return firstName
    }
}