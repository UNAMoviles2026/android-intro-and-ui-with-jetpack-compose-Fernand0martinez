package com.moviles.unaroom.ui.screens.classrooms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moviles.unaroom.R
import com.moviles.unaroom.data.Classroom
import com.moviles.unaroom.ui.components.AppBottomBar
import com.moviles.unaroom.ui.components.ClassroomCard
import com.moviles.unaroom.ui.theme.AppBackground
import com.moviles.unaroom.ui.theme.AppNavUnselected
import com.moviles.unaroom.ui.theme.AppPrimary
import com.moviles.unaroom.ui.theme.AppSecondaryText

@Composable
private fun navigationBarItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = AppPrimary,
    selectedTextColor = AppPrimary,
    unselectedIconColor = AppNavUnselected,
    unselectedTextColor = AppNavUnselected,
    indicatorColor = AppBackground
)

private val mockClassrooms = listOf(
    Classroom(name = "Aula A101", capacity = 30, location = "Building 1"),
    Classroom(name = "Aula B205", capacity = 25, location = "Building 2"),
    Classroom(name = "Lecture Hall 101", capacity = 150, location = "Building 3"),
    Classroom(name = "Aula C310", capacity = 24, location = "Building 1"),
    Classroom(name = "Meeting Room 201", capacity = 12, location = "Building 2")
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassroomsScreen(
    modifier: Modifier = Modifier,
    classrooms: List<Classroom> = mockClassrooms,
    successMessage: String? = null,
    onSuccessMessageShown: () -> Unit = {},
    onAddClassroomClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val navBarItemColors = navigationBarItemColors()

    LaunchedEffect(successMessage) {
        successMessage?.let {
            snackbarHostState.showSnackbar(message = it)
            onSuccessMessageShown()
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.available_classrooms),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                actions = {
                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Logout,
                            contentDescription = stringResource(R.string.logout),
                            tint = AppPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClassroomClick,
                containerColor = AppPrimary,
                contentColor = AppBackground
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.add_classroom)
                )
            }
        },
        bottomBar = {
            AppBottomBar(navigationBarItemColors = { navBarItemColors })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                Snackbar(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    snackbarData = snackbarData,
                    containerColor = AppPrimary,
                    contentColor = AppBackground
                )
            }
        }
    ) { innerPadding ->
        if (classrooms.isEmpty()) {
            EmptyClassroomsContent(modifier = Modifier.padding(innerPadding))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(
                    start = 14.dp,
                    end = 14.dp,
                    top = 12.dp,
                    bottom = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(classrooms, key = { it.name }) { classroom ->
                    ClassroomCard(classroom = classroom)
                }
            }
        }
    }
}

@Composable
private fun EmptyClassroomsContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_classrooms_available),
            color = AppSecondaryText,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClassroomsScreenPreview() {
    ClassroomsScreen()
}

