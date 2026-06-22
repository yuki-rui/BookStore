package com.example.bookstore

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 시작하기(로그인) 버튼 찾기
        val btnSubmit: ImageView = findViewById(R.id.btn_login_submit)

        // 버튼 클릭 이벤트
        btnSubmit.setOnClickListener {
            // 로그인 성공 메시지 띄우기 (실제 로그인 DB 구현을 하지 않고 무조건 로그인 성공)
            Toast.makeText(this, getString(R.string.toast_login_success), Toast.LENGTH_SHORT).show()

            // 로그인 화면 닫기 (자동으로 이전 화면인 메인 화면으로 돌아감)
            finish()
        }
    }
}