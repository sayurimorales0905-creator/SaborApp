package com.senati.saborapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.senati.saborapp.databinding.ActivityPlatosBinding

class PlatosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlatosBinding
    private lateinit var dbHelper: DBHelper
    private var categoriaSeleccionada: String? = "Todos"
    private var textoBusqueda: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlatosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.fabAgregar.setOnClickListener {
            val intent = Intent(this, PlatoFormActivity::class.java)
            startActivity(intent)
        }

        binding.chipGroupCategorias.setOnCheckedStateChangeListener { group, checkedIds ->
            categoriaSeleccionada = when {
                checkedIds.contains(R.id.chipFondos) -> "Fondos"
                checkedIds.contains(R.id.chipBebidas) -> "Bebidas"
                checkedIds.contains(R.id.chipPostres) -> "Postres"
                else -> "Todos"
            }
            cargarPlatos()
        }

        binding.etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textoBusqueda = s?.toString()?.trim()
                cargarPlatos()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onResume() {
        super.onResume()
        cargarPlatos()
    }

    private fun cargarPlatos() {
        binding.containerPlatos.removeAllViews()
        val platos = dbHelper.obtenerPlatos(categoriaSeleccionada, textoBusqueda)

        for (plato in platos) {
            val cardView = crearCardPlato(plato)
            binding.containerPlatos.addView(cardView)
        }
    }

    private fun crearCardPlato(plato: Plato): View {
        val card = MaterialCardView(this).apply {
            layoutParams = android.widget.LinearLayout.LayoutParams(
                android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
                android.widget.LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = dpToPx(10)
            }
            radius = dpToPx(16).toFloat()
            cardElevation = 0f
            strokeWidth = dpToPx(1)
            setStrokeColor(getColor(R.color.sabor_gris_borde))
            setCardBackgroundColor(getColor(R.color.white))
        }

        val relativeLayout = RelativeLayout(this).apply {
            setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))
        }

        val linearTextos = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            params.addRule(RelativeLayout.ALIGN_PARENT_START)
            layoutParams = params
        }

        val tvNombre = TextView(this).apply {
            text = plato.nombre
            setTextColor(getColor(R.color.sabor_negro))
            textSize = 15f
            setTypeface(null, android.graphics.Typeface.BOLD)
        }

        val tvDetalle = TextView(this).apply {
            text = "${plato.categoria} · S/ ${String.format("%.2f", plato.precio)}"
            setTextColor(getColor(R.color.sabor_gris_texto))
            textSize = 13f
        }

        linearTextos.addView(tvNombre)
        linearTextos.addView(tvDetalle)

        val tvEstado = TextView(this).apply {
            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            params.addRule(RelativeLayout.ALIGN_PARENT_END)
            params.addRule(RelativeLayout.CENTER_VERTICAL)
            layoutParams = params

            if (plato.disponible) {
                text = getString(R.string.estado_disponible)
                setBackgroundResource(R.drawable.bg_chip_disponible)
                setTextColor(getColor(R.color.sabor_negro))
            } else {
                text = getString(R.string.estado_agotado)
                setBackgroundResource(R.drawable.bg_chip_agotado)
                setTextColor(getColor(R.color.sabor_rojo))
                setTypeface(null, android.graphics.Typeface.BOLD)
            }
            textSize = 12f
        }

        relativeLayout.addView(linearTextos)
        relativeLayout.addView(tvEstado)
        card.addView(relativeLayout)

        card.setOnClickListener {
            val intent = Intent(this, PlatoFormActivity::class.java).apply {
                putExtra("plato_id", plato.id)
            }
            startActivity(intent)
        }

        return card
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}