document.addEventListener("DOMContentLoaded", async function () {
    try {
        // Hacer la solicitud GET a la API
        const response = await fetch("http://localhost:8080/api/empresas");
        const empresas = await response.json();

        if (empresas.length > 0) {
            const empresa = empresas[0]; // Tomamos la primera empresa

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
        }
    } catch (error) {
        console.error("Error al obtener los datos:", error);
    }
});
