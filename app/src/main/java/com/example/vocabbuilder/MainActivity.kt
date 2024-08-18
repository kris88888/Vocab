package com.example.vocabbuilder

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.vocabbuilder.framework.database.VocabDatabase
import com.example.vocabbuilder.ui.composables.HomeScreen
import com.example.vocabbuilder.ui.theme.VocabBuilderTheme
import com.example.vocabbuilder.ui.viewmodel.VocabViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(applicationContext, VocabDatabase::class.java ,"vocabs.db").build()
    }

    private val viewmodel by viewModels<VocabViewModel>(
        factoryProducer = { object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return VocabViewModel(db.dao) as T
            }
        }
    })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VocabBuilderTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state = viewmodel.uiState.collectAsState()
                    Log.d("SCREEN***", "onCreate: State is ${state.value}")
                    HomeScreen(state.value) {
                        viewmodel.onEvent(it)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VocabBuilderTheme {
        Greeting("Android")
    }
}