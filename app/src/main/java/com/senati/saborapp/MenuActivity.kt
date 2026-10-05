package com.senati.saborapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.senati.saborapp.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuario = intent.getStringExtra("usuario") ?: "admin"
        val rol = intent.getStringExtra("rol") ?: "Administrador"

        binding.tvBienvenida.text = getString(R.string.menu_bienvenida, usuario)
        binding.tvRol.text = getString(R.string.menu_rol, rol)
        binding.tvAvatarInicial.text = usuario.take(1).uppercase()

        if (rol.lowercase() != "administrador") {
            binding.btnReportes.visibility = View.GONE
        }

        binding.btnPlatos.setOnClickListener { abrir(PlatosActivity::class.java) }
        binding.btnMesas.setOnClickListener { abrir(MesasActivity::class.java) }
        binding.btnPedidos.setOnClickListener { abrir(PedidoActivity::class.java) }
        binding.btnReportes.setOnClickListener { abrir(ReportesActivity::class.java) }

        binding.btnSalir.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    private fun abrir(destino: Class<*>) {
        startActivity(Intent(this, destino))
    }
}