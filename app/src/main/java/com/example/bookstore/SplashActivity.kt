package com.example.bookstore

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 스플래시 화면 찾기.
        setContentView(R.layout.activity_splash)

        // Handler를 사용하여 3초(3000ms) 뒤에 실행될 코드를 예약합니다.
        Handler(Looper.getMainLooper()).postDelayed({
            // 메인 화면으로 이동하는 Intent 생성
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // 메인 화면으로 넘어간 뒤, 뒤로 가기 버튼을 눌렀을 때 스플래시 화면이 다시 나오면 안 되므로 종료합니다
            finish()
        }, 3000)
    }
}