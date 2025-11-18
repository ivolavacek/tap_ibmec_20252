package br.edu.ibmec.universidade.service;

import java.util.List;

import br.edu.ibmec.universidade.entity.Aluno;
import br.edu.ibmec.universidade.entity.Turma;
import br.edu.ibmec.universidade.repository.AlunoRepository;
import br.edu.ibmec.universidade.repository.TurmaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import br.edu.ibmec.universidade.entity.Inscricao;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.repository.InscricaoRepository;

@Service
@RequiredArgsConstructor
public class InscricaoService {

    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final InscricaoRepository inscricaoRepository;

    // -------------------------------------------------------------
    // CRIAR INSCRIÇÃO (somente aluno + turma)
    // -------------------------------------------------------------
    @Transactional
    public Inscricao criarInscricao(Integer alunoId, Long turmaId) throws DaoException {

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new DaoException("Aluno não encontrado"));

        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new DaoException("Turma não encontrada"));

        if (aluno.getCurso() == null || turma.getDisciplina() == null || turma.getDisciplina().getCurso() == null) {
            throw new DaoException("Dados incompletos para validação de curso");
        }

        // se quiser validar curso, descomente:
        // if (!aluno.getCurso().getCodigo().equals(turma.getDisciplina().getCurso().getCodigo())) {
        //     throw new DaoException("Aluno não pode se inscrever em disciplina de outro curso");
        // }

        Inscricao inscricao = new Inscricao(aluno, turma);

        aluno.addInscricao(inscricao);
        turma.addInscricao(inscricao);

        return inscricaoRepository.save(inscricao);
    }

    // -------------------------------------------------------------
    // ATUALIZAR NOTAS E FALTAS
    // -------------------------------------------------------------
    @Transactional
    public Inscricao atualizarNotasEFaltas(Long inscricaoId, Float avaliacao1, Float avaliacao2, Float media,
                                           Integer numFaltas, String situacao) throws DaoException {

        Inscricao inscricao = inscricaoRepository.findById(inscricaoId)
                .orElseThrow(() -> new DaoException("Inscrição não encontrada"));

        if (avaliacao1 != null) inscricao.setAvaliacao1(avaliacao1);
        if (avaliacao2 != null) inscricao.setAvaliacao2(avaliacao2);
        if (media != null) inscricao.setMedia(media);
        if (numFaltas != null) inscricao.setNumFaltas(numFaltas);
        if (situacao != null) inscricao.setSituacao(situacao);

        return inscricaoRepository.save(inscricao);
    }

    // -------------------------------------------------------------
    // OUTROS MÉTODOS
    // -------------------------------------------------------------

    public List<Inscricao> listarTodos() throws ServiceException {
        try {
            return inscricaoRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erro ao listar inscrições", e);
        }
    }

    public void inscreverAlunoEmTurma(Aluno aluno, Turma turma) {
        Inscricao inscricao = new Inscricao();
        inscricao.setAluno(aluno);
        inscricao.setTurma(turma);
        aluno.addInscricao(inscricao);
    }

    public Inscricao buscarPorId(Long id) throws ServiceException {
        try {
            return inscricaoRepository.findById(id)
                    .orElseThrow(() -> new ServiceException("Inscrição não encontrada"));
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar inscrição", e);
        }
    }

    public Inscricao salvar(Inscricao inscricao) throws DaoException {
        try {
            return inscricaoRepository.save(inscricao);
        } catch (Exception e) {
            throw new DaoException("Erro ao salvar inscrição", e);
        }
    }

    public void deletar(Long id) throws ServiceException {
        try {
            inscricaoRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erro ao deletar inscrição", e);
        }
    }
}
