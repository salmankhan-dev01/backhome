package com.example.backhome.presentation.ProfileScreen

import androidx.compose.foundation.lazy.items
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.backhome.presentation.components.PersonCard
import com.example.backhome.util.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyRegisterPerson(
    navController: NavController,
    viewModel: MyRegisterPersonViewModel = hiltViewModel()
) {

    val personState by viewModel.getMyPersonState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMyPersons()
    }

    var search by remember {
        mutableStateOf("")
    }

    var selectedFilter by remember {
        mutableStateOf("All")
    }

    Scaffold(

        topBar = {

            Column {

                TopAppBar(
                    title = {
                        Text("My Register Person")
                    },

                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                )

                OutlinedTextField(
                    value = search,
                    onValueChange = {
                        search = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    placeholder = {
                        Text("Search...")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(50)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    listOf("All", "Missing", "Found").forEach { filter ->

                        FilterChip(
                            selected = selectedFilter == filter,
                            onClick = {
                                selectedFilter = filter
                            },
                            label = {
                                Text(filter)
                            }
                        )

                    }

                }
            }
        }

    ) { padding ->

        when (val state = personState) {

            Result.Idle -> {

                Text(
                    text = "No Data",
                    modifier = Modifier.padding(16.dp)
                )

            }

            Result.Loading -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }


            }

            is Result.Failure -> {

                Text(
                    text = state.message,
                    modifier = Modifier.padding(16.dp)
                )
                Log.d("Firestore", "fail.................")

            }

            is Result.Success -> {

                val queryAge = search.toIntOrNull()

                val filteredPersons = state.data.filter { person ->

                    val matchesSearch =
                        search.isBlank() ||

                                fuzzyMatch(search, person.name) ||

                                fuzzyMatch(search, person.fatherName) ||

                                fuzzyMatch(search, person.place) ||

                                fuzzyMatch(search, person.district) ||

                                fuzzyMatch(search, person.state) ||

                                (queryAge != null &&
                                        person.age.toIntOrNull()?.let {
                                            kotlin.math.abs(it - queryAge) <= 5
                                        } == true)

                    val matchesFilter = when (selectedFilter) {
                        "All" -> true
                        "Missing" -> person.type.equals("Missing", true)
                        "Found" -> person.type.equals("Found", true)
                        else -> true
                    }

                    matchesSearch && matchesFilter
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    items(filteredPersons) { person ->

                        PersonCard(
                            imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTsuoQ7vB5ArqFQUtjamvtRnM5bD9mHegSmJg&s",
                            name = person.name,
                            age = person.age,
                            fatherName = person.fatherName,
                            description = person.description,
                            place = person.place,
                            address = person.youraddress,
                            district = person.district,
                            state = person.state,
                            contact = person.yourphonenumber,
                            type = person.type,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }        }

    }

}


fun levenshteinDistance(a: String, b: String): Int {
    val dp = Array(a.length + 1) { IntArray(b.length + 1) }

    for (i in 0..a.length) dp[i][0] = i
    for (j in 0..b.length) dp[0][j] = j

    for (i in 1..a.length) {
        for (j in 1..b.length) {
            val cost = if (a[i - 1] == b[j - 1]) 0 else 1
            dp[i][j] = minOf(
                dp[i - 1][j] + 1,
                dp[i][j - 1] + 1,
                dp[i - 1][j - 1] + cost
            )
        }
    }
    return dp[a.length][b.length]
}

fun fuzzyMatch(query: String, text: String, threshold: Int = 2): Boolean {
    val q = query.lowercase().trim()
    val t = text.lowercase().trim()

    return t.contains(q) || levenshteinDistance(q, t) <= threshold
}