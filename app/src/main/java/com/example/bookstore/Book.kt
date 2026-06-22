package com.example.bookstore

// 책 1권의 정보를 담는 데이터 모델
data class Book(
    val imageResId: Int, // 표지 이미지 리소스 ID (예: R.drawable.books_book1)
    val title: String,   // 책 제목
    val author: String,  // 저자
    val price: Int       // 가격
)