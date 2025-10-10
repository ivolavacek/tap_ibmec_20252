package br.edu.ibmec.universidade.service;

import java.util.Collection;

import br.edu.ibmec.universidade.dao.EscolaDAO;
import br.edu.ibmec.universidade.dto.DisciplinaDTO;
import br.edu.ibmec.universidade.entity.Disciplina;
import br.edu.ibmec.universidade.exception.DaoException;
import br.edu.ibmec.universidade.exception.ServiceException;
import br.edu.ibmec.universidade.exception.ServiceException.ServiceExceptionEnum;

public class DisciplinaService {
	private final EscolaDAO dao;

	public DisciplinaService() {
		this.dao = EscolaDAO.getInstance();
	}

	public DisciplinaDTO buscarDisciplina(int codigo) throws DaoException {
		try {
            return new DisciplinaDTO(dao.getDisciplina(
                    codigo).getCodigo(), dao.getDisciplina(codigo).getNome(),
                    dao.getDisciplina(codigo).getCurso().getCodigo());
		} catch (DaoException e) {
			throw new DaoException("");
		}
	}

	public Collection<Disciplina> listarDisciplinas() {
		return dao.getDisciplinas();
	}

	public void cadastrarDisciplina(DisciplinaDTO disciplinaDTO)
			throws ServiceException, DaoException {
		if ((disciplinaDTO.getCodigo() < 1) || (disciplinaDTO.getCodigo() > 99)) {
			throw new ServiceException(
					ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
		}
		if ((disciplinaDTO.getNome().isEmpty())
				|| (disciplinaDTO.getNome().length() > 20)) {
			throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
		}

		Disciplina disciplina = new Disciplina(disciplinaDTO.getCodigo(),
				disciplinaDTO.getNome(), dao.getCurso(disciplinaDTO.getCurso()));

		try {
			dao.addDisciplina(disciplina);
		} catch (DaoException e) {
			throw new DaoException("erro do dao no service throw");
		}
	}

	public void alterarDisciplina(DisciplinaDTO disciplinaDTO)
			throws ServiceException, DaoException {
		if ((disciplinaDTO.getCodigo() < 1) || (disciplinaDTO.getCodigo() > 99)) {
			throw new ServiceException(
					ServiceExceptionEnum.CURSO_CODIGO_INVALIDO);
		}
		if ((disciplinaDTO.getNome().isEmpty())
				|| (disciplinaDTO.getNome().length() > 20)) {
			throw new ServiceException(ServiceExceptionEnum.CURSO_NOME_INVALIDO);
		}

		Disciplina disciplina = new Disciplina(disciplinaDTO.getCodigo(),
				disciplinaDTO.getNome(), dao.getCurso(disciplinaDTO.getCurso()));

		try {
			dao.updateDisciplina(disciplina);
		} catch (DaoException e) {
			throw new DaoException("erro do dao no service throw");
		}
	}

	public void removerDisciplina(int codigo) throws DaoException {
		try {
			dao.removeDisciplina(codigo);
		} catch (DaoException e) {
			throw new DaoException("");
		}
	}

}
