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
import android.widget.Button
import ru.mirea.lukutin.controltask.ui.stories.StoryView
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import androidx.fragment.app.Fragment
import java.util.ArrayList

class StoriesFragment : Fragment(), View.OnClickListener {
    private var recyclerView: RecyclerView? = null
    private var newStory: Button? = null
    private var items: List<Item> = ArrayList()
    private var itemDao: ItemDao? = null
    private var db: AppDatabase? = null
    private var mParam1: String? = null
    private var mParam2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stories, container, false)
        newStory = view.findViewById(R.id.newStorybtn)
        newStory.setOnClickListener(this)
        db = App.getInstance().database
        itemDao = db.itemDao()
        setInitialData()
        recyclerView = view.findViewById(R.id.recyclerView)
        val adapter = ItemAdapter(context, items)
        recyclerView.setAdapter(adapter)

        // Inflate the layout for this fragment
        return view
    }

    fun setInitialData() {
        items = itemDao!!.all
    }

    override fun onClick(view: View) {
        val intent = Intent(view.context, StoryView::class.java)
        startActivity(intent)
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String?, param2: String?): StoriesFragment {
            val fragment = StoriesFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}