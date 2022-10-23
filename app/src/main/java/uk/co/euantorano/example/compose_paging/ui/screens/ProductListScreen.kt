package uk.co.euantorano.example.compose_paging.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import uk.co.euantorano.example.compose_paging.data.models.Product
import uk.co.euantorano.example.compose_paging.ui.view_models.ProductListViewModel
import uk.co.euantorano.example.compose_paging.ui.view_models.ProductRow
import uk.co.euantorano.example.compose_paging.ui.widgets.SearchBarWidget

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLifecycleComposeApi::class,
    ExperimentalLayoutApi::class, ExperimentalFoundationApi::class
)
@Composable
fun ProductListScreen(
    vm: ProductListViewModel,
) {
    val isSearchBarVisible by vm.isSearchShowing.collectAsStateWithLifecycle()
    val search by vm.search.collectAsStateWithLifecycle()
    val products: LazyPagingItems<Product> = vm.productsSearchResults.collectAsLazyPagingItems()
    val refreshState = rememberSwipeRefreshState(
        isRefreshing = products.loadState.refresh is LoadState.Loading,
    )

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Products")
                },
                actions = {
                    IconButton(
                        onClick = {
                            vm.toggleIsSearchShowing()
                        },
                    ) {
                        Icon(Icons.Default.Search, contentDescription = "Search products")
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }
    ) { paddingValues ->
        Box(
            Modifier.padding(paddingValues)
                .consumedWindowInsets(paddingValues)
        ) {
            SwipeRefresh(
                state = refreshState,
                onRefresh = {
                    products.refresh()
                },
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top,
                ) {
                    if (isSearchBarVisible) {
                        stickyHeader {
                            SearchBarWidget(
                                query = search,
                                onQueryChanged = {
                                    vm.setSearch(it)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                label = "Search",
                                placeholder = "Search products",
                            )
                        }
                    }

                    items(
                        items = products,
                        key = {
                            it.id
                        },
                    ) { product ->
                        product?.let {
                            ProductRow(
                                title = it.title,
                                brand = it.brand,
                                price = it.price,
                                thumbnail = it.thumbnail,
                            )
                        }

                        Divider()
                    }
                }
            }
        }
    }
}