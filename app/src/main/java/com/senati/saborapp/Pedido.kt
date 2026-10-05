package com.senati.saborapp

data class Pedido(
    val id: Int = 0,
    val mesaNumero: Int,
    val fecha: String,
    val total: Double = 0.0,
    val estado: String = "ABIERTO"
)