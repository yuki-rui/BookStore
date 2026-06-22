package com.example.bookstore

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2 // 배너 기능을 위한 추가

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. 기본 화면 설정
        setContentView(R.layout.activity_main)

        // 2. 뷰페이저(배너) 찾기
        val viewPager: ViewPager2 = findViewById(R.id.viewpager_banner)

        // 3. 사용할 배너 이미지 3개 리스트 만들기
        val bannerImages = listOf(
            R.drawable.books_banner,
            R.drawable.menu_banner1,
            R.drawable.menu_banner2
        )

        // 4. 뷰페이저에 어댑터 연결하기
        viewPager.adapter = BannerAdapter(bannerImages)

        // 1번 메뉴 버튼(도서목록) 찾기
        val btnMenu1: ImageView = findViewById(R.id.btn_menu_1)

        // 버튼을 클릭했을 때의 동작 설정
        btnMenu1.setOnClickListener {
            // BookListActivity로 이동하는 Intent 생성 및 실행
            val intent = Intent(this, BookListActivity::class.java)
            startActivity(intent)
        }

        // 2번 메뉴 버튼 (E-Book) - 도서목록과 똑같은 화면으로 재사용 (눈속임)
        val btnMenu2: ImageView = findViewById(R.id.btn_menu_2)
        btnMenu2.setOnClickListener {
            val intent = Intent(this, BookListActivity::class.java)
            startActivity(intent)
        }

        // 마지막으로 본 도서를 나의 페이지에서 확인
        val btnMenu4: ImageView = findViewById(R.id.btn_menu_4)
        btnMenu4.setOnClickListener {
            // 1. 저장소(SharedPreferences) 열기
            val sharedPref = getSharedPreferences("BookStorePrefs", MODE_PRIVATE)

            // 2. 저장된 "마지막 도서 제목" 꺼내보기 (없으면 null 반환)
            val lastBookTitle = sharedPref.getString("lastBookTitle", null)

            if (lastBookTitle != null) {
                // 3-1. 저장된 기록이 있다면 상세 화면(BookDetailActivity)으로 이동!
                val intent = Intent(this, BookDetailActivity::class.java)

                // 저장소에 있던 정보들을 다시 꺼내서 Intent에 담아줍니다.
                intent.putExtra("bookImage", sharedPref.getInt("lastBookImage", 0))
                intent.putExtra("bookTitle", lastBookTitle)
                intent.putExtra("bookAuthor", sharedPref.getString("lastBookAuthor", ""))
                intent.putExtra("bookPrice", sharedPref.getInt("lastBookPrice", 0))

                startActivity(intent)
            } else {
                // 3-2. 저장된 기록이 없다면 (앱을 깔고 책을 한 번도 안 봤다면) 토스트 띄우기
                android.widget.Toast.makeText(this, getString(R.string.toast_no_recent_book), android.widget.Toast.LENGTH_SHORT).show()
            }
        }

        // 3,5,6번 메뉴 버튼들 찾기
        val btnMenu3: ImageView = findViewById(R.id.btn_menu_3)
        val btnMenu5: ImageView = findViewById(R.id.btn_menu_5)
        val btnMenu6: ImageView = findViewById(R.id.btn_menu_6)

        // 토스트 메시지를 띄우는 '공통 클릭 이벤트' 만들기
        val underConstructionListener = android.view.View.OnClickListener {
            android.widget.Toast.makeText(
                this,
                getString(R.string.toast_under_construction),
                android.widget.Toast.LENGTH_SHORT
            ).show()
        }

        // 3,5,6번 버튼에 공통 클릭 이벤트 달아주기
        btnMenu3.setOnClickListener(underConstructionListener)
        btnMenu5.setOnClickListener(underConstructionListener)
        btnMenu6.setOnClickListener(underConstructionListener)

        // 툴바 구현

        // 1. 툴바 찾기
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

        // 2. 툴바에 메뉴(toolbar_menu)를 집어넣기
        toolbar.inflateMenu(R.menu.toolbar_menu)

        // 3. 메뉴를 클릭했을 때의 동작 설정
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_search -> {
                    Toast.makeText(this, getString(R.string.toast_search_clicked), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_settings -> {
                    Toast.makeText(this, getString(R.string.toast_settings_clicked), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_cs -> {
                    // 고객센터 미구현 메시지
                    Toast.makeText(this, getString(R.string.toast_under_construction), Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_login -> {
                    // 로그인 클릭 시 LoginActivity로 이동
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    // 화면이 다시 보일 때마다 실행되는 함수 (장바구니 배지 숫자 업데이트용)
    override fun onResume() {
        super.onResume()

        // 1. 하단 네비게이션 뷰 찾기 (activity_main.xml에 있는 ID와 맞춰주세요!)
        val bottomNav = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation) // ID가 다르면 수정 필요

        // 2. 장바구니에 담긴 개수 확인
        val sharedPref = getSharedPreferences("BookStorePrefs", MODE_PRIVATE)
        val cartItems = sharedPref.getStringSet("cartItems", setOf()) ?: setOf()

        // 3. 개수에 따라 빨간 배지(Badge) 띄우기
        val badge = bottomNav.getOrCreateBadge(R.id.bottom_nav_cart) // bottom_nav_menu.xml의 장바구니 ID
        if (cartItems.isNotEmpty()) {
            badge.isVisible = true
            badge.number = cartItems.size // 준비하신 빨간 네모 이미지와 똑같은 효과!
        } else {
            badge.isVisible = false
        }

        // 4. 하단 메뉴 클릭 이벤트 처리
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_nav_cart -> {
                    // 장바구니 클릭 시 BookListActivity 재활용 ("CART" 꼬리표 달기)
                    val intent = Intent(this, BookListActivity::class.java)
                    intent.putExtra("SHOW_TYPE", "CART")
                    startActivity(intent)
                    true
                }
                else -> {
                    // 나머지 하단 메뉴는 미구현 토스트 띄우기
                    android.widget.Toast.makeText(this@MainActivity, getString(R.string.toast_under_construction), android.widget.Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }
    }
}