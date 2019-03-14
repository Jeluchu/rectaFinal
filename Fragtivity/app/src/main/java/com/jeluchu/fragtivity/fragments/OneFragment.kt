package com.jeluchu.fragtivity.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jeluchu.fragtivity.R
import kotlinx.android.synthetic.main.fragment_one.*


class OneFragment : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_one, container, false)

        btnBool.setOnClickListener {

            val fragmentTwo = TwoFragment()

            val bundle = Bundle()
            bundle.putBoolean("bool", true)
            fragmentTwo.arguments = bundle
        }

        return view
    }





}
