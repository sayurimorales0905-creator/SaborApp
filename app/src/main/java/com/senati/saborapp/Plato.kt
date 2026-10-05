package com.senati.saborapp

data class Plato(
    val id: Int = 0,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val disponible: Boolean = true
)