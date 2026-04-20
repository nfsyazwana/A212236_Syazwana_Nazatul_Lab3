package com.example.a212236_syazwana_nazatul_lab4

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a212236_syazwana_nazatul_lab4.data.locations
import com.example.a212236_syazwana_nazatul_lab4.data.LocationData
import com.example.a212236_syazwana_nazatul_lab4.ui.theme.*

@Composable
fun MainScreen(
    userStats: UserStats,
    onNavigateToForm: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var locationInput by remember { mutableStateOf("") }
    var searchedLocation by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    // Removed the Box with Background Image and Overlay because it's now in RecyclingApp.kt
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // Welcome Section
        WelcomeSection()

        // Point Balance
        PointBalance(totalPoints = userStats.totalPoints)

        // Points Breakdown (Recycled vs Donated)
        PointsBreakdown(
            recycledCount = userStats.recycledItems,
            donatedCount = userStats.donatedItems
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Recycle Categories
        RecycleCategories(onCategoryClick = onNavigateToForm)

        // Location Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "View Nearby Recycling Centre",
                style = Typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dimensionResource(R.dimen.padding_medium)),
                shape = SoftRoundShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.9f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(
                        text = "Find recycling centers near you",
                        style = Typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small))
                    )

                    OutlinedTextField(
                        value = locationInput,
                        onValueChange = { locationInput = it },
                        label = { Text("Enter location") },
                        placeholder = { Text("e.g., Kuala Lumpur, Selangor, 43650...") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = Typography.bodyLarge,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = SubtleShape
                    )

                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                    Button(
                        onClick = {
                            if (locationInput.isNotBlank()) {
                                searchedLocation = locationInput
                                isSubmitted = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = fixedPrimary
                        ),
                        shape = buttonShape
                    ) {
                        Text(
                            text = "Search Locations",
                            style = Typography.labelSmall,
                            color = Color.White
                        )
                    }

                    if (isSubmitted && searchedLocation.isNotBlank()) {
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.9f)
                            ),
                            shape = GentleShape
                        ) {
                            Column(
                                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                            ) {
                                Text(
                                    text = "📍 Searching for recycling centers near:",
                                    style = Typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                                Text(
                                    text = searchedLocation,
                                    style = Typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(R.dimen.padding_small))
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                locations.forEach { location ->
                    LocationCard(location = location)
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun WelcomeSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "👤", fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))

        Column {
            Text(
                text = "Welcome Back",
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Eco Warrior",
                style = Typography.displayLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun PointBalance(totalPoints: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium), vertical = dimensionResource(R.dimen.padding_small)),
        shape = SoftRoundShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Point Balance",
                    style = Typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                )
                Text(
                    text = totalPoints.toString(),
                    style = Typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
            ) {
                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f), buttonShape)
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium), vertical = dimensionResource(R.dimen.padding_small)),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🎁", fontSize = 14.sp)
                    Text(
                        text = "Reward",
                        style = Typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                Row(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f), buttonShape)
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium), vertical = dimensionResource(R.dimen.padding_small)),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "💰", fontSize = 14.sp)
                    Text(
                        text = "Cash-out",
                        style = Typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun PointsBreakdown(recycledCount: Int, donatedCount: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.recycle),
                contentDescription = "Recycled",
                modifier = Modifier.size(50.dp),
                tint = Color.Unspecified
            )
            Text(
                text = recycledCount.toString(),
                style = Typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Item",
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "RECYCLED",
                style = Typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = R.drawable.gift),
                contentDescription = "Donated",
                modifier = Modifier.size(50.dp),
                tint = Color.Unspecified
            )
            Text(
                text = donatedCount.toString(),
                style = Typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Item",
                style = Typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "DONATED",
                style = Typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun RecycleCategories(onCategoryClick: (String) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(R.dimen.padding_medium), vertical = dimensionResource(R.dimen.padding_small))
    ) {
        Text(
            text = "Select Item Type",
            style = Typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val categories = listOf("Clothes", "Glass", "Paper", "Bottles", "Newspaper", "Electronic", "Books", "Used Cooking Oil", "Inkjet Cartridge")
            categories.forEach { category ->
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(75.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f), GentleShape)
                        .clickable { onCategoryClick(category) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = category,
                        style = Typography.labelSmall,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LocationCard(location: LocationData, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .width(220.dp)
            .animateContentSize()
            .clickable { expanded = !expanded },
        shape = GentleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.9f)
        )
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = location.icon, fontSize = 24.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = location.name,
                    style = Typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Address:",
                    style = Typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = location.address,
                    style = Typography.labelSmall,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Status:",
                    style = Typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = location.openTime,
                    style = Typography.labelSmall,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}