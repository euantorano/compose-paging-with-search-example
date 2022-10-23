package uk.co.euantorano.example.compose_paging.ui.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import uk.co.euantorano.example.compose_paging.data.Repository
import uk.co.euantorano.example.compose_paging.data.paging.ProductPagingSource
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    private val _search = MutableStateFlow("")

    val search = _search.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

    private val _isSearchShowing = MutableStateFlow(false)

    val isSearchShowing = _isSearchShowing.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false,
        )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val productsSearchResults = search.debounce(300.milliseconds).flatMapLatest { query ->
        Pager(
            PagingConfig(
                pageSize = ProductPagingSource.PAGE_SIZE,
                enablePlaceholders = false,
            )
        ) {
            ProductPagingSource(
                repository = repository,
                search = query,
            )
        }.flow.cachedIn(viewModelScope)
    }


    fun setSearch(query: String) {
        _search.value = query
    }

    fun toggleIsSearchShowing() {
        _isSearchShowing.value = !_isSearchShowing.value
    }
}