package com.jeluchu.spinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {


    val users = arrayListOf("Selecciona el usuario", "José Luis", "Fernando", "José Carlos", "Txema")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner!!.onItemSelectedListener = this

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, users)

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner!!.adapter = arrayAdapter

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position != 0)
            Toast.makeText(applicationContext, "Usuario: " + users[position], Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }
}
