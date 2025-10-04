package com.example.borrowitapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image                      // ✅ 추가 1
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale                // ✅ 추가 2
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.borrowitapp.ui.location.LocationScreen
import com.example.borrowitapp.ui.navigation.Screen
import com.example.borrowitapp.ui.verify.VerifyFlowScreen

// ✅ 추가 3: R 클래스 자동 생성됨 (별도 import 필요 없음)
// 옵션 2

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // ✅ 방법 1: 테마 없이 실행 (Material3 기본 테마 사용)
            com.example.borrowitapp.ui.map.MapTabScreen()

            // ✅ 방법 2: 테마 있으면 주석 해제
            // BorrowItAppTheme {
            //     BorrowItApp()
            // }
        }
    }
}

@Composable
fun BorrowItApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(
                onStartClick = {
                    navController.navigate(Screen.Location.route)
                },
                onLoginClick = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(route = Screen.Location.route) {
            LocationScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onVerificationStart = {
                    navController.navigate(Screen.Verify.route)
                }
            )
        }

        composable(route = Screen.Verify.route) {
            VerifyFlowScreen(
                onComplete = { name, rrn6, rrn1, carrier, phone ->
                    println("✅ 인증 완료: $name, $carrier, $phone")
                    navController.popBackStack(Screen.Welcome.route, inclusive = false)
                }
            )
        }

        composable(route = Screen.Login.route) {
            WelcomeScreen(
                onStartClick = {},
                onLoginClick = {}
            )
        }
    }
}

@Composable
fun WelcomeScreen(
    onStartClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF5FFF7),
                        Color(0xFFFFFFFF),
                        Color(0xFFFAFFFB),
                        Color(0xFFFFFFFF)
                    )
                )
            )
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .offset(x = (-80).dp, y = 120.dp)
                .size(250.dp)
                .alpha(0.06f)
                .blur(50.dp)
                .clip(RoundedCornerShape(125.dp))
                .background(Color(0xFF35cc6f))
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 100.dp, y = (-40).dp)
                .size(200.dp)
                .alpha(0.08f)
                .blur(40.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(Color(0xFF2dd36f))
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-40).dp, y = 100.dp)
                .size(180.dp)
                .alpha(0.05f)
                .blur(45.dp)
                .clip(RoundedCornerShape(90.dp))
                .background(Color(0xFF42d77d))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            LogoSection()

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "BILLIM",
                fontSize = 36.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFF0F172A),
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "빌리며 시작하는",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E293B),
                textAlign = TextAlign.Center,
                lineHeight = 26.sp
            )

            Text(
                text = "나의 새로운 취미",
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E293B),
                textAlign = TextAlign.Center,
                lineHeight = 26.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "지금, 같은 취미를 즐기는",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Text(
                text = "사람들과 연결되어 보세요",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF64748B),
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color.White)
                    .padding(vertical = 16.dp, horizontal = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "🌏", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "대한민국",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "▼",
                        fontSize = 11.sp,
                        color = Color(0xFF94A3B8)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF22c55e),
                                Color(0xFF10b981),
                                Color(0xFF059669)
                            )
                        )
                    )
            ) {
                Button(
                    onClick = onStartClick,
                    modifier = Modifier.fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "시작하기",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "이미 계정이 있나요?",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF64748B)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "로그인",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF10b981),
                    textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun LogoSection() {
    Box(contentAlignment = Alignment.Center) {
        // 배경 글로우 효과
        Box(
            modifier = Modifier
                .size(300.dp)
                .alpha(0.25f)
                .blur(60.dp)
                .clip(RoundedCornerShape(150.dp))
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF10b981).copy(alpha = 0.3f),
                            Color(0xFF10b981).copy(alpha = 0.1f),
                            Color.Transparent
                        )
                    )
                )
        )

        // ✅ 로고 이미지
        Image(
            painter = painterResource(id = R.drawable.logo1),
            contentDescription = "BILLIM 로고",
            modifier = Modifier.size(220.dp),
            contentScale = ContentScale.Fit
        )
    }
}
