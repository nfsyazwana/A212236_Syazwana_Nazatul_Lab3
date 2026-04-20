package com.example.a212236_syazwana_nazatul_lab4

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a212236_syazwana_nazatul_lab4.ui.theme.*

@Composable
fun PreviewScreen(
    submission: Submission,
    onConfirmButtonClicked: () -> Unit,
    onEditButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pointsEarned = if (submission.actionType == "Recycle") {
        (10 * submission.quantity).toInt()
    } else {
        (15 * submission.quantity).toInt()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Submission Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = SoftRoundShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    DetailRow("Item", submission.itemCategory, "📦")
                    DetailRow("Quantity", "${submission.quantity} kg", "⚖️")
                    DetailRow("Action", submission.actionType, if (submission.actionType == "Recycle") "♻️" else "🎁")
                    DetailRow("Center", submission.locationName, "📍")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Points Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = SoftRoundShape,
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🌟 Points to Earn",
                        style = Typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "+$pointsEarned",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF9800)
                    )


                    Text(
                        text = "eco-points",
                        style = Typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onEditButtonClicked,
                    modifier = Modifier.weight(1f),
                    shape = buttonShape,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text("Edit")
                }

                Button(
                    onClick = onConfirmButtonClicked,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = fixedPrimary,
                        contentColor = Color.White
                    ),
                    shape = buttonShape
                ) {
                    Text("Confirm")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = onCancelButtonClicked,
                modifier = Modifier.fillMaxWidth(),
                shape = buttonShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Cancel")
            }
        }
}

@Composable
fun DetailRow(label: String, value: String, icon: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(icon, fontSize = 20.sp)
            Text(
                text = label,
                style = Typography.bodyMedium,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
        Text(
            text = value,
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
