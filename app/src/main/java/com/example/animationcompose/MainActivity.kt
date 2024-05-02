package com.example.animationcompose

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animationcompose.ui.theme.AnimationComposeTheme
import kotlinx.coroutines.coroutineScope

val userImages = listOf(
    R.drawable.drawable1,
    R.drawable.drawable2,
    R.drawable.drawable3,
    R.drawable.drawable4,
    R.drawable.drawable5,
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

                // A surface container using the 'background' color from the theme
               Surface {
                   val configuration = LocalConfiguration.current
                   val screenHeight = configuration.screenHeightDp.dp
                   val screenWidth = configuration.screenWidthDp.dp
                   TransitionSampleScreen(
                       screenWidth, screenHeight
                   )
               }




        }
    }
}

@Composable
fun TransitionSampleScreen(
    screenWidth: Dp,
    screenHeight: Dp
) {
    var isHorizontal by remember {
        mutableStateOf(false)
    }
    var isHorizontalState by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isHorizontalState) {
        isHorizontal = isHorizontalState
    }
    val animationDuration = 300
    val easing = FastOutLinearInEasing
    val animationSpec = tween<Dp>(
        easing = easing,
        durationMillis = animationDuration
    )
 val animationSpec1=   tween<Float>(
        easing = easing,
        durationMillis = animationDuration
    )
    val offsetXStart = (screenWidth - 25.dp) / 2
    val offsetYStart = (screenHeight - 25.dp) / 2
    Box(
        modifier = Modifier.fillMaxSize(),

    ) {
        // Other content in the Box
        // LazyColumn {
        userImages.withIndex().forEach { (index, drawable) ->
            val offsetX = offsetXStart + if (isHorizontal) (index * 25).dp else 0.dp
            val offsetY = offsetYStart + if (isHorizontal) 0.dp else (index * 50).dp
            val lastElementVisibility =
                if (isHorizontal && index == userImages.lastIndex) 0f else 1f
            val targetCordX by animateDpAsState(
                targetValue = offsetX,
                animationSpec = animationSpec
            )

            val targetCordY by animateDpAsState(
                targetValue = offsetY,
                animationSpec = animationSpec
            )
            val alpha by animateFloatAsState(
                targetValue = lastElementVisibility,
                animationSpec = animationSpec1
            )
            val modifier = Modifier
                .size(50.dp)
                .absoluteOffset(
                    x = targetCordX,
                    y = targetCordY,
                )
                .alpha(alpha)


            UserImage(modifier = modifier, image = drawable)


        }


        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = { isHorizontal = !isHorizontal },
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add"
            )
        }
    }

    }






@Composable
fun UserImage(modifier: Modifier, image: Int) {
    Card(
        modifier = modifier,
        shape = CircleShape,
        border = BorderStroke(
            width = 3.dp,
            color = Color.Black
        ),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = "user image",
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimationComposeTheme {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp
        TransitionSampleScreen(
            screenWidth, screenHeight
        )
    }
}