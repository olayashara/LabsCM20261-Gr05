package co.edu.udea.compumovil.gr05_20261.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import co.edu.udea.compumovil.gr05_20261.lab2.model.Article
import co.edu.udea.compumovil.gr05_20261.lab2.ui.theme.Labs20261Gr05Theme
import co.edu.udea.compumovil.gr05_20261.lab2.worker.NewsWorker
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Aquí despertamos al Worker para que vaya a MockAPI en segundo plano
        val workRequest = OneTimeWorkRequestBuilder<NewsWorker>().build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)

        setContent {
            Labs20261Gr05Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JetnewsApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetnewsApp() {
    // Estado para controlar si vemos la lista o el detalle
    var selectedArticle by remember { mutableStateOf<Article?>(null) }

    // Datos de prueba temporales para la UI (mientras el Worker descarga los reales)
    val mockArticles = listOf(
        Article("1", "Compose domina el mundo móvil", "Android Devs"),
        Article("2", "Nuevas funciones de WorkManager", "Google Team")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Jetnews Colombia") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { paddingValues ->
        if (selectedArticle == null) {
            // Funcionalidad 1: Pantalla Principal (Lista)
            ArticleList(
                articles = mockArticles,
                modifier = Modifier.padding(paddingValues),
                onArticleClick = { article -> selectedArticle = article }
            )
        } else {
            // Funcionalidad 2: Pantalla de Detalle
            ArticleDetail(
                article = selectedArticle!!,
                modifier = Modifier.padding(paddingValues),
                onBackClick = { selectedArticle = null }
            )
        }
    }
}

@Composable
fun ArticleList(
    articles: List<Article>,
    modifier: Modifier = Modifier,
    onArticleClick: (Article) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize().padding(16.dp)) {
        items(articles) { article ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onArticleClick(article) },
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = article.title, style = MaterialTheme.typography.titleLarge)
                    Text(text = "Por: ${article.author}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun ArticleDetail(
    article: Article,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = onBackClick) {
            Text("Volver")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = article.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Autor: ${article.author}", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Aquí iría el contenido completo de la noticia descargada desde nuestro servicio REST en MockAPI...", style = MaterialTheme.typography.bodyLarge)
    }
}