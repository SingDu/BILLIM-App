package com.example.borrowitapp.ui.map

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.borrowitapp.R
import com.example.borrowitapp.ui.theme.BrandGreen
import com.example.borrowitapp.ui.theme.BrandTeal
import com.example.borrowitapp.ui.theme.TextMuted
import com.example.borrowitapp.ui.theme.TextPrimary

/* ------------------ 공통 규격 ------------------ */
private val ScreenHPadding = 24.dp
private val BigGap = 16.dp
private val SmallGap = 12.dp
private val LargeCorner = 24.dp
private val PillCorner = 28.dp

@Composable
fun MapTabScreen(
    onSearch: () -> Unit = {},
    onChangeDistrict: () -> Unit = {},
    onItemClick: (Long) -> Unit = {},
    @DrawableRes mapImageResId: Int = R.drawable.map_placeholder,
    items: List<NearbyItemUi> = demoNearbyItemsWithImages()
) {
    var selectedAddress by rememberSaveable { mutableStateOf("남구") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.White,
                        Color.White,
                        BrandTeal.copy(alpha = 0.06f),
                        Color.White
                    )
                )
            )
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = ScreenHPadding)
        ) {

            MapHeaderHomeStyle(
                logoRes = R.drawable.logo1,
                title = "BILLM",
                selectedAddress = selectedAddress,
                onAddressClick = {
                    onChangeDistrict()
                }
            )

            Spacer(Modifier.height(12.dp))

            SearchPill(
                placeholder = "무엇을 빌리고 싶으신가요?",
                onClick = onSearch
            )

            Spacer(Modifier.height(20.dp))

            MapImageCard(mapImageResId)

            Spacer(Modifier.height(20.dp))

            Text(
                text = "내 주변",
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(SmallGap))

            NearbyHorizontalList(
                items = items,
                onItemClick = onItemClick
            )

            Spacer(Modifier.navigationBarsPadding())
        }
    }
}
@Composable
private fun MapHeaderHomeStyle(
    @DrawableRes logoRes: Int,
    title: String,
    selectedAddress: String,
    onAddressClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(logoRes),
                contentDescription = "BILLM 로고",
                modifier = Modifier.size(36.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = BrandGreen,
                letterSpacing = 0.5.sp
            )
        }
        LocationChipHomeStyle(
            label = selectedAddress,
            onClick = onAddressClick
        )
    }
}
@Composable
private fun LocationChipHomeStyle(
    label: String,
    onClick: () -> Unit
) {
    Surface(
        color = Color.White,
        shadowElevation = 1.dp,
        shape = RoundedCornerShape(16.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                color = TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.width(6.dp))

            Text("▾", color = TextMuted, fontSize = 12.sp)
        }
    }
}
@Composable
private fun SearchPill(
    placeholder: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 1.dp,
        shape = RoundedCornerShape(28.dp),
        color = Color.White,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .height(56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = TextMuted
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = placeholder,
                color = TextMuted,
                fontSize = 15.sp,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
@Composable
private fun MapImageCard(@DrawableRes mapImageResId: Int) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(LargeCorner),
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
    ) {
        Image(
            painter = painterResource(id = mapImageResId),
            contentDescription = "지도 이미지",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
data class NearbyItemUi(
    val id: Long,
    val title: String,
    val pricePerDay: Int,          // ₩/일
    val distanceKm: Double,        // km
    val ownerRating: Double,       // 4.9
    val reviewCount: Int,          // (30)
    val deposit: Int,              // ₩
    val availableToday: Boolean,   // 오늘 대여 가능
    val meetMethod: String,        // 직거래/택배 등
    val category: String,          // 의류/운동 등
    @DrawableRes val thumbnailRes: Int
)

@Composable
private fun NearbyHorizontalList(
    items: List<NearbyItemUi>,
    onItemClick: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEach { item ->
            NearbyCard(item) { onItemClick(item.id) }
        }
    }
}

@Composable
private fun NearbyCard(item: NearbyItemUi, onClick: () -> Unit) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(LargeCorner),
        shadowElevation = 1.dp,
        modifier = Modifier
            .width(260.dp)
            .clickable { onClick() }
    ) {
        Column(Modifier.padding(16.dp)) {
            // 썸네일
            Image(
                painter = painterResource(id = item.thumbnailRes),
                contentDescription = "${item.title} 썸네일",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(10.dp))

            // 제목
            Text(
                text = item.title,
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // 가격
            Text(
                text = "₩${item.pricePerDay.toPrice()}/일",
                color = BrandGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(6.dp))

            // 거리
            MetaLine("${item.distanceKm}km", muted = true)

            // 평점
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = BrandGreen,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "${item.ownerRating} (${item.reviewCount})",
                    color = TextPrimary,
                    fontSize = 12.sp
                )
            }

            // 보증금
            MetaBadge(
                text = "보증금 ₩${item.deposit.toPrice()}",
                tint = Color(0xFF065F46) ,
                bg = Color(0xFFE7F8F0)
            )

            // 오늘 대여/거래 방식
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                if (item.availableToday) {
                    SmallChip(
                        leading = {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = null,
                                tint = BrandGreen,
                                modifier = Modifier.size(14.dp)
                            )
                        },
                        text = "오늘 대여 가능"
                    )
                }
                SmallChip(
                    leading = {
                        Icon(
                            imageVector = Icons.Filled.LocalShipping,
                            contentDescription = null,
                            tint = BrandGreen,
                            modifier = Modifier.size(14.dp)
                        )
                    },
                    text = item.meetMethod
                )
            }

            // 카테고리 태그
            Spacer(Modifier.height(4.dp))
            SmallChip(
                leading = {
                    Icon(
                        imageVector = Icons.Filled.Tag,
                        contentDescription = null,
                        tint = BrandGreen,
                        modifier = Modifier.size(14.dp)
                    )
                },
                text = "#${item.category}"
            )

            }
        }
    }


@Composable
private fun MetaLine(text: String, muted: Boolean) {
    Text(
        text = text,
        color = if (muted) TextMuted else TextPrimary,
        fontSize = 12.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun MetaBadge(
    text: String,
    tint: Color,
    bg: Color
) {
    Box(
        modifier = Modifier
            .padding(top = 4.dp, bottom = 2.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text = text, color = tint, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun SmallChip(
    leading: @Composable (() -> Unit)? = null,
    text: String
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF1F5F9))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leading != null) {
            leading()
            Spacer(Modifier.width(4.dp))
        }
        Text(text = text, color = TextPrimary, fontSize = 11.sp)
    }
}

/* ============================ 발표용 예시 이미지 ============================ */

private fun Int.toPrice(): String =
    String.format("%,d", this)

private fun demoNearbyItemsWithImages() = listOf(
    NearbyItemUi(
        id = 1L,
        title = "양복",
        pricePerDay = 5000,
        distanceKm = 0.5,
        ownerRating = 4.9,
        reviewCount = 20,
        deposit = 50000,
        availableToday = true,
        meetMethod = "직거래",
        category = "의류",
        thumbnailRes = R.drawable.suit
    ),
    NearbyItemUi(
        id = 2L,
        title = "런닝화",
        pricePerDay = 3000,
        distanceKm = 0.8,
        ownerRating = 4.7,
        reviewCount = 30,
        deposit = 20000,
        availableToday = true,
        meetMethod = "택배",
        category = "운동",
        thumbnailRes = R.drawable.running_shoes
    ),
    NearbyItemUi(
        id = 3L,
        title = "자전거",
        pricePerDay = 7000,
        distanceKm = 1.9,
        ownerRating = 4.8,
        reviewCount = 30,
        deposit = 100000,
        availableToday = false,
        meetMethod = "직거래",
        category = "레저",
        thumbnailRes = R.drawable.bike
    )
)