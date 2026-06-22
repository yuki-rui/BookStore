package com.example.bookstore

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(private val bookList: List<Book>) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    // item_book.xml 안에 있는 이미지뷰와 텍스트뷰를 찾아주는 역할
    inner class BookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivCover: ImageView = view.findViewById(R.id.iv_book_cover)
        val tvTitle: TextView = view.findViewById(R.id.tv_book_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        // item_book.xml을 화면에 그릴 준비를 합니다
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        // 현재 순서(position)에 맞는 책 데이터를 가져와서 화면에 세팅합니다
        val currentBook = bookList[position]
        holder.ivCover.setImageResource(currentBook.imageResId)
        holder.tvTitle.text = currentBook.title

        // holder.itemView는 책 1권이 들어있는 카드 전체(영역)를 의미합니다.
        holder.itemView.setOnClickListener {
            // 1. 현재 클릭된 아이템의 Context(화면 정보)를 가져옵니다.
            val context = holder.itemView.context

            // 2. 이동할 목적지(BookDetailActivity)를 설정하는 Intent를 만듭니다.
            val intent = Intent(context, BookDetailActivity::class.java)

            // 3. Intent(택배 상자)에 책의 상세 정보들을 담습니다 (putExtra).
            intent.putExtra("bookImage", currentBook.imageResId)
            intent.putExtra("bookTitle", currentBook.title)
            intent.putExtra("bookAuthor", currentBook.author)
            intent.putExtra("bookPrice", currentBook.price)

            // 4. 정보가 담긴 Intent를 들고 상세 화면으로 이동합니다.
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return bookList.size // 총 책의 개수 반환
    }
}