package amhsn.newsapp.presentation.news_list.components

import amhsn.domain.entities.Article
import amhsn.newsapp.presentation.theme.LightGray100
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import kotlin.math.roundToInt

@ExperimentalMaterialApi
@Composable
fun NewsItem(
    modifier: Modifier, onCLickItem: () -> Unit, article: Article, onShareItem: () -> Unit) {

    val squareSize = 100.dp
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, -sizePx to 2)

    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(250.dp)
            .clickable {  }, elevation = 8.dp, shape = RoundedCornerShape(8.dp)
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = LightGray100)
                    .swipeable(
                        state = swipeableState,
                        anchors = anchors,
                        thresholds = { _, _ ->
                            FractionalThreshold(0.3f)
                        },
                        orientation = Orientation.Horizontal
                    )
            ) {

                ActionsRow(actionIconSize = 50.dp) {
                    onShareItem()
                }

                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                swipeableState.offset.value.roundToInt(), 0
                            )
                        }
                        .align(Alignment.CenterStart)
                ) {

                    Column(
                        modifier
                            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                            .height(250.dp)
                            .fillMaxWidth()
                            .clickable { onCLickItem() },
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Image(
                            painter = rememberImagePainter(article.urlToImage),
                            contentDescription = "image",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        )

                        article.title?.let {
                            Text(
                                text = it,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                            )
                        }

                        article.description?.let {
                            Text(
                                text = it,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Start,
                                maxLines = 2,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ShimmerAnimationNewsItem(
) {
    val shimmerColorShades = listOf(

        Color.LightGray.copy(0.9f),

        Color.LightGray.copy(0.2f),

        Color.LightGray.copy(0.9f)

    )

    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(

        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(


            // Tween Animates between values over specified [durationMillis]
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    ShimmerNewsItem(brush = brush)
}

@Composable
fun ShimmerNewsItem(brush: Brush) {
    Card(
        modifier = Modifier
            .height(250.dp)
            .padding(8.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {

        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {

            Image(
                painter = rememberImagePainter(""),
                contentDescription = "image",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .background(brush)
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Text(
                text = "",
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush)
                    .padding(horizontal = 8.dp)
            )


            Text(
                text = "",
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier
                    .background(brush)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

        }
    }
}

//
//@ExperimentalMaterialApi
//@Composable
//fun SwipeLeftRight(modifier: Modifier, content: @Composable () -> Unit) {
//    val squareSize = 60.dp
//    val swipeableState = rememberSwipeableState(initialValue = 0)
//    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
//    val anchors = mapOf(0f to 0, -sizePx to 2)
//    val text = remember { mutableStateOf("") }
//
//    Card(
//        modifier = modifier
//            .fillMaxWidth()
//            .height(250.dp), elevation = 4.dp, shape = RoundedCornerShape(8.dp)
//    ) {
//
//        Column(
//            modifier = Modifier
//                .background(Color.White)
//                .padding(8.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(8.dp))
//                    .background(color = LightGray100)
//                    .swipeable(
//                        state = swipeableState,
//                        anchors = anchors,
//                        thresholds = { _, _ ->
//                            FractionalThreshold(0.3f)
//                        },
//                        orientation = Orientation.Horizontal
//                    )
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    IconButton(
//                        onClick = {
//                            text.value = "Do you want to edit item ${+1}"
//                        },
//                        modifier = Modifier.size(50.dp)
//                    ) {
//                        Icon(
//                            Icons.Filled.Edit,
//                            contentDescription = "Edit",
//                            tint = Color.White
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.width(10.dp))
//
//                    IconButton(
//                        onClick = {
//                            text.value = "Do you want to delete item ${+1}"
//                        },
//                        modifier = Modifier.size(50.dp)
//                    ) {
//                        Icon(
//                            Icons.Filled.Delete,
//                            contentDescription = "Delete",
//                            tint = Color.White
//                        )
//                    }
//                }
//
//                Box(
//                    modifier = Modifier
//                        .offset {
//                            IntOffset(
//                                swipeableState.offset.value.roundToInt(), 0
//                            )
//                        }
//                        .align(Alignment.CenterStart)
//                ) {
//                    content()
//                }
//            }
//        }
//    }
//}


