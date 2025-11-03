package com.example.borrowitapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.borrowitapp.R
import com.example.borrowitapp.ui.theme.BrandGreen
import com.example.borrowitapp.ui.theme.TextMuted
import com.example.borrowitapp.ui.theme.TextPrimary
import com.example.borrowitapp.ui.theme.AppShapes
import com.example.borrowitapp.ui.theme.Dimen
import java.util.Locale

// ===== models =====
data class Category(
    val id: String,
    val name: String,
    val iconEmoji: String // 발표 전까지 임시
)

data class Item(
    val id: String,
    val name: String,
    val price: String,      // "7000" 형식 권장
    val distance: String,   // "0.5km"
    val imageResId: Int? = null,
    val isRecommended: Boolean = false
)

@Composable
fun HomeScreen(
    onAddressClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
) {
    var searchText by remember { mutableStateOf("") }
    var selectedAddress by remember { mutableStateOf("남구") }

    val categories = remember {
        listOf(
            Category("sports", "운동물품", "🏃"),
            Category("tools", "공구", "🔧"),
            Category("camp", "캠핑", "⛺"),
            Category("kitchen", "주방", "🍳"),
            Category("music", "음향/악기", "🎵"),
            Category("etc", "기타", "∞")
        )
    }

    val recommendedItems = remember {
        listOf(
            Item("1", "카메라", "5000", "0.5km", R.drawable.camera, true),
            Item("2", "전동 드릴", "3000", "0.8km", R.drawable.drill, true),
            Item("3", "목공방 대여", "7000", "1.9km", R.drawable.woodworking_shop, true),
            Item("4", "빔프로젝터", "10000", "1.0km", R.drawable.projector, true)
        )
    }

    val nearbyItems = remember {
        listOf(
            Item("5", "양복 대여", "15000", "0.3km", R.drawable.suit),
            Item("6", "러닝화 대여", "2000", "0.6km", R.drawable.running_shoes),
            Item("7", "자전거 대여", "12000", "0.7km", R.drawable.bike)
        )
    }

    // ====== background ======
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color.White,
                        Color.White,
                        Color.White
                    )
                )
            )
            .statusBarsPadding()
            .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Top))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // 헤더
            item {
                Header(
                    selectedAddress = selectedAddress,
                    onAddressClick = onAddressClick
                )
                Spacer(Modifier.height(12.dp))
            }

            // 검색바
            item {
                SearchBar(
                    value = searchText,
                    onClick = onSearchClick,
                    onValueChange = { searchText = it }
                )
                Spacer(Modifier.height(20.dp))
            }

            // 카테고리
            item {
                CategorySection(categories)
                Spacer(Modifier.height(28.dp))
            }

            // 추천 물품
            item {
                SectionTitle("추천 물품 및 장소")
                Spacer(Modifier.height(12.dp))
                TwoColumnCards(items = recommendedItems)
                Spacer(Modifier.height(28.dp))
            }

            // 주변 물품
            item {
                SectionTitle("내 주변")
                Spacer(Modifier.height(12.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = Dimen.screenHPadding),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(nearbyItems, key = { it.id }) { item ->
                        ItemCardHorizontal(item)
                    }
                }
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

/* ---------------- components ---------------- */

@Composable
private fun Header(
    selectedAddress: String,
    onAddressClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.screenHPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.logo1),
                contentDescription = "BILLM 로고",
                modifier = Modifier.size(36.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "BILLM",
                color = BrandGreen,
                fontSize = 24.sp,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold)
            )
        }

        Surface(
            color = Color.White,
            shadowElevation = 1.dp,
            shape = RoundedCornerShape(16.dp),
            onClick = onAddressClick
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = selectedAddress,
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.width(6.dp))
                Text("▾", color = TextMuted, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.screenHPadding),
        shadowElevation = 1.dp,
        shape = AppShapes.large,
        color = Color.White,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .height(56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.material3.Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "검색",
                tint = TextMuted
            )
            Spacer(Modifier.width(12.dp))
            Text(
                text = if (value.isBlank()) "무엇을 빌리고 싶으신가요?" else value,
                color = if (value.isBlank()) TextMuted else TextPrimary,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        color = TextPrimary,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.screenHPadding)
    )
}

@Composable
private fun CategorySection(categories: List<Category>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = Dimen.screenHPadding)
    ) {
        items(categories, key = { it.id }) { c ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Surface(
                    color = Color(0xFFF0FDF4),
                    shape = CircleShape,
                    shadowElevation = 0.dp
                ) {
                    Box(
                        modifier = Modifier.size(56.dp),
                        contentAlignment = Alignment.Center
                    ) { Text(c.iconEmoji, fontSize = 26.sp) }
                }
                Spacer(Modifier.height(8.dp))
                Text(c.name, color = TextPrimary, fontSize = 12.sp)
            }
        }
    }
}

/** 2열 그리드(행 단위 구성) */
@Composable
private fun TwoColumnCards(items: List<Item>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.screenHPadding),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                row.getOrNull(0)?.let { Box(Modifier.weight(1f)) { ItemCard(it) } }
                row.getOrNull(1)?.let { Box(Modifier.weight(1f)) { ItemCard(it) } }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ItemCard(item: Item) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = AppShapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // 이미지는 고정 높이 대신 비율로 → 발표용 PNG가 잘리지 않도록
            if (item.imageResId != null) {
                Image(
                    painter = painterResource(item.imageResId),
                    contentDescription = item.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f),
                    color = Color(0xFFF0FDF4),
                    shape = RoundedCornerShape(12.dp)
                ) {}
            }

            Spacer(Modifier.height(10.dp))
            Text(item.name, color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, maxLines = 2)
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(formatPrice(item.price), color = BrandGreen, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                Text(item.distance, color = TextMuted, fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun ItemCardHorizontal(item: Item) {
    Card(
        modifier = Modifier.width(156.dp),
        shape = AppShapes.large,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            if (item.imageResId != null) {
                Image(
                    painter = painterResource(item.imageResId),
                    contentDescription = item.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f),
                    color = Color(0xFFF0FDF4),
                    shape = RoundedCornerShape(12.dp)
                ) {}
            }

            Spacer(Modifier.height(8.dp))
            Text(
                item.name,
                color = TextPrimary,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Text(formatPrice(item.price), color = BrandGreen, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Text(item.distance, color = TextMuted, fontSize = 12.sp)
            }
        }
    }
}

/* ---------- utils ---------- */
private fun formatPrice(price: String): String {
    return runCatching {
        val n = price.replace(Regex("[^0-9]"), "").toInt()
        "${String.format(Locale.getDefault(), "%,d", n)}원"
    }.getOrElse { "${price}원" }
}
