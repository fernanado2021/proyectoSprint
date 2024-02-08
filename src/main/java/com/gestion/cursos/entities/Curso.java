package com.gestion.cursos.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="tbl_curso")
@Data //metodo getter and setter
@AllArgsConstructor
@NoArgsConstructor
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 128, nullable = false)
    private String titulo;
    @Column(length = 26)
    private String descripcion;
    @Column(nullable = false)
    private int nivel;
    @Column(name = "estado_publicacion")
    private boolean isPublicado;
}

