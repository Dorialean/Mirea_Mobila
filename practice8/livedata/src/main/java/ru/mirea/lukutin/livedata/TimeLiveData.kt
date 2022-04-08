package ru.mirea.lukutin.livedata

import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import java.util.*


internal object TimeLiveData {
    private val data = MutableLiveData<Long>()

    //sets latest time to LiveData
    val time: LiveData<Long>
        get() {
            data.value = Date().time
            return data
        }

    fun setTime() {
        data.value = Date().time
    }
}
