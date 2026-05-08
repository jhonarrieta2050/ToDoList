package com.example.todolist.tareas.infrastructure.web.dto;

import com.example.todolist.tareas.domain.model.EstadoTarea;
import java.time.LocalDate;

public record TareaResponse(
    Long id,
    String titulo,
    String descripcion,
    EstadoTarea estado,
    LocalDate fechaCreacion
) {
}
