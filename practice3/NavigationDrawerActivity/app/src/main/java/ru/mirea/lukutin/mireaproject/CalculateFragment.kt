package ru.mirea.lukutin.mireaproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var resultField: TextView
private lateinit var operationField: TextView
private lateinit var numberField: EditText
private var operand: Double? = null;
private var lastOperation:String = "="

/**
 * A simple [Fragment] subclass.
 * Use the [CalculateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalculateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_calculate, container, false)
        resultField = view.findViewById(R.id.resultField)
        operationField = view.findViewById(R.id.operationField)
        numberField = view.findViewById(R.id.numberField)
        return view;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalculateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalculateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun onNumberClick(view:View){
        val button: Button = view as Button
        numberField.append(button.text)
        if(lastOperation == "=" && operand != null) operand = null
    }

    fun onOperationClick(view:View){
        val button: Button = view as Button
        var op:String = button.text.toString()
        var number:String = numberField.text.toString()
        if(number.isNotEmpty()){
            number = number.replace(',','.')
            performOperation(number as Double,op)
        }
        lastOperation = op;
        operationField.text = lastOperation;
    }

    private fun performOperation(number:Double, operation:String){
        if(operand == null) operand = number
        else{
            if(lastOperation == "=") lastOperation = operation
            when(lastOperation){
                "=" -> operand = number
                "/" -> {
                    operand = if(number.equals(0)) 0.0 else operand!! / number
                }
                "*" -> operand = operand!! * number
                "+" -> operand = operand!! + number
                "-" -> operand = operand!! - number
            }
        }
        resultField.text = operand.toString().replace('.',',')
        numberField.setText("")
    }
}