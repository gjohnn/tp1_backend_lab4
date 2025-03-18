package config

import (
	"github.com/labstack/echo/v4"
	"gorm.io/gorm"
)

// Este middleware agrega la conexión de base de datos al contexto
func DatabaseMiddleware(db *gorm.DB) echo.MiddlewareFunc {
	return func(next echo.HandlerFunc) echo.HandlerFunc {
		return func(c echo.Context) error {
			// Pasar la conexión de base de datos al contexto
			c.Set("db", db)
			return next(c)
		}
	}
}
