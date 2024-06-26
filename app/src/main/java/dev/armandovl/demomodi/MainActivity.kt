package dev.armandovl.demomodi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import dev.armandovl.demomodi.ui.theme.DemoModiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoModiTheme {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "input_screen") {
        composable("input_screen") { InputScreen(navController) }
        composable("display_screen/{inputText}") { backStackEntry ->
            val inputText = backStackEntry.arguments?.getString("inputText")
            DisplayScreen(inputText, navController)
        }
    }
}

@Composable
fun InputScreen(navController: NavHostController) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text("Ingresa el texto") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                navController.navigate("display_screen/${textState.text}")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text("Enviar")
        }
    }
}

@Composable
fun DisplayScreen(inputText: String?, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .size(width = 300.dp, height = 150.dp)
                .padding(8.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color(0xFFE0E0E0),
                contentColor = Color.Black
            )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = inputText ?: "No text received",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController.navigate("input_screen")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Regresar a la pantalla del input")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InputScreenPreview() {
    DemoModiTheme {
        InputScreen(rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayScreenPreview() {
    DemoModiTheme {
        DisplayScreen("Sample text", rememberNavController())
    }
}
