package com.example.watchlistapp.repository

import android.content.Context
import androidx.datastore.preferences.core.edit

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.watchlistapp.data.WatchItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("watch_list")

class WatchListRepository(private val context: Context) {

    private val gson = Gson()
    private val WATCH_LIST_KEY = stringPreferencesKey("watch_list_key")

    // Save watch list as JSON string
    suspend fun saveWatchList(watchList: List<WatchItem>) {
        val json = gson.toJson(watchList)
        context.dataStore.edit { preferences ->
            preferences[WATCH_LIST_KEY] = json
        }
    }

    // Load watch list as List<WatchItem>
    val watchListFlow: Flow<List<WatchItem>> = context.dataStore.data
        .map { preferences ->
            val json = preferences[WATCH_LIST_KEY] ?: return@map emptyList<WatchItem>()
            val type = object : TypeToken<List<WatchItem>>() {}.type
            gson.fromJson(json, type)
        }
}
