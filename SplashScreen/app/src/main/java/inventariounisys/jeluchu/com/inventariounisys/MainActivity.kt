package inventariounisys.jeluchu.com.inventariounisys

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import inventariounisys.jeluchu.com.inventariounisys.adapter.DeviceListAdapter
import inventariounisys.jeluchu.com.inventariounisys.objets.Device
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import com.google.firebase.database.DataSnapshot

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class MainActivity : AppCompatActivity() {

    private var timer: Timer? = null
    private var i = 0
    private var rnds = (0..100).random()
    var rndsTime = (3000..6000).random()

    /* ---------   VARIABLES   --------- */
    val deviceList: ArrayList<Device> = ArrayList()
    private lateinit var database: FirebaseDatabase
    private lateinit var myRef : DatabaseReference

    private lateinit var toolbar: Toolbar

    /* ---------   ONCREATE   --------- */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //loadSplash()
        //finishSplash()

        //ESTABLECEMOS LA TOOLBAR
        toolbar = findViewById(R.id.mainToolbar)
        toolbar.inflateMenu(R.menu.toolbar_menu)

        // TOOLBAR OPTIONS
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.user -> {
                    val login = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(login)
                }
            }
            false
        }

        // INICIALIZAMOS FIREBASE
        FirebaseApp.initializeApp(this)

        // INSTANCIAMOS LA BASE DE DATOS Y COGEMOS LA REFERENCIA
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference(Constants.FIREBASE_ITEM)

        //Progresbar visible

        deviceListenerFirebase(object : OnLoad{
            override fun onLoad(string: String) {
                mainContainer.visibility = View.VISIBLE
                splash.visibility = View.GONE
            }

            override fun onCancel(string: String) {
                mainContainer.visibility = View.VISIBLE
                splash.visibility = View.GONE
                Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
            }

        })
        userChanged()
        getUserSharedPreferences()
        fabAddDevicesAdminRol()

    }

    /* ---------   FUNCIONES   --------- */

    private fun finishSplash() {
        Handler().postDelayed({
            mainContainer.visibility = View.VISIBLE
            splash.visibility = View.GONE
        }, rndsTime.toLong())
    }

    private fun loadSplash() {
        progBar.progress = 0

        val period: Long = rnds.toLong()
        timer = Timer()
        timer!!.schedule(object:TimerTask() {

                @SuppressLint("SetTextI18n")
                override fun run() {

                    if (i < 1) {
                        progBar.progress = i
                        i++
                    } else {
                        timer!!.cancel()
                    }

                }
        }, 0, period)
    }

    // OCULTAR FLOAT BUTTOM SEGÚN EL ROL DEL USUARIO
    private fun fabAddDevicesAdminRol() {
        val fab: View = findViewById(R.id.fab)

        if (tvUser.text == "admin") {
            fab.visibility = View.GONE
            fab.visibility = View.VISIBLE

            fab.setOnClickListener { view ->
                val add = Intent(this@MainActivity, AddActivity::class.java)
                startActivity(add)
            }

        } else {
            fab.visibility = View.GONE
            fab.visibility = View.INVISIBLE
        }
    }

    // OBTIENE EL USUARIO PARA GUARDARLO
    private fun getUserSharedPreferences() {
        val pref = this@MainActivity.getSharedPreferences("SharedPreferenceLogin", Context.MODE_PRIVATE)
        tvUser.text = pref.getString("user", "Usuario")
    }

    // RECARGA DE DATOS
    private fun loadDevicesOnrecyclerView() {
        recycler_devices.layoutManager = LinearLayoutManager(baseContext)

        if (tvUser.text == "admin"){
            recycler_devices.adapter = DeviceListAdapter(deviceList) { devices: Device ->  deviceClickedAdmin(devices)}
        } else {
            recycler_devices.adapter = DeviceListAdapter(deviceList) { devices: Device ->  deviceClickedPublic(devices)}
        }

    }

    // ACCIÓN AL PULSAR
    private fun deviceClickedPublic(device: Device) {

        // CREACIÓN DEL ALERT VIEW
        val builder = AlertDialog.Builder(this@MainActivity)

        // CONDICIÓN Y ACCIONES
        if (!device.prestado) {

            builder.setTitle("Préstamo")
            builder.setMessage("¿Vas coger este teléfono?")

            builder.setPositiveButton("Sí"){
                dialog, which ->
                telephoneStatus(true, device)
                Toast.makeText(applicationContext, "${device.modelo} prestado correctamente", Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("No"){
                dialog, which ->
                telephoneStatus(false, device)
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()

        } else {

            builder.setTitle("Devolución")
            builder.setMessage("¿Vas a devolver este teléfono?")

            builder.setPositiveButton("Sí"){
                dialog, which ->
                telephoneStatus(false, device)
                Toast.makeText(applicationContext, "${device.modelo} devuelto correctamente", Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("No"){
                dialog, which ->
                telephoneStatus(true, device)
            }

            val dialog: AlertDialog = builder.create()

            // SE MUESTRA LA ALERTA
            dialog.show()

        }
    }

    // ACCIÓN AL PULSAR
    private fun deviceClickedAdmin(devices: Device) {

        // CREACIÓN DEL ALERT VIEW
        val builder = AlertDialog.Builder(this@MainActivity)

        // CONDICIÓN Y ACCIONES
        if (!devices.prestado) {

            builder.setTitle("Préstamo")
            builder.setMessage("¿Vas coger este teléfono?")

            builder.setPositiveButton("Sí"){
                dialog, which ->
                telephoneStatus(true, devices)
                Toast.makeText(applicationContext, "${devices.modelo} prestado correctamente", Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("No"){
                dialog, which ->
                telephoneStatus(false, devices)
            }

            builder.setNeutralButton("Editar"){
                dialog, which ->
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("deviceClicked", devices.id)
                this@MainActivity.startActivity(intent)
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()

        } else {

            builder.setTitle("Devolución")
            builder.setMessage("¿Vas a devolver este teléfono?")

            builder.setPositiveButton("Sí"){
                dialog, which ->
                telephoneStatus(false, devices)
                Toast.makeText(applicationContext, "${devices.modelo} devuelto correctamente", Toast.LENGTH_SHORT).show()
            }

            builder.setNegativeButton("No"){
                dialog, which ->
                telephoneStatus(true, devices)
            }

            builder.setNeutralButton("Editar"){
                dialog, which ->
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                intent.putExtra("deviceClicked", devices.id)
                this@MainActivity.startActivity(intent)
            }

            val dialog: AlertDialog = builder.create()

            // SE MUESTRA LA ALERTA
            dialog.show()

        }
    }

    // ENVIAR DATOS TRUE O FALSE A FIREBASE
    private fun telephoneStatus(status: Boolean, devices: Device) {
        myRef.child(devices.id).child("prestado").setValue(status)
    }


    // RECEPCIÓN DE DATOS DESDE LOGIN ACTIVITY
    private fun userChanged(){
        if(intent.getStringExtra("user") != null){
            val usuario = intent.extras?.getString("user")
            tvUser.text = usuario
        }
    }


    // FIREBASE DATOS DE DISPOSITIVOS
    private fun deviceListenerFirebase(onLoad: OnLoad) {

        val deviceListListener = object : ChildEventListener{

            // CANCELAR EN BASE DE DATOS
            override fun onCancelled(p0: DatabaseError) {
                onLoad.onCancel(p0.toString())
                Toast.makeText(this@MainActivity.applicationContext, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show()

            }

            // MOVIMIENTOS EN LA BASE DE DATOS
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
                Log.d("Firebase", "onChildMoved:" + p0.key!!)
            }

            // CAMBIO EN LA BASE DE DATOS
            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                // GUARDAMOS EL OBJETO EN AUX
                val aux = Device(
                        p0.key.toString(),
                        p0.child("modelo").value.toString(),
                        p0.child("marca").value.toString(),
                        p0.child("numSerie").value.toString(),
                        p0.child("categoria").value.toString(),
                        p0.child("mac").value.toString(),
                        p0.child("prestado").value.toString().toBoolean())

                // BUSCAMOS LA POSICIÓN Y VEMOS SI LAS ID'S SON IGUALES
                for ( i in 0..deviceList.size){
                    if(deviceList[i].id == aux.id){
                        deviceList[i]=aux
                        break
                    }
                }
                loadDevicesOnrecyclerView()
            }

            // NUEVOS DATOS EN LA BASE DE DATOS
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                deviceList.add(Device(
                        p0.key.toString(),
                        p0.child("modelo").value.toString(),
                        p0.child("marca").value.toString(),
                        p0.child("numSerie").value.toString(),
                        p0.child("categoria").value.toString(),
                        p0.child("mac").value.toString(),
                        p0.child("prestado").value.toString().toBoolean()))
                loadDevicesOnrecyclerView()
                onLoad.onLoad("cargado")
            }

            // ELIMINACIÓN EN LA BASE DE DATOS
            override fun onChildRemoved(p0: DataSnapshot) {

                // BUSCAMOS LA POSICIÓN Y ELIMINAMOS LA KEY
                for ( i in 0..deviceList.size){
                    if(deviceList[i].id == p0.key.toString()){
                        deviceList.removeAt(i)
                        break
                    }
                }
                loadDevicesOnrecyclerView()
            }
        }
        myRef.addChildEventListener(deviceListListener)
    }

    interface OnLoad{
        fun onLoad(string: String)
        fun onCancel(string: String)
    }
}

