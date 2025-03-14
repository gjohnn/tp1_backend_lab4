package models

import (
	"github.com/google/uuid"
	"gorm.io/gorm"
)

// Empresa representa la tabla 'empresa'
type Empresa struct {
	ID              uuid.UUID `gorm:"type:uuid;primaryKey" json:"id"`
	Denominacion    string    `gorm:"type:varchar(128);not null" json:"denominacion"`
	Telefono        string    `gorm:"type:varchar(50)" json:"telefono"`
	HorarioAtencion string    `gorm:"type:varchar(256)" json:"horario_atencion"`
	QuienesSomos    string    `gorm:"type:varchar(1024)" json:"quienes_somos"`
	Latitud         float64   `gorm:"type:decimal(10,8)" json:"latitud"`
	Longitud        float64   `gorm:"type:decimal(11,8)" json:"longitud"`
	Domicilio       string    `gorm:"type:varchar(256)" json:"domicilio"`
	Email           string    `gorm:"type:varchar(75);unique;not null" json:"email"`
	Noticias        []Noticia `gorm:"foreignKey:EmpresaID" json:"noticias"`
}

// AutoMigración
func AutoMigrateEmpresa(db *gorm.DB) {
	db.AutoMigrate(&Empresa{})
}
