package uk.co.euantorano.example.compose_paging.ui.view_models

import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductRow(
    title: String,
    brand: String,
    price: Long,
    thumbnail: String,
) {
    ListItem(
        headlineText = {
            Text(title)
        },
        supportingText = {
            Text("Brand: $brand")
        },
        leadingContent = {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = "Thumbnail for product",
                contentScale = ContentScale.Crop,
                modifier = Modifier.widthIn(max = 100.dp),
            )
        },
        trailingContent = {
            Text("Price: $price")
        },
    )
}