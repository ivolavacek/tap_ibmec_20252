package br.edu.ibmec.universidade.service;

import java.util.List;

import br.edu.ibmec.universidade.entity.Aluno;
import br.edu.ibmec.universidade.entity.Turma;
import br.edu.ibmec.universidade.repository.AlunoRepository;
import br.edu.ibmec.universidade.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ibmec.universidade.entity.Inscricao;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.repository.InscricaoRepository;

@Service
@RequiredArgsConstructor
public class InscricaoService {

    @Autowired
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final InscricaoRepository inscricaoRepository;

    public Inscricao criarInscricao(Integer alunoId, Long turmaId) {

        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        Turma turma = turmaRepository.findById(turmaId)
                .orElseThrow(() -> new RuntimeException("Turma não encontrada"));

        // 🔒 Regra 1: aluno só pode se inscrever em disciplina do seu curso
        if (!aluno.getCurso().getId().equals(turma.getDisciplina().getCurso().getId())) {
            throw new RuntimeException("Aluno não pode se inscrever em disciplina de outro curso");
        }

        // 🔥 Criação obrigatória: aluno + turma
        Inscricao inscricao = new Inscricao(aluno, turma);

        // Amarra bidirecional
        aluno.addInscricao(inscricao);
        turma.addInscricao(inscricao);

        return inscricaoRepository.save(inscricao);
    }

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
        // Setando as avaliações, faltas e situação, caso necessário.
        aluno.addInscricao(inscricao);
    }

    public Inscricao buscarPorId(Long id) throws ServiceException {
        try {
            return inscricaoRepository.findById(id).orElseThrow(() -> new ServiceException("Inscrição não encontrada"));
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
