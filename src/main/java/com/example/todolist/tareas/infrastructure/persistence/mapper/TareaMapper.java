package com.example.todolist.tareas.infrastructure.persistence.mapper;

import com.example.todolist.tareas.domain.model.Tarea;
import com.example.todolist.tareas.infrastructure.persistence.entity.TareaEntity;

public final class TareaMapper {
    private TareaMapper() {
    }

    public static Tarea toDomain(TareaEntity entity) {
        return new Tarea(
            entity.getId(),
            entity.getTitulo(),
            entity.getDescripcion(),
            entity.getEstado(),
            entity.getFechaCreacion()
        );
    }

    public static TareaEntity toEntity(Tarea tarea) {
        TareaEntity entity = new TareaEntity();
        entity.setId(tarea.getId());
        entity.setTitulo(tarea.getTitulo());
        entity.setDescripcion(tarea.getDescripcion());
        entity.setEstado(tarea.getEstado());
        entity.setFechaCreacion(tarea.getFechaCreacion());
        return entity;
    }
}
