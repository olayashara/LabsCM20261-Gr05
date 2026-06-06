package co.edu.udea.compumovil.gr05_20261.lab2.network

import co.edu.udea.compumovil.gr05_20261.lab2.model.Article
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Interfaz que define las rutas de nuestra API
interface NewsApiService {
    @GET("articles") // Este es el endpoint que creaste en MockAPI
    suspend fun getArticles(): List<Article>
}

// Objeto Singleton para no crear múltiples instancias de Retrofit
object RetrofitClient {
    private const val BASE_URL = "https://6a23868a5c610353286af620.mockapi.io/"

    val apiService: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Convierte el JSON a nuestro Article.kt
            .build()
            .create(NewsApiService::class.java)
    }
}