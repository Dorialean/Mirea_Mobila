package ru.mirea.lukutin.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment

class MyDateDialogFragment(context: Context,
                           listener: OnDateSetListener?,
                           year: Int,
                           month: Int,
                           dayOfMonth: Int
) : DatePickerDialog(context, listener, year, month, dayOfMonth) {
}