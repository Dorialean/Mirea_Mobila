package ru.mirea.lukutin.controltask.ui.settings

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import ru.mirea.lukutin.controltask.ui.settings.SettingsFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import ru.mirea.lukutin.controltask.R

class SettingsFragment : Fragment(), View.OnClickListener {
    private var nameEdit: EditText? = null
    private var ageEdit: EditText? = null
    private var salaryEdit: EditText? = null
    private var nameView: TextView? = null
    private var ageView: TextView? = null
    private var salaryView: TextView? = null
    private var preferences: SharedPreferences? = null
    private var mParam1: String? = null
    private var mParam2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val btnUpdate = view.findViewById<Button>(R.id.btnUpdate)
        btnUpdate.setOnClickListener(this)
        nameEdit = view.findViewById(R.id.nameEdit)
        ageEdit = view.findViewById(R.id.ageEdit)
        salaryEdit = view.findViewById(R.id.salaryEdit)
        nameView = view.findViewById(R.id.textViewName)
        ageView = view.findViewById(R.id.textViewAge)
        salaryView = view.findViewById(R.id.textViewSalary)
        preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        setPersonDate()

        // Inflate the layout for this fragment
        return view
    }

    private fun btnUpdateClick(view: View) {
        Log.d("TAG", "button clicked")
        val editor = preferences!!.edit()
        editor.putString("name", nameEdit!!.text.toString())
        editor.putString("age", ageEdit!!.text.toString())
        editor.putString("salary", salaryEdit!!.text.toString())
        editor.apply()
        nameEdit!!.setText("")
        ageEdit!!.setText("")
        salaryEdit!!.setText("")
        setPersonDate()
    }

    private fun setPersonDate() {
        if (preferences!!.getString("name", "root") == "root") nameView!!.text =
            "root" else nameView!!.text = preferences!!.getString("name", "root")
        if (preferences!!.getString("age", "0") == "0") ageView!!.text = "0" else ageView!!.text =
            preferences!!.getString("age", "0")
        if (preferences!!.getString("salary", "0") == "0") salaryView!!.text =
            "0" else salaryView!!.text = preferences!!.getString("salary", "0")
    }

    override fun onClick(view: View) {
        btnUpdateClick(view)
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String?, param2: String?): SettingsFragment {
            val fragment = SettingsFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}