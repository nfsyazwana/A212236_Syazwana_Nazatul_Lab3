package com.example.a212236_syazwana_nazatul_lab4

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.a212236_syazwana_nazatul_lab4.data.locations
import com.example.a212236_syazwana_nazatul_lab4.data.LocationData
import com.example.a212236_syazwana_nazatul_lab4.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubmissionFormScreen(
    onNextButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    viewModel: RecyclingViewModel,
    modifier: Modifier = Modifier
) {
    val currentSubmission by viewModel.currentSubmission.collectAsState()
    
    var quantity by remember { mutableStateOf("") }
    var selectedAction by remember { mutableStateOf("") }
    var selectedLocation by remember { mutableStateOf<LocationData?>(null) }
    var isLocationExpanded by remember { mutableStateOf(false) }

    val isFormValid = currentSubmission.itemCategory.isNotBlank() &&
            quantity.isNotBlank() &&
            selectedAction.isNotBlank() &&
            selectedLocation != null

    // Removed the Box with Background Image and Overlay because it's now in RecyclingApp.kt
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "♻️ Submit New Item",
            style = Typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Category Display (No longer a dropdown)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = GentleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Item Category",
                    style = Typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = currentSubmission.itemCategory,
                    style = Typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quantity Input
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = GentleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.9f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Quantity (kg) *",
                    style = Typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("e.g., 2.5", color = Color.White.copy(alpha = 0.5f)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    shape = SubtleShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
                        focusedPlaceholderColor = Color.White.copy(alpha = 0.5f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Action Type
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = GentleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.9f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Action Type *",
                    style = Typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f).height(60.dp),
                        shape = GentleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedAction == "Recycle")
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.9f)
                        ),
                        onClick = { selectedAction = "Recycle" }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("♻️", fontSize = 24.sp)
                            Text(
                                "Recycle",
                                color = if (selectedAction == "Recycle") Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f).height(60.dp),
                        shape = GentleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedAction == "Donate")
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.surfaceContainerHighest.copy(alpha = 0.9f)
                        ),
                        onClick = { selectedAction = "Donate" }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("🎁", fontSize = 24.sp)
                            Text(
                                "Donate",
                                color = if (selectedAction == "Donate") Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Location Dropdown
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = GentleShape,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.9f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Select Recycling Center *",
                    style = Typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = isLocationExpanded,
                    onExpandedChange = { isLocationExpanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedLocation?.name ?: "",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isLocationExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor(),
                        placeholder = { Text("Choose recycling center", color = Color.White.copy(alpha = 0.5f)) },
                        shape = SubtleShape,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedTextColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
                            focusedPlaceholderColor = Color.White.copy(alpha = 0.5f)
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = isLocationExpanded,
                        onDismissRequest = { isLocationExpanded = false }
                    ) {
                        locations.forEach { location ->
                            DropdownMenuItem(
                                text = {
                                    Column {
                                        Text("${location.icon} ${location.name}")
                                        Text(
                                            location.address.take(40) + "...",
                                            fontSize = 10.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                },
                                onClick = {
                                    selectedLocation = location
                                    isLocationExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onCancelButtonClicked,
                modifier = Modifier.weight(1f),
                shape = buttonShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Cancel")
            }

            Button(
                onClick = {
                    if (isFormValid && selectedLocation != null) {
                        val submission = Submission(
                            itemCategory = currentSubmission.itemCategory,
                            quantity = quantity.toDoubleOrNull() ?: 0.0,
                            actionType = selectedAction,
                            locationName = selectedLocation!!.name,
                            locationAddress = selectedLocation!!.address
                        )
                        viewModel.updateCurrentSubmission(submission)
                        onNextButtonClicked()
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = isFormValid,
                colors = ButtonDefaults.buttonColors(
                    containerColor = fixedPrimary,
                    contentColor = Color.White,
                    disabledContainerColor = fixedPrimary.copy(alpha = 0.5f),
                    disabledContentColor = Color.White.copy(alpha = 0.5f)
                ),
                shape = buttonShape
            ) {
                Text("Preview →")
            }
        }
    }
}