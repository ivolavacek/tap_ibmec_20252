package br.edu.ibmec.universidade.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import br.edu.ibmec.universidade.dto.AlunoDTO;
import br.edu.ibmec.universidade.entity.Aluno;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.repository.AlunoRepository;
import br.edu.ibmec.universidade.service.strategy.MensalidadeBolsistaStrategy;
import br.edu.ibmec.universidade.service.strategy.MensalidadePorDisciplinaStrategy;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final MensalidadePorDisciplinaStrategy normalStrategy;
    private final MensalidadeBolsistaStrategy bolsistaStrategy;

    /**
     * Buscar aluno e converter para DTO
     */
    public AlunoDTO buscarAluno(int matricula) throws DaoException {
        Aluno aluno = alunoRepository.findById(matricula)
                .orElseThrow(() -> new DaoException("Aluno não encontrado"));
        return new AlunoDTO(aluno);
    }

    /**
     * Cadastrar aluno
     */
    public void cadastrarAluno(AlunoDTO dto) throws ServiceException, DaoException {
        if (alunoRepository.existsById(dto.getMatricula())) {
            throw new ServiceException("Aluno já existe");
        }

        Aluno aluno = dto.toEntity(); // você já tem esse método
        alunoRepository.save(aluno);
    }

    /**
     * Alterar aluno
     */
    public void alterarAluno(AlunoDTO dto) throws ServiceException, DaoException {
        Aluno aluno = alunoRepository.findById(dto.getMatricula())
                .orElseThrow(() -> new DaoException("Aluno não encontrado"));

        aluno.setNome(dto.getNome());
        aluno.setMatriculaAtiva(dto.isMatriculaAtiva());
        aluno.setTelefones(dto.getTelefones());
        aluno.setEstadoCivil(dto.getEstadoCivil() != null ?
                dto.getEstadoCivil().toEntity() : null);

        alunoRepository.save(aluno);
    }

    /**
     * Remover aluno
     */
    public void removerAluno(int matricula) throws DaoException {
        if (!alunoRepository.existsById(matricula)) {
            throw new DaoException("Aluno não encontrado");
        }
        alunoRepository.deleteById(matricula);
    }

    /**
     * Listar todos os alunos
     */
    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }

    /**
     * Calcula mensalidade usando strategy + bolsista
     */
    public double calcularMensalidade(int matricula) throws DaoException {
        Aluno aluno = alunoRepository.findById(matricula)
                .orElseThrow(() -> new DaoException("Aluno não encontrado"));

        return aluno.isBolsista()
                ? bolsistaStrategy.calcularMensalidade(aluno)
                : normalStrategy.calcularMensalidade(aluno);
    }
}
