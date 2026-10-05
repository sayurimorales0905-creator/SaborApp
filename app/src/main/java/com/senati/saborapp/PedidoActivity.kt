package com.senati.saborapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.senati.saborapp.databinding.ActivityPedidoBinding
import java.util.Locale

class PedidoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPedidoBinding
    private lateinit var dbHelper: DBHelper
    private var mesaNumero: Int = 4
    private var pedidoActual: Pedido? = null
    private var listaPlatosBD: List<Plato> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.toolbar.setNavigationOnClickListener { finish() }

        mesaNumero = intent.getIntExtra("mesa_numero", 4)
        binding.tvMesaSeleccionada.text = getString(R.string.pedido_abierto_fmt, "Mesa $mesaNumero")

        // Cargar platos desde SQLite
        listaPlatosBD = dbHelper.obtenerPlatos().filter { it.disponible }
        val nombresPlatos = listaPlatosBD.map { it.nombre }.toTypedArray()

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, nombresPlatos)
        binding.spPlato.setAdapter(adapter)

        if (nombresPlatos.isNotEmpty()) {
            binding.spPlato.setText(nombresPlatos[0], false)
        }

        binding.btnAgregar.setOnClickListener { agregarPlato() }

        binding.btnVerCuenta.setOnClickListener {
            val intent = Intent(this, CuentaActivity::class.java).apply {
                putExtra("mesa_numero", mesaNumero)
                putExtra("pedido_id", pedidoActual?.id ?: 125)
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        cargarPedidoActual()
    }

    private fun cargarPedidoActual() {
        pedidoActual = dbHelper.obtenerOBuscarPedidoAbierto(mesaNumero)
        val pedido = pedidoActual ?: return

        binding.containerItemsPedido.removeAllViews()
        val detalles = dbHelper.obtenerDetallesPedido(pedido.id)

        for (item in detalles) {
            val row = RelativeLayout(this).apply {
                layoutParams = android.widget.LinearLayout.LayoutParams(
                    android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                    android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = dpToPx(8)
                }
            }

            val tvNombre = TextView(this).apply {
                text = "${item.cantidad} x ${item.platoNombre}"
                setTextColor(getColor(R.color.sabor_negro))
                textSize = 14f
            }

            val tvSubtotal = TextView(this).apply {
                val params = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                params.addRule(RelativeLayout.ALIGN_PARENT_END)
                layoutParams = params
                text = String.format(Locale.US, "%.2f", item.subtotal)
                setTextColor(getColor(R.color.sabor_negro))
                textSize = 14f
                setTypeface(null, android.graphics.Typeface.BOLD)
            }

            row.addView(tvNombre)
            row.addView(tvSubtotal)
            binding.containerItemsPedido.addView(row)
        }

        val totalCalculado = detalles.sumOf { it.subtotal }
        binding.tvTotalPedido.text = String.format(Locale.US, "S/ %.2f", totalCalculado)
    }

    private fun agregarPlato() {
        val nombreSeleccionado = binding.spPlato.text.toString().trim()
        val cantStr = binding.etCantidad.text.toString().trim()

        if (nombreSeleccionado.isEmpty()) {
            Toast.makeText(this, "Selecciona un plato", Toast.LENGTH_SHORT).show()
            return
        }

        val cant = cantStr.toIntOrNull() ?: 1
        if (cant <= 0) {
            Toast.makeText(this, "Ingresa una cantidad válida", Toast.LENGTH_SHORT).show()
            return
        }

        val plato = listaPlatosBD.find { it.nombre.equals(nombreSeleccionado, ignoreCase = true) }
        if (plato == null) {
            Toast.makeText(this, "Plato no encontrado", Toast.LENGTH_SHORT).show()
            return
        }

        val pedido = pedidoActual ?: dbHelper.obtenerOBuscarPedidoAbierto(mesaNumero)
        dbHelper.agregarPlatoAPedido(pedido.id, plato, cant)

        Toast.makeText(this, "Se agregó $cant x ${plato.nombre}", Toast.LENGTH_SHORT).show()
        cargarPedidoActual()
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}