package uk.co.euantorano.example.compose_paging.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import uk.co.euantorano.example.compose_paging.data.Repository
import uk.co.euantorano.example.compose_paging.data.models.Product

class ProductPagingSource(
    private val repository: Repository,
    val search: String,
): PagingSource<Int, Product>() {
    companion object {
        const val PAGE_SIZE = 10

        private const val INITIAL_LOAD_SIZE = 0
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val position = params.key ?: INITIAL_LOAD_SIZE
            val offset = if (params.key != null) ((position - 1) * PAGE_SIZE) else INITIAL_LOAD_SIZE

            val result = repository.getProducts(
                skip = offset,
                limit = params.loadSize,
                search = search,
            )

            val nextKey = if (result.products.isEmpty()) {
                null
            } else {
                position + (params.loadSize / PAGE_SIZE)
            }

            return LoadResult.Page(
                data = result.products,
                prevKey = null,
                nextKey = nextKey,
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }
}
