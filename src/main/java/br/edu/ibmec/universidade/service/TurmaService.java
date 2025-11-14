package br.edu.ibmec.universidade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ibmec.universidade.entity.Turma;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.repository.TurmaRepository;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository repository;

    public List<Turma> listarTodos() throws ServiceException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erro ao listar turmas", e);
        }
    }

    public Turma buscarPorId(Long id) throws ServiceException {
        try {
            return repository.findById(id).orElseThrow(() -> new ServiceException("Turma não encontrada"));
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar turma", e);
        }
    }

    public Turma salvar(Turma turma) throws DaoException {
        try {
            return repository.save(turma);
        } catch (Exception e) {
            throw new DaoException("Erro ao salvar turma", e);
        }
    }

    public void deletar(Long id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erro ao deletar turma", e);
        }
    }
}
