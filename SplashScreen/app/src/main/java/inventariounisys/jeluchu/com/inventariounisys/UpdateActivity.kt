package inventariounisys.jeluchu.com.inventariounisys

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.database.*
import inventariounisys.jeluchu.com.inventariounisys.objets.Device
import kotlinx.android.synthetic.main.activity_update.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class UpdateActivity : AppCompatActivity() {

    /* ---------   VARIABLES   --------- */
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef : DatabaseReference
    private var value : String = ""

    //var deviceData = intent.getStringExtra("deviceClicked")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        // INSTANCIAMOS LA BASE DE DATOS Y COGEMOS LA REFERENCIA
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference(Constants.FIREBASE_ITEM)

        val userName = intent.extras

        if (userName != null) {
            value = userName.getString("deviceClicked")
            readData(value)
        }

        btnEdit.setOnClickListener {
            updateDevice(value)
        }

        btnDelete.setOnClickListener {
            deleteDevice(value)
        }
    }

    private fun readData(position : String) {

        myRef.child(position).addValueEventListener(object:ValueEventListener {
            override fun onDataChange(dataSnapshot:DataSnapshot) {
                etMarcaUp.setText(dataSnapshot.child("marca").value.toString())
                etModeloUp.setText(dataSnapshot.child("modelo").value.toString())
                etNumeroSerieUp.setText(dataSnapshot.child("numSerie").value.toString())
                etDirecionMacUp.setText(dataSnapshot.child("mac").value.toString())
                //sCategoriaUp.(dataSnapshot.child("categoria").value.toString())
            }
            override fun onCancelled(databaseError:DatabaseError) {
                System.out.println("Fallo la lectura: " + databaseError.code)
            }
        })

    }

    private fun updateDevice(id: String) {

        val marca = etMarcaUp.text.toString()
        val modelo = etModeloUp.text.toString()
        val numerSerie : String = etNumeroSerieUp.text.toString()
        val direcMac = etDirecionMacUp.text.toString()
        val categoria = sCategoriaUp.selectedItem.toString()

        val prestado = false

        if (!TextUtils.isEmpty(marca)
                || !TextUtils.isEmpty(modelo)
                || !TextUtils.isEmpty(numerSerie)) {

            val device = Device(id, modelo, marca, numerSerie, categoria, direcMac, prestado)

            myRef.child(id).setValue(device)

            etMarcaUp.setText("")
            etModeloUp.setText("")
            etNumeroSerieUp.setText("")
            etDirecionMacUp.setText("")

            Toast.makeText(this, "Dispositivo actualizado", Toast.LENGTH_LONG).show()
            val back = Intent(this@UpdateActivity, MainActivity::class.java)
            startActivity(back)

        } else {
            Toast.makeText(this, "Por favor, rellena los campos", Toast.LENGTH_LONG).show()
        }
    }

    private fun deleteDevice(position : String){

        myRef.child(position).addValueEventListener(object:ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                for (appleSnapshot in p0.children) {
                    appleSnapshot.ref.removeValue()
                }
                val back = Intent(this@UpdateActivity, MainActivity::class.java)
                startActivity(back)
            }

            override fun onCancelled(databaseError:DatabaseError) {
                System.out.println("Fallo la lectura: " + databaseError.code)
            }

        })
    }

}
