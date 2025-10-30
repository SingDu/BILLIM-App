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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

/* ------------------ 프롬프트 반영 공통 규격 ------------------ */
private val ScreenHPadding = 24.dp
private val BigGap = 16.dp
private val SmallGap = 12.dp
private val LargeCorner = 24.dp
private val PillCorner = 28.dp

// 위치칩 드롭다운 색(시안 느낌의 딥 블루 계열)
private val ChipChevron = Color(0xFF2F6BB2)

@Composable
fun MapTabScreen(
    onSearch: () -> Unit = {},
    onChangeDistrict: () -> Unit = {},
    onItemClick: (Long) -> Unit = {},
    @DrawableRes mapImageResId: Int = R.drawable.map_placeholder, // TODO: 발표용 지도 이미지로 교체하세요 (예: R.drawable.map_placeholder)
    items: List<NearbyItemUi> = demoNearbyItemsWithImages()
) {
    // 배경: 화이트 + 은은한 민트 그라데이션(보라/핑크 제거)
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
            Spacer(Modifier.height(8.dp))

            // 1) 헤더(로고 업, BILLM = BrandGreen, 위치칩 + 아래 화살표)
            Header(
                onChangeDistrict = onChangeDistrict,
                logoRes = R.drawable.logo1
            )

            Spacer(Modifier.height(BigGap))

            // 2) 검색 바(화이트 캡슐 + 좌 돋보기 + 우 36dp 원형 업 버튼)
            SearchPill(
                placeholder = "무엇을 빌리고 싶으신가요?",
                onClick = onSearch
            )

            Spacer(Modifier.height(BigGap))

            // 3) 지도 카드: 발표용 이미지로 대체
            MapImageCard(mapImageResId)

            Spacer(Modifier.height(BigGap))

            // 4) 섹션 타이틀
            Text(
                text = "내 주변",
                color = TextPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(Modifier.height(SmallGap))

            // 5) 실제 PNG 연결 가능한 카드 리스트
            NearbyHorizontalList(
                items = items,
                onItemClick = onItemClick
            )

            Spacer(Modifier.navigationBarsPadding())
        }
    }
}

/* ============================== Header =============================== */

@Composable
private fun Header(
    onChangeDistrict: () -> Unit,
    @DrawableRes logoRes: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 로고 사이즈 업(32 -> 40dp) + 텍스트 BrandGreen
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = logoRes),
                contentDescription = "BILLM 로고",
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "BILLM",
                color = BrandGreen,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.weight(1f))
        // 위치 칩 (화이트 표면 + 그림자 + 아래 화살표)
        Surface(
            color = Color.White,
            shadowElevation = 2.dp,
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .clickable { onChangeDistrict() }
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "남구",
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Filled.ExpandMore,
                    contentDescription = "지역 변경",
                    tint = BrandGreen,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

/* ============================ Search Pill ============================ */

@Composable
private fun SearchPill(
    placeholder: String,
    onClick: () -> Unit
) {
    Surface(
        color = Color.White,
        shadowElevation = 1.dp,
        shape = RoundedCornerShape(PillCorner),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
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
                color = TextMuted,
                fontSize = 15.sp,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.width(8.dp))
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

/* ============================ Map Image Card ============================ */
/** 발표용: 지도 API PNG 이미지를 표시 */
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

/* ======================= Nearby Horizontal Cards ====================== */

data class NearbyItemUi(
    val id: Long,
    val title: String,
    val priceText: String,
    val meta1: String,
    val meta2: String,
    val meta3: String,
    @DrawableRes val thumbnailRes: Int   // PNG 리소스 연결용
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
            .width(240.dp)
            .clickable { onClick() }
    ) {
        Column(Modifier.padding(16.dp)) {
            // 예시 PNG 파일 사용중
            Image(
                painter = painterResource(id = item.thumbnailRes),
                contentDescription = "${item.title} 썸네일",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = item.title,
                color = TextPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.priceText,
                color = BrandGreen,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(Modifier.height(6.dp))

            MetaLine(item.meta1, muted = true)
            Spacer(Modifier.height(2.dp))
            MetaLine(item.meta2, muted = false)
            Spacer(Modifier.height(2.dp))
            MetaLine(item.meta3, muted = false)

            Spacer(Modifier.height(8.dp))

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

/* ============================ Demo Data ============================ */
/**
 * 발표용 예시 데이터:
 */
private fun demoNearbyItemsWithImages() = listOf(
    NearbyItemUi(
        id = 1L,
        title = "양복",
        priceText = "₩5,000/일",
        meta1 = "0.5km",
        meta2 = "0.5km",
        meta3 = "₩5,100 (20)",
        thumbnailRes = R.drawable.suit // TODO: 실제 PNG로 교체
    ),
    NearbyItemUi(
        id = 2L,
        title = "런닝화",
        priceText = "₩3,000/일",
        meta1 = "0.8km",
        meta2 = "1.0km",
        meta3 = "4.9 (30)",
        thumbnailRes = R.drawable.running_shoes // TODO: 실제 PNG로 교체
    ),
    NearbyItemUi(
        id = 3L,
        title = "자전거",
        priceText = "₩7,000/일",
        meta1 = "1.9km (30)",
        meta2 = "빌 프로포단",
        meta3 = "₩10,000/일",
        thumbnailRes = R.drawable.bike // TODO: 실제 PNG로 교체
    )
)
