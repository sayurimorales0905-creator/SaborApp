package com.senati.saborapp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.senati.saborapp.databinding.ActivityPlatoFormBinding

class PlatoFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlatoFormBinding
    private lateinit var dbHelper: DBHelper
    private var platoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlatoFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.toolbar.setNavigationOnClickListener { finish() }

        val categorias = arrayOf("Fondos", "Bebidas", "Postres", "Entradas")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categorias)
        binding.spCategoria.setAdapter(adapter)

        platoId = intent.getIntExtra("plato_id", -1)

        if (platoId > 0) {
            binding.toolbar.title = getString(R.string.form_editar_titulo)
            binding.btnActualizar.text = getString(R.string.form_actualizar)
            binding.btnEliminar.visibility = View.VISIBLE
            cargarDatosPlato()
        } else {
            binding.toolbar.title = getString(R.string.form_nuevo_titulo)
            binding.btnActualizar.text = getString(R.string.form_guardar)
            binding.btnEliminar.visibility = View.GONE
        }

        binding.btnActualizar.setOnClickListener { guardarOActualizar() }
        binding.btnEliminar.setOnClickListener { eliminar() }
    }

    private fun cargarDatosPlato() {
        val plato = dbHelper.obtenerPlatoPorId(platoId) ?: return
        binding.etNombre.setText(plato.nombre)
        binding.spCategoria.setText(plato.categoria, false)
        binding.etPrecio.setText(plato.precio.toString())
        binding.swDisponible.isChecked = plato.disponible
    }

    private fun guardarOActualizar() {
        val nombre = binding.etNombre.text.toString().trim()
        val categoria = binding.spCategoria.text.toString().trim()
        val precioStr = binding.etPrecio.text.toString().trim()
        val disponible = binding.swDisponible.isChecked

        if (nombre.isEmpty()) {
            binding.etNombre.error = "Ingresa el nombre del plato"
            return
        }
        if (precioStr.isEmpty()) {
            binding.etPrecio.error = "Ingresa el precio"
            return
        }

        val precio = precioStr.toDoubleOrNull() ?: 0.0

        if (platoId > 0) {
            val plato = Plato(platoId, nombre, categoria, precio, disponible)
            dbHelper.actualizarPlato(plato)
            Toast.makeText(this, "Plato actualizado con éxito", Toast.LENGTH_SHORT).show()
        } else {
            val plato = Plato(nombre = nombre, categoria = categoria, precio = precio, disponible = disponible)
            dbHelper.insertarPlato(plato)
            Toast.makeText(this, "Plato registrado con éxito", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    private fun eliminar() {
        if (platoId > 0) {
            dbHelper.eliminarPlato(platoId)
            Toast.makeText(this, "Plato eliminado", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}