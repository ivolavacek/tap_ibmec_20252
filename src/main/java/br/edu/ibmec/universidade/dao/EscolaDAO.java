/**
* Aplicação com serviços REST para gestão de cursos.
*
* @author  Thiago Silva de Souza
* @version 1.0
* @since   2012-02-29
*/

package br.edu.ibmec.universidade.dao;

import java.util.*;

import br.edu.ibmec.universidade.entity.Aluno;
import br.edu.ibmec.universidade.entity.Curso;
import br.edu.ibmec.universidade.entity.DataNascimento;
import br.edu.ibmec.universidade.entity.EstadoCivil;
import br.edu.ibmec.universidade.exception.DaoException;

public class EscolaDAO {

	private Map<Integer, Aluno> alunos;
	private Map<Integer, Curso> cursos;

	private static EscolaDAO instance;

	public EscolaDAO() {
		alunos = new HashMap<Integer, Aluno>();
		cursos = new HashMap<Integer, Curso>();

		Curso curso = new Curso(99, "Computacao",600);
		try {
			this.addCurso(curso);
		} catch (DaoException e) {
			e.printStackTrace();
		}

		List<String> telefones = new ArrayList<String>();
		telefones.add("2177776666");
		telefones.add("3177776669");
		telefones.add("6177778889");

		DataNascimento data = new DataNascimento(10, 10, 1990);
		Aluno aluno = new Aluno(11, "Joao da Silva", data, true,
				EstadoCivil.SOLTEIRO, curso, telefones);
		curso.getAlunos().add(aluno);

		try {
			this.addAluno(aluno);
		} catch (DaoException e) {
			e.printStackTrace();
		}

	}

	public static EscolaDAO getInstance() {
		if (instance == null) {
			instance = new EscolaDAO();
		}
		return instance;
	}

	// ok
	public void addAluno(Aluno a) throws DaoException {
		alunos.put(a.getMatricula(), a);
		getAluno(a.getMatricula());
	}

	// ok
	public Aluno updateAluno(Aluno alunoNovo) throws DaoException {
		alunos.put(alunoNovo.getMatricula(), alunoNovo);
		return alunoNovo;
	}

	// ok
	public Collection<Aluno> getAlunos() {
		return alunos.values();
	}

	// ok
	public Aluno getAluno(int codAluno) throws DaoException {
		if (alunos.get(codAluno) == null) {
			throw new DaoException("");
		}
		return alunos.get(codAluno);
	}

	// ok
	public void removeAluno(int codAluno) throws DaoException {
		if (alunos.get(codAluno) == null) {
			throw new DaoException("");
		}
		alunos.remove(codAluno);
	}

	// ok
	public void addCurso(Curso c) throws DaoException {
		cursos.put(c.getCodigo(), c);
		getCurso(c.getCodigo());
	}

	// ok
	public Curso updateCurso(Curso cursoNovo) throws DaoException {
		cursos.put(cursoNovo.getCodigo(), cursoNovo);
		return cursoNovo;
	}

	// ok
	public Curso getCurso(int codCurso) throws DaoException {
		if (cursos.get(codCurso) == null) {
			throw new DaoException("");
		}
		Curso curso=  cursos.get(codCurso);
		System.out.println(curso.getAlunos());
		return curso;

	}

	// ok
	public Collection<Curso> getCursos() {
		return cursos.values();
	}

	// ok
	public void removeCurso(int codCurso) throws DaoException {
		if (cursos.get(codCurso) == null) {
			throw new DaoException("");
		}
		Curso curso = getCurso(codCurso);
		if (curso.getAlunos().isEmpty()) {
			System.out.println(curso.getAlunos().size());
			cursos.remove(codCurso);
		} else {
			throw new DaoException("");
		}
	}
}