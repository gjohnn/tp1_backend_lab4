package controllers

import (
	"net/http"

	"github.com/labstack/echo/v4"
)

// GetUser obtiene un usuario por ID
func GetUser(c echo.Context) error {
	id := c.Param("id")
	return c.String(http.StatusOK, "Usuario con ID: "+id)
}

// SaveUser guarda un nuevo usuario
func SaveUser(c echo.Context) error {
	// Get name and email
	name := c.FormValue("name")
	email := c.FormValue("email")
	return c.String(http.StatusOK, "name: "+name+", email: "+email)
}

// UpdateUser actualiza un usuario existente
func UpdateUser(c echo.Context) error {
	id := c.Param("id")
	name := c.FormValue("name")
	email := c.FormValue("email")
	return c.String(http.StatusOK, "Usuario con ID "+id+" actualizado. name: "+name+", email: "+email)
}

// DeleteUser elimina un usuario
func DeleteUser(c echo.Context) error {
	id := c.Param("id")
	return c.String(http.StatusOK, "Usuario con ID "+id+" eliminado")
}
