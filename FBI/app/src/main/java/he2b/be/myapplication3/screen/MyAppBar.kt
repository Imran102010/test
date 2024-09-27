package he2b.be.myapplication3.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController
import he2b.be.myapplication3.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(navController: NavHostController?, showBackButton: Boolean = false) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.fbi),
                    contentDescription = "FBI Logo",
                    modifier = Modifier.size(56.dp),
                    tint = Color.Unspecified
                )
                Text(
                    text = "FBI",
                    color = Color(red = 244, green = 203, blue = 71),
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(
                            R.font.arial_black,
                            weight = FontWeight.Normal,
                            style = FontStyle.Normal
                        )
                    )
                )
            }
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = { navController?.navigateUp() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(red = 30, green = 42, blue = 73),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun MenuBottomAppBar(
    modifier: Modifier = Modifier,
    homeButton: Boolean = true,
    navController: NavHostController?,
    favoritesButton: Boolean = false,
    infoButton: Boolean = false
) {
    BottomAppBar(
        modifier = modifier
            .background(Color(0xFF1E2A49))
            .height(65.dp)
            .padding(0.dp)
            .drawWithContent {
                drawContent()
                drawRect(
                    color = Color.Gray,
                    topLeft = Offset.Zero,
                    size = Size(size.width, 1.dp.toPx())
                )
            },
        containerColor = Color(0xFF1E2A49),
        contentColor = Color.White,
        tonalElevation = 2.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            if (favoritesButton) {
                IconButton(onClick = {
                    navController?.navigate("favorites")
                }) {
                    Icon(Icons.Default.Star, contentDescription = "Favorites", tint = Color.White)
                }
            }
            if (homeButton) {
                IconButton(onClick = {
                    navController?.navigate("home")
                }) {
                    Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White)
                }
            }
            if (infoButton) {
                IconButton(onClick = {
                    navController?.navigate("about")
                }) {
                    Icon(Icons.Default.Info, contentDescription = "About", tint = Color.White)
                }
            }
        }
    }
}


