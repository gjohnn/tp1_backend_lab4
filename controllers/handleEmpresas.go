package controllers

import (
	"net/http"
	"v1/models"

	"github.com/labstack/echo/v4"
	"gorm.io/gorm"
)

// GetEmpresas obtiene todas las empresas desde la base de datos
func GetEmpresas(c echo.Context) error {
	// Obtener la conexión a la base de datos desde el contexto
	db, ok := c.Get("db").(*gorm.DB)
	if !ok {
		return c.JSON(http.StatusInternalServerError, map[string]string{
			"error": "Failed to retrieve database connection",
		})
	}

	empresas, err := models.GetAllEmpresas(db)
	if err != nil {
		return c.JSON(http.StatusInternalServerError, map[string]string{
			"error": err.Error(),
		})
	}

	return c.JSON(http.StatusOK, empresas)
}
