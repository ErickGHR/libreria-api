package com.api.libreria.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @NotBlank(message = "El ISBN es obligatorio")
    @Column(length = 20)
    private String isbn;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false, length = 180)
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Column(nullable = false, length = 140)
    private String autor;

    @NotNull(message = "El año de publicación es obligatorio")
    @Min(value = 1000, message = "El año de publicación no es válido")
    @Column(name = "anio_publicacion", nullable = false)
    private Integer anioPublicacion;

    @NotNull(message = "El número de ejemplares es obligatorio")
    @Positive(message = "Debe existir por lo menos un ejemplar")
    @Column(name = "total_ejemplares", nullable = false)
    private Integer totalEjemplares;

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public Integer getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Integer anioPublicacion) { this.anioPublicacion = anioPublicacion; }
    public Integer getTotalEjemplares() { return totalEjemplares; }
    public void setTotalEjemplares(Integer totalEjemplares) { this.totalEjemplares = totalEjemplares; }
}
