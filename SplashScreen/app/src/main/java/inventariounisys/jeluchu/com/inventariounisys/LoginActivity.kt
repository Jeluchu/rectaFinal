package inventariounisys.jeluchu.com.inventariounisys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import inventariounisys.jeluchu.com.inventariounisys.objets.Users
import kotlinx.android.synthetic.main.activity_login.*


@Suppress("UNUSED_VARIABLE")
class LoginActivity : AppCompatActivity() {


    /* ---------   VARIABLES   --------- */
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef : DatabaseReference
    private lateinit var userSpinner: Spinner
    private var array : ArrayList<String> = ArrayList()
    private var userArray : ArrayList<Users> = arrayListOf()
    private var spinnerPosition: Int = 0

    /* ---------   ONCREATE   --------- */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        FirebaseApp.initializeApp(this)

        database = FirebaseDatabase.getInstance()
        myRef = database.getReference(Constants.FIREBASE_USER)

        userFirebase()

        etPassword.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                return@OnEditorActionListener true
            }
            false
        })
        initListener()
    }


    /* ---------   FUNCIONES   --------- */

    // CONDICIÓN DE INICIO DE SESIÓN
    private fun initListener() {
        btnLogin.setOnClickListener {

            val userIntent: String = userArray[spinnerPosition].nick

                val passUser: String = userArray[spinnerPosition].password
                val passIntro = etPassword.text.toString()

                if (passUser == passIntro) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("user", userIntent)

                    val pref = this@LoginActivity.getSharedPreferences("SharedPreferenceLogin", Context.MODE_PRIVATE)
                    val editor = pref.edit().putString("user", userIntent).commit()

                    startActivity(intent)
                } else {

                    val builder = AlertDialog.Builder(this@LoginActivity)

                    builder.setTitle("Error")
                    builder.setMessage("La contraseña que has introducido no coincide con el usuario escogido")

                    builder.setPositiveButton("Intentarlo de nuevo") { dialog, _ ->
                        dialog.dismiss()
                    }

                    val dialog: AlertDialog = builder.create()
                    dialog.show()
                }
            }
    }

    // SPINNER DATA ADAPTER
    private fun spinnerAdapter() {
        val arrayAdapter  = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, array)
        userSpinner = findViewById(R.id.user)
        userSpinner.adapter = arrayAdapter
        userSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinnerPosition = position
                Toast.makeText(this@LoginActivity, array[position], Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ENVIO DE DATOS A OTRA ACTIVITY
    private fun userFirebase(){
        val userListener = object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                array.add(p0.child("nick").value.toString())

                userArray.add(Users(
                        p0.key.toString(),
                        p0.child("nick").value.toString(),
                        p0.child("password").value.toString())
                )
                spinnerAdapter()
            }

            override fun onChildRemoved(p0: DataSnapshot) {
            }
        }
        myRef.addChildEventListener(userListener)

    }
}
