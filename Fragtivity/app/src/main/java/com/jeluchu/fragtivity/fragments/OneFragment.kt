package com.jeluchu.fragtivity.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.jeluchu.fragtivity.R
import kotlinx.android.synthetic.main.fragment_one.*


class OneFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_one, container, false)

        val btnBole = view.findViewById<Button>(R.id.btnBool)

        btnBole.setOnClickListener {

            val fragmentTwo = TwoFragment.newInstance(true)
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.message, fragmentTwo, fragmentTwo.javaClass.simpleName)
                .commit()
        }

        return view
    }





}
