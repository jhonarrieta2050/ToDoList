package com.example.todolist.tareas.infrastructure.persistence.adapter;

import com.example.todolist.tareas.domain.model.Tarea;
import com.example.todolist.tareas.domain.port.TareaRepository;
import com.example.todolist.tareas.infrastructure.persistence.mapper.TareaMapper;
import com.example.todolist.tareas.infrastructure.persistence.repository.TareaJpaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class TareaRepositoryAdapter implements TareaRepository {
    private final TareaJpaRepository tareaJpaRepository;

    public TareaRepositoryAdapter(TareaJpaRepository tareaJpaRepository) {
        this.tareaJpaRepository = tareaJpaRepository;
    }

    @Override
    public List<Tarea> findAll() {
        return tareaJpaRepository.findAll()
            .stream()
            .map(TareaMapper::toDomain)
            .toList();
    }

    @Override
    public Optional<Tarea> findById(Long id) {
        return tareaJpaRepository.findById(id).map(TareaMapper::toDomain);
    }

    @Override
    public Tarea save(Tarea tarea) {
        return TareaMapper.toDomain(tareaJpaRepository.save(TareaMapper.toEntity(tarea)));
    }

    @Override
    public void deleteById(Long id) {
        tareaJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return tareaJpaRepository.existsById(id);
    }
}
