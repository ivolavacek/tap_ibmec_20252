package br.edu.ibmec.universidade.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ibmec.universidade.entity.Inscricao;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.repository.InscricaoRepository;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository repository;

    public List<Inscricao> listarTodos() throws ServiceException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erro ao listar inscrições", e);
        }
    }

    public Inscricao buscarPorId(Long id) throws ServiceException {
        try {
            return repository.findById(id).orElseThrow(() -> new ServiceException("Inscrição não encontrada"));
        } catch (Exception e) {
            throw new ServiceException("Erro ao buscar inscrição", e);
        }
    }

    public Inscricao salvar(Inscricao inscricao) throws DaoException {
        try {
            return repository.save(inscricao);
        } catch (Exception e) {
            throw new DaoException("Erro ao salvar inscrição", e);
        }
    }

    public void deletar(Long id) throws ServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erro ao deletar inscrição", e);
        }
    }
}
