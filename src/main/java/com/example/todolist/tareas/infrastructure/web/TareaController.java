package com.example.todolist.tareas.infrastructure.web;

import com.example.todolist.tareas.application.service.TareaService;
import com.example.todolist.tareas.domain.model.Tarea;
import com.example.todolist.tareas.infrastructure.web.dto.CreateTareaRequest;
import com.example.todolist.tareas.infrastructure.web.dto.TareaResponse;
import com.example.todolist.tareas.infrastructure.web.dto.UpdateTareaRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tareas")
public class TareaController {
    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping
    public List<TareaResponse> listar() {
        return tareaService.listar().stream()
            .map(this::toResponse)
            .toList();
    }

    @GetMapping("/{id}")
    public TareaResponse obtener(@PathVariable Long id) {
        return toResponse(tareaService.obtener(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TareaResponse crear(@Valid @RequestBody CreateTareaRequest request) {
        Tarea tarea = new Tarea(null, request.titulo(), request.descripcion(), request.estado(), null);
        return toResponse(tareaService.crear(tarea));
    }

    @PutMapping("/{id}")
    public TareaResponse actualizar(@PathVariable Long id, @Valid @RequestBody UpdateTareaRequest request) {
        Tarea tarea = new Tarea(id, request.titulo(), request.descripcion(), request.estado(), null);
        return toResponse(tareaService.actualizar(id, tarea));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
    }

    private TareaResponse toResponse(Tarea tarea) {
        return new TareaResponse(
            tarea.getId(),
            tarea.getTitulo(),
            tarea.getDescripcion(),
            tarea.getEstado(),
            tarea.getFechaCreacion()
        );
    }
}
