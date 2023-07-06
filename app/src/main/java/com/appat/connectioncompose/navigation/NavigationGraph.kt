package com.appat.connectioncompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.appat.connectioncompose.PhotoGridImageView
import com.appat.connectioncompose.PhotosGrid

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(navController, startDestination = "main") {
        composable("main") {
            PhotosGrid(navController)
        }
        composable("image_view/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })) { backstackEntry ->
            backstackEntry.arguments?.getInt("id")?.let {
                PhotoGridImageView(it)
            }
        }
    }
}