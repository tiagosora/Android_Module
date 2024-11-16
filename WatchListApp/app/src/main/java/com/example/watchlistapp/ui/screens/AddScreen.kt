package com.example.watchlistapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.watchlistapp.viewmodel.WatchListViewModel

@Composable
fun AddScreen(viewModel: WatchListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var textFieldState by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = textFieldState,
            onValueChange = { textFieldState = it },
            label = { Text("Add Item") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.addItem(textFieldState.text)
                textFieldState = TextFieldValue("")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add to List")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Items in list: ${viewModel.watchList.size}")
    }
}
