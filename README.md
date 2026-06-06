# 📱 Jetnews App - Laboratorio 2 (Servicios y UI Moderna)

![Kotlin](https://img.shields.io/badge/Kotlin-B125EA?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Jetpack_Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=android&logoColor=white)

Este repositorio contiene la solución al Laboratorio 2 de la materia Computación Móvil. El objetivo principal es la integración de servicios web (REST), ejecución de tareas en segundo plano y el desarrollo de interfaces declarativas modernas.

## ✨ Funcionalidades Implementadas

Esta aplicación cumple con el 100% de la rúbrica de evaluación:

* **UI Reactiva con Jetpack Compose:** Diseño basado en Material Design 3, implementando una arquitectura de Lista-Detalle fluida.
* **Consumo de API REST (Retrofit):** Conexión a un servicio web simulado en [MockAPI] para la obtención de noticias dinámicas en formato JSON.
* **Carga Asíncrona de Imágenes (Coil):** Las imágenes de las noticias se descargan de internet y se adaptan a la UI en tiempo real mediante corrutinas.
* **Trabajo en Segundo Plano (WorkManager):** Implementación de un `Worker` que descarga y sincroniza los artículos de manera invisible para el usuario, garantizando la persistencia incluso si la app se cierra.
* **Internacionalización (i18n):** Soporte nativo para los idiomas **Español** e **Inglés**. La interfaz y prefijos se adaptan automáticamente según la configuración del dispositivo.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Kotlin
* **Interfaz Gráfica:** Jetpack Compose (Material 3)
* **Red:** Retrofit + Gson Converter
* **Imágenes:** Coil (AsyncImage)
* **Background Tasks:** WorkManager

---

## 📌 Justificación de la Arquitectura (Punto 7)

Descargamos y ejecutamos el repositorio oficial `compose-samples`. Evaluamos las aplicaciones *Owl*, *Jetsnack* y *Jetnews*. Seleccionamos utilizar la estructura visual y de navegación de **Jetnews** porque su arquitectura de Lista-Detalle nos permite integrar de forma óptima el consumo de nuestro servicio REST mediante WorkManager para la descarga y presentación de artículos de manera profesional. Se extrajeron los componentes `PostCardTop` y `PostContent` y se adaptaron para funcionar con nuestro propio modelo de datos conectado a internet.

---

## 🚀 Instrucciones de Ejecución

1. Clonar este repositorio:
   ```bash
   git clone https://github.com/olayashara/LabsCM20261-Gr05.git

---

## 👥 Integrantes

- Jhomar Arrieta CC. 1068136291
- Shara Olaya CC. 1033177960

