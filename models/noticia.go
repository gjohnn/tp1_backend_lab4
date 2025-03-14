package models

import (
	"time"

	"github.com/google/uuid"
	"gorm.io/gorm"
)

// Noticia representa la tabla 'noticia'
type Noticia struct {
	ID               uuid.UUID `gorm:"type:uuid;primaryKey" json:"id"`
	Titulo           string    `gorm:"type:varchar(128);not null" json:"titulo"`
	Resumen          string    `gorm:"type:varchar(1024)" json:"resumen"`
	ImagenNoticia    string    `gorm:"type:varchar(128)" json:"imagen_noticia"`
	ContenidoHTML    string    `gorm:"type:varchar(20480)" json:"contenido_html"`
	Publicada        string    `gorm:"type:char(1);not null;default:'N'" json:"publicada"` // 'Y' o 'N'
	FechaPublicacion time.Time `gorm:"type:date" json:"fecha_publicacion"`
	EmpresaID        uuid.UUID `gorm:"type:uuid;not null" json:"empresa_id"`
	Empresa          Empresa   `gorm:"foreignKey:EmpresaID" json:"empresa"`
}

func AutoMigrateNoticia(db *gorm.DB) {
	db.AutoMigrate(&Noticia{})
}
