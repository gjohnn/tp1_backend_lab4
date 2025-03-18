package models

import (
	"gorm.io/gorm"
)

type Empresa struct {
	ID              uint   `gorm:"primaryKey"`
	Denominacion    string `gorm:"size:128"`
	Telefono        string `gorm:"size:50"`
	HorarioAtencion string `gorm:"size:256"`
	QuienesSomos    string `gorm:"size:1024"`
	Latitud         float64
	Longitud        float64
	Domicilio       string `gorm:"size:256"`
	Email           string `gorm:"size:75"`
}

func GetAllEmpresas(db *gorm.DB) ([]Empresa, error) {
	var empresas []Empresa
	if err := db.Find(&empresas).Error; err != nil {
		return nil, err
	}
	return empresas, nil
}
