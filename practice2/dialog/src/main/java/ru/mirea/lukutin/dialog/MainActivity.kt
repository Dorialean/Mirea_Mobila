package ru.mirea.lukutin.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myTimeDialogFragment: MyTimeDialogFragment
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
    }

    fun onClickShowDialog(view: View) {
        var dialogFragment:MyDialogFragment = MyDialogFragment()
        dialogFragment.show(supportFragmentManager,"mirea")
    }

    fun onOkClicked(){
        Toast.makeText(applicationContext,"Вы выбрали кнопку \"Иду дальше\" !",Toast.LENGTH_LONG).show()
    }

    fun onCancelClicked(){
        Toast.makeText(applicationContext,"Вы выбрали кнопку \"Нет\" !",Toast.LENGTH_LONG).show()
    }

    fun onNeutralClicked(){
        Toast.makeText(applicationContext,"Вы выбрали кнопку \"На паузе\" !",Toast.LENGTH_LONG).show()
    }

    fun onTimeClicked(view: View){
        val mTimePickerDialog: MyTimeDialogFragment
        val curTime = Calendar.getInstance()
        val hour = curTime.get(Calendar.HOUR_OF_DAY)
        val minute = curTime.get(Calendar.MINUTE)

        mTimePickerDialog = MyTimeDialogFragment(this, 0,
            { view, hourOfDay, minute -> },hour,minute,true)
        mTimePickerDialog.show()
    }

    fun onDateClicked(view:View){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val myDatePickerDialog: MyDateDialogFragment = MyDateDialogFragment(this, DatePickerDialog.OnDateSetListener{view,year,monthOfYear,dayOfMonth ->},year,month,day)
        myDatePickerDialog.show()
    }

    fun onProgressClicked(view: View){
        val myProgressDialogFragment: MyProgressDialogFragment = MyProgressDialogFragment(this)
        myProgressDialogFragment.setMessage("Mirea downloading....")
        myProgressDialogFragment.setCancelable(true)
        myProgressDialogFragment.show()
    }
}