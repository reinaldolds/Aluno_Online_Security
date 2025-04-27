package br.com.Alunoonline.api.dtos;


import br.com.Alunoonline.api.enums.MatriculaStatusEnum;
import br.com.Alunoonline.api.repository.MatriculaAlunoRepository;
import lombok.Data;

@Data
public class DisciplinaAlunoResponse {
    private String nomeDisciplina;
    private String nomeProfessor;
    private Double nota1;
    private Double nota2;
    private Double media;
    private MatriculaStatusEnum status;
}
