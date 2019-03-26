package inventariounisys.jeluchu.com.inventariounisys

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import inventariounisys.jeluchu.com.inventariounisys.objets.Device
import kotlinx.android.synthetic.main.activity_add.*


class AddActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    private var device: List<Device>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        database = FirebaseDatabase.getInstance()
        myRef = database.getReference(Constants.FIREBASE_ITEM)

        device = ArrayList()

        btn_add.setOnClickListener { addDevice() }
    }

    private fun addDevice() {

        val marca = etMarca.text.toString()
        val modelo = etModelo.text.toString()
        val numerSerie = etNumeroSerie.text.toString()
        val direcMac = etDirecionMac.text.toString()
        val categoria = sCategoria.selectedItem.toString()
        val prestado = false

        if (!TextUtils.isEmpty(marca)
                || !TextUtils.isEmpty(modelo)
                || !TextUtils.isEmpty(numerSerie)) {

            val id: String = myRef.push().key.toString()
            val device = Device(id, modelo, marca, numerSerie, categoria, direcMac, prestado)

            myRef.child(id).setValue(device)

            etMarca.setText("")
            etModelo.setText("")
            etNumeroSerie.setText("")
            etDirecionMac.setText("")

            Toast.makeText(this, "Dispositivo añadido", Toast.LENGTH_LONG).show()
            val back = Intent(this@AddActivity, MainActivity::class.java)
            startActivity(back)

        } else {
            Toast.makeText(this, "Por favor, rellena los campos", Toast.LENGTH_LONG).show()
        }
    }


}
