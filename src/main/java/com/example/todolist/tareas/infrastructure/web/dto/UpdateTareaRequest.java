package com.example.todolist.tareas.infrastructure.web.dto;

import com.example.todolist.tareas.domain.model.EstadoTarea;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTareaRequest(
    @NotBlank(message = "El titulo es obligatorio")
    String titulo,
    @NotBlank(message = "La descripcion es obligatoria")
    @Size(min = 5, message = "La descripcion debe tener al menos 5 caracteres")
    String descripcion,
    @NotNull(message = "El estado es obligatorio")
    EstadoTarea estado
) {
}
