package com.example.borrowitapp.ui.verify

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val BillimGreen = Color(0xFF10b981)
private val BillimGreenLight = Color(0xFF22c55e)
private val BillimGreenDark = Color(0xFF059669)
private val BillimBackground = Color(0xFFF5FFF7)
private val BillimTextPrimary = Color(0xFF0F172A)
private val BillimTextSecondary = Color(0xFF64748B)
private val BillimSurface = Color.White
private val CheckboxGreen = Color(0xFF10b981)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyFlowScreen(
    onComplete: (name: String, rrn6: String, rrn1: String, carrier: String, phone: String) -> Unit = { _,_,_,_,_ -> }
) {
    /* ---------- 진행 단계 ---------- */
    var step by rememberSaveable { mutableStateOf(VerifyStep.NAME) }

    /* ---------- 입력값 ---------- */
    var name by rememberSaveable { mutableStateOf("") }
    var rrn6 by rememberSaveable { mutableStateOf("") }
    var rrn1 by rememberSaveable { mutableStateOf("") }
    var carrierLabel by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }

    /* ---------- 약관 동의 BottomSheet ---------- */
    var showTermsSheet by rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()

    /* ---------- 포커스/키보드 ---------- */
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val nameFR = remember { FocusRequester() }
    val rrn6FR = remember { FocusRequester() }
    val rrn1FR = remember { FocusRequester() }
    val phoneFR = remember { FocusRequester() }

    /* ---------- 유효성 ---------- */
    val isNameValid = name.trim().isNotEmpty()
    val isRrnValid = rrn6.length == 6 && rrn1.length == 1
    val isCarrierValid = carrierLabel.isNotBlank()
    val isPhoneValid = phone.length in 10..11

    /* ---------- 최종 완료 여부 ---------- */
    val isAllCompleted = step == VerifyStep.REVIEW

    /* ---------- 타이틀 ---------- */
    val title = if (isAllCompleted) {
        "입력한 정보를\n확인해주세요"
    } else {
        when (step) {
            VerifyStep.NAME -> "본인 확인을 위해\n이름을 입력해주세요"
            else -> "본인 확인을 위해\n정보를 입력해주세요"
        }
    }

    Box(Modifier.fillMaxSize()) {
        // BILLIM 배경 그라데이션
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
        )

        // 배경 장식 원형들
        Box(
            modifier = Modifier
                .offset(x = (-80).dp, y = 120.dp)
                .size(250.dp)
                .alpha(0.04f)
                .blur(50.dp)
                .clip(RoundedCornerShape(125.dp))
                .background(BillimGreenLight)
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 100.dp, y = (-40).dp)
                .size(200.dp)
                .alpha(0.06f)
                .blur(40.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(BillimGreen)
        )

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = (-40).dp, y = 100.dp)
                .size(180.dp)
                .alpha(0.03f)
                .blur(45.dp)
                .clip(RoundedCornerShape(90.dp))
                .background(BillimGreenDark)
        )

        // 메인 컨텐츠
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // 스크롤 가능한 컨텐츠 영역
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 32.dp)
            ) {
                Spacer(Modifier.height(60.dp))

                // 타이틀
                Text(
                    text = title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = BillimTextPrimary,
                    lineHeight = 38.sp
                )

                Spacer(Modifier.height(48.dp))

                /* ===== 완료된 블록들 ===== */
                if (step > VerifyStep.NAME) {
                    CompletedField("이름", name, isReviewMode = isAllCompleted)
                    Spacer(Modifier.height(20.dp))
                }

                if (step > VerifyStep.RRN) {
                    CompletedRrnField(rrn6, isReviewMode = isAllCompleted)
                    Spacer(Modifier.height(20.dp))
                }

                if (step > VerifyStep.CARRIER) {
                    CompletedField("통신사", carrierLabel, isReviewMode = isAllCompleted)
                    Spacer(Modifier.height(20.dp))
                }

                if (step > VerifyStep.PHONE) {
                    CompletedField("휴대폰 번호", formatPhone(phone), isReviewMode = isAllCompleted)
                    Spacer(Modifier.height(20.dp))
                }

                /* ===== 현재 입력 단계 ===== */
                if (!isAllCompleted) {
                    // 1) 이름 입력
                    AnimatedVisibility(
                        visible = step == VerifyStep.NAME,
                        enter = slideInVertically(initialOffsetY = { it / 3 }) + fadeIn(),
                        exit = slideOutVertically(targetOffsetY = { -it / 3 }) + fadeOut()
                    ) {
                        BillimInputBlock(
                            label = "이름",
                            value = name,
                            onValueChange = { name = it.trimStart() },
                            placeholder = "홍길동",
                            focusRequester = nameFR,
                            imeAction = if (isNameValid) ImeAction.Done else ImeAction.None,
                            keyboardType = KeyboardType.Text,
                            onImeDone = {
                                if (isNameValid) {
                                    step = VerifyStep.RRN
                                }
                            },
                            autoFocus = true,
                            keyboardController = keyboardController
                        )
                    }

                    // 2) 주민등록번호 입력
                    AnimatedVisibility(
                        visible = step == VerifyStep.RRN,
                        enter = slideInVertically(initialOffsetY = { it / 3 }) + fadeIn(),
                        exit = slideOutVertically(targetOffsetY = { -it / 3 }) + fadeOut()
                    ) {
                        Column(Modifier.fillMaxWidth()) {
                            Text(
                                "주민등록번호",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BillimTextSecondary
                            )
                            Spacer(Modifier.height(12.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(BillimSurface)
                                    .padding(horizontal = 20.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    BillimTextField(
                                        value = rrn6,
                                        onValueChange = {
                                            val digits = it.filter { c -> c.isDigit() }.take(6)
                                            rrn6 = digits
                                            if (digits.length == 6) {
                                                rrn1FR.requestFocus()
                                            }
                                        },
                                        placeholder = "123456",
                                        focusRequester = rrn6FR,
                                        imeAction = ImeAction.Next,
                                        keyboardType = KeyboardType.Number,
                                        onImeDone = {},
                                        modifier = Modifier.weight(1f)
                                    )

                                    Text(
                                        "—",
                                        fontSize = 18.sp,
                                        color = BillimTextSecondary,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )

                                    BillimTextField(
                                        value = rrn1,
                                        onValueChange = {
                                            val digit = it.filter { c -> c.isDigit() }.take(1)
                                            rrn1 = digit
                                        },
                                        placeholder = "1",
                                        focusRequester = rrn1FR,
                                        imeAction = if (isRrnValid) ImeAction.Done else ImeAction.None,
                                        keyboardType = KeyboardType.NumberPassword,
                                        onImeDone = {
                                            if (isRrnValid) {
                                                step = VerifyStep.CARRIER
                                            }
                                        },
                                        modifier = Modifier.width(60.dp),
                                        isPassword = true
                                    )
                                }
                            }

                            LaunchedEffect(Unit) {
                                delay(100)
                                rrn6FR.requestFocus()
                                keyboardController?.show()
                            }
                        }
                    }

                    // 3) 통신사 선택
                    AnimatedVisibility(
                        visible = step == VerifyStep.CARRIER,
                        enter = slideInVertically(initialOffsetY = { it / 3 }) + fadeIn(),
                        exit = slideOutVertically(targetOffsetY = { -it / 3 }) + fadeOut()
                    ) {
                        var expanded by rememberSaveable { mutableStateOf(false) }

                        Column(Modifier.fillMaxWidth()) {
                            Text(
                                "통신사",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BillimTextSecondary
                            )
                            Spacer(Modifier.height(12.dp))

                            Box {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(BillimSurface)
                                        .clickable { expanded = true }
                                        .padding(horizontal = 20.dp, vertical = 20.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = carrierLabel.ifEmpty { "통신사 선택" },
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = if (carrierLabel.isEmpty())
                                                BillimTextSecondary.copy(alpha = 0.5f)
                                            else
                                                BillimTextPrimary
                                        )
                                        Icon(
                                            Icons.Default.ArrowDropDown,
                                            contentDescription = null,
                                            tint = BillimGreen
                                        )
                                    }
                                }

                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false },
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .background(BillimSurface, RoundedCornerShape(14.dp))
                                ) {
                                    listOf(
                                        Carrier.SKT,
                                        Carrier.KT,
                                        Carrier.LGU
                                    ).forEach { carrier ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(
                                                    carrier.label,
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.Medium,
                                                    color = BillimTextPrimary
                                                )
                                            },
                                            onClick = {
                                                carrierLabel = carrier.label
                                                expanded = false
                                                step = VerifyStep.PHONE
                                            }
                                        )
                                    }
                                }
                            }

                            LaunchedEffect(Unit) {
                                delay(200)
                                expanded = true
                                keyboardController?.hide()
                            }
                        }
                    }

                    // 4) 휴대폰 번호 입력
                    AnimatedVisibility(
                        visible = step == VerifyStep.PHONE,
                        enter = slideInVertically(initialOffsetY = { it / 3 }) + fadeIn(),
                        exit = slideOutVertically(targetOffsetY = { -it / 3 }) + fadeOut()
                    ) {
                        BillimInputBlock(
                            label = "휴대폰 번호",
                            value = formatPhone(phone),
                            onValueChange = {
                                val digits = it.filter { c -> c.isDigit() }.take(11)
                                phone = digits

                                if (digits.length in 10..11) {
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                    step = VerifyStep.REVIEW
                                }
                            },
                            placeholder = "010-1234-5678",
                            focusRequester = phoneFR,
                            imeAction = if (isPhoneValid) ImeAction.Done else ImeAction.None,
                            keyboardType = KeyboardType.Number,
                            onImeDone = {
                                if (isPhoneValid) {
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                    step = VerifyStep.REVIEW
                                }
                            },
                            autoFocus = true,
                            keyboardController = keyboardController
                        )
                    }
                }

                Spacer(Modifier.height(120.dp))
            }

            // 하단 고정 버튼 (REVIEW 모드에서만 표시)
            if (isAllCompleted) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(horizontal = 32.dp)
                        .navigationBarsPadding()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        BillimGreenLight,
                                        BillimGreen,
                                        BillimGreenDark
                                    )
                                )
                            )
                    ) {
                        Button(
                            onClick = {
                                showTermsSheet = true
                            },
                            modifier = Modifier.fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text(
                                "확인",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }
        }

        // 약관 동의 BottomSheet
        if (showTermsSheet) {
            TermsAgreementBottomSheet(
                sheetState = sheetState,
                onDismiss = {
                    coroutineScope.launch {
                        sheetState.hide()
                        showTermsSheet = false
                    }
                },
                onConfirm = {
                    coroutineScope.launch {
                        sheetState.hide()
                        showTermsSheet = false
                        onComplete(name, rrn6, rrn1, carrierLabel, phone)
                    }
                }
            )
        }
    }
}

/* ===================== 약관 동의 BottomSheet ===================== */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TermsAgreementBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var allAgreed by rememberSaveable { mutableStateOf(false) }
    var term1 by rememberSaveable { mutableStateOf(false) }
    var term2 by rememberSaveable { mutableStateOf(false) }
    var term3 by rememberSaveable { mutableStateOf(false) }
    var term4 by rememberSaveable { mutableStateOf(false) }
    var term5 by rememberSaveable { mutableStateOf(false) }
    var term6 by rememberSaveable { mutableStateOf(false) }
    var term7 by rememberSaveable { mutableStateOf(false) }

    // 전체 동의 상태 업데이트
    LaunchedEffect(term1, term2, term3, term4, term5, term6, term7) {
        allAgreed = term1 && term2 && term3 && term4 && term5 && term6 && term7
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = BillimSurface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(BillimTextSecondary.copy(alpha = 0.3f))
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            // 타이틀
            Text(
                "BILLIM 이용을 위해 동의가 필요해요",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = BillimTextPrimary
            )

            Spacer(Modifier.height(16.dp))

            // 약관 동의 리스트
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
                    .verticalScroll(rememberScrollState())
            ) {
                // 전체 동의
                TermsCheckboxItem(
                    text = "모두 동의",
                    checked = allAgreed,
                    onCheckedChange = { checked ->
                        allAgreed = checked
                        term1 = checked
                        term2 = checked
                        term3 = checked
                        term4 = checked
                        term5 = checked
                        term6 = checked
                        term7 = checked
                    },
                    isHeader = true
                )

                Spacer(Modifier.height(8.dp))

                // 안내 문구
                Text(
                    text = "서비스 이용에 필수적인 최소한의 개인정보 수집 및 이용, 본인확인, 위치정보 수집 및 이용, 마케팅 정보 수신(선택), 맞춤형 광고 수신(선택)을 포함합니다.",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = BillimTextSecondary.copy(alpha = 0.7f),
                    lineHeight = 18.sp
                )

                Spacer(Modifier.height(20.dp))

                // 개별 약관들
                TermsCheckboxItem(
                    text = "(필수) 서비스 이용 약관",
                    checked = term1,
                    onCheckedChange = { term1 = it },
                    hasArrow = true
                )

                Spacer(Modifier.height(12.dp))

                TermsCheckboxItem(
                    text = "(필수) 개인정보 수집 및 이용",
                    checked = term2,
                    onCheckedChange = { term2 = it },
                    hasArrow = true
                )

                Spacer(Modifier.height(12.dp))

                TermsCheckboxItem(
                    text = "(필수) 위치기반서비스 이용약관",
                    checked = term3,
                    onCheckedChange = { term3 = it },
                    hasArrow = true
                )

                Spacer(Modifier.height(12.dp))

                TermsCheckboxItem(
                    text = "(필수) 만 14세 이상",
                    checked = term4,
                    onCheckedChange = { term4 = it },
                    hasArrow = true
                )

                Spacer(Modifier.height(12.dp))

                TermsCheckboxItem(
                    text = "(필수) 본인확인서비스 동의사항",
                    checked = term5,
                    onCheckedChange = { term5 = it },
                    hasArrow = true
                )

                Spacer(Modifier.height(12.dp))

                TermsCheckboxItem(
                    text = "(선택) 마케팅 정보 수신 동의",
                    checked = term6,
                    onCheckedChange = { term6 = it },
                    hasArrow = true
                )

                Spacer(Modifier.height(12.dp))

                TermsCheckboxItem(
                    text = "(선택) 맞춤형 광고 목적 개인정보 수집 및 이용",
                    checked = term7,
                    onCheckedChange = { term7 = it },
                    hasArrow = true
                )
            }

            Spacer(Modifier.height(24.dp))

            // 확인 버튼 (필수 약관만 체크하면 활성화)
            val canProceed = term1 && term2 && term3 && term4 && term5

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        if (canProceed) {
                            Brush.horizontalGradient(
                                colors = listOf(
                                    BillimGreenLight,
                                    BillimGreen,
                                    BillimGreenDark
                                )
                            )
                        } else {
                            Brush.horizontalGradient(
                                colors = listOf(
                                    BillimTextSecondary.copy(alpha = 0.3f),
                                    BillimTextSecondary.copy(alpha = 0.3f)
                                )
                            )
                        }
                    )
            ) {
                Button(
                    onClick = {
                        if (canProceed) {
                            onConfirm()
                        }
                    },
                    modifier = Modifier.fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(14.dp),
                    enabled = canProceed
                ) {
                    Text(
                        "확인",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                }
            }
        }
    }
}

/* ===================== 약관 체크박스 아이템 ===================== */
@Composable
private fun TermsCheckboxItem(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isHeader: Boolean = false,
    hasArrow: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 체크박스 (그린 원형 + 흰색 체크 아이콘)
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(
                    if (checked) CheckboxGreen else Color.Transparent
                )
                .then(
                    if (!checked) Modifier.border(
                        width = 2.dp,
                        color = BillimTextSecondary.copy(alpha = 0.3f),
                        shape = CircleShape
                    ) else Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            if (checked) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(Modifier.width(12.dp))

        // 텍스트
        Text(
            text = text,
            fontSize = if (isHeader) 16.sp else 14.sp,
            fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
            color = BillimTextPrimary,
            modifier = Modifier.weight(1f)
        )

        // 화살표 아이콘
        if (hasArrow) {
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = BillimTextSecondary.copy(alpha = 0.5f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

/* ===================== BILLIM 스타일 입력 블록 ===================== */
@Composable
private fun BillimInputBlock(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    focusRequester: FocusRequester,
    imeAction: ImeAction,
    keyboardType: KeyboardType,
    onImeDone: () -> Unit,
    autoFocus: Boolean = false,
    keyboardController: androidx.compose.ui.platform.SoftwareKeyboardController?
) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            label,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = BillimTextSecondary
        )
        Spacer(Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(BillimSurface)
                .padding(horizontal = 20.dp, vertical = 4.dp)
        ) {
            BillimTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = placeholder,
                focusRequester = focusRequester,
                imeAction = imeAction,
                keyboardType = keyboardType,
                onImeDone = onImeDone
            )
        }

        if (autoFocus) {
            LaunchedEffect(Unit) {
                delay(100)
                focusRequester.requestFocus()
                keyboardController?.show()
            }
        }
    }
}

/* ===================== BILLIM 스타일 텍스트필드 ===================== */
@Composable
private fun BillimTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    focusRequester: FocusRequester,
    imeAction: ImeAction,
    keyboardType: KeyboardType,
    onImeDone: () -> Unit,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        placeholder = {
            Text(
                placeholder,
                color = BillimTextSecondary.copy(alpha = 0.5f),
                fontSize = 16.sp
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = if (isFocused || value.isNotEmpty())
                BillimGreen
            else
                Color.Transparent,
            unfocusedIndicatorColor = if (value.isNotEmpty())
                BillimGreen.copy(alpha = 0.5f)
            else
                BillimTextSecondary.copy(alpha = 0.2f),
            cursorColor = BillimGreen,
            focusedTextColor = BillimTextPrimary,
            unfocusedTextColor = BillimTextPrimary
        ),
        textStyle = androidx.compose.ui.text.TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ),
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { isFocused = it.isFocused },
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onDone = { onImeDone() }
        )
    )
}

/* ===================== 완료된 필드 표시 ===================== */
@Composable
private fun CompletedField(label: String, value: String, isReviewMode: Boolean) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            label,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = if (isReviewMode) BillimTextSecondary else BillimTextSecondary.copy(alpha = 0.7f)
        )
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (isReviewMode)
                        BillimSurface
                    else
                        BillimSurface.copy(alpha = 0.6f)
                )
                .padding(horizontal = 18.dp, vertical = 14.dp)
        ) {
            Text(
                value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = if (isReviewMode) BillimTextPrimary else BillimTextPrimary.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
private fun CompletedRrnField(rrn6: String, isReviewMode: Boolean) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            "주민등록번호",
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = if (isReviewMode) BillimTextSecondary else BillimTextSecondary.copy(alpha = 0.7f)
        )
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (isReviewMode)
                        BillimSurface
                    else
                        BillimSurface.copy(alpha = 0.6f)
                )
                .padding(horizontal = 18.dp, vertical = 14.dp)
        ) {
            Text(
                "$rrn6 - •",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = if (isReviewMode) BillimTextPrimary else BillimTextPrimary.copy(alpha = 0.6f)
            )
        }
    }
}

/* ===================== 유틸리티 함수 ===================== */
private fun formatPhone(digits: String): String {
    val d = digits.filter { it.isDigit() }
    return when {
        d.isEmpty() -> ""
        d.length <= 3 -> d
        d.length <= 7 -> "${d.substring(0, 3)}-${d.substring(3)}"
        else -> "${d.substring(0, 3)}-${d.substring(3, 7)}-${d.substring(7)}"
    }
}
