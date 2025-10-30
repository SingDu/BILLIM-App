package com.example.borrowitapp.ui.verify

/** 진행 단계 - 하나의 파일에서만 정의합니다. */
enum class VerifyStep {
    NAME, RRN, CARRIER, PHONE, REVIEW
}

enum class Carrier(val label: String) {
    SKT("SKT"),
    KT("KT"),
    LGU("LG U+"),
    MVNO("알뜰폰");

    companion object {
        fun fromLabel(label: String): Carrier? =
            entries.firstOrNull { it.label == label }
    }
}
