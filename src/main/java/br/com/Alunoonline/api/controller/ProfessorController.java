package br.com.Alunoonline.api.controller;


import br.com.Alunoonline.api.model.Professor;
import br.com.Alunoonline.api.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professores")
@Tag(name = "Professor", description = "Operações de gerenciamento de Professores")
public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @Operation(summary = "Criar professor")
    @ApiResponse(responseCode = "201", description = "Inseri professor no banco de dados")
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void criarProfessor(@RequestBody Professor professor){
        professorService.criarProfessor(professor);
    }

    @Operation(summary = "Listar todos os professores")
    @ApiResponse(responseCode = "200", description = "Listar todos os professores")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Professor> buscarTodosProfessores(){ return professorService.listarTodosProfessores();}

    @Operation(summary = "Buscar professor pro ID")
    @ApiResponse(responseCode = "200", description = "Busca o professor no banco de dados pelo ID")
    @GetMapping("/id")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Professor> buscarProfessorPorId(@PathVariable Long id){
        return professorService.buscarProfessorPorId(id);
    }

    @Operation(summary = "Deleta professor pro ID")
    @ApiResponse(responseCode = "204", description = "Deleta o professor no banco de dados pelo ID")
    @DeleteMapping("/id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarProfessorPorId(@PathVariable Long id){
        professorService.deletarProfessorPorId(id);
    }

    @Operation(summary = "Atualiza professor pro ID")
    @ApiResponse(responseCode = "200", description = "Faz atualização do professor no banco de dados pelo ID")
    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarProfessorPorId(@PathVariable Long id, @RequestBody Professor professor){
        professorService.atualizarprofessorPorId(id, professor);
    }

}
