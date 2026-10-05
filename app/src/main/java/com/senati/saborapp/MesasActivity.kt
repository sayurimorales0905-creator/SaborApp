package com.senati.saborapp

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.senati.saborapp.databinding.ActivityMesasBinding

class MesasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMesasBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMesasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.fabAgregarMesa.setOnClickListener { mostrarDialogoNuevaMesa() }

        cargarMesas()
    }

    private fun cargarMesas() {
        binding.gridMesas.removeAllViews()
        val mesas = dbHelper.obtenerMesas()

        for (mesa in mesas) {
            val cardView = crearCardMesa(mesa)
            binding.gridMesas.addView(cardView)
        }
    }

    private fun crearCardMesa(mesa: Mesa): View {
        val card = MaterialCardView(this).apply {
            val params = android.widget.GridLayout.LayoutParams().apply {
                width = 0
                height = dpToPx(70)
                columnSpec = android.widget.GridLayout.spec(android.widget.GridLayout.UNDEFINED, 1f)
                setMargins(dpToPx(4), dpToPx(4), dpToPx(4), dpToPx(4))
            }
            layoutParams = params
            radius = dpToPx(12).toFloat()
            cardElevation = 0f

            if (mesa.estado.lowercase() == "ocupada") {
                strokeWidth = dpToPx(2)
                setStrokeColor(getColor(R.color.sabor_naranja))
                setCardBackgroundColor(getColor(R.color.sabor_rojo_suave))
            } else {
                strokeWidth = dpToPx(1)
                setStrokeColor(getColor(R.color.sabor_gris_borde))
                setCardBackgroundColor(getColor(R.color.white))
            }
        }

        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8))
        }

        val tvMesa = TextView(this).apply {
            text = "Mesa ${mesa.numero}"
            setTextColor(getColor(R.color.sabor_negro))
            textSize = 14f
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        val tvEstado = TextView(this).apply {
            text = "${mesa.estado} (${mesa.capacidad}p)"
            if (mesa.estado.lowercase() == "ocupada") {
                setTextColor(getColor(R.color.sabor_rojo))
            } else {
                setTextColor(getColor(R.color.sabor_gris_texto))
            }
            textSize = 11f
        }

        container.addView(tvMesa)
        container.addView(tvEstado)
        card.addView(container)

        card.setOnClickListener {
            val intent = Intent(this, PedidoActivity::class.java).apply {
                putExtra("mesa_numero", mesa.numero)
            }
            startActivity(intent)
        }

        return card
    }

    private fun mostrarDialogoNuevaMesa() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Registrar Nueva Mesa")

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dpToPx(20), dpToPx(10), dpToPx(20), dpToPx(10))
        }

        val etNumero = EditText(this).apply {
            hint = "Número de Mesa (ej: 7)"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
        }

        val etCapacidad = EditText(this).apply {
            hint = "Capacidad de personas (ej: 4)"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER
        }

        layout.addView(etNumero)
        layout.addView(etCapacidad)
        builder.setView(layout)

        builder.setPositiveButton("Guardar") { dialog, _ ->
            val numStr = etNumero.text.toString().trim()
            val capStr = etCapacidad.text.toString().trim()

            if (numStr.isNotEmpty() && capStr.isNotEmpty()) {
                val num = numStr.toInt()
                val cap = capStr.toInt()

                val res = dbHelper.insertarMesa(Mesa(numero = num, capacidad = cap, estado = "Libre"))
                if (res > 0) {
                    Toast.makeText(this, "Mesa $num registrada con éxito", Toast.LENGTH_SHORT).show()
                    cargarMesas()
                } else {
                    Toast.makeText(this, "Error: El número de mesa ya existe", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}