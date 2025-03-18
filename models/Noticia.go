package models

type Noticia struct {
	ID               uint   `gorm:"primaryKey"`
	Titulo           string `gorm:"size:128"`
	Resumen          string `gorm:"size:1024"`
	Imagen           string `gorm:"size:128"`
	ContenidoHTML    string `gorm:"size:20480"`
	Publicada        string `gorm:"size:1"`
	FechaPublicacion string `gorm:"size:10"`
	EmpresaID        uint
}
