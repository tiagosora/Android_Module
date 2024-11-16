package com.example.watchlistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.watchlistapp.ui.theme.WatchListAppTheme
import com.example.watchlistapp.ui.screens.AddScreen
import com.example.watchlistapp.ui.screens.ListScreen
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatchListAppTheme {
                WatchListApp()
            }
        }
    }
}

@Composable
fun WatchListApp() {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = { coroutineScope.launch { pagerState.scrollToPage(0) } }
                ) {
                    Text("Add")
                }
                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = { coroutineScope.launch { pagerState.scrollToPage(1) } }
                ) {
                    Text("List")
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            count = 2,
            state = pagerState,
            modifier = Modifier.padding(innerPadding)
        ) { page ->
            when (page) {
                0 -> AddScreen()
                1 -> ListScreen()
            }
        }
    }
}