package com.example.papyrus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.util.*


class AddListView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun AddListScreen(navController: NavController) {
    var selectedColor by remember { mutableStateOf(Color(176, 210, 126)) }
    var showColorPicker by remember { mutableStateOf(false) }
    var previewColor by remember { mutableStateOf(selectedColor) }
    var showItemDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(previewColor) // Background changes dynamically
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeaderAddlist(navController = navController, onOpenColorPicker = { showColorPicker = true })
            Spacer(modifier = Modifier.weight(1f))
        }

        // Floating Action Button - Positioned Bottom End
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = { showItemDialog = true },
                modifier = Modifier.padding(16.dp).padding(bottom = 25.dp),
                containerColor = Color.White,
                shape = RoundedCornerShape(50.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 13.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.papyrusaddlisticon),
                        contentDescription = null,
                        modifier = Modifier.size(26.dp).padding(start = 9.dp, end = 2.dp),
                        tint = Color(57, 69, 40)
                    )
                    Text(
                        text = "Add Item",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(57, 69, 40),
                        modifier = Modifier.padding(start = 9.dp, end = 8.dp)
                    )
                }
            }
        }
    }

    if (showColorPicker) { // Color picker pop-up
        ColorPickerDialog(
            currentColor = previewColor,
            onPreviewColor = { previewColor = it }, // Live update
            onDismiss = {
                selectedColor = previewColor // Apply on exit
                showColorPicker = false
            }
        )
    }

    if (showItemDialog) { // Add item pop-up
        AddItemPopup(
            onDismiss = { showItemDialog = false },
            onConfirm = { itemName, quantity ->
                showItemDialog = false
            }
        )
    }
}

@Composable
fun AddItemPopup(
    onDismiss: () -> Unit,
    onConfirm: (String, Int) -> Unit,
) {
    var itemName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf(1) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add Item") },
        text = {
            Column{
                TextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    placeholder = { Text("Type item name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Quantity"
                    )
                    IconButton(onClick = { if (quantity > 1) quantity-- }) {
                        Icon(
                            painter = painterResource(id = R.drawable.papyrusminusicon),
                            contentDescription = "Decrease Quantity")
                    }
                    Text(text = quantity.toString(), fontSize = 18.sp)
                    IconButton(onClick = { quantity++ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.papyrusplusicon),
                            contentDescription = "Increase Quantity")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(itemName, quantity) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(57, 69, 40),
                    contentColor = Color.White
                )
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(57, 69, 40),
                    contentColor = Color.White
                )
            ) {
                Text("Cancel")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerDialog(
    currentColor: Color,
    onPreviewColor: (Color) -> Unit,
    onDismiss: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    .clickable { onDismiss() }
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Select a Color",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row {
                        ColorOption(Color(155, 61, 38), onPreviewColor)
                        ColorOption(Color(74, 143, 143), onPreviewColor)
                        ColorOption(Color(117,142, 79), onPreviewColor)
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Row {
                        ColorOption(Color(0xFFD3D3D3), onPreviewColor)
                        ColorOption(Color(0xFFFFA500), onPreviewColor)
                        ColorOption(Color(0xFF800080), onPreviewColor)
                    }
                }
            }
        }
    )
}


@Composable//for color
fun ColorOption(color: Color, onPreviewColor: (Color) -> Unit) {
    Box(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
            .height(height = 150.dp)
            .width(width = 85.dp)
            .background(color, shape = RoundedCornerShape(12.dp))
            .clickable { onPreviewColor(color) }
    )
}

//with the color picker
@Composable
fun HeaderAddlist(navController: NavController, onOpenColorPicker: () -> Unit,) {
    var title by remember { mutableStateOf("") }//remove if para sa database
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(top = 25.dp)
            .shadow(8.dp, shape = RoundedCornerShape(16.dp))
            .background(Color.White, shape = RoundedCornerShape(16.dp)) // Keep header neutral
            .padding(16.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    painter = painterResource(id = R.drawable.papyrusbackbutton),
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(23.dp),
                    tint = Color(57, 69, 40)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedTextField(
                value = title,//change for database
                onValueChange = { title = it },//change for database
                modifier = Modifier.weight(5f),
                placeholder = { Text("Enter title", color = Color.Black) },
                singleLine = true,
                shape = RoundedCornerShape(0.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            //Calendar
            DatePicker()

            // Three-dot menu with dropdown
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.papyrusthreedoticon),
                        contentDescription = "Menu",
                        modifier = Modifier.size(20.dp),
                        tint = Color(57, 69, 40)
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(Color.White)
                        .size(160.dp, 120.dp)
                        .offset(y = 5.dp),
                ) {
                    DropdownMenuItem(
                        text = { Text("Change Color", fontSize = 15.sp) },
                        onClick = { onOpenColorPicker() },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.papyruschangecoloricon),
                                contentDescription = "Change Color",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(22.dp)
                            )
                        }
                    )

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.Gray,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, end = 5.dp)
                    )

                    DropdownMenuItem(
                        text = { Text("Delete", fontSize = 15.sp, color = Color.Red) },
                        onClick = { /* Handle delete action */ },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.papyrusdeleteicon),
                                contentDescription = "Delete",
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(22.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker() {
    var showPopup by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    val calendar = Calendar.getInstance()

    //calendar colors
    val datePickerColors = DatePickerDefaults.colors(
        containerColor = Color.White,
        titleContentColor = Color.Black,
        headlineContentColor = Color.Gray,
        weekdayContentColor = Color(133, 188, 60),
        selectedDayContentColor = Color.White,
        selectedDayContainerColor = Color(83, 111, 46),
        todayContentColor = Color.Black,
        todayDateBorderColor = Color(125, 191, 39)
    )

    Box {
        IconButton(onClick = { showPopup = true }) {
            Icon(
                painter = painterResource(id = R.drawable.papyruscalendaricon),
                contentDescription = null,
                modifier = Modifier.size(23.dp),
                tint = Color(57, 69, 40)
            )
        }
    }

    if (showPopup) {
        DatePickerDialog(
            onDismissRequest = { showPopup = false },
            confirmButton = {
                TextButton(onClick = { showPopup = false }) {
                    Text("OK")
                }
            }
        ) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = calendar.timeInMillis
            )

            DatePicker(
                state = datePickerState,
                showModeToggle = true,
                colors = datePickerColors
            )
            LaunchedEffect(datePickerState.selectedDateMillis) {
                datePickerState.selectedDateMillis?.let { millis ->
                    calendar.timeInMillis = millis
                    selectedDate = "${calendar.get(Calendar.DAY_OF_MONTH)}/" +
                            "${calendar.get(Calendar.MONTH) + 1}/" +
                            "${calendar.get(Calendar.YEAR)}"
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ViewAddListScreen() {
    val navController = rememberNavController()
    AddListScreen(navController = navController)
}
