package br.edu.ibmec.universidade.service;

import br.edu.ibmec.universidade.dto.CursoDTO;
import br.edu.ibmec.universidade.entity.Curso;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.exception.ServiceException.ServiceExceptionEnum;
import br.edu.ibmec.universidade.repository.CursoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public CursoDTO buscarCurso(int codigo) throws DaoException {
        Curso curso = cursoRepository.findById(codigo)
                .orElseThrow(() -> new DaoException("Curso não encontrado"));
        return new CursoDTO(curso.getCodigo(), curso.getNome(), curso.getValorPorDisciplina());
    }

    public Collection<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    public void cadastrarCurso(CursoDTO cursoDTO) throws ServiceException {
        validar(cursoDTO);
        Curso curso = new Curso(cursoDTO.getCodigo(), cursoDTO.getNome(),cursoDTO.getValorPorDisciplina());
        cursoRepository.save(curso);
    }

    public void alterarCurso(CursoDTO cursoDTO) throws ServiceException, DaoException {
        validar(cursoDTO);
        if (!cursoRepository.existsById(cursoDTO.getCodigo())) {
            throw new DaoException("Curso não encontrado");
        }
        Curso curso = new Curso(cursoDTO.getCodigo(), cursoDTO.getNome(), cursoDTO.getValorPorDisciplina());
        cursoRepository.save(curso);
    }

    public void removerCurso(int codigo) throws DaoException {
        if (!cursoRepository.existsById(codigo)) {
            throw new DaoException("Curso não encontrado");
        }
        cursoRepository.deleteById(codigo);
    }

    private void validar(CursoDTO dto) throws ServiceException {
        if (dto.getCodigo() < 1 || dto.getCodigo() > 99) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
        }
        if (dto.getNome() == null || dto.getNome().isBlank() || dto.getNome().length() > 20) {
            throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
        }
    }
}
