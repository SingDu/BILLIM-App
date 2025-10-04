package com.example.borrowitapp.ui.map

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.borrowitapp.ui.theme.AppShapes
import com.example.borrowitapp.ui.theme.BrandDeep
import com.example.borrowitapp.ui.theme.BrandGreen
import com.example.borrowitapp.ui.theme.BrandTeal
import com.example.borrowitapp.ui.theme.Dimen
import com.example.borrowitapp.ui.theme.TextMuted
import com.example.borrowitapp.ui.theme.TextPrimary

@Composable
fun MapTabScreen(
    onSearchClick: () -> Unit = {},
    onChangeDistrict: () -> Unit = {},
    onItemClick: (Long) -> Unit = {}
) {
    Scaffold(
        topBar = { MapTopBar(onChangeDistrict) }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(horizontal = Dimen.screenHPadding)
        ) {
            Spacer(Modifier.height(Dimen.bigGap))

            SearchPill(
                placeholder = "무엇을 빌리고 싶으신가요?",
                onClick = onSearchClick
            )

            Spacer(Modifier.height(Dimen.bigGap))

            // 지도 더미: 그라디언트 박스 (SDK 붙일 때 교체)
            Surface(
                shape = AppShapes.large,
                tonalElevation = 1.dp,
                shadowElevation = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            ) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    BrandTeal.copy(alpha = 0.15f),
                                    Color.White,
                                    BrandGreen.copy(alpha = 0.12f)
                                )
                            )
                        )
                ) {
                    MapPin(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(start = 36.dp, top = 140.dp),
                        icon = Icons.Filled.CameraAlt
                    )
                    MapPin(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(start = 40.dp),
                        icon = Icons.Filled.Construction
                    )
                    MapPin(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(end = 24.dp, top = 56.dp),
                        icon = Icons.Filled.MusicNote
                    )
                    MapPin(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 72.dp, bottom = 28.dp),
                        icon = Icons.Filled.CameraAlt
                    )
                    MapPin(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(end = 60.dp, bottom = 24.dp),
                        icon = Icons.Filled.CameraAlt
                    )
                }
            }

            Spacer(Modifier.height(Dimen.bigGap))

            Text(
                text = "내 주변",
                color = BrandGreen,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Spacer(Modifier.height(Dimen.vGap))

            NearbyHorizontalList(
                items = demoNearbyItems(),
                onItemClick = onItemClick
            )

            Spacer(Modifier.height(Dimen.bigGap))
        }
    }
}

/* ─────────────────────────── Top Bar ─────────────────────────── */

@Composable
private fun MapTopBar(onChangeDistrict: () -> Unit) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "BILLM",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                )
            }
        },
        actions = {
            DistrictPill(text = "강남구", onClick = onChangeDistrict)
            Spacer(Modifier.width(8.dp))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DistrictPill(text: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = AppShapes.large,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
        shadowElevation = 0.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 13.sp),
                color = TextPrimary
            )
        }
    }
}

/* ─────────────────────────── Search Pill ─────────────────────────── */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchPill(
    placeholder: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = BrandGreen
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = TextMuted,
                    fontSize = 15.sp
                ),
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(BrandGreen),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowUpward,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

/* ─────────────────────────── Map Pin ─────────────────────────── */

@Composable
private fun MapPin(
    modifier: Modifier = Modifier,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(BrandGreen)
            .padding(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(BrandDeep), // BrandYellow → BrandDeep (기존 토큰만 사용)
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = TextPrimary)
        }
    }
}

/* ─────────────────────────── Nearby Horizontal List ─────────────────────────── */

private data class NearbyItemUi(
    val id: Long,
    val title: String,
    val priceText: String,
    val metaLines: List<String>
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
        Spacer(Modifier.width(2.dp))
        items.forEach { item ->
            NearbyCard(item = item, onClick = { onItemClick(item.id) })
        }
        Spacer(Modifier.width(2.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NearbyCard(
    item: NearbyItemUi,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = AppShapes.large,
        tonalElevation = 1.dp,
        modifier = Modifier
            .width(180.dp)
            .heightIn(min = 220.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            // 썸네일 자리 (이미지 리소스 없이도 컴파일되도록 placeholder)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clip(AppShapes.medium)
                    .background(BrandTeal.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = null,
                    tint = BrandGreen
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = item.priceText,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = BrandGreen,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(Modifier.height(6.dp))

            item.metaLines.forEachIndexed { idx, line ->
                Text(
                    text = line,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = if (idx == 0) TextMuted else TextPrimary,
                        fontSize = 12.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(2.dp))
            }

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = BrandGreen
                )
            }
        }
    }
}

/* ─────────────────────────── Demo Data ─────────────────────────── */

private fun demoNearbyItems() = listOf(
    NearbyItemUi(
        id = 1L,
        title = "카메라 렌즈",
        priceText = "₩5,000/일",
        metaLines = listOf("0.5km", "0.5km", "₩5,100 (20)")
    ),
    NearbyItemUi(
        id = 2L,
        title = "전동 드릴",
        priceText = "₩3,000/일",
        metaLines = listOf("0.8km", "1.0km", "4.9 (30)")
    ),
    NearbyItemUi(
        id = 3L,
        title = "자전거",
        priceText = "₩7,000/일",
        metaLines = listOf("1.9km (30)", "빌 프로포탄", "₩10,000/일")
    )
)
