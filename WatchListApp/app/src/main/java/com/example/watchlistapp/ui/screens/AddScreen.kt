package com.example.watchlistapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.watchlistapp.viewmodel.WatchListViewModel

@Composable
fun AddScreen(viewModel: WatchListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var textFieldState by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 64.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text("You have ", style = MaterialTheme.typography.bodyMedium)
            Text(
                "${viewModel.watchList.size}",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
            )
            Text(" items in your watch list", style = MaterialTheme.typography.bodyMedium)
        }

        Row {
            Text("You have watched ", style = MaterialTheme.typography.bodyMedium)
            Text(
                "${viewModel.watchList.count { it.isWatched }}",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
            )
            Text(" items", style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(48.dp))
        TextField(
            value = textFieldState,
            onValueChange = { textFieldState = it },
            label = { Text("New Item") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.addItem(textFieldState.text)
                textFieldState = TextFieldValue("")
            },
            modifier = Modifier.width(200.dp).fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Add to ")
                Text("Watch List")
            }
        }
    }
}
