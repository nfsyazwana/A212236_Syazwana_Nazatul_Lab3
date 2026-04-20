package com.example.a212236_syazwana_nazatul_lab4

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a212236_syazwana_nazatul_lab4.ui.theme.Typography
import androidx.compose.ui.unit.dp

enum class RecyclingScreen(@StringRes val title: Int) {
    Main(R.string.app_name),
    Form(R.string.submit_recycling),
    Preview(R.string.preview_submission),
    Notifications(R.string.notifications),
    Account(R.string.account)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecyclingAppBar(
    currentScreen: RecyclingScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { 
            Text(
                stringResource(currentScreen.title),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            ) 
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun RecyclingApp(
    viewModel: RecyclingViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = RecyclingScreen.valueOf(
        backStackEntry?.destination?.route ?: RecyclingScreen.Main.name
    )

    val userStats by viewModel.userStats.collectAsState()
    val currentSubmission by viewModel.currentSubmission.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Unified Background Image
        Image(
            painter = painterResource(id = R.drawable.saveearth),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        
        // 2. Unified Semi-transparent Overlay (covers everything including status/nav bars)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.75f))
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                // The Top Bar is now transparent, showing the unified background/overlay
                RecyclingAppBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() }
                )
            },
            bottomBar = {
                CustomNavigationBar()
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = RecyclingScreen.Main.name,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                composable(route = RecyclingScreen.Main.name) {
                    MainScreen(
                        userStats = userStats,
                        onNavigateToForm = { category ->
                            viewModel.updateCurrentSubmission(currentSubmission.copy(itemCategory = category))
                            navController.navigate(RecyclingScreen.Form.name)
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable(route = RecyclingScreen.Form.name) {
                    SubmissionFormScreen(
                        onNextButtonClicked = {
                            navController.navigate(RecyclingScreen.Preview.name)
                        },
                        onCancelButtonClicked = {
                            viewModel.resetSubmission()
                            navController.popBackStack()
                        },
                        viewModel = viewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable(route = RecyclingScreen.Preview.name) {
                    PreviewScreen(
                        submission = currentSubmission,
                        onConfirmButtonClicked = {
                            viewModel.saveSubmission(currentSubmission)
                            navController.popBackStack(RecyclingScreen.Main.name, inclusive = false)
                        },
                        onEditButtonClicked = {
                            navController.popBackStack()
                        },
                        onCancelButtonClicked = {
                            viewModel.resetSubmission()
                            navController.navigate(RecyclingScreen.Main.name) {
                                popUpTo(RecyclingScreen.Main.name) { inclusive = true }
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                composable(route = RecyclingScreen.Notifications.name) {
                    // Placeholder for Notifications Screen
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Notifications Screen", style = MaterialTheme.typography.headlineMedium)
                    }
                }

                composable(route = RecyclingScreen.Account.name) {
                    // Placeholder for Account Screen
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Account Screen", style = MaterialTheme.typography.headlineMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun CustomNavigationBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(com.example.a212236_syazwana_nazatul_lab4.ui.theme.fixedPrimary.copy(alpha = 0.9f))
            .windowInsetsPadding(WindowInsets.navigationBars)
            .height(70.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.home),
                contentDescription = "Home",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
            Text(
                text = "Home",
                style = Typography.labelSmall,
                color = Color.White.copy(alpha = 0.9f)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.notification),
                contentDescription = "Notification",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
            Text(
                text = "Notification",
                style = Typography.labelSmall,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.account),
                contentDescription = "Account",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
            Text(
                text = "Account",
                style = Typography.labelSmall,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}
