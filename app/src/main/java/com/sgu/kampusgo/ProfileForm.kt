// guided by Android Basics codelab: Intro to state in Compose.
package com.sgu.kampusgo

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sgu.kampusgo.ui.theme.KampusGoTheme

private val majors = listOf(
    "Informatics",
    "Information Systems",
    "Computer Engineering",
)

@Composable
fun LabeledField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    supportingText: String? = null,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        isError = isError,
        supportingText = {
            if (supportingText != null) Text(supportingText)
        },
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
fun ProfileForm(
    name: String,
    onNameChange: (String) -> Unit,
    npm: String,
    onNpmChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    major: String,
    onMajorChange: (String) -> Unit,
    angkatan: String,
    onAngkatanChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    val emailError = email.isNotEmpty() && !email.contains("@")
    val valid = name.isNotBlank() && email.contains("@")
    val context = LocalContext.current

    Column(modifier = modifier) {
        Text("KampusGo")
        LabeledField(
            value = name,
            onValueChange = onNameChange,
            label = "Name"
        )
        LabeledField(
            value = npm,
            onValueChange = onNpmChange,
            label = "NPM"
        )
        LabeledField(
            value = email,
            onValueChange = onEmailChange,
            label = "Email",
            isError = emailError,
            supportingText = if (emailError) "Email needs @" else null,
        )
        Text("Major", modifier = Modifier.padding(top = 12.dp))
        majors.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = major == option, onClick = { onMajorChange(option) })
                Text(option)
            }
        }
        LabeledField(
            value = angkatan,
            onValueChange = onAngkatanChange,
            label = "Angkatan"
        )
        Button(
            onClick = {
                Toast.makeText(context, "Saved ($major)", Toast.LENGTH_SHORT).show()
            },
            enabled = valid,
            modifier = Modifier.padding(top = 16.dp),
        ) {
            Text("Save")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileFormPreview() {
    var name by remember { mutableStateOf("") }
    var npm by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var major by remember { mutableStateOf(listOf(R.array.majors.toString()).first()) }
    var angkatan by remember { mutableStateOf("") }

    KampusGoTheme {
        ProfileForm(
            name = name,
            onNameChange = { name = it },
            npm = npm,
            onNpmChange = { npm = it},
            email = email,
            onEmailChange = { email = it },
            major = major,
            onMajorChange = { major = it },
            angkatan = angkatan,
            onAngkatanChange = { angkatan = it },
            modifier = Modifier
                .padding(24.dp)
        )
    }
}