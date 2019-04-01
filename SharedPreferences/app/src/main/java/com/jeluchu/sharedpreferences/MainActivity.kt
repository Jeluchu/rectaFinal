package com.jeluchu.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class MainActivity : AppCompatActivity() {

    private var myPreferences: SharedPreferences? = null
    private var prefEdit: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myPreferences = getPreferences(Context.MODE_PRIVATE)
        prefEdit = myPreferences!!.edit()


        val people = Person("Fulaniato", "Erniato", 324)
        savePerson(people)
        getPerson()

    }


    private fun getPerson(): Person {

        val gson = Gson()
        val json: String = myPreferences!!.getString("Person", "Person")
        return gson.fromJson(json, Person::class.java)

    }

    private fun savePerson(people: Person) {

        val gson = Gson()
        val json: String = gson.toJson(people)

        prefEdit!!.putString("Person", json)
        prefEdit!!.commit()

    }

}
