package com.senati.saborapp

import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.senati.saborapp.databinding.ActivityCuentaBinding
import java.util.Locale

class CuentaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCuentaBinding
    private lateinit var dbHelper: DBHelper
    private var mesaNumero: Int = 4
    private var pedidoId: Int = 125

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        mesaNumero = intent.getIntExtra("mesa_numero", 4)
        pedidoId = intent.getIntExtra("pedido_id", 125)

        binding.toolbar.title = getString(R.string.cuenta_titulo_fmt, "Mesa $mesaNumero")
        binding.toolbar.setNavigationOnClickListener { finish() }

        cargarCuenta()

        binding.btnCerrarCuenta.setOnClickListener {
            val exito = dbHelper.cerrarCuentaMesa(pedidoId, mesaNumero)
            if (exito) {
                Toast.makeText(this, "Cuenta de Mesa $mesaNumero cerrada con éxito", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MenuActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error al cerrar la cuenta", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnWhatsApp.setOnClickListener {
            val detalles = dbHelper.obtenerDetallesPedido(pedidoId)
            val total = detalles.sumOf { it.subtotal }
            val sb = StringBuilder()
            sb.append("Resumen Cuenta Mesa $mesaNumero (Pedido #$pedidoId):\n")
            for (det in detalles) {
                sb.append("- ${det.cantidad} x ${det.platoNombre}: S/ ${String.format(Locale.US, "%.2f", det.subtotal)}\n")
            }
            sb.append("Total: S/ ${String.format(Locale.US, "%.2f", total)}")

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, sb.toString())
            }
            startActivity(Intent.createChooser(intent, "Compartir cuenta"))
        }
    }

    private fun cargarCuenta() {
        val detalles = dbHelper.obtenerDetallesPedido(pedidoId)
        binding.containerItemsCuenta.removeAllViews()

        binding.tvNumPedidoYFecha.text = getString(R.string.cuenta_num_fmt, pedidoId, "04/10/2026 13:20")

        var totalCalculado = 0.0

        for (item in detalles) {
            totalCalculado += item.subtotal

            val row = RelativeLayout(this).apply {
                layoutParams = android.widget.LinearLayout.LayoutParams(
                    android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                    android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = dpToPx(10)
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
            binding.containerItemsCuenta.addView(row)
        }

        binding.tvTotalCuenta.text = String.format(Locale.US, "S/ %.2f", totalCalculado)
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}