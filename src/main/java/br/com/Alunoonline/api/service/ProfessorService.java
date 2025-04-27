package br.com.Alunoonline.api.service;


import br.com.Alunoonline.api.model.Professor;
import br.com.Alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository professorRepository;

    public void criarProfessor(Professor professor){
        professorRepository.save(professor);
    }

    public List<Professor> listarTodosProfessores(){ return professorRepository.findAll();}

    public Optional<Professor> buscarProfessorPorId(Long id){
       return professorRepository.findById(id);
    }

    public void deletarProfessorPorId(Long id){
        professorRepository.deleteById(id);
    }

    public void atualizarprofessorPorId(Long id, Professor professor){

        Optional<Professor> professorDoBanco = buscarProfessorPorId(id);
        //se não existir
        if (professorDoBanco.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Professor não encontrado no Banco de Dados");
        }
        // caso exista
        Professor professorEditado = professorDoBanco.get();

        professorEditado.setNome(professor.getNome());
        professorEditado.setCpf(professor.getCpf());
        professorEditado.setEmail(professor.getEmail());

        professorRepository.save(professorEditado);
    }
}
