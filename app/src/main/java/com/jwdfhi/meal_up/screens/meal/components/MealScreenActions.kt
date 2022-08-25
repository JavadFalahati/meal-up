package com.jwdfhi.meal_up.screens.meal.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.models.MealModel
import com.jwdfhi.meal_up.ui.theme.Black50Color
import com.jwdfhi.meal_up.ui.theme.PrimaryColor
import com.jwdfhi.meal_up.ui.theme.Red80Color
import com.jwdfhi.meal_up.ui.theme.White100Color
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

@Composable
fun MealScreenActions(mealItem: MealModel) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 0.1.dh)
                .padding(horizontal = 0.007.dw)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomCenter)
                    .clip(shape = RoundedCornerShape(10.dp))
            ) {
                when (!mealItem.isMarked) {
                    false -> Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Button(
                            modifier = Modifier
                                .align(Alignment.Bottom)
                                .fillMaxWidth()
                                .clip(
                                    shape = RoundedCornerShape(
                                        10.dp
                                    )
                                )
                                .padding(vertical = 4.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = PrimaryColor
                            ),
                            onClick = {  },
                        ) {
                            Text(
                                text = "Mark now",
                                style = TextStyle(
                                    color = White100Color,
                                    fontSize = 0.02.sh
                                ),
                                modifier = Modifier
                                    .padding(6.dp)
                            )
                        }
                    }
                    true -> Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Button(
                            modifier = Modifier
                                .weight(0.5f)
                                .align(Alignment.Bottom)
                                .clip(
                                    shape = RoundedCornerShape(
                                        10.dp
                                    )
                                )
                                .padding(vertical = 4.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Red80Color
                            ),
                            onClick = {  },
                        ) {
                            Text(
                                text = "Remove",
                                style = TextStyle(
                                    color = White100Color,
                                    fontSize = 0.02.sh
                                ),
                                modifier = Modifier
                                    .padding(6.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Button(
                            modifier = Modifier
                                .weight(0.5f)
                                .align(Alignment.Bottom)
                                .clip(
                                    shape = RoundedCornerShape(
                                        10.dp
                                    )
                                )
                                .padding(vertical = 4.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Black50Color
                            ),
                            onClick = {  },
                        ) {
                            Text(
                                text = "Go to marks",
                                style = TextStyle(
                                    color = White100Color,
                                    fontSize = 0.02.sh
                                ),
                                modifier = Modifier
                                    .padding(6.dp)
                            )
                        }
                    }
                }

            }
        }
    }
}