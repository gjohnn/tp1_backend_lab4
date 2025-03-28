document.addEventListener("DOMContentLoaded", async function () {
    try {
        // Hacer la solicitud GET a la API
        const response = await fetch("http://localhost:8080/api/noticias");
        const noticias = await response.json();

        if (noticias.length > 0) {
            const noticia = noticias[0]; // Tomamos la primera noticia

            // Verificar si los elementos existen antes de modificarlos
            if (document.getElementById("titulo")) {
                document.getElementById("titulo").innerText = noticia.título;
            }
            if (document.getElementById("contenido")) {
                document.getElementById("contenido").innerHTML = noticia.contenido_html;
            }
            if (document.getElementById("fecha")) {
                document.getElementById("fecha").innerText = new Date(noticia.fecha_publicacion).toLocaleDateString();
            }
            if (document.getElementById("resumen")) {
                document.getElementById("resumen").innerText = noticia.resumen;
            }
            if (document.getElementById("imagen")) {
                document.getElementById("imagen").src = noticia.imagen;
                document.getElementById("imagen").alt = noticia.título;
            }
            if (document.getElementById("publicada")) {
                document.getElementById("publicada").innerText = noticia.publicada ? "Sí" : "No";
            }
        }
    } catch (error) {
        console.error("Error al obtener los datos:", error);
    }
});
