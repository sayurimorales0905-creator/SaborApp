package com.senati.saborapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.senati.saborapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // HU-12: Verificación de sesión recordada en SharedPreferences
        val prefs = getSharedPreferences("saborapp_session", MODE_PRIVATE)
        val usuarioGuardado = prefs.getString("usuario", null)
        val rolGuardado = prefs.getString("rol", null)

        if (!usuarioGuardado.isNullOrEmpty() && !rolGuardado.isNullOrEmpty()) {
            val intent = Intent(this, MenuActivity::class.java)
                .putExtra("usuario", usuarioGuardado)
                .putExtra("rol", rolGuardado)
            startActivity(intent)
            finish()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.btnIngresar.setOnClickListener { validar() }
    }

    private fun validar() {
        val usuarioStr = binding.etUsuario.text.toString().trim()
        val claveStr = binding.etClave.text.toString().trim()

        binding.tilUsuario.error = null
        binding.tilClave.error = null

        var valido = true
        if (usuarioStr.isEmpty()) {
            binding.tilUsuario.error = getString(R.string.error_usuario_vacio)
            valido = false
        }
        if (claveStr.isEmpty()) {
            binding.tilClave.error = getString(R.string.error_clave_vacia)
            valido = false
        }
        if (!valido) return

        // HU-04: Validar contra la base de datos SQLite
        val usuarioEncontrado = dbHelper.validarUsuario(usuarioStr, claveStr)

        if (usuarioEncontrado == null) {
            Toast.makeText(this, R.string.error_credenciales, Toast.LENGTH_SHORT).show()
            return
        }

        // HU-12: Guardar sesión en SharedPreferences
        val prefs = getSharedPreferences("saborapp_session", MODE_PRIVATE)
        prefs.edit()
            .putString("usuario", usuarioEncontrado.usuario)
            .putString("rol", usuarioEncontrado.rol)
            .apply()

        val intent = Intent(this, MenuActivity::class.java)
            .putExtra("usuario", usuarioEncontrado.usuario)
            .putExtra("rol", usuarioEncontrado.rol)
        startActivity(intent)
        finish()
    }
}