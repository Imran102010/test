package he2b.be.myapplication3.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.material.Surface

@Composable
fun About(navController: NavHostController) {
    Surface(
        color = Color(0xFF1E2A49),
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                MyAppBar(navController = navController, showBackButton = true)
            },
            bottomBar = {
                MenuBottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    true,
                    navController = navController,
                    favoritesButton = true,
                    infoButton = true
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Imran E.",
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "ID: 61411",
                            style = TextStyle(
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "F12",
                            style = TextStyle(
                                fontSize = 18.sp,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        )
    }
}


