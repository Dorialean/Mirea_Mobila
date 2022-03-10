package ru.mirea.lukutin.dialog

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.DialogFragment

class MyTimeDialogFragment(
    context: Context?,
    themeResId: Int,
    listener: OnTimeSetListener?,
    hourOfDay: Int,
    minute: Int,
    is24HourView: Boolean
) : TimePickerDialog(context, themeResId, listener, hourOfDay, minute, is24HourView) {
}