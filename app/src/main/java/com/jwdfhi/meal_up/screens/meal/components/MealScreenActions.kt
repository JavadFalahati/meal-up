package com.jwdfhi.meal_up.screens.meal.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.jwdfhi.meal_up.models.MealModel
import com.jwdfhi.meal_up.models.getName
import com.jwdfhi.meal_up.ui.theme.*
import com.jwdfhi.meal_up.utils.ManagementSettings
import com.slaviboy.composeunits.dh
import com.slaviboy.composeunits.dw
import com.slaviboy.composeunits.sh

@Composable
fun MealScreenActions(
    mealItem: MealModel,
    markOnTap: () -> Unit,
    goToMarksOnTap: () -> Unit,
    removeMarkOnTap: () -> Unit
) {
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
                when (mealItem.isMarked) {
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
                                ),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = ManagementSettings.PrimaryColor
                            ),
                            onClick = { markOnTap() },
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
                                .width(0.18.dw)
                                .align(Alignment.Bottom)
                                .clip(
                                    shape = RoundedCornerShape(
                                        10.dp
                                    )
                                ),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Red80Color
                            ),
                            onClick = { removeMarkOnTap() },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Remove",
                                tint = White100Color,
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Bottom)
                                .clip(
                                    shape = RoundedCornerShape(
                                        10.dp
                                    )
                                ),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = ManagementSettings.Primary80Color
                            ),
                            onClick = {
                                goToMarksOnTap()
                            },
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "Done",
                                    tint = White100Color,
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "Marked as ${mealItem.markCategory?.getName()}",
                                    style = TextStyle(
                                        color = White100Color,
                                        fontSize = 0.02.sh
                                    ),
                                    modifier = Modifier
                                        .padding(3.dp)
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}