package com.example.papyrus

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashArtScreen(navController: NavController) {
    var alpha by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        alpha = 1f
        delay(2000)
        navController.navigate("home") { popUpTo("splashScreen") { inclusive = true } }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.White, Color(177, 205, 136)),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.splashartcheck2),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .scale(1.4f)
                .alpha(0.1f)
                .graphicsLayer { translationX = 100f }
                .align(Alignment.BottomCenter)
        )

        val animatedAlpha by animateFloatAsState(
            targetValue = alpha,
            animationSpec = tween(durationMillis = 1000)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logosplashscreen),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .graphicsLayer {
                        translationX = 50f
                        this.alpha = animatedAlpha
                    }
            )
        }
    }
}





