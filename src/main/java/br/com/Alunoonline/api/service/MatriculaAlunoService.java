package br.com.Alunoonline.api.service;


import br.com.Alunoonline.api.dtos.AtualizarNotasRequest;
import br.com.Alunoonline.api.dtos.DisciplinaAlunoResponse;
import br.com.Alunoonline.api.dtos.HistoricoAlunoResponse;
import br.com.Alunoonline.api.enums.MatriculaStatusEnum;
import br.com.Alunoonline.api.model.MatriculaAluno;
import br.com.Alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatriculaAlunoService {

    public static final double MEDIA_PARA_APROVACAO = 7.00;

    @Autowired
    MatriculaAlunoRepository matriculaAlunoRepository;

    public void criarMatriculaAluno(MatriculaAluno matriculaAluno){

        matriculaAluno.setStatus(MatriculaStatusEnum.MATRICULADO);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    public void trancarMatricula(Long matriculaAlunoId){
        MatriculaAluno matriculaAluno =
                matriculaAlunoRepository.findById(matriculaAlunoId)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Matricula Aluno não encontrada."));

        if (!MatriculaStatusEnum.MATRICULADO.equals(matriculaAluno.getStatus())){
            //lançar o erro se o status não for matriculado
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "só é possível trancar uma matricula com o status MATRICULADO");
        }
        matriculaAluno.setStatus(MatriculaStatusEnum.TRANCADO);
        matriculaAlunoRepository.save(matriculaAluno);
    }

    public void atualizarNotas(Long matriculaAlunoId, AtualizarNotasRequest atualizarNotasRequest){
        MatriculaAluno matriculaAluno =
                matriculaAlunoRepository.findById(matriculaAlunoId)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Matricula Aluno não encontrada."));
        //verifica se o front tá mandando nota1
        //atualizarNotasRequest.getNota1(): traz a nota que vem do front
        if(atualizarNotasRequest.getNota1() != null){
            //matricula.setNota1: atualiza a nota1 que esta atualmente no BD
            matriculaAluno.setNota1(atualizarNotasRequest.getNota1());
        }
        //verifica se o front tá mandando nota2
        //atualizarNotasRequest.getNota2(): traz a nota que vem do front
        if(atualizarNotasRequest.getNota2() != null){
            //matricula.setNota2: atualiza a nota1 que esta atualmente no BD
            matriculaAluno.setNota2(atualizarNotasRequest.getNota2());
        }

        calcularMedia(matriculaAluno);
        matriculaAlunoRepository.save(matriculaAluno);

    }
    private void calcularMedia(MatriculaAluno matriculaAluno){
        Double nota1 = matriculaAluno.getNota1();
        Double nota2 = matriculaAluno.getNota2();

        if(nota1 != null && nota2 != null){
            Double media = (nota1+nota2)/2;
            matriculaAluno.setStatus(media >= MEDIA_PARA_APROVACAO ? MatriculaStatusEnum.APROVADO : MatriculaStatusEnum.REPROVADO);
        }
    }

    public Double cal_Media(double x, double y){
      double total = (x+y)/2;
      return total;
    };

    public HistoricoAlunoResponse emitirHistorico(Long alunoId){
        List<MatriculaAluno> matriculasDoAluno = matriculaAlunoRepository.findByAlunoId(alunoId);

        if (matriculasDoAluno.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Esse aluno não possui matricula");
        }

        HistoricoAlunoResponse historicoAluno = new HistoricoAlunoResponse();
        historicoAluno.setNomeAluno(matriculasDoAluno.get(0).getAluno().getNome());
        historicoAluno.setCpfAluno(matriculasDoAluno.get(0).getAluno().getCpf());
        historicoAluno.setEmailAluno(matriculasDoAluno.get(0).getAluno().getEmail());

        List<DisciplinaAlunoResponse> disciplinasList = new ArrayList<>();
        for (MatriculaAluno matriculaAluno: matriculasDoAluno){
            DisciplinaAlunoResponse disciplinaAlunoResponse = new DisciplinaAlunoResponse();
            disciplinaAlunoResponse.setNomeDisciplina(matriculaAluno.getDisciplina().getNome());
            disciplinaAlunoResponse.setNomeProfessor(matriculaAluno.getDisciplina().getProfessor().getNome());
            disciplinaAlunoResponse.setNota1(matriculaAluno.getNota1());
            disciplinaAlunoResponse.setNota2(matriculaAluno.getNota2());

            if (matriculaAluno.getNota1() != null && matriculaAluno.getNota2() != null){
                disciplinaAlunoResponse.setMedia(cal_Media(matriculaAluno.getNota1(), matriculaAluno.getNota2()));

            }else {
                disciplinaAlunoResponse.setMedia(null);
            }
            disciplinaAlunoResponse.setStatus(matriculaAluno.getStatus());

            disciplinasList.add(disciplinaAlunoResponse);
        }
        historicoAluno.setDisciplinaAlunoResponses(disciplinasList);


        return historicoAluno;
    }
}