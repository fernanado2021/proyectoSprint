package com.gestion.cursos.controller;

import com.gestion.cursos.entities.Curso;
import com.gestion.cursos.reports.CursoExporterExcel;
import com.gestion.cursos.reports.CursoExporterPDF;
import com.gestion.cursos.repository.CursoRepository;
import com.gestion.cursos.service.CursoService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.List;

@Controller
public class CursoController {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private CursoService cursoService;
    @GetMapping
    public String home(){
        return "redirect:/cursos";
    }
    @GetMapping("/cursos")
    public String listarCursos(Model model){
        List<Curso> cursos=cursoRepository.findAll();
        cursos=cursoRepository.findAll();
        model.addAttribute("cursos",cursos);
        return "cursos";
    }
    @GetMapping("/cursos/nuevo")
    public String agregarCurso(Model model) {
        model.addAttribute("curso", new Curso());
        model.addAttribute("pageTitle","Nuevo Curso");
        return "curso_form";
    }
    @PostMapping("/cursos/save")
    public String guardarCurso(Curso curso, RedirectAttributes redirectAttributes) {
        try {
            cursoRepository.save(curso);
            redirectAttributes.addFlashAttribute("message","El curso ha sido guardado con éxito");
        }catch (Exception e){
            redirectAttributes.addAttribute("message",e.getMessage());

        }
        return "redirect:/cursos";
    }
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarCurso(@PathVariable Integer id, Model model){
        Curso curso = cursoService.obtenerPorId(id);
        model.addAttribute("curso", curso); // Corregido de "tienda" a "curso"
        model.addAttribute("accion", "/editar/" + id); // Corregido de "/Cursos/editar/" a "/editar/"
        return "formulario_curso";
    }

    @PostMapping("/editar/{id}")
    public String actualizarCurso(@PathVariable Integer id, @ModelAttribute Curso curso , RedirectAttributes redirectAttributes){
        try {
            // Verificar si el curso existe antes de eliminar
            Optional<Curso> optionalCurso = cursoRepository.findById(id);
            if (optionalCurso.isPresent()) {
                cursoService.actualizarCurso(id, curso);
                redirectAttributes.addFlashAttribute("message", "El curso ha sido editado con éxito");
            } else {
                // Manejo de error si el curso no se encuentra
                redirectAttributes.addFlashAttribute("message", "El curso no fue encontrado");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/cursos";
    }


    // Método para eliminar un curso
    @GetMapping("/cursos/eliminar/{id}")
    public String eliminarCurso(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            // Verificar si el curso existe antes de eliminar
            Optional<Curso> optionalCurso = cursoRepository.findById(id);
            if (optionalCurso.isPresent()) {
                cursoRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("message", "El curso ha sido eliminado con éxito");
            } else {
                // Manejo de error si el curso no se encuentra
                redirectAttributes.addFlashAttribute("message", "El curso no fue encontrado");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/cursos";
    }

    @GetMapping("export/pdf")
    public void generarReporte(HttpServletResponse response) throws IOException {
        response.setContentType("aplication/pdf");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=cursos"+currentDateTime+".pdf";
        response.setHeader(headerKey,headerValue);
        List<Curso> cursos = cursoRepository.findAll();
        CursoExporterPDF exporterPDF = new CursoExporterPDF(cursos);
        exporterPDF.export(response);
    }

    @GetMapping("export/excel")
    public void generarReporteExcel(HttpServletResponse response) throws  IOException{
        response.setContentType("application/octet-stream");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=cursos"+currentDateTime+".xlsx";
        response.setHeader(headerKey,headerValue);
        List<Curso> cursos = cursoRepository.findAll();
        CursoExporterExcel exporterExcel = new CursoExporterExcel(cursos);
        exporterExcel.export(response);
    }

}
