package com.jeluchu.fragtivity.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.jeluchu.fragtivity.R
import kotlinx.android.synthetic.main.fragment_two.*

class TwoFragment : Fragment() {

    companion object {
        fun newInstance(boolean: Boolean): TwoFragment {

            val fragment = TwoFragment()
            val bundle = Bundle()

            fragment.arguments = bundle
            bundle.putBoolean("bool", boolean)

            return fragment
        }
    }

    private var condicion: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)

        val view = inflater.inflate(R.layout.fragment_two, container, false)

        val tvBoolean = view.findViewById<TextView>(R.id.booleans)

        condicion = arguments!!.getBoolean("bool")

        tvBoolean.text = condicion.toString()


        return view
    }

}
