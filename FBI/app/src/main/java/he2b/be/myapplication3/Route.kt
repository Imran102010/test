package he2b.be.myapplication3

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import he2b.be.myapplication3.screen.About
import he2b.be.myapplication3.screen.DetailsScreen
import he2b.be.myapplication3.screen.FavScreen
import he2b.be.myapplication3.screen.HomeScreen
import he2b.be.myapplication3.screen.MapViewContainer
import he2b.be.myapplication3.viewmodel.DataViewModel
import he2b.be.myapplication3.viewmodel.DetailsViewModels
import he2b.be.myapplication3.viewmodel.MainViewModel
import he2b.be.myapplication3.viewmodel.MapsViewModel


@Composable
fun AppNavHost(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    dataViewModel: DataViewModel,
    detailsViewModels: DetailsViewModels
) {
    NavHost(navController, startDestination = "titleList") {
        composable("titleList") {
            HomeScreen(viewModel = mainViewModel, navController = navController)
        }
        composable("details/{itemUid}") { backStackEntry ->
            val uid = backStackEntry.arguments?.getString("itemUid") ?: ""
            DetailsScreen(uid, navController, dataViewModel, detailsViewModels)
        }
        composable("map/{fieldOffice}") {   backStackEntry ->
            MapViewContainer(
                fieldOffice = backStackEntry.arguments?.getString("fieldOffice") ?: "Unknown",
                navController = navController, MapsViewModel()
            )
        }
        composable("favorites") {
            FavScreen(viewModel = dataViewModel, PaddingValues(),navController = navController)
        }
        composable("about"){
            About(navController = navController)
        }
        composable("home"){
            HomeScreen(viewModel = mainViewModel, navController = navController)
        }
    }
}