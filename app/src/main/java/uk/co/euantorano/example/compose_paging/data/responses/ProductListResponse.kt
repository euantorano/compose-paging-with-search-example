package uk.co.euantorano.example.compose_paging.data.responses

import kotlinx.serialization.SerialName
import uk.co.euantorano.example.compose_paging.data.models.Product

@kotlinx.serialization.Serializable
data class ProductListResponse(
    @SerialName("products") val products: List<Product>,
    @SerialName("total") val total: Long,
    @SerialName("skip") val skip: Long,
    @SerialName("limit") val limit: Long,
)
