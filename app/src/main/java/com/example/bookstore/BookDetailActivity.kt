package com.example.bookstore

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BookDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        // 1. 화면에 있는 뷰(View)들 찾아오기
        val ivCover: ImageView = findViewById(R.id.iv_detail_cover)
        val tvTitle: TextView = findViewById(R.id.tv_detail_title)
        val tvAuthor: TextView = findViewById(R.id.tv_detail_author)
        val tvPrice: TextView = findViewById(R.id.tv_detail_price)

        // 2. Intent로 전달받은 데이터 꺼내기
        // 데이터가 안 넘어왔을 경우를 대비해 기본값(0 또는 "")을 설정합니다.
        val bookImageId = intent.getIntExtra("bookImage", 0)
        val bookTitle = intent.getStringExtra("bookTitle") ?: ""
        val bookAuthor = intent.getStringExtra("bookAuthor") ?: ""
        val bookPrice = intent.getIntExtra("bookPrice", 0)

        // 3. 화면에 데이터 띄우기
        if (bookImageId != 0) ivCover.setImageResource(bookImageId)
        tvTitle.text = bookTitle
        tvAuthor.text = getString(R.string.label_author) + " " + bookAuthor
        tvPrice.text = getString(R.string.label_price) + " " + bookPrice + getString(R.string.unit_won)

        // 버튼 클릭 이벤트 구현

        // 1. 버튼 3개 찾아오기
        val btnAddCart: android.widget.Button = findViewById(R.id.btn_add_cart)
        val btnRelated: android.widget.Button = findViewById(R.id.btn_related)
        val btnShare: android.widget.Button = findViewById(R.id.btn_share)

        // 2. 연관도서 버튼 (미구현 알림)
        val underConstructionListener = android.view.View.OnClickListener {
            android.widget.Toast.makeText(this, getString(R.string.toast_under_construction), android.widget.Toast.LENGTH_SHORT).show()
        }
        btnRelated.setOnClickListener(underConstructionListener)

        // 3. 공유하기 버튼 (링크 복사 눈속임 알림)
        btnShare.setOnClickListener {
            android.widget.Toast.makeText(this, getString(R.string.toast_link_copied), android.widget.Toast.LENGTH_SHORT).show()
        }

        // 4. 장바구니 저장 버튼 (가산점 AlertDialog(팝업창) 기능 포함)
        btnAddCart.setOnClickListener {
            // 1. SharedPreferences 열기
            val sharedPref = getSharedPreferences("BookStorePrefs", MODE_PRIVATE)

            // 2. 기존 장바구니 목록 불러와서 복사하기 (Set 형태)
            val oldCart = sharedPref.getStringSet("cartItems", setOf()) ?: setOf()
            val newCart = HashSet(oldCart) // 안전하게 복사

            // 3. 현재 책 제목을 장바구니에 추가하고 다시 저장
            newCart.add(bookTitle)
            sharedPref.edit().putStringSet("cartItems", newCart).apply()

            // 4. 가산점 +2점 획득을 위한 AlertDialog(팝업창) 띄우기
                androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("장바구니")
                .setMessage("'$bookTitle' 도서가 장바구니에 담겼습니다.")
                .setPositiveButton("확인") { dialog, _ ->
                    dialog.dismiss() // 확인 누르면 창 닫기
                }
                .show()
        }

        // 가산점: SharedPreferences에 마지막으로 본 도서 정보 저장하기
        // 메인 화면의 "나의 페이지" 버튼을 눌러 확인 가능
        // "BookStorePrefs"라는 이름의 저장소 파일을 엽니다.
        val sharedPref = getSharedPreferences("BookStorePrefs", MODE_PRIVATE)
        val editor = sharedPref.edit()

        // 넘겨받았던 책 정보들을 저장소에 기록합니다.
        editor.putInt("lastBookImage", bookImageId)
        editor.putString("lastBookTitle", bookTitle)
        editor.putString("lastBookAuthor", bookAuthor)
        editor.putInt("lastBookPrice", bookPrice)

        // apply()를 호출해야 실제로 저장됩니다.
        editor.apply()
    }
}