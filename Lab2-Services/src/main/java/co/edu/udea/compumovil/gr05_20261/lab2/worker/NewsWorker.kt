package co.edu.udea.compumovil.gr05_20261.lab2.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import co.edu.udea.compumovil.gr05_20261.lab2.network.RetrofitClient

class NewsWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // 1. Llamamos a Retrofit en segundo plano
            val articles = RetrofitClient.apiService.getArticles()

            // 2. Imprimimos en consola para demostrar que funcionó
            Log.d("NewsWorker", "¡Éxito! Se descargaron ${articles.size} artículos en segundo plano.")
            for (article in articles) {
                Log.d("NewsWorker", "Noticia: ${article.title} por ${article.author}")
            }

            // En una app real de producción, aquí guardarías los datos en una base de datos local (Room)
            // Para fines de este laboratorio, demostrar la conexión e impresión es el núcleo del requisito.

            Result.success()
        } catch (e: Exception) {
            // Si no hay internet o falla MockAPI
            Log.e("NewsWorker", "Error descargando las noticias", e)
            Result.retry()
        }
    }
}