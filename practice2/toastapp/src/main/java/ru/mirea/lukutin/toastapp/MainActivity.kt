package ru.mirea.lukutin.toastapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toast: Toast = Toast.makeText(applicationContext,"Здравствуй, MIREA! ЛАКУТИН ДМИТРИЙ АЛЕКСЕЕВИЧ",Toast.LENGTH_LONG)
        toast.setGravity(Gravity.BOTTOM,50,500)
        val toastContainer:LinearLayout = toast.view as LinearLayout
        toastContainer.solidColor
        val catImageView : ImageView = ImageView(applicationContext)
        catImageView.setImageResource(R.drawable.ic_launcher_background)
        toastContainer.addView(catImageView,0)
        toast.show()
    }
}