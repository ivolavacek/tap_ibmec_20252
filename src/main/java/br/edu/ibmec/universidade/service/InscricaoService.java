package br.edu.ibmec.universidade.service;

import java.util.List;
import java.util.Objects;

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
import br.edu.ibmec.universidade.service.strategy.ApprovalStrategy;

@Service
@RequiredArgsConstructor
public class InscricaoService {

    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final InscricaoRepository inscricaoRepository;
    private final ApprovalStrategy approvalStrategy;

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

        // validação: aluno só pode inscrever em disciplinas do próprio curso
        if (!Objects.equals(aluno.getCurso().getCodigo(), turma.getDisciplina().getCurso().getCodigo())) {
            throw new DaoException("Aluno não pode se inscrever em disciplina de outro curso");
        }

        // evita inscrição duplicada (se repository suportar método). Caso não exista, comente essa verificação e eu crio o repo method.
        boolean jaInscrito = inscricaoRepository.existsByAlunoMatriculaAndTurmaId(alunoId, turmaId);
        if (jaInscrito) {
            throw new DaoException("Aluno já inscrito nesta turma");
        }

        Inscricao inscricao = new Inscricao(aluno, turma);

        aluno.addInscricao(inscricao);
        turma.addInscricao(inscricao);

        return inscricaoRepository.save(inscricao);
    }

    // -------------------------------------------------------------
    // ATUALIZAR NOTAS / MEDIA / FALTAS / SITUACAO - métodos específicos
    // -------------------------------------------------------------
    @Transactional
    public Inscricao atualizarAvaliacao1(Long inscricaoId, Float avaliacao1) throws DaoException {
        Inscricao inscricao = findInscricaoOrThrow(inscricaoId);
        inscricao.setAvaliacao1(avaliacao1);
        // opcional: recalcular media se quiser aqui
        return inscricaoRepository.save(inscricao);
    }

    @Transactional
    public Inscricao atualizarAvaliacao2(Long inscricaoId, Float avaliacao2) throws DaoException {
        Inscricao inscricao = findInscricaoOrThrow(inscricaoId);
        inscricao.setAvaliacao2(avaliacao2);
        return inscricaoRepository.save(inscricao);
    }

    @Transactional
    public Inscricao atualizarMedia(Long inscricaoId, Float media) throws DaoException {
        Inscricao inscricao = findInscricaoOrThrow(inscricaoId);
        inscricao.setMedia(media);

        // atualiza a situacao automaticamente usando a strategy de approval
        String situacao = approvalStrategy.calcularSituacao(inscricao);
        inscricao.setSituacao(situacao);

        return inscricaoRepository.save(inscricao);
    }

    @Transactional
    public Inscricao atualizarFaltas(Long inscricaoId, Integer numFaltas) throws DaoException {
        Inscricao inscricao = findInscricaoOrThrow(inscricaoId);
        inscricao.setNumFaltas(numFaltas);
        return inscricaoRepository.save(inscricao);
    }

    @Transactional
    public Inscricao atualizarSituacao(Long inscricaoId, String situacao) throws DaoException {
        Inscricao inscricao = findInscricaoOrThrow(inscricaoId);
        inscricao.setSituacao(situacao);
        return inscricaoRepository.save(inscricao);
    }

    private Inscricao findInscricaoOrThrow(Long inscricaoId) throws DaoException {
        return inscricaoRepository.findById(inscricaoId)
                .orElseThrow(() -> new DaoException("Inscrição não encontrada"));
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
