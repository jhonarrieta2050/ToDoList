package com.example.todolist.tareas.application.service;

import com.example.todolist.tareas.domain.exception.InvalidEstadoTransitionException;
import com.example.todolist.tareas.domain.model.EstadoTarea;
import com.example.todolist.tareas.domain.model.Tarea;
import com.example.todolist.tareas.domain.port.TareaRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TareaServiceTest {

    @Test
    void asignaFechaCreacionCuandoEsNula() {
        TareaService service = new TareaService(new InMemoryTareaRepository());
        LocalDate hoy = LocalDate.now();

        Tarea creada = service.crear(new Tarea(null, "Titulo", "Descripcion valida", EstadoTarea.PENDIENTE, null));

        assertNotNull(creada.getFechaCreacion());
        assertEquals(hoy, creada.getFechaCreacion());
    }

    @Test
    void noPermitePasarDePendienteACompletada() {
        TareaService service = new TareaService(new InMemoryTareaRepository());
        Tarea creada = service.crear(new Tarea(null, "Titulo", "Descripcion valida", EstadoTarea.PENDIENTE, null));

        Tarea intento = new Tarea(creada.getId(), "Titulo", "Descripcion valida", EstadoTarea.COMPLETADA, null);

        assertThrows(InvalidEstadoTransitionException.class, () -> service.actualizar(creada.getId(), intento));
    }

    @Test
    void permitePasarDePendienteAEnProceso() {
        TareaService service = new TareaService(new InMemoryTareaRepository());
        Tarea creada = service.crear(new Tarea(null, "Titulo", "Descripcion valida", EstadoTarea.PENDIENTE, null));

        Tarea actualizada = new Tarea(creada.getId(), "Titulo", "Descripcion valida", EstadoTarea.EN_PROCESO, null);
        Tarea resultado = service.actualizar(creada.getId(), actualizada);

        assertEquals(EstadoTarea.EN_PROCESO, resultado.getEstado());
    }

    private static class InMemoryTareaRepository implements TareaRepository {
        private final Map<Long, Tarea> store = new LinkedHashMap<>();
        private final AtomicLong sequence = new AtomicLong(1);

        @Override
        public List<Tarea> findAll() {
            return new ArrayList<>(store.values());
        }

        @Override
        public Optional<Tarea> findById(Long id) {
            return Optional.ofNullable(store.get(id));
        }

        @Override
        public Tarea save(Tarea tarea) {
            Long id = tarea.getId() != null ? tarea.getId() : sequence.getAndIncrement();
            Tarea saved = new Tarea(
                id,
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.getEstado(),
                tarea.getFechaCreacion()
            );
            store.put(id, saved);
            return saved;
        }

        @Override
        public void deleteById(Long id) {
            store.remove(id);
        }

        @Override
        public boolean existsById(Long id) {
            return store.containsKey(id);
        }
    }
}
