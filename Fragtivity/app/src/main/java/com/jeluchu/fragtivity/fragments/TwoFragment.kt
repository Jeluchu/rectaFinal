package com.jeluchu.fragtivity.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jeluchu.fragtivity.R
import kotlinx.android.synthetic.main.fragment_two.*

class TwoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_two, container, false)

        val bundle: Bundle? = arguments
        lateinit var others: String

        if (bundle != null) {
            others = bundle.getSerializable("bool") as String
            booleans.text = others
        } else {
            Toast.makeText(activity, "Error...", Toast.LENGTH_SHORT).show()
        }



        return view
    }

}
