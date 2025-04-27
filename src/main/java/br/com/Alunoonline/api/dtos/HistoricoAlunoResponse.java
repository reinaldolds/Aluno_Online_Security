package br.com.Alunoonline.api.dtos;


import lombok.Data;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Data
public class HistoricoAlunoResponse {
    private String nomeAluno;
    private String emailAluno;
    private String cpfAluno;
    private List<DisciplinaAlunoResponse> disciplinaAlunoResponses;
}
