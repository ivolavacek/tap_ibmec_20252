package br.edu.ibmec.universidade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ibmec.universidade.entity.Professor;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.repository.ProfessorRepository;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository repository;

    public List<Professor> listarTodos() throws ServiceException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erro ao listar professores", e);
        }
    }

    public Professor buscarPorId(Long id) throws ServiceException {
        try {
            return repository.findById(id).orElseThrow(() -> new ServiceException("Professor não encontrado"));
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar professor", e);
        }
    }

    public Professor salvar(Professor professor) throws DaoException {
        try {
            return repository.save(professor);
        } catch (Exception e) {
            throw new DaoException("Erro ao salvar professor", e);
        }
    }

    public void deletar(Long id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erro ao deletar professor", e);
        }
    }
}
