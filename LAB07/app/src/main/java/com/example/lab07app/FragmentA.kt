package com.example.lab07app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class FragmentA : Fragment(R.layout.fragment_a) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etInput = view.findViewById<EditText>(R.id.etInputText)
        val etStudent = view.findViewById<EditText>(R.id.etStudentNumber)

        view.findViewById<Button>(R.id.btnSend).setOnClickListener {
            val bundle = Bundle().apply {
                putString("userText", etInput.text.toString())
                putString("studentNumber", etStudent.text.toString())
            }
            parentFragmentManager.setFragmentResult("requestKey", bundle)
        }
    }
}
