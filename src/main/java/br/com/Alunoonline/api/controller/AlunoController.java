package br.com.Alunoonline.api.controller;


import br.com.Alunoonline.api.model.Aluno;
import br.com.Alunoonline.api.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
@Tag(name = "Alunos", description = "Operações de gerenciamento de alunos")
public class AlunoController {

    @Autowired
    AlunoService alunoService;

    @Operation(summary = "Criar aluno")
    @ApiResponse(responseCode = "201", description = "Inseri aluno no banco de dados")
    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void criarAluno(@RequestBody Aluno aluno,
                           @AuthenticationPrincipal String username ){

        alunoService.criarAluno(aluno);
    }

    @Operation(summary = "Listar todos os alunos")
    @ApiResponse(responseCode = "200", description = "Listar todos os alunos.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Aluno> listarTodosAlunos(){
       return alunoService.listarTodosAlunos();
    }

    @Operation(summary = "Buscar aluno pro ID")
    @ApiResponse(responseCode = "200", description = "Busca aluno no banco de dados pelo ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Aluno> buscarAlunoPorId(@PathVariable Long id){
        return alunoService.buscarAlunoPorId(id);
    }

    @Operation(summary = "Deleta aluno pro ID")
    @ApiResponse(responseCode = "204", description = "Deleta o aluno no banco de dados pelo ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAlunoPorId(@PathVariable Long id){
        alunoService.deletarAlunoPorId(id);
    }

    @Operation(summary = "Atualiza aluno pro ID")
    @ApiResponse(responseCode = "200", description = "Faz atualização do aluno no banco de dados pelo ID")
    @PutMapping("/id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarAlunoPorId(@PathVariable long id, @RequestBody Aluno aluno){
        alunoService.atualizarAlunoPorId(id, aluno);
    }
}
