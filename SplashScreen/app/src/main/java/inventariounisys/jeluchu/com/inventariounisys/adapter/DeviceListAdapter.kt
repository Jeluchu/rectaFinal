package inventariounisys.jeluchu.com.inventariounisys.adapter

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import inventariounisys.jeluchu.com.inventariounisys.R
import inventariounisys.jeluchu.com.inventariounisys.objets.Device
import kotlinx.android.synthetic.main.recycler_devices.view.*


@Suppress("DEPRECATION")
class DeviceListAdapter (private var deviceList: MutableList<Device>, private val clickListener: (Device) -> Unit): RecyclerView.Adapter<DeviceListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceListAdapter.ViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.recycler_devices, parent, false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    override fun onBindViewHolder(holder: DeviceListAdapter.ViewHolder, position: Int) {
        holder.bindItems(deviceList[position], clickListener)
    }

    class ViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: Device, clickListener: (Device) -> Unit) {
            itemView.tvModelo.text = Html.fromHtml("<b>Modelo: </b>" + item.modelo)
            itemView.tvMarca.text = Html.fromHtml("<b>Marca: </b>" + item.marca)
            itemView.numSerie.text = Html.fromHtml("<b>Número de Serie: </b>" + item.numSerie)
            itemView.categoria.text = Html.fromHtml("<b>Categoría: </b>" + item.categoria)
            itemView.mac.text = Html.fromHtml("<b>Dirección MAC: </b>" + item.mac)
            itemView.prestado.text = Html.fromHtml("<b>Prestado: </b>" + item.prestado)


            if (item.prestado){
                itemView.rectanguloEstado.setBackgroundColor(Color.RED)
            } else{
                itemView.rectanguloEstado.setBackgroundColor(Color.GREEN)
            }

            if (item.categoria == "Móvil" || item.categoria == "móvil" || item.categoria == "movil"){
                itemView.dispositivo.setImageResource(R.drawable.ic_smartphone)
            } else if (item.categoria == "Tablet" || item.categoria == "tablet")
            {
                itemView.dispositivo.setImageResource(R.drawable.ic_tablet)
            } else {
                itemView.dispositivo.setImageResource(R.drawable.ic_error)
            }

            if (item.marca == "Apple" || item.marca == "apple"){
                itemView.so.setImageResource(R.drawable.ic_apple)
            } else {
                itemView.so.setImageResource(R.drawable.ic_android)
            }

            if (item.marca.isEmpty()) {
                itemView.so.setImageResource(R.drawable.ic_info)
            }

            if(item.prestado){
                itemView.prestado.text = Html.fromHtml("<b>Prestado: </b>" + "Ocupado" )
            } else {
                itemView.prestado.text = Html.fromHtml("<b>Prestado: </b>" + "Disponible")
            }

            itemView.setOnClickListener {
                clickListener(item)
            }
        }
    }
}