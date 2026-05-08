package com.example.todolist.tareas.application.service;

import com.example.todolist.tareas.domain.exception.InvalidEstadoTransitionException;
import com.example.todolist.tareas.domain.exception.TareaNotFoundException;
import com.example.todolist.tareas.domain.model.EstadoTarea;
import com.example.todolist.tareas.domain.model.Tarea;
import com.example.todolist.tareas.domain.port.TareaRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TareaService {
    private final TareaRepository tareaRepository;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public List<Tarea> listar() {
        return tareaRepository.findAll();
    }

    public Tarea obtener(Long id) {
        return tareaRepository.findById(id)
            .orElseThrow(() -> new TareaNotFoundException(id));
    }

    public Tarea crear(Tarea tarea) {
        validarTitulo(tarea.getTitulo());
        validarEstado(tarea.getEstado());
        LocalDate fechaCreacion = tarea.getFechaCreacion() != null ? tarea.getFechaCreacion() : LocalDate.now();
        Tarea nueva = new Tarea(null, tarea.getTitulo(), tarea.getDescripcion(), tarea.getEstado(), fechaCreacion);
        return tareaRepository.save(nueva);
    }

    public Tarea actualizar(Long id, Tarea tareaActualizada) {
        validarTitulo(tareaActualizada.getTitulo());
        validarEstado(tareaActualizada.getEstado());
        Tarea existente = obtener(id);
        validarTransicion(existente.getEstado(), tareaActualizada.getEstado());
        Tarea aGuardar = new Tarea(
            existente.getId(),
            tareaActualizada.getTitulo(),
            tareaActualizada.getDescripcion(),
            tareaActualizada.getEstado(),
            existente.getFechaCreacion()
        );
        return tareaRepository.save(aGuardar);
    }

    public void eliminar(Long id) {
        if (!tareaRepository.existsById(id)) {
            throw new TareaNotFoundException(id);
        }
        tareaRepository.deleteById(id);
    }

    private void validarTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio");
        }
    }

    private void validarEstado(EstadoTarea estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado es obligatorio");
        }
    }

    private void validarTransicion(EstadoTarea estadoActual, EstadoTarea estadoNuevo) {
        if (estadoActual == EstadoTarea.PENDIENTE && estadoNuevo == EstadoTarea.COMPLETADA) {
            throw new InvalidEstadoTransitionException(estadoActual, estadoNuevo);
        }
    }
}
