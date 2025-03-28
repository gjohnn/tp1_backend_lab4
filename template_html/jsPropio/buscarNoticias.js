document.addEventListener("DOMContentLoaded", function () {
    // Obtener el parámetro de la URL
    const urlParams = new URLSearchParams(window.location.search);
    const searchQuery = urlParams.get('buscar');
    
    // Si hay una consulta de búsqueda, puedes mostrarla en el campo de búsqueda o usarla para realizar la búsqueda
    if (searchQuery) {
      document.getElementById('searchInput').value = searchQuery;
      realizarBusqueda(searchQuery);
    }
  });
  
  // Función para realizar la búsqueda según el término
  function realizarBusqueda(query) {
    fetch(`http://localhost:8080/api/noticias/buscar?query=${query}&page=0&size=20`)
      .then(response => response.json())
      .then(data => {
        if (data.content && data.content.length > 0) {
          mostrarNoticias(data.content);
        } else {
          alert("No se encontraron noticias");
        }
      })
      .catch(error => {
        console.error('Error al buscar noticias:', error);
        alert("Hubo un error al realizar la búsqueda");
      });
  }
  
  // Función para mostrar las noticias
  function mostrarNoticias(noticias) {
    const resultadosDiv = document.getElementById("resultados"); // Asegúrate de tener un contenedor en el HTML
    resultadosDiv.innerHTML = ""; // Limpiar los resultados previos
  
    noticias.forEach(noticia => {
      const noticiaElement = document.createElement("div");
      noticiaElement.classList.add("noticia-item");
      
      noticiaElement.innerHTML = `
        <h3>${noticia.titulo}</h3>
        <p>${noticia.resumen}</p>
        <p><strong>Fecha:</strong> ${new Date(noticia.fechaPublicacion).toLocaleDateString()}</p>
      `;
      
      resultadosDiv.appendChild(noticiaElement);
    });
  }
  