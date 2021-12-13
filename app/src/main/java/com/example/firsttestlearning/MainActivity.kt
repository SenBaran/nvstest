package com.example.firsttestlearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var spinner : Spinner
    lateinit var adapter : ArrayAdapter<String>

    private val getBooksButton : Button
    get() = findViewById(R.id.btn_movieGenresId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadAllGenres()

        getBooksButton.setOnClickListener {
            var intent =  Intent(this, BooksActivity::class.java);
            intent.putExtra("genre", spinner.selectedItem.toString())
            startActivity(intent)
        }
    }

    private fun loadAllGenres(){
        val call = RestClient().bookService.getAllGenres()
        call.enqueue(object : Callback<List<String>> {

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.e("MainActivity", "Failed to get search results", t)
            }
            override fun onResponse(
                call: Call<List<String>>,
                response: Response<List<String>>
            ) {
                if (response.isSuccessful) {
                    val genres = (response.body()!! as ArrayList<String>)
                    adapter = ArrayAdapter(applicationContext,android.R.layout.simple_spinner_dropdown_item,genres)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner = findViewById(R.id.genreSpinnerId) as Spinner
                    spinner.adapter = adapter

                    Log.d(
                        "MainActivity",
                        genres.toString()
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
