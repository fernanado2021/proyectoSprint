package com.gestion.cursos.service.impl;

import com.gestion.cursos.entities.Curso;
import com.gestion.cursos.repository.CursoRepository;
import com.gestion.cursos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursosServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;
    @Override
    public List<Curso> obtenerTodas() {
        return cursoRepository.findAll();
    }
    @Override
    public void eliminarCurso(Integer id) {
        cursoRepository.deleteById(id);
    }
    @Override
    public Curso obtenerPorId(Integer id){
        return cursoRepository.findById(id).orElse(null);
    }
    @Override
    public Curso actualizarCurso(Integer id, Curso curso) {
        Curso cursoBBDD = cursoRepository.findById(id).orElse(null);
        if (cursoBBDD != null && curso != null) {
            cursoBBDD.setTitulo(curso.getTitulo());
            cursoBBDD.setDescripcion(curso.getDescripcion());
            cursoBBDD.setNivel(curso.getNivel());

            return cursoRepository.save(cursoBBDD);
        }
        return null;
    }
}
