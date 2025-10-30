# 🚀 BILLIM-App (물품 대여 플랫폼 서비스)

> 신뢰 기반 개인 간 물품 대여 플랫폼 서비스
> 
> 
> Android · Jetpack Compose · Kotlin 기반
> 

## 🧩 프로젝트 소개

BorrowItApp은 개인 간 물건을 대여·빌림 과정에서 발생할 수 있는 불신과 불편함을 해결하기 위해 **본인 인증, 대여 내역 관리, 커뮤니티 연결 기능을 통합한 신뢰형 플랫폼 입니다.**

모두 지원하는 통합형 대여 서비스입니다.

사용자는 **휴대폰 본인인증 과정을 통해 신뢰 기반 거래**를 진행할 수 있으며,

신뢰할 수 있는 상대와 안전한 대여 거래를 진행할 수 있습니다..

## 📌 프로젝트 개요

- **프로젝트명:** BILLIM-App (물품 대여 서비스)
- **개발 목표:** 개인 간 물품 대여 및 본인 인증 기반의 신뢰형 대여 플랫폼 서비스 구축
- **핵심 기능:** 본인 인증 / 대여 관리 / 위치 인증 / 커뮤니티 기능
- **프레임워크:** Jetpack Compose (Kotlin)
- **개발 인원:** 프론트엔드팀 2명
- 진행기간: 2025.03 ~ 현재 (진행 중)

## 🧠 프론트엔드 주요 역할

✅ **SingDu**

- VerifyFlowScreen (본인 인증 UI) 설계 및 Compose 애니메이션 구현
- 전체적인 앱의 기술 구현
- 전체 README / PROGRESS 문서화 담당

✅ **rainwox00**

- 앱 UI 및 화면 디자인 구현
- Navigation 구조 설계 및 화면 전환 테스트
- 기본 컴포넌트 구조화 및 코드 스타일 정리

## ⚙️ 주요 기술 스택

| 구분 | 기술 |
| --- | --- |
| **Frontend** | Kotlin, Jetpack Compose, Material3 |
| **State Management** | Zustand |
| **UI Library** | Material3, AnimatedVisibility, FocusRequester |
| **Navigation** | Jetpack Navigation for Compose |
| **Version Control** | Git / GitHub |
| **Build Tool** | Android Studio, Gradle |

## 📂 현재 프로젝트 디렉토리 구조 (2025-10-30 기준)

```
BorrowItApp/
├── .gradle/
├── .idea/
├── .kotlin/
├── app/
│   ├── build/
│   └── src/
│       ├── androidTest/
│       └── main/
│           └── java/
│               └── com/
│                   └── example/
│                       └── borrowitapp/
│                           ├── ui/
│                           │   ├── common/
│                           │   │   ├── AppBackground.kt             # 공통 배경 컴포넌트
│                           │   │   ├── PrimaryButton.kt             # 주요 버튼 UI 정의
│                           │   │   └── SelectChip.kt                # 선택용 Chip UI 컴포넌트
│                           │   │
│                           │   ├── home/
│                           │   │   └── HomeScreen.kt                # BILLM 메인 홈 화면
│                           │   │
│                           │   ├── map/
│                           │   │   └── MapTabScreen.kt              # 지도 탭 화면 (임시 지도 이미지 적용)
│                           │   │
│                           │   ├── location/
│                           │   │   ├── LocationScreen.kt            # 위치 인증 화면
│                           │   │   └── LocationVerificationBottomSheet.kt  # 위치 인증용 하단 시트
│                           │   │
│                           │   ├── navigation/
│                           │   │   ├── AppNavGraph.kt               # 전체 화면 네비게이션 그래프
│                           │   │   ├── BottomBar.kt                 # 하단 네비게이션 바
│                           │   │   └── Screen.kt                    # 화면 enum / route 관리
│                           │   │
│                           │   ├── theme/
│                           │   │   ├── Color.kt                     # 앱 시그니처 컬러 팔레트
│                           │   │   ├── Dimens.kt                    # 여백 및 크기 단위 정의
│                           │   │   ├── Shape.kt                     # 라운드, 모서리 등 Shape 스타일
│                           │   │   ├── Theme.kt                     # 전체 테마 구성
│                           │   │   └── Type.kt                      # 폰트/타이포그래피 설정
│                           │   │
│                           │   ├── verify/
│                           │   │   ├── VerifyContract.kt            # 인증 단계 Enum 및 데이터 구조
│                           │   │   └── VerifyFlowScreen.kt          # 본인 인증 단계별 화면(UI)
│                           │   │
│                           │   └── MainActivity.kt                  # 메인 진입 Activity (Jetpack Compose Host)
│                           │
│                           └── myapplication/
│                               └── ui/
│                                   └── MainActivity.kt              # 별도 테스트용 샘플 Activity
│
├── build.gradle
├── settings.gradle
└── README.md

```

---

## 📘 구조 설명

### 🧱 **공통 컴포넌트 (common/)**

- 앱 전체에서 공통으로 사용하는 UI 요소.
- `AppBackground.kt`: 화면 배경 일관화.
- `PrimaryButton.kt`: 앱 전반에서 사용하는 대표 버튼 스타일 정의.
- `SelectChip.kt`: 선택형 버튼(필터/옵션 선택 등) UI.

---

### 📍 **위치 인증 (location/)**

- 사용자의 위치 기반 인증 관련 화면 구성.
- `LocationScreen.kt`: GPS 권한 요청 및 지도 화면.
- `LocationVerificationBottomSheet.kt`: 위치 인증 시 표시되는 BottomSheet.

---

### 🧭 **네비게이션 (navigation/)**

- 앱 화면 전환 및 라우팅 관리.
- `AppNavGraph.kt`: Compose 기반 네비게이션 그래프.
- `BottomBar.kt`: 하단 탭 네비게이션 UI
    
    → 하단 메뉴 5개(Home / Map / Post / Message / My) 구성
    
    → 중앙 “글쓰기” 버튼은 항상 BrandGreen 유지, 완전한 CircleShape 적용
    
    → 아이콘 크기(26dp), 텍스트(12sp), 간격(6dp) 통일
    
- `Screen.kt`: 화면 enum 관리 (Navigation-safe routing).

---

### 🎨 **테마 (theme/)**

- UI 전체 스타일 정의 및 통합 관리.
- `Color.kt`: 시그니처 컬러(#1E88E5) 포함 색상 팔레트.
- `Dimens.kt`: 공통 마진, 패딩 등 크기 단위.
- `Shape.kt`: 버튼, 카드 등 모서리 스타일.
- `Type.kt`: 폰트, 텍스트 스타일 정의.
- `Theme.kt`: MaterialTheme 세트로 통합.

---

### 🔐 **본인 인증 (verify/)**

- 신뢰 기반 거래를 위한 사용자 본인 인증 프로세스.
- `VerifyContract.kt`: 인증 단계 Enum(`VerifyStep`) 정의 및 데이터 컨트랙트.
- `VerifyFlowScreen.kt`: 이름 → 주민번호 → 통신사 → 전화번호 순의 단계형 인증 UI.

---

### 🏠 **메인 홈 화면 (home/)**

- BILLM 앱의 홈 화면 (앱 실행 시 첫 진입화면).
- 로고, 위치 선택, 검색바, 카테고리, 추천/주변 물품 리스트로 구성.
- `HomeScreen.kt`:
    - UI 전체를 LazyColumn으로 구성하여 스크롤 구조 정리
    - 추천 물품과 주변 물품 카드형 UI 추가
    - 카드 이미지 비율 `aspectRatio(16/9f)`로 조정
    - `BrandGreen`과 `TextPrimary`를 중심으로 디자인 통일
    - `OutlinedTextField` → 캡슐형 Surface(56dp)로 리디자인
    - 상단 로고와 위치칩 UI를 VerifyFlow/MapTabScreen과 통일

### 🗺️ **지도 탭 화면 (map/)**

- “내 주변” 중심의 지도 UI 화면.
- `MapTabScreen.kt`:
    - VerifyFlowScreen과 디자인 톤, 라운드, 색상 완전 통일
    - 지도 API 미연동 상태 → 임시 지도 이미지로 대체
    - 주변 물품 카드 비율/정렬 수정 (이미지 잘림 해결)
    - 상단 로고 + 위치칩 + “내 주변” 텍스트 정렬 개선
    - 이미지 리소스(PNG)로 실제 상품 사진 삽입 가능하도록 수정

### ⚙️ **메인 진입점**

- `MainActivity.kt`: Compose Host Activity.
    - `setContent { BorrowItApp() }`로 앱 전체 UI 시작점 구성.

## 🎨 주요 화면 및 기능 구현 현황

### 🔹 1. 본인 인증 (VerifyFlowScreen)

- 이름 → 주민번호 → 통신사 → 번호 단계별 입력 플로우
- 자동 키보드 활성화 및 입력 포커싱 (`FocusRequester`)
- TextField 밑줄 색상 변경 (입력 시 시그니처 컬러 전환)
- 입력 시 키보드 상단에 “확인” 버튼 자동 표시
- 단계별 전환 애니메이션 (`AnimatedVisibility`)
- 입력 완료 시 키보드 자동 비활성화
- 문구 전환: **“입력한 정보를 확인해 주세요”**

📁 관련 파일

```
app/src/main/java/com/example/borrowitapp/ui/verify/
├── VerifyFlowScreen.kt
├── VerifyViewModel.kt
└── VerifyContract.kt

```

---

### 🔹 2. 위치 인증 (LocationVerification)

- GPS 권한 요청 및 현재 위치 인증 화면
- 하단 시트(`BottomSheet`)를 통한 인증 단계 UX
- 사용자 위치 기반 신뢰도 표시 기능 (추후 확장 예정)

📁 관련 파일

```
app/src/main/java/com/example/borrowitapp/ui/location/
├── LocationScreen.kt
└── LocationVerificationBottomSheet.kt

```

---

### 🔹 3. 네비게이션 / UI 구조

- Compose Navigation 기반 화면 전환 (`AppNavGraph.kt`)
- Verify → Home 전환 로직 테스트 완료
- 시그니처 컬러 `#1E88E5`로 통일
- UI 여백, 폰트, 라운드, 패딩 일관화

📁 관련 파일

```
app/src/main/java/com/example/borrowitapp/ui/navigation/AppNavGraph.kt
app/src/main/java/com/example/borrowitapp/ui/theme/Color.kt

```

---

### 🔹 4. 메인 홈 화면 (HomeScreen)

- BILLM 홈 UI 신규 구현 완료
- 상단 로고 + 위치 설정 버튼 디자인 통일
- 검색바(OutlinedTextField → 캡슐형 Surface) 리디자인
- 추천 물품 / 주변 물품 카드 추가
- 카드 이미지 비율 조정 (`aspectRatio(16/9f)`)
- LazyColumn / LazyRow 패딩 및 간격 통합

📁 관련 파일

```
app/src/main/java/com/example/borrowitapp/ui/home/
└── HomeScreen.kt

```

---

### 🔹 5. 지도 탭 (MapTabScreen)

- VerifyFlow와 이질감 해결 (톤·폰트·라운드 통일)
- 지도 영역 API 미연동 상태 → **임시 지도 이미지 적용**
- 주변 물품 카드 UI 및 비율 조정
- 상단 위치칩, 로고, “내 주변” 타이틀 스타일 통일

📁 관련 파일

```
app/src/main/java/com/example/borrowitapp/ui/map/
└── MapTabScreen.kt

```

---

### 🔹 6. 하단 네비게이션 (BottomBar)

- 하단 탭 UI 비율 및 정렬 재조정
- 아이콘 크기(26dp), 텍스트 크기(12sp), 간격(6dp) 통일
- 텍스트 짤림 현상 수정
- 중앙 “글쓰기” 버튼을 항상 BrandGreen으로 유지
- 완전한 CircleShape + Border(3dp, BrandGreen) 적용

📁 관련 파일

```
app/src/main/java/com/example/borrowitapp/ui/navigation/
└── BottomBar.kt

```

## 🧠 프론트엔드 구현 현황 요약

| 항목 | 상태 | 설명 |  |
| --- | --- | --- | --- |
| VerifyFlow | ✅ 완료 | 단계별 전환 / 키보드 자동 제어 |  |
| UI/UX 디자인 | ✅ 완료 | 컬러, 폰트, 애니메이션 통합 |  |
| Location 인증 | 🔄 진행 중 | GPS 권한 및 위치 검증 화면 |  |
| Spring 연동 | 🔜 예정 |  |  |
| 대여 글 작성 | 🔜 예정 |  |  |
| 커뮤니티 피드 | 🔜 예정 |  |  |
| 홈 화면 구현 | ✅ 완료 |  |  |
| 동네 지도 화면 구현 | ✅ 완료 |  |  |

## 🧭 개발 규칙

- 모든 커밋 전 `git pull origin main` 필수
- 새로운 기능 작업 시 `feature/[기능명]` 브랜치 생성
- PR 전 최소 1회 코드 리뷰 진행
- `.gradle`, `.idea`, `node_modules` 등 불필요 파일 커밋 금지
- `package-lock.json` 및 `gradle-wrapper.properties` 유지

## 🚀 향후 계획

- [ ]  Spring Boot API 연동 (본인 인증 / 위치 검증 / 대여 내역)
- [ ]  커뮤니티 피드 UI 구현
- [ ]  대여 알림 및 자동 푸시 기능
- [ ]  사용자 신뢰 지수 시각화
- [ ]  Play Store 배포 준비
- [ ]  대여 글 작성 화면 구현

## 🕓 프론트엔드 업데이트 로그 및 작업 타임라인

📅 **2025-10-05 ~ 2025-10-11**

- VerifyFlow UI 기본 구조 완성
- 확인 버튼 키보드 상단 표시 구현
- 앱 전체 UI 디자인 정의
- 모든 입력 단계 자동 전환 및 안내 문구 구현
- Location 인증 화면 추가
- 전체 UI 통합 / 시그니처 컬러 적용 완료

![BILLM앱 인증화면.png](attachment:f560592f-1146-472c-9058-780e19f741ee:BILLM앱_인증화면.png)

📅 **2025-10-27 ~ 2025-10-31**

### 🧭 네비게이션 구조 정비

- 기존 개별 화면 호출 구조 → `AppNavGraph` 기반 **NavHost 통합**
- `MainActivity` → `AppNavGraph` 연결 및 **HomeScreen 기본 시작화면** 설정
- 하단바(`BillmBottomBar`) 클릭 시 **각 route(Screen.Home / Screen.Map 등)** 로 정상 이동 구현
- `navigateSingleTop()` 로 뒤로가기 스택 꼬임 방지 처리

---

### 🏠 HomeScreen (BILLM 메인 홈화면)

- 전체 레이아웃 Compose 리빌드 및 **프로젝트 디자인 시스템 통일**
- `BrandGreen`, `TextPrimary`, `AppShapes`, `Dimen` 등 토큰 기반 스타일로 수정
- 상단 로고 + 위치 선택 버튼 디자인 개선
- 검색바 `OutlinedTextField` → **Surface 캡슐형(56dp, 라운드 28dp)** 변경
- 카테고리 섹션, 추천 물품, 주변 물품 섹션 구조 재정리
- **카드 레이아웃 비율 개선 (aspectRatio 16:9)** → 이미지가 잘리지 않게 수정
- LazyColumn 간격/패딩 통일 (`horizontal = 24dp`, `verticalGap = 12~16dp`)

---

### 🗺️ MapTabScreen (지도 탭 화면)

- 기존 VerifyFlow와 톤 차이 수정 → **BILLM 브랜드 UI로 리디자인**
- 시그니처 컬러 및 라운드, 섀도우, 간격 등 통일
- 지도 부분은 API 미연동 상태로 **임시 지도 이미지 대체**
- 주변 아이템 카드 비율 조정 → 이미지/텍스트 잘림 해결
- 상단 지역칩 + 로고 비율 및 정렬 개선

---

### 🧭 BottomBar (하단 내비게이션 바)

- 전체 구조 및 비율 리팩토링
- 텍스트 짤림/정렬 문제 해결
- 중앙 `글쓰기` 버튼을 **항상 시그니처 색상 BrandGreen 유지**
- 
    - 아이콘 외곽 완전 원형으로 수정 (`CircleShape + border(3.dp, BrandGreen)`)
- 모든 아이콘 크기 균형 재조정 (아이콘 26dp, 라벨 12sp, 간격 6dp)

---

### 🧩 프로젝트 구조 통합

- `Screen.kt` 에 route 정의 (`home`, `map`, `post`, `message`, `my`)
- 모든 route가 `AppNavGraph` 내 `NavHost` 에 등록됨
- **Scaffold(bottomBar + NavHost)** 구조 완성
- 실행 시 `HomeScreen` → `MapTabScreen` 전환 정상 작동

![Screenshot_20251030_153109.png](attachment:b72cdba6-680d-4264-86ce-4d3eb0e1b49d:Screenshot_20251030_153109.png)

![Screenshot_20251030_153123.png](attachment:a0ddd2cd-1faf-4cb0-9c6a-efdd8522e6da:Screenshot_20251030_153123.png)

## 📄 문서 정보

- 작성자: **SingDu**
- 팀명: **BILLIM-App 프론트엔드 개발팀**
- 문서 버전: **v1.1.0**
- 최종 수정일: **2025-10-29**
