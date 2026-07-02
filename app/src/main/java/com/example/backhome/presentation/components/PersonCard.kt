package com.example.backhome.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun PersonCard(
    imageUrl: String,
    name: String,
    age: String,
    fatherName: String,
    description: String,
    place: String,
    address: String,
    district: String,
    state: String,
    contact:String,
    type:String,

    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF3E0)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            AsyncImage(
                model = imageUrl,
                contentDescription = "Person Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFFE3F2FD),
                                Color.White
                            )
                        )
                    )
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = name,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1)
                )

                Text(
                    text = "Age: $age | Father: $fatherName",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )


                Text(
                    text = "Place: ${if (place.isNotBlank()) place else "Unknown"}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Text(
                    text = "District: ${district}",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "State: ${state}",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "Type: ${type}",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Your Contact: ${contact}",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Your Address: ${address}",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text ="Description: ${description}",
                    fontSize = 14.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}



//PersonCard(
//imageUrl ="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsuoQ7vB5ArqFQUtjamvtRnM5bD9mHegSmJg&s",
//name = "Yalina",
//age = "45",
//fatherName ="unknown",
//description = "Nothing",
//place ="Lucknow",
//address = "skdjf",
//modifier = Modifier
//)