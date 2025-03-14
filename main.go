package main

import (
	"go-lab4-tp1/database"
	"go-lab4-tp1/models"
	"log"
	"net/http"
)

func main() {
	// Conectar a la base de datos
	database.Connect()

	// Ejecutar migraciones
	models.AutoMigrateEmpresa(database.DB)
	models.AutoMigrateNoticia(database.DB)

	log.Println("Migraciones completadas")

	// Configurar el servidor HTTP y especificar el puerto
	http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
		w.Write([]byte("¡Servidor funcionando!"))
	})

	// Establecer el puerto en el que el servidor escuchará (en este caso 8080)
	log.Println("Servidor corriendo en el puerto :8080")
	err := http.ListenAndServe(":8080", nil)
	if err != nil {
		log.Fatal("Error al iniciar el servidor:", err)
	}
}
