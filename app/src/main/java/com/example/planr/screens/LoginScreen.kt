package com.example.planr.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.planr.R
import com.example.planr.ui.theme.PlanrTheme
import com.example.planr.ui.theme.Purple80


//@Composable
//fun LoginScreen (
//    onClick: () -> Unit,
//    onSignUpClick: () -> Unit,
//    onForgotClick: () -> Unit,
//){
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//    ){
//        Text(
//            text = "LOGIN",
//            modifier = Modifier.clickable { onClick() },
//            fontWeight = FontWeight.Bold
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(
//            text = "Sign UP",
//            modifier = Modifier.clickable { onSignUpClick() },
//            fontWeight = FontWeight.Bold
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(
//            text = "Forgot Password?",
//            modifier = Modifier.clickable { onForgotClick() },
//            fontWeight = FontWeight.Bold
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun LoginScreen(navController: NavHostController,
                onClick: () -> Unit,
                onSignUpClick: () -> Unit,
                onForgotClick: () -> Unit,

) {
PlanrTheme {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center

        ) {

            Text(
                text = "Don't have an account?  ",
//                    modifier = Modifier.padding(20.dp), // Add any desired modifiers here
                color = Color.Black,
                fontSize = 16.sp, // Customize the font size
                fontWeight = FontWeight.Bold,
            )

            ClickableText(
                text = AnnotatedString("Sign up here"),
//                    modifier = Modifier.padding(20.dp),
                onClick = { onSignUpClick() },
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Default,
                    textDecoration = TextDecoration.Underline,
                    color = Purple80
                )
            )
        }


    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(70.dp),
                text = "Welcome!",
                style = TextStyle(fontSize = 40.sp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(0.dp))


        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )
    {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            val email = remember { mutableStateOf(TextFieldValue()) }
            val password = remember { mutableStateOf(TextFieldValue()) }

            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.clock_animation))
            Box (modifier = Modifier.size(300.dp)){
                LottieAnimation(
                    modifier = Modifier
                        .fillMaxSize()
                        .size(100.dp)
                        .padding(10.dp),
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Email Address") },
                value = email.value,
                onValueChange = { email.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = "Password") },
                value = password.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { password.value = it })

            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(80.dp),
                    modifier = Modifier.clickable { onClick() }
                        .fillMaxWidth()
                        .height(50.dp)

                ) {
                    Text(text = "Login")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            ClickableText(
                modifier = Modifier.clickable { onForgotClick() },

                text = AnnotatedString("Forgot password?"),
                onClick = { },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    textDecoration = TextDecoration.Underline
                )
            )
        }
    }

}

}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//@Preview
//fun LoginScreenPreview() {
//    val navController = rememberNavController()
//
//    LoginScreen(
//        navController = navController,
//        onClick = { /* Define action for the onClick event */ },
//        onSignUpClick = { /* Define action for the onSignUpClick event */ },
//        onForgotClick = { /* Define action for the onForgotClick event */ },
//    )
//}
