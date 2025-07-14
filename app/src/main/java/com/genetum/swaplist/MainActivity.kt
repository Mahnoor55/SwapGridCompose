package com.genetum.swaplist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.genetum.swaplist.ui.theme.SwapListTheme
import com.genetum.swaplist.ui.theme.blue
import com.swap.compose.DragSwapColumn
import com.swap.compose.DragSwapGrid
import com.swap.compose.DragSwapRow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SwapListTheme {
                SwapListPreview(
                )
            }
        }
    }
}
data class StaffMember(
    val role: String,
    val avatarRes: Int
)
@Preview
@Composable
fun SwapListPreview() {
    val staffList = remember {
        mutableStateListOf(
            StaffMember("📞 Agent", R.drawable.f1),
            StaffMember("💼 Manager", R.drawable.p2),
            StaffMember("🔧 Tech Support", R.drawable.p1),
            StaffMember("📋 Supervisor", R.drawable.f2)

        )
    }
        val animalList = remember {
            mutableStateListOf(
                R.drawable.cat,
                R.drawable.elephant,
                R.drawable.bug,
                R.drawable.rat
            )
    }
    val boxSize = 80.dp
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally,) {
        Text(
            text = "Team Members",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        DragSwapRow(listSize = staffList.size, boxSize = boxSize, onSwap = { from, to ->
            staffList.apply {
                val temp = this[from]
                this[from] = this[to]
                this[to] = temp
            }
        }) { index ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(blue, shape = RoundedCornerShape(8.dp))
                    .padding(3.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = staffList[index].avatarRes),
                        contentDescription = staffList[index].role,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f) // Fill available vertical space proportionally
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.FillHeight
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = staffList[index].role,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }

        }

        val images = remember {
            mutableStateListOf(
                R.drawable.j1, R.drawable.j2, R.drawable.j3, R.drawable.j4,
                R.drawable.j5, R.drawable.j6,R.drawable.j7, R.drawable.j8, R.drawable.j9,
            )
        }

        DragSwapGrid(
            listSize = images.size,
            columns = 3,
            boxSize = 80.dp,
            onSwap = { from, to ->
                images.apply {
                    val temp = this[from]
                    this[from] = this[to]
                    this[to] = temp
                }
            }
        ) { index ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(3.dp)
            ) {
                Image(
                    painter = painterResource(id = images[index]),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.FillHeight
                )
            }
        }
        DragSwapColumn (listSize = animalList.size, boxSize = boxSize, onSwap = { from, to ->
            animalList.apply {
                val temp = this[from]
                this[from] = this[to]
                this[to] = temp
            }
        }) { index ->
            Image(
                painter = painterResource(id = animalList[index]),
                contentDescription = "Image $index",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}