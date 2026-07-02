package com.example.backhome.presentation.form

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.backhome.util.Result

@Composable
fun PersonForm(
    navController: NavController,
    viewModel: FormViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val formState by viewModel.formState.collectAsState()

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var fatherName by remember { mutableStateOf("") }
    var place by remember { mutableStateOf("") }
    var district by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var myAddress by remember { mutableStateOf("") }
    var myPhoneNumber by remember { mutableStateOf("") }

    var personType by remember { mutableStateOf("Missing") }

    LaunchedEffect(formState) {

        when (formState) {

            is Result.Success -> {

                Toast.makeText(
                    context,
                    "Data Uploaded Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                // Clear Form
                name = ""
                age = ""
                fatherName = ""
                place = ""
                district = ""
                state = ""
                myAddress = ""
                myPhoneNumber = ""
                description = ""
                personType = "Missing"

                // Optional Navigation
                // navController.popBackStack()
            }

            is Result.Failure -> {

                Toast.makeText(
                    context,
                    (formState as Result.Failure).message,
                    Toast.LENGTH_LONG
                ).show()
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Person Details",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = fatherName,
            onValueChange = { fatherName = it },
            label = { Text("Father Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = place,
            onValueChange = { place = it },
            label = { Text("Place") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = district,
            onValueChange = { district = it },
            label = { Text("District") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state,
            onValueChange = { state = it },
            label = { Text("State") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = myAddress,
            onValueChange = { myAddress = it },
            label = { Text("Your Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Person Type",
            style = MaterialTheme.typography.titleMedium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = personType == "Missing",
                    onClick = {
                        personType = "Missing"
                    }
                )

                Text("Missing")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                RadioButton(
                    selected = personType == "Found",
                    onClick = {
                        personType = "Found"
                    }
                )

                Text("Found")
            }
        }

        OutlinedTextField(
            value = myPhoneNumber,
            onValueChange = { myPhoneNumber = it },
            label = { Text("Your Phone Number") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone
            ),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            minLines = 4,
            maxLines = 6,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {

                viewModel.inputform(
                    name = name,
                    age = age,
                    fatherName = fatherName,
                    place = place,
                    district = district,
                    state = state,
                    youraddress = myAddress,
                    yourphonenumber = myPhoneNumber,
                    description = description,
                    type = personType
                )

            },
            modifier = Modifier.fillMaxWidth(),
            enabled = formState !is Result.Loading
        ) {

            if (formState is Result.Loading) {

                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )

            } else {

                Text("Submit")

            }
        }
    }
}