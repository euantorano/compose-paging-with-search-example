package uk.co.euantorano.example.compose_paging.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import uk.co.euantorano.example.compose_paging.data.responses.ProductListResponse

class Repository(private val client: HttpClient) {
    suspend fun getProducts(search: String?, skip: Int = 0, limit: Int = 100): ProductListResponse {
        val response = client.get("https://dummyjson.com/products") {
            accept(ContentType.Application.Json)

            url {
                parameters.append("skip", skip.toString(10))
                parameters.append("limit", limit.toString(10))

                if (!search.isNullOrEmpty()) {
                    parameters.append("q", search)

                    appendPathSegments("search")
                }
            }

            expectSuccess = true
        }

        return response.body()
    }
}