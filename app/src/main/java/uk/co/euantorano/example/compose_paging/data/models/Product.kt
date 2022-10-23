package uk.co.euantorano.example.compose_paging.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("description") val description: String,
    @SerialName("price") val price: Long,
    @SerialName("discountPercentage") val discountPercentage: Double,
    @SerialName("rating") val rating: Double,
    @SerialName("stock") val stock: Long,
    @SerialName("brand") val brand: String,
    @SerialName("category") val category: String,
    @SerialName("thumbnail") val thumbnail: String,
    @SerialName("images") val images: List<String>,
)
