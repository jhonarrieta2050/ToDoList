package com.example.todolist.tareas.domain.model;

import java.time.LocalDate;

public class Tarea {
    private final Long id;
    private final String titulo;
    private final String descripcion;
    private final EstadoTarea estado;
    private final LocalDate fechaCreacion;

    public Tarea(Long id, String titulo, String descripcion, EstadoTarea estado, LocalDate fechaCreacion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public EstadoTarea getEstado() {
        return estado;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
}
