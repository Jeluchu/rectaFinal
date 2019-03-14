package com.jeluchu.fragtivity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.jeluchu.fragtivity.fragments.OneFragment
import com.jeluchu.fragtivity.fragments.TwoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                val fragment = OneFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.message, fragment, fragment.javaClass.simpleName)
                    .commit()

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val fragment = TwoFragment()
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.message, fragment, fragment.javaClass.simpleName)
                    .commit()

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
