package com.example.todolist.tareas.domain.exception;

import com.example.todolist.tareas.domain.model.EstadoTarea;

public class InvalidEstadoTransitionException extends RuntimeException {
    public InvalidEstadoTransitionException(EstadoTarea estadoActual, EstadoTarea estadoNuevo) {
        super("No se puede cambiar de " + estadoActual + " a " + estadoNuevo + " directamente");
    }
}
