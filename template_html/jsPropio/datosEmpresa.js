document.addEventListener("DOMContentLoaded", async function () {
    try {
        // Obtener el ID de la empresa desde la URL
        const params = new URLSearchParams(window.location.search);
        const empresaId = params.get("id");

        if (!empresaId) {
            console.error("No se proporcionó un ID de empresa.");
            return;
        }

        // Hacer la solicitud GET a la API con el ID de la empresa
        const response = await fetch(`http://localhost:8080/api/empresas/${empresaId}`);
        const empresa = await response.json();

        console.log("Datos de la empresa:", empresa);

        if (empresa) {
            // Verificar si los elementos existen antes de modificarlos
            if (document.getElementById("telefono")) {
                document.getElementById("telefono").innerText = empresa.telefono;
            }
            if (document.getElementById("denominacion")) {
                document.getElementById("denominacion").innerText = empresa.denominacion;
            }
            if (document.getElementById("horario")) {
                document.getElementById("horario").innerText = empresa.horarioAtencion;
            }
            if (document.getElementById("quienesSomos")) {
                document.getElementById("quienesSomos").innerText = empresa.quienesSomos;
            }
            if (document.getElementById("domicilio")) {
                document.getElementById("domicilio").innerText = empresa.domicilio;
            }
            if (document.getElementById("email")) {
                document.getElementById("email").innerText = empresa.email;
                document.getElementById("email").href = `mailto:${empresa.email}`;
            }
            if (document.getElementById("footerDenominacion")) {
                document.getElementById("footerDenominacion").innerText = empresa.denominacion;
            }

            // Actualizar el mapa con la ubicación de la empresa
            if (empresa.latitud && empresa.longitud) {
                const mapIframe = document.getElementById("mapa");
                if (mapIframe) {
                    mapIframe.src = `https://www.google.com/maps/embed?pb=!1m14!1m12!1m3!1d1000!2d${empresa.longitud}!3d${empresa.latitud}!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!5e0!3m2!1ses-419!2sar&z=15`;
                }
            }
        }
    } catch (error) {
        console.error("Error al obtener los datos:", error);
    }
});
