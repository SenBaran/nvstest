package com.example.firsttestlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BooksActivity : AppCompatActivity() {

    private val genreTextView : TextView
        get()= findViewById(R.id.genreViewID)

    val recView : RecyclerView
        get () = findViewById(R.id.genreRecyclerViewId)
    lateinit var adapter : ArrayAdapter<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        val genre = intent.getStringExtra("genre")

        genreTextView.text = genre
    }

    private fun getBooksByGenre(genreName : String){
        val call = RestClient().bookService.getBooksByGenre(genreName=genreName, limit= "50")
        call.enqueue(object : Callback<BookResult> {

            override fun onFailure(call: Call<BookResult>, t: Throwable) {
                Log.e("MainActivity", "Failed to get search results", t)
            }
            override fun onResponse(
                call: Call<BookResult>,
                response: Response<BookResult>
            ) {
                if (response.isSuccessful) {
                    val books = (response.body()!!.books as ArrayList<Book>)


                    Log.d(
                        "MainActivity",
                        books.toString()
                    )
                } else {
                    Log.e(
                        "MainActivity",
                        "Failed to get search results\n${response.errorBody()?.string() ?: ""}"
                    )
                }
            }
        })
    }
}