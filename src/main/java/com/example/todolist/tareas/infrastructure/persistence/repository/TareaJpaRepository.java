package com.example.todolist.tareas.infrastructure.persistence.repository;

import com.example.todolist.tareas.infrastructure.persistence.entity.TareaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TareaJpaRepository extends JpaRepository<TareaEntity, Long> {
}
