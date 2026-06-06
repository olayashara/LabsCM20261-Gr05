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
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

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

    // Estado para guardar las noticias reales de MockAPI
    var articlesList by remember { mutableStateOf<List<Article>>(emptyList()) }

    // Esto descarga las noticias de MockAPI para mostrarlas en pantalla apenas se abre la app
    LaunchedEffect(Unit) {
        try {
            articlesList = co.edu.udea.compumovil.gr05_20261.lab2.network.RetrofitClient.apiService.getArticles()
        } catch (e: Exception) {
            // Si hay error (no hay internet), mostramos una vacía o podríamos mostrar un mensaje
            articlesList = emptyList()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                // Aquí usamos el idioma configurado!
                title = { Text(text = stringResource(id = R.string.title_jetnews)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { paddingValues ->
        if (selectedArticle == null) {
            ArticleList(
                articles = articlesList,
                modifier = Modifier.padding(paddingValues),
                onArticleClick = { article -> selectedArticle = article }
            )
        } else {
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
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(0.dp) // Jetnews usa 0 de padding externo, lo maneja interno
    ) {
        item {
            Text(
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp),
                text = "Últimas Noticias",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        items(articles) { article ->
            // Esta es la réplica exacta de PostCardTop del archivo original de Jetnews
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onArticleClick(article) }
                    .padding(16.dp)
            ) {
                val imageModifier = Modifier
                    .heightIn(min = 180.dp)
                    .fillMaxWidth()
                    .clip(shape = MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.surfaceVariant)

                Box(modifier = imageModifier, contentAlignment = Alignment.Center) {
                    AsyncImage(
                        model = article.imageUrl,
                        contentDescription = null,
                        modifier = imageModifier,
                        contentScale = androidx.compose.ui.layout.ContentScale.Crop
                    )
                }
                Spacer(Modifier.height(16.dp))

                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "${stringResource(id = R.string.author_prefix)}${article.author}",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = "06 Junio • 5 min read", // Dato estático tal como el preview de Jetnews
                    style = MaterialTheme.typography.bodySmall
                )
            }
            // El divisor exacto de Jetnews
            Divider(
                modifier = Modifier.padding(horizontal = 14.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
            )
        }
    }
}

@Composable
fun ArticleDetail(
    article: Article,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    // Réplica de PostContent.kt (pantalla de detalle)
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onBackClick) {
                Text("← ${stringResource(id = R.string.btn_back)}")
            }
            Spacer(Modifier.height(8.dp))

            // PostHeaderImage exacto
            val imageModifier = Modifier
                .heightIn(min = 180.dp)
                .fillMaxWidth()
                .clip(shape = MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.surfaceVariant)

            Box(modifier = imageModifier, contentAlignment = Alignment.Center) {
                AsyncImage(
                    model = article.imageUrl,
                    contentDescription = null,
                    modifier = imageModifier,
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop
                )
            }
            Spacer(Modifier.height(16.dp))

            // Título
            Text(article.title, style = MaterialTheme.typography.headlineLarge)
            Spacer(Modifier.height(16.dp))
        }

        item {
            // PostMetadata exacto
            Row(modifier = Modifier.padding(bottom = 24.dp)) {
                // Jetnews usa ic_account_circle, aquí usamos el equivalente de Compose
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        text = "${stringResource(id = R.string.author_prefix)}${article.author}",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = "06 Junio • 5 min read",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        item {
            Text(
                text = article.content,
                style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 28.sp),
                modifier = Modifier.padding(bottom = 48.dp)
            )
        }
    }
}