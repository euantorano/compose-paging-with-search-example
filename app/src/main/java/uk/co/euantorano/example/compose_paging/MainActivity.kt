package uk.co.euantorano.example.compose_paging

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import uk.co.euantorano.example.compose_paging.ui.screens.ProductListScreen
import uk.co.euantorano.example.compose_paging.ui.theme.ComposePagingExampleTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePagingExampleTheme {
                ProductListScreen(vm = viewModel())
            }
        }
    }
}
