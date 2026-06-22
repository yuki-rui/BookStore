package com.example.bookstore

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

// 이미지 리스트(Int 배열)를 받아서 화면에 그려주는 어댑터
class BannerAdapter(private val bannerList: List<Int>) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.iv_banner_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        // item_banner.xml을 불러옵니다
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        // 현재 페이지에 맞는 이미지를 띄워줍니다
        holder.imageView.setImageResource(bannerList[position])

        holder.imageView.setOnClickListener {
            val context = holder.imageView.context

            if (position == 0) {
                // 1. 첫 번째 배너 (이달의 추천도서) 클릭 시
                val intent = Intent(context, BookListActivity::class.java)
                // "추천 도서만 보여줘!" 라는 꼬리표를 달아서 보냅니다.
                intent.putExtra("SHOW_TYPE", "RECOMMEND")
                context.startActivity(intent)
            } else {
                // 2. 나머지 배너 클릭 시 (토스트 메시지 출력)
                android.widget.Toast.makeText(
                    context,
                    context.getString(R.string.toast_under_construction),
                    android.widget.Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return bannerList.size // 총 배너 개수
    }
}