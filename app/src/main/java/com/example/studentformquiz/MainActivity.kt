package com.example.studentformquiz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Cream.toArgb()
        window.navigationBarColor = Cream.toArgb()
        setContent {
            MaterialTheme(colorScheme = FormColorScheme) {
                StudentFormScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentFormScreen() {
    val context = LocalContext.current

    var nameState by remember { mutableStateOf("") }
    var surnameState by remember { mutableStateOf("") }
    var emailState by remember { mutableStateOf("") }
    var dateState by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("") }
    var isAgreed by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val genres = listOf("როკი", "კლასიკა", "ჯაზი")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(22.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            HeaderBlock()

            FormInput(
                title = "სახელი",
                value = nameState,
                placeholder = "სახელი",
                onChange = { nameState = it }
            )

            FormInput(
                title = "გვარი",
                value = surnameState,
                placeholder = "გვარი",
                onChange = { surnameState = it }
            )

            FormInput(
                title = "იმეილი",
                value = emailState,
                placeholder = "email@example.com",
                keyboardType = KeyboardType.Email,
                onChange = { emailState = it }
            )

            FormInput(
                title = "თარიღი",
                value = dateState,
                placeholder = "აირჩიე თარიღი",
                readOnly = true,
                onChange = {},
                onClick = { showDatePicker = true }
            )

            Text(
                text = "აირჩიეთ საყვარელი ჟანრი",
                color = Ink,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 24.dp, bottomEnd = 24.dp))
                    .background(Color.White)
                    .border(
                        width = 1.dp,
                        color = SoftGreen,
                        shape = RoundedCornerShape(topStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .padding(vertical = 8.dp)
            ) {
                genres.forEach { genre ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedOption == genre,
                            onClick = { selectedOption = genre },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Green,
                                unselectedColor = Muted
                            )
                        )
                        Text(text = genre, color = Ink, fontSize = 15.sp)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 18.dp, bottomEnd = 18.dp))
                    .background(PaleGreen)
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ვეთანხმები წესებს და პირობებს",
                    modifier = Modifier.weight(1f),
                    color = Ink,
                    fontSize = 14.sp
                )
                Switch(
                    checked = isAgreed,
                    onCheckedChange = { isAgreed = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Green,
                        uncheckedThumbColor = Muted,
                        uncheckedTrackColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val isFormFilled = nameState.isNotBlank()
                            && surnameState.isNotBlank()
                            && emailState.isNotBlank()
                            && dateState.isNotBlank()
                            && selectedOption.isNotBlank()
                            && isAgreed

                    val message = if (isFormFilled) {
                        "მონაცემები გაიგზავნა!"
                    } else {
                        "შეავსეთ ყველა ველი!"
                    }
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(topStart = 20.dp, bottomEnd = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Green)
            ) {
                Text(text = "Submit", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            dateState = formatDate(millis)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("არჩევა", color = Green)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("გაუქმება", color = Muted)
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun HeaderBlock() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 34.dp, bottomEnd = 34.dp))
            .background(DarkGreen)
            .padding(22.dp)
    ) {
        Text(
            text = "Student Form",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
private fun FormInput(
    title: String,
    value: String,
    placeholder: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            color = Ink,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(6.dp))
        Box {
            OutlinedTextField(
                value = value,
                onValueChange = onChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(placeholder, color = Muted) },
                readOnly = readOnly,
                singleLine = true,
                shape = RoundedCornerShape(topStart = 18.dp, bottomEnd = 18.dp),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Green,
                    unfocusedBorderColor = SoftGreen,
                    focusedTextColor = Ink,
                    unfocusedTextColor = Ink,
                    cursorColor = Green,
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )
            if (onClick != null) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .padding(1.dp)
                        .clip(RoundedCornerShape(topStart = 18.dp, bottomEnd = 18.dp))
                        .background(Color.Transparent)
                        .noRippleClick(onClick)
                )
            }
        }
    }
}

private fun formatDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

private fun Modifier.noRippleClick(onClick: () -> Unit): Modifier {
    return composed {
        clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            onClick = onClick
        )
    }
}

private val Cream = Color(0xFFF4F1EA)
private val Green = Color(0xFF2F7D57)
private val DarkGreen = Color(0xFF163B2B)
private val PaleGreen = Color(0xFFDDEDE2)
private val SoftGreen = Color(0xFFB9D3C3)
private val Ink = Color(0xFF20231F)
private val Muted = Color(0xFF7D847C)

private val FormColorScheme = lightColorScheme(
    primary = Green,
    secondary = DarkGreen,
    background = Cream,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Ink,
    onSurface = Ink
)

@Preview(showBackground = true)
@Composable
private fun StudentFormPreview() {
    MaterialTheme(colorScheme = FormColorScheme) {
        StudentFormScreen()
    }
}
