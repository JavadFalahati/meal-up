package com.jwdfhi.meal_up.components

import android.graphics.Paint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.jwdfhi.meal_up.R
import com.jwdfhi.meal_up.models.FilteredMealModel
import com.jwdfhi.meal_up.models.MealModel
import com.jwdfhi.meal_up.models.getColor
import com.jwdfhi.meal_up.models.getName
import com.jwdfhi.meal_up.ui.theme.*
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

@Composable
fun <T> MealItem(
    onTap: () -> Unit,
    likeOnTap: () -> Unit,
    item: T,
    height: Dp = if (item is FilteredMealModel) 0.17.dh else 0.4.dh,
    margin: Dp = 8.dp,
    padding: Dp = 8.dp,
    backgroundColor: androidx.compose.ui.graphics.Color = Color.White,
    borderRadius: Dp = 6.dp
) {
    Box(
        modifier = Modifier
            .padding(vertical = margin)
            .clip(shape = RoundedCornerShape(borderRadius))
            .background(backgroundColor)
            .height(height = height)
            .fillMaxWidth()
            .clickable { onTap() }
    ) {
        when (item) {
            is MealModel -> {

                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val (backgroundImage, likeIcon) = createRefs()

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .constrainAs(backgroundImage) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                            }
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(item.strMealThumb),
                            contentDescription = "Meal",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(shape = RoundedCornerShape(borderRadius))
                            // .align(CenterVertically)
                        )
                    }
                    Box(
                        modifier = Modifier
                            .constrainAs(likeIcon) {}
                            .fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                            ) {
                                if (item.isMarked) {
                                    MarkItem(item = item)
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                            ) {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .clip(shape = RoundedCornerShape(borderRadius))
                                        .background(color = Black80Color.copy(0.7f))
                                        .blur(radius = 12.dp)
                                        .padding(6.dp),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Bottom,
                                ) {
                                    Text(
                                        text = item.strMeal,
                                        style = TextStyle(
                                            color = White100Color,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = if (item.strMeal.length < 35) 0.027.sh else 0.020.sh
                                        ),
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = (
                                                if (item.strInstructions.length > 250)
                                                    item.strInstructions.substring(0, 250) + "..."
                                                else
                                                    item.strInstructions
                                                ).replace("\\r\\n\\r\\n", "\\r\\n"),
                                        style = TextStyle(
                                            color = White80Color,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 0.016.sh
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is FilteredMealModel -> {
                Row(
                    modifier = Modifier
                        .padding(padding)
                    /*.align(Center)*/,
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(0.35.dw)

                    ) {
                        val (backgroundImage, likeIcon) = createRefs()

                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .constrainAs(backgroundImage) {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    width = Dimension.fillToConstraints
                                }
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(item.strMealThumb),
                                contentDescription = "Meal",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(0.35.dw)
                                    .clip(shape = RoundedCornerShape(borderRadius))
                                // .align(CenterVertically)
                            )
                        }
                        Box(
                            modifier = Modifier
                                .constrainAs(likeIcon) {
                                    // bottom.linkTo(backgroundImage.top)
                                    // end.linkTo(backgroundImage.start)
                                    top.linkTo(backgroundImage.top)
                                    start.linkTo(backgroundImage.start)
                                }
                                .padding(
                                    top = 6.dp,
                                    start = 6.dp,
                                    end = 6.dp,
                                    bottom = 6.dp,
                                )
                        ) {
                            CustomLikeIcon(
                                isLiked = item.isLiked,
                                onTap = { likeOnTap() },
                                height = 0.04.dh,
                                iconPadding = 6.dp,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(0.05.dw))
                    Column(
                        modifier = Modifier
                            .align(Alignment.Top)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = item.strMeal,
                            style = TextStyle(
                                color = Black90Color,
                                fontWeight = FontWeight.Bold,
                                fontSize = if (item.strMeal.length < 35) 0.027.sh else 0.020.sh
                            ),
                        )
                        if (item.isMarked) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.End)
                            ) {
                                MarkItem(
                                    item = item,
                                    backgroundColor = TransparentColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}