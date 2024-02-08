package com.gestion.cursos.service;

import com.gestion.cursos.entities.Curso;

import java.util.List;

public interface CursoService {
    List<Curso> obtenerTodas();
    Curso obtenerPorId(Integer id);
    Curso actualizarCurso(Integer id, Curso curso);
    void eliminarCurso(Integer id);

}
