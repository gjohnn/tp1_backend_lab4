package config

import (
	"log"

	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

var DB *gorm.DB

// ConnectDatabase establece la conexión a la base de datos y la asigna a la variable global DB
func ConnectDatabase() *gorm.DB {
	// Reemplaza con tu cadena de conexión
	dsn := "user=postgres password=postgres dbname=tp1_lab sslmode=disable"
	var err error
	DB, err = gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatal("Error al conectar a la base de datos: ", err)
	}

	// Retorna la instancia de DB
	return DB
}
