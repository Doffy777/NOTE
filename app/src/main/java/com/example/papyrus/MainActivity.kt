package com.example.papyrus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = "splashScreen",//change para makita yung home
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            composable("home") { HomeScreen(navController) }
            composable("addList") { AddListScreen(navController) }
            composable("splashScreen") {SplashArtScreen(navController)}
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Box(modifier = Modifier
        .fillMaxSize())
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(176, 210, 126), Color(57, 69, 40)),
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 1000f)
                    )
                )
                /*.padding(WindowInsets.systemBars.asPaddingValues())*/
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            Header()
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                Spacer(modifier = Modifier.height(2.dp))
                CalendarCard()
                Spacer(modifier = Modifier.height(2.dp))
                ListCard()
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            AddListButton(navController)
        }
    }
}


@Composable
fun Header() {
    Box(
        modifier = Modifier
            .graphicsLayer {
                shadowElevation = 10f
                shape = RectangleShape
                clip = false
            }
            .background(Color.White)//header color
            .padding(20.dp)
            .padding(top = 24.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logotitle),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(50.dp)

            )
            Spacer(modifier = Modifier.height(20.dp))
            SearchBar()
        }
    }
}

@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }
    var expandedFilter by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)//search bar color(yung buong box nya XD)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(Color.LightGray, shape = RoundedCornerShape(20.dp))
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.papyrussearchicon),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Search")
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color(243, 243,243),
                    unfocusedContainerColor = Color(243, 243,243)
                )
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box{
            IconButton(onClick = { expandedFilter = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.papyrusfiltericon),
                    modifier = Modifier.size(23.dp),
                    contentDescription = null,
                    tint = Color(57, 69, 40)
                )
            }
            DropdownMenu(
                expanded = expandedFilter,
                onDismissRequest = {expandedFilter = false},
                modifier = Modifier
                    .background(Color.White)
                    .size(160.dp, 120.dp)
                    .offset(y = 5.dp)
            ) {
                DropdownMenuItem(
                    text = { Text("Filter 1", fontSize = 15.sp) },
                    onClick = {/*to do on the future*/}
                )

                /*HorizontalDivider(
                    thickness = 1.dp,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 5.dp)
                )*/
            }
        }
    }
}

@Composable
fun AddListButton(navController: NavController) {
    FloatingActionButton(
        onClick = { navController.navigate("addList") },
        modifier = Modifier
            .padding(bottom = 25.dp)
            .padding(16.dp)
            .size(72.dp),
        containerColor = Color.White,
        contentColor = Color(57, 69, 40),
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add",
            modifier = Modifier.size(48.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarCard() {
    val todayMillis = System.currentTimeMillis()

    val dateDisplay = rememberDatePickerState(
        initialSelectedDateMillis = todayMillis,
        initialDisplayedMonthMillis = todayMillis
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Adds spacing around the card
        shape = RoundedCornerShape(16.dp), // Rounded corners
        elevation = CardDefaults.cardElevation(8.dp) // Adds shadow for depth
    ) {
        DatePicker(
            modifier = Modifier.fillMaxWidth(),
            state = dateDisplay,
            showModeToggle = true,
            colors = DatePickerDefaults.colors(
                containerColor = Color.White,
                titleContentColor = Color.Black,
                headlineContentColor = Color.Gray,
                weekdayContentColor = Color(133, 188, 60),
                selectedDayContentColor = Color.White,
                selectedDayContainerColor = Color(83, 111, 46),
                todayContentColor = Color.Black,
                todayDateBorderColor = Color(125, 191, 39) // Highlights today's date with a green border
            )
        )
    }
}

@Composable
fun ListCard() {
    Box(//to be change in card if needed
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(16.dp)
            .shadow(6.dp, shape = RoundedCornerShape(16.dp))
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
       Image(
           painter = painterResource(id = R.drawable.emptyalert),
           contentDescription = null,
           modifier = Modifier
               .size(250.dp)
       )
    }
}

@Preview(showBackground = true)
@Composable
fun MyAppPreview() {
    MyApp()
}
