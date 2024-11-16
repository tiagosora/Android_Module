package com.example.watchlistapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.watchlistapp.data.WatchItem
import com.example.watchlistapp.viewmodel.WatchListViewModel

@Composable
fun ListScreen(viewModel: WatchListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    var sortOrder by remember { mutableStateOf("No sorting") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(
            text = "Your Watch List",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth().wrapContentWidth()
        )

        DropdownMenu(sortOrder) { newSortOrder -> sortOrder = newSortOrder }

        Spacer(modifier = Modifier.height(16.dp))

        val sortedList = when (sortOrder) {
            "Sort by Title" -> viewModel.watchList.sortedBy { it.title }
            "Sort by State" -> viewModel.watchList.sortedBy { it.isWatched }
            else -> viewModel.watchList
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(sortedList) { item ->
                WatchListItem(
                    item = item,
                    onRemove = { viewModel.removeItem(item) },
                    onToggle = { viewModel.toggleWatched(item) }
                )
            }
        }
    }
}

@Composable
fun DropdownMenu(selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Row (Modifier.fillMaxWidth().wrapContentWidth()) {
        Box {
            Button(onClick = { expanded = true }) {
                Text(selectedOption)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text = { Text("No sorting") }, onClick = {
                    onOptionSelected("No sorting")
                    expanded = false
                })
                DropdownMenuItem(text = { Text("Sort by Title") }, onClick = {
                    onOptionSelected("Sort by Title")
                    expanded = false
                })
                DropdownMenuItem(text = { Text("Sort by State") }, onClick = {
                    onOptionSelected("Sort by State")
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun WatchListItem(
    item: WatchItem,
    onRemove: () -> Unit,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = item.title, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = if (item.isWatched) "Watched" else "Not Watched",
                color = if (item.isWatched) Color.Green else Color.Red
            )
        }
        Row {
            Checkbox(checked = item.isWatched, onCheckedChange = { onToggle() })
            IconButton(onClick = { onRemove() }) {
                Icon(Icons.Default.Delete, contentDescription = "Remove")
            }
        }
    }
}
