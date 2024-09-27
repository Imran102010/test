package he2b.be.myapplication3.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import he2b.be.myapplication3.viewmodel.MainViewModel
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable

@Composable
fun HomeScreen(viewModel: MainViewModel, navController: NavHostController) {
    val titles by viewModel.titles.collectAsState(initial = emptyList())
    var expanded by rememberSaveable { mutableStateOf(false) }
    val options = listOf("default", "most", "missing", "terrorist", "ten", "criminal enterprise", "white Collar", "ecap", "kidnapping")
    var selectedOption by rememberSaveable { mutableStateOf(viewModel.lastSelectedOption) }
    val errorMessage by viewModel.errorMessage

    Surface(color = Color(0xFF1E2A49)) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column {
                MyAppBar(navController = navController, showBackButton = false)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                        .clickable(onClick = { expanded = true })
                        .padding(16.dp)
                ) {
                    Text(
                        text = selectedOption,
                        style = TextStyle(color = Color(0xFF1E2A49), fontSize = 16.sp)
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(onClick = {
                                selectedOption = option
                                expanded = false
                                viewModel.updateFilter(option)
                                viewModel.saveLastSelectedOption(option)
                            }) {
                                Text(text = option, style = TextStyle(color = Color(0xFF1E2A49)))
                            }
                        }
                    }
                }
                if (titles.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No result",
                            style = TextStyle(color = Color.White, fontSize = 20.sp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        items(titles) { item ->
                            TitleDetail(
                                title = item.title,
                                imageUrl = item.images.firstOrNull()?.original,
                                itemUid = item.uid,
                                navController = navController,
                                onDelete = null,
                            )
                            if (titles.indexOf(item) == titles.lastIndex) {
                                LaunchedEffect(Unit) {
                                    viewModel.fetchByCategory(selectedOption)
                                }
                            }
                        }
                    }
                    errorMessage?.let {
                        ConnectionError(message = it)
                    }
                }
            }
            MenuBottomAppBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                true,
                navController = navController,
                favoritesButton = true,
                infoButton = true
            )
        }
    }
}

@Composable
fun TitleDetail(title: String, imageUrl: String?,itemUid: String, navController: NavHostController, onDelete: (() -> Unit)?) {
    Surface(
        color = Color.Transparent,
        border = BorderStroke(1.dp, Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("details/$itemUid") }
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp),
                onTextLayout = null
            )
            imageUrl?.let { NetworkImage(url = it) }
        }
        if (onDelete != null) {
            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}


@Composable
fun ConnectionError(message: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Refresh,
            contentDescription = null,
            tint = Color.Red,
        )
        Text(
            text = message,
            color = Color.Red,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


