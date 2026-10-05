package com.senati.saborapp

data class DetallePedido(
    val id: Int = 0,
    val pedidoId: Int,
    val platoId: Int,
    val platoNombre: String,
    val precioUnitario: Double,
    val cantidad: Int,
    val subtotal: Double
)