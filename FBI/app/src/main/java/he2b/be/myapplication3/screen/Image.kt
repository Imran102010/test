package he2b.be.myapplication3.screen

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun NetworkImage(url: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .setHeader("User-Agent", "Android App")
            .build(),
        contentDescription = null,
        modifier = Modifier
            .aspectRatio(8f / 8f)
            .height(150.dp),
        contentScale = ContentScale.Crop
    )
}