package com.example.warbyparkerandroid.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignIn() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxHeight(0.98f)
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .background(Color(0xFFf8f7f9)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            "Sign in",
            textAlign = TextAlign.Center,
            fontSize = 26.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Serif
        )
        Text(buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Serif
                )
            ) {
                append("Don't have an account? ")

            }
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colors.primaryVariant,
                    fontWeight = FontWeight.SemiBold
                )
            ) {
                append("Create an account")
            }
        }, fontSize = 20.sp, modifier = Modifier.padding(bottom = 20.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            placeholder = {
                Text(
                    "Email address",
                    color = Color.Gray,
                    fontSize = 22.sp
                )
            },
            modifier = Modifier
                .border(
                    1.dp,
                    color = Color.LightGray,
                    shape = MaterialTheme.shapes.medium,
                )
                .height(65.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = 22.sp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            placeholder = {
                Text(
                    "Password",
                    color = Color.Gray,
                    fontSize = 22.sp
                )
            },
            modifier = Modifier
                .border(
                    1.dp,
                    color = Color.LightGray,
                    shape = MaterialTheme.shapes.medium,
                )
                .height(65.dp)
                .fillMaxHeight()
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(fontSize = 22.sp)
        )

        val noobCredReqs = email.length > 1 && password.length > 2
        Button(
            onClick = { },
            modifier = Modifier
                .height(80.dp)
                .padding(top = 20.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primaryVariant,
                disabledBackgroundColor = MaterialTheme.colors.primaryVariant,
                disabledContentColor = Color.Red
            ),
            shape = MaterialTheme.shapes.large,
            enabled = noobCredReqs
        ) {
            Text(
                "Sign in",
                color = if (noobCredReqs) Color.White else Color.LightGray,
                fontSize = 18.sp
            )
        }
        Text(
            "Forgot password?",
            style = MaterialTheme.typography.button,
            color = MaterialTheme.colors.primaryVariant,
            fontSize = 18.sp
        )
    }
}