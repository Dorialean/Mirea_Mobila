package ru.mirea.lukutin.controltask.ui.stories

import androidx.recyclerview.widget.RecyclerView
import ru.mirea.lukutin.controltask.ui.stories.ItemDao
import android.os.Bundle
import ru.mirea.lukutin.controltask.ui.stories.StoriesFragment
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.mirea.lukutin.controltask.R
import ru.mirea.lukutin.controltask.ui.stories.ItemAdapter
import android.content.Intent
import android.view.View
import ru.mirea.lukutin.controltask.ui.stories.StoryView
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import ru.mirea.lukutin.controltask.MainActivity

class StoryView : AppCompatActivity() {
    private var nameEditStory: EditText? = null
    private var dateEditStory: EditText? = null
    private var wordEditStory: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_view)
        nameEditStory = findViewById(R.id.nameEditStory)
        dateEditStory = findViewById(R.id.dateEditStory)
        wordEditStory = findViewById(R.id.wordEditStory)
    }

    fun saveBtn(view: View?) {
        val db = App.getInstance().database
        val itemDao = db.itemDao()
        itemDao.insert(
            Item(
                nameEditStory!!.text.toString(),
                dateEditStory!!.text.toString(),
                wordEditStory!!.text.toString()
            )
        )
        nameEditStory!!.setText("")
        dateEditStory!!.setText("")
        wordEditStory!!.setText("")
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}