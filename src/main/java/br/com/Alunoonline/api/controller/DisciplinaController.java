package br.com.Alunoonline.api.controller;


import br.com.Alunoonline.api.model.Disciplina;
import br.com.Alunoonline.api.service.DisciplinaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/disciplinas")
@Tag(name = "Disciplina", description = "gerenciamento de disciplina")
public class DisciplinaController {

    @Autowired
    DisciplinaService disciplinaService;

    @Operation(summary = "Cria disciplina")
    @ApiResponse(responseCode = "201", description = "Criação de disciplina no banco de dados")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarDisciplina(@RequestBody Disciplina disciplina){
        disciplinaService.criarDisciplina(disciplina);
    }

    @Operation(summary = "Lista as disciplinas do professor")
    @ApiResponse(responseCode = "200", description = "Lista as disciplina do professor no banco de dados")
    @GetMapping("/professor/{professorId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Disciplina> listarDisciplinaDoProf(@PathVariable Long professorId){
        return disciplinaService.listarDisciplinasDoProf(professorId);
    }

    @Operation(summary = "Listar todas as disciplinas")
    @ApiResponse(responseCode = "200", description = "Lista todas as disciplina no banco de dados")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Disciplina> listarTodosDisciplinas() {

        return disciplinaService.listarTodosDisciplinas();

    }

    @Operation(summary = "Busca disciplina por ID")
    @ApiResponse(responseCode = "200", description = "Busca a disciplina no banco de dados pelo ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Disciplina> buscarDisciplinaPorId(@PathVariable Long id) {

        return disciplinaService.buscarDisciplinaPorId(id);

    }

    @Operation(summary = "Deleta disciplina por ID")
    @ApiResponse(responseCode = "204", description = "Deleta a disciplina no banco de dados pelo ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarDisciplinaPorId(@PathVariable Long id) {

        disciplinaService.deletarDisciplinaPorId(id);

    }

    @Operation(summary = "Atualiza disciplina por ID")
    @ApiResponse(responseCode = "200", description = "Atualiza a disciplina no banco de dados pelo ID")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarDisciplinaPorID(@PathVariable Long id, @RequestBody Disciplina disciplina) {

        disciplinaService.atualizarDisciplinaPorID(id, disciplina);

    }

}
