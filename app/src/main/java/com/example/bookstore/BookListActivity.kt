package com.example.bookstore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class BookListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_list)

        // 1. RecyclerView 찾기
        val rvBookList: RecyclerView = findViewById(R.id.rv_book_list)

        // 2. 6권의 도서 전체 데이터 만들기
        val allBooks = listOf(
            Book(R.drawable.books_book1, getString(R.string.book1_title), getString(R.string.book1_author), 25000),
            Book(R.drawable.books_book2, getString(R.string.book2_title), getString(R.string.book2_author), 22000),
            Book(R.drawable.books_book3, getString(R.string.book3_title), getString(R.string.book3_author), 24000),
            Book(R.drawable.books_book4, getString(R.string.book4_title), getString(R.string.book4_author), 26000),
            Book(R.drawable.books_recommended1, getString(R.string.book5_title), getString(R.string.book5_author), 20000),
            Book(R.drawable.books_recommenede2, getString(R.string.book6_title), getString(R.string.book6_author), 18000)
        )

        // 3. 넘어온 꼬리표(Intent) 확인하기
        val showType = intent.getStringExtra("SHOW_TYPE")

        // + 장바구니 데이터 불러오기
        val sharedPref = getSharedPreferences("BookStorePrefs", MODE_PRIVATE)
        val cartItems = sharedPref.getStringSet("cartItems", setOf()) ?: setOf()

        // 4. 꼬리표에 따라 보여줄 리스트 결정하기 (when 문으로 깔끔하게 정리)
        val displayBooks = when (showType) {
            "RECOMMEND" -> {
                allBooks.filter {
                    it.imageResId == R.drawable.books_recommended1 || it.imageResId == R.drawable.books_recommenede2
                }
            }
            "CART" -> {
                // 장바구니에 저장된 제목(cartItems)과 일치하는 책만 걸러내기
                allBooks.filter { cartItems.contains(it.title) }
            }
            else -> allBooks // 기본 도서목록
        }

        // 5. 결정된 리스트(displayBooks)를 어댑터에 연결하기
        val adapter = BookAdapter(displayBooks)
        rvBookList.adapter = adapter
    }
}