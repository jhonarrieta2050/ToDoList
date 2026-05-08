package com.example.todolist.tareas.infrastructure.web.exception;

import java.util.Map;

public record ApiError(String mensaje, Map<String, String> errores) {
}
