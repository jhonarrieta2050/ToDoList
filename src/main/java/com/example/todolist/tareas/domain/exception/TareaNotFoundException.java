package com.example.todolist.tareas.domain.exception;

public class TareaNotFoundException extends RuntimeException {
    public TareaNotFoundException(Long id) {
        super("Tarea con id " + id + " no encontrada");
    }
}
