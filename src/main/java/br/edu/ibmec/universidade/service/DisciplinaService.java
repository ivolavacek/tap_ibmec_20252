package br.edu.ibmec.universidade.service;

import br.edu.ibmec.universidade.dto.DisciplinaDTO;
import br.edu.ibmec.universidade.entity.Curso;
import br.edu.ibmec.universidade.entity.Disciplina;
import br.edu.ibmec.universidade.entity.Turma;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.repository.CursoRepository;
import br.edu.ibmec.universidade.repository.DisciplinaRepository;
import br.edu.ibmec.universidade.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final CursoRepository cursoRepository;
    private final TurmaRepository turmaRepository;

    public Disciplina criarDisciplina(DisciplinaDTO dto) throws DaoException {

        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new DaoException("Curso não encontrado"));

        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo(dto.getCodigo());
        disciplina.setNome(dto.getNome());
        disciplina.setCurso(curso);

        disciplina = disciplinaRepository.save(disciplina);

        // ---- CRIA TURMA AUTOMATICAMENTE ----
        Turma turma = new Turma();
        turma.setAno(2024);
        turma.setSemestre(1);
        turma.setCodigo(dto.getCodigo() * 10);
        turma.setDisciplina(disciplina);

        turmaRepository.save(turma);

        return disciplina;
    }

    // ----- RESTANTE -----

    public List<Disciplina> listarTodos() {
        return disciplinaRepository.findAll();
    }

    public Disciplina buscarPorId(Long id) throws ServiceException {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Disciplina não encontrada"));
    }

    public Disciplina salvar(Disciplina disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public void deletar(Long id) {
        disciplinaRepository.deleteById(id);
    }
}
