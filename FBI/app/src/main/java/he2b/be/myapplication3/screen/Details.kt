package he2b.be.myapplication3.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import he2b.be.myapplication3.viewmodel.DataViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import he2b.be.myapplication3.viewmodel.DetailsViewModels

@Composable
fun DetailsScreen(
    uid: String?,
    navController: NavHostController?,
    dataViewModel: DataViewModel,
    detailsViewModel: DetailsViewModels
) {
    val context = LocalContext.current
    var uidError by remember { mutableStateOf(false) }

    if (uid != null) {
        LaunchedEffect(key1 = uid) {
            uidError = if (isValidUID(uid)) {
                detailsViewModel.fetchDetailsByUID(uid, context)
                false
            } else {
                true
            }
        }
    } else {
        uidError = true
    }

    val details = detailsViewModel.details.value

    val textStyle = TextStyle(
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.sp
    )
    val backgroundColor = Color(0xFF1E2A49)

    Surface(
        color = backgroundColor,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                MyAppBar(navController = navController, showBackButton = true)
            },
            bottomBar = {
                MenuBottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    homeButton = true,
                    navController = navController,
                    favoritesButton = true,
                    infoButton = true
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(backgroundColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .background(backgroundColor)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                     if (details != null) {
                        val imageUrl = details.images.firstOrNull()?.original ?: ""
                        val name = details.name ?: "No name specified"
                        val age = details.age ?: "No age specified"
                        val description = details.description ?: "No Description Available"
                        val fieldOffice = details.fieldOffice?.firstOrNull() ?: ""
                        val caution = details.caution ?: "No caution information"

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .background(Color(0xFF2E3B55))
                                    .padding(16.dp)
                            ) {
                                if (imageUrl.isNotEmpty()) {
                                    NetworkImage(url = imageUrl)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }

                                Text(
                                    text = "Details for UID: ${uid ?: "Unknown"}",
                                    style = MaterialTheme.typography.headlineMedium.merge(textStyle),
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )

                                Text(
                                    text = "Name: $name",
                                    style = textStyle,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )

                                Text(
                                    text = "Age: $age",
                                    style = textStyle,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )

                                Text(
                                    text = "Description: $description",
                                    style = textStyle,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                CautionText(
                                        caution = caution,
                                        textStyle = textStyle.toSpanStyle()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Row {
                                    Button(
                                        onClick = { dataViewModel.likeItem(details) },
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text("Like")
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    if (fieldOffice.isNotBlank()) {
                                        Button(
                                            onClick = {
                                                navController?.navigate("map/$fieldOffice")
                                            },
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text("View on Map")
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Text(
                            text = "Loading details... Check your connection",
                            style = textStyle,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

fun isValidUID(uid: String): Boolean {
    return uid.isNotEmpty()
}

@Composable
fun CautionText(caution: String, textStyle: SpanStyle) {
    val cleanedText = caution.replace("<p>", "").replace("</p>", "")

    Text(
        text = AnnotatedString.Builder().apply {
            withStyle(textStyle) {
                append("Caution: $cleanedText")
            }
        }.toAnnotatedString(),
        modifier = Modifier.padding(bottom = 4.dp)
    )
}



