package com.example.firsttestlearning


import com.squareup.moshi.Json
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

class RestClient {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://book-data.p.rapidapi.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    val  bookService : BookService
            by lazy { retrofit.create(BookService::class.java) }
}

interface BookService {
    @GET("Search Author")
    fun searchBooks(
        @Header("X-Rapidapi-Host") host: String = "book-data.p.rapidapi.com",
        @Header("X-Rapidapi-Key") key: String = "cf91af59b9mshb2e145664f2fe8bp1e4b05jsncd021d327f80",
        @Query("string") query: String
    ) : Call<AuthorResult>

    @GET("Get Random Book")
    fun getRandomBooks(
        @Header("X-Rapidapi-Host") host: String = "book-data.p.rapidapi.com",
        @Header("X-Rapidapi-Key") key: String = "cf91af59b9mshb2e145664f2fe8bp1e4b05jsncd021d327f80",
        @Query("number") numberOfBooksToReturn : String
    ) : Call<List<String>>

    @GET("Get Book From ID")
    fun getBestBooks(
        @Header("X-Rapidapi-Host") host: String = "book-data.p.rapidapi.com",
        @Header("X-Rapidapi-Key") key: String = "cf91af59b9mshb2e145664f2fe8bp1e4b05jsncd021d327f80",
        @Query("id") bookId: String
    ) : Call<Book>

    @GET("get_genres")
    fun getAllGenres(
        @Header("X-Rapidapi-Host") host: String = "book-data.p.rapidapi.com",
        @Header("X-Rapidapi-Key") key: String = "cf91af59b9mshb2e145664f2fe8bp1e4b05jsncd021d327f80"
    ) : Call<List<String>>

    @GET("get_book/genres")
    fun getBooksByGenre(
        @Header("X-Rapidapi-Host") host: String = "book-data.p.rapidapi.com",
        @Header("X-Rapidapi-Key") key: String = "cf91af59b9mshb2e145664f2fe8bp1e4b05jsncd021d327f80",
        @Query("genre") genreName: String,
        @Query("number") limit: String
    ) : Call<BookResult>
}

data class AuthorResult(
    @field:Json(name = "results") val authors: List<Author>
)


data class Author(
    @field:Json(name = "name") val title: String?
)

data class BookResult(
    @field:Json(name = "result") val books : List<Book>
)
data class Book(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "Book title") val title: String?,
    @field:Json(name = "Author") val author : String,
    @field:Json(name = "Publication date") val releaseDate : String,
    @field:Json(name = "Book Genres") val bookGenres : String,
    @field:Json(name = "Plot summary") val plotSummary : String

)

