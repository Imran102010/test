package he2b.be.myapplication3.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import he2b.be.myapplication3.viewmodel.DataViewModel

@Composable
fun FavScreen(
    viewModel: DataViewModel,
    paddingValues: PaddingValues,
    navController: NavHostController
) {
    val items by viewModel.allItems
    val backgroundColor = Color(0xFF1E2A49)

    Surface(color = backgroundColor) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                MyAppBar(navController = navController, showBackButton = true)
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    items(items) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = 4.dp,
                            shape = RoundedCornerShape(8.dp),
                            backgroundColor = Color.White
                        ) {
                            Column {
                                TitleDetail(
                                    title = item.title,
                                    itemUid = item.uid,
                                    imageUrl = item.images,
                                    navController = navController,
                                ) {
                                    viewModel.deleteItem(item)
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
            MenuBottomAppBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                homeButton = true,
                navController = navController,
                favoritesButton = true,
                infoButton = true
            )
        }
    }
}
