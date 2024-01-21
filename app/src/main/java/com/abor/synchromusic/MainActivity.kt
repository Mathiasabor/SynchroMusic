package com.abor.synchromusic

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abor.synchromusic.Models.Person
import com.abor.synchromusic.ui.theme.SynchroMusicTheme

class MainActivity : ComponentActivity() {
    private val KEY_MY_INSTANCE = "myInstance"
    var person : Person? = null
   lateinit var nav : NavHostController
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            if (savedInstanceState !=null)
            {
                person = savedInstanceState.getSerializable(KEY_MY_INSTANCE) as Person?
                nav = person?.nav !!
            }else{
                nav = rememberNavController()
                person = Person("AHO","Mathias", nav)
            }
            var texts = rememberSaveable{mutableStateOf("")}
            val _texts : State<String> = texts

            SynchroMusicTheme {
                // A surface container using the 'background' color from the theme

                 NavHost(navController = nav, startDestination = "/")
                {
                    composable("/")
                    {

                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            Column {
                                person?.name?.let { Greeting(it) }
                                Spacer(modifier = Modifier.padding(20.dp))


                                TextfieldCustom(_texts, { text -> texts.value = text  })

                                Button(onClick = { person?.Navigate("suivant") }) {
                                    Text(text = "next")
                                }


                            }
                        }

                    }

                    composable("suivant")
                    {
                        Column {
                            Text(text = " Page2")
                            Text(text = " texte rentré :"+_texts.value)
                        }
                    }

                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {

        super.onSaveInstanceState(outState)
       outState.putSerializable(KEY_MY_INSTANCE, person)
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
    SynchroMusicTheme {
        Greeting("Android")
    }
}

@Composable
fun TextfieldCustom(text : State<String>, upateText : (text : String)->Unit)
{
    OutlinedTextField(value = text.value, onValueChange = { upateText(it)})
}