package he2b.be.myapplication3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import he2b.be.myapplication3.repository.Repository
import he2b.be.myapplication3.viewmodel.DataViewModel
import he2b.be.myapplication3.viewmodel.DataViewModelFactory
import he2b.be.myapplication3.viewmodel.DetailsViewModelFactory
import he2b.be.myapplication3.viewmodel.DetailsViewModels
import he2b.be.myapplication3.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Repository.initDatabase(applicationContext)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel()
    val dataViewModelFactory = DataViewModelFactory()
    val dataViewModel: DataViewModel = viewModel(factory = dataViewModelFactory)
    val detailsViewModelFactory = DetailsViewModelFactory()
    val detailsViewModels: DetailsViewModels = viewModel(factory = detailsViewModelFactory)

    AppNavHost(navController, mainViewModel, dataViewModel, detailsViewModels)
}

