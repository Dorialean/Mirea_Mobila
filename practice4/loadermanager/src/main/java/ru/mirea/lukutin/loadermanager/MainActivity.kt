package ru.mirea.lukutin.loadermanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import kotlin.random.Random

class MainActivity : AppCompatActivity() , LoaderManager.LoaderCallbacks<String> {

    val TAG:String = this.javaClass.simpleName
    var LoaderID:Int = 1234
    lateinit var inputTextView:TextView
    lateinit var editText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editRandomiseCharsText)
        inputTextView = findViewById(R.id.result)
        val bundle:Bundle = Bundle()
        bundle.putString("word","mirea")
        supportLoaderManager.initLoader(LoaderID,bundle, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> {
         Toast.makeText(this, "onCreateLoader" + id,Toast.LENGTH_SHORT).show()
        return MyLoader(this, args)
    }

    override fun onLoadFinished(loader: Loader<String>, data: String?) {
        if(loader.id == LoaderID){
            Log.d(TAG,"onLoadFinished" + data)
            Toast.makeText(this, "onLoadFinished" + data, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLoaderReset(loader: Loader<String>) {
        Log.d(TAG,"onLoaderReset")
    }

    fun onClickReassembleEditText(view:View){
        val bundle = Bundle()
        bundle.putString("OldWord", editText.text.toString())
        LoaderID++
        supportLoaderManager.initLoader(LoaderID, bundle, this)
        inputTextView.text = randomise(editText.text.toString())
    }

    private fun randomise(s:String) : String{
        var res = s.toCharArray()
        for (i in s.length-1 downTo 1){
            var j = Random.nextInt(i+1)
            var temp = res[i]
            res[j] = res[i]
            res[i] = temp
        }
        return res.concatToString()
    }
}