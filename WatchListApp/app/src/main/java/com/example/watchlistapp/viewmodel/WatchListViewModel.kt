package com.example.watchlistapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.watchlistapp.data.WatchItem

class WatchListViewModel : ViewModel() {
    // Mutable state for the watch list
    val watchList = mutableStateListOf<WatchItem>()

    // TODO: Remove this block of code
    // Add 100 more mocked items to the watch list
    init {
        for (i in 1..100) {
            watchList.add(WatchItem("Item $i"))
        }
    }

    // Add a new item
    fun addItem(title: String) {
        if (title.isNotBlank()) {
            watchList.add(WatchItem(title))
        }
    }

    // Remove an item
    fun removeItem(item: WatchItem) {
        watchList.remove(item)
    }

    // Mark an item as watched/unwatched
    fun toggleWatched(item: WatchItem) {
        val index = watchList.indexOf(item)
        if (index != -1) {
            watchList[index] = watchList[index].copy(isWatched = !watchList[index].isWatched)
        }
    }
}
