package br.com.Alunoonline.api.controller;

import br.com.Alunoonline.api.dtos.AtualizarNotasRequest;
import br.com.Alunoonline.api.dtos.HistoricoAlunoResponse;
import br.com.Alunoonline.api.model.MatriculaAluno;
import br.com.Alunoonline.api.service.MatriculaAlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matriculas-alunos")
@Tag(name = "Matricula", description = "gerenciamento de matricula")
public class MatriculaAlunoController {

    @Autowired
    MatriculaAlunoService matriculaAlunoService;

    @Operation(summary = "Cria matricula")
    @ApiResponse(responseCode = "201", description = "Criação de matricula no banco de dados")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarMatriculaAluno(@RequestBody MatriculaAluno matriculaAluno){
        matriculaAlunoService.criarMatriculaAluno(matriculaAluno);
    }

    @Operation(summary = "Trancar matricula")
    @ApiResponse(responseCode = "200", description = "Tranca a matricula do aluno pelo ID")
    @PatchMapping("/trancar/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void trancarMatricula(@PathVariable Long id){
        matriculaAlunoService.trancarMatricula(id);
    }

    @Operation(summary = "Atualizar notas")
    @ApiResponse(responseCode = "200", description = "Atualiza a nota no banco de dados pelo ID")
    @PatchMapping("/atualizar-notas/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarNotas(@RequestBody AtualizarNotasRequest atualizarNotasRequest
            , @PathVariable Long id){
        matriculaAlunoService.atualizarNotas(id, atualizarNotasRequest);
    }

    @Operation(summary = "Emitir historico")
    @ApiResponse(responseCode = "200", description = "Emissão do historico pelo ID")
    @GetMapping("/emitir-historico/{alunoId}")
    @ResponseStatus(HttpStatus.OK)
    public HistoricoAlunoResponse emitirHistorico(@PathVariable Long alunoId) {

        return matriculaAlunoService.emitirHistorico(alunoId);

    }

}