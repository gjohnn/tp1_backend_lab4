document.addEventListener("DOMContentLoaded", async function () {
    try {
        // Aquí debes reemplazar `ID_DE_LA_NOTICIA` con el id real que deseas obtener
        const params = new URLSearchParams(window.location.search);
        const noticiaId = params.get("id");// Asumiendo que estás buscando la noticia con ID = 1

        const response = await fetch(`http://localhost:8080/api/noticias/${noticiaId}`);

        // Verificar si la respuesta fue exitosa
        if (!response.ok) {
            throw new Error('Error al obtener la noticia');
        }

        const noticia = await response.json();

        console.log("Datos de la noticia:", noticia);

        // Verificar si los elementos existen antes de modificarlos
        if (document.getElementById("titulo")) {
            document.getElementById("titulo").innerText = noticia.titulo;
            console.log(noticia.titulo);  // Verifica si la clave realmente es 'título' y tiene el valor esperado
        }
        if (document.getElementById("contenido")) {
            document.getElementById("contenido").innerHTML = noticia.contenidoHtml;
        }
        if (document.getElementById("fecha")) {
            document.getElementById("fecha").innerText = new Date(noticia.fechaPublicacion).toLocaleDateString();
        }
        if (document.getElementById("resumen")) {
            document.getElementById("resumen").innerText = noticia.resumen;
            console.log(noticia.resumen);  // Verifica si la clave realmente es 'resumen' y tiene el valor esperado
        }
        if (document.getElementById("imagen")) {
            document.getElementById("imagen").src = noticia.imagen;
            document.getElementById("imagen").alt = noticia.título;
            console.log(noticia.imagen);  // Verifica si la clave realmente es 'imagen' y tiene el valor esperado
        }
        if (document.getElementById("publicada")) {
            document.getElementById("publicada").innerText = noticia.publicada ? "Sí" : "No";
        }

        if (noticia.empresa) {
            if (document.getElementById("denominacion")) {
                document.getElementById("denominacion").innerText = noticia.empresa.denominacion;
            }
            if (document.getElementById("denominacionFooter")) {
                document.getElementById("denominacionFooter").innerText = noticia.empresa.denominacion;
            }
            if (document.getElementById("empresa-direccion")) {
                document.getElementById("empresa-direccion").innerText = noticia.empresa.direccion;
            }
            if (document.getElementById("telefono")) {
                document.getElementById("telefono").innerText = noticia.empresa.telefono;
            }
        }

    } catch (error) {
        console.error("Error al obtener los datos:", error);
    }
});
