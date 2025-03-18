package main

import (
	"net/http"
	"v1/config"
	"v1/controllers"

	"github.com/labstack/echo/v4"
)

func main() {
	e := echo.New()

	// Conectar a la base de datos
	db := config.ConnectDatabase()

	// Usar el middleware para pasar la conexión a la base de datos
	e.Use(config.DatabaseMiddleware(db))

	// Ruta de prueba
	e.GET("/", func(c echo.Context) error {
		return c.String(http.StatusOK, "¡Hola, mundo!")
	})

	// Rutas para usuarios
	e.POST("/users", controllers.SaveUser)
	e.GET("/users/:id", controllers.GetUser)
	e.PUT("/users/:id", controllers.UpdateUser)
	e.DELETE("/users/:id", controllers.DeleteUser)

	// Rutas para empresas
	e.GET("/empresas", controllers.GetEmpresas)

	// Iniciar el servidor
	e.Logger.Fatal(e.Start(":8080"))
}
