package br.edu.ibmec.universidade.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Disciplina {
	private int codigo;
	private String nome;
	private Curso curso;

	private List<Turma> turmas = new ArrayList<Turma>();
	//private List<AlunoMonitor> monitores = new ArrayList<AlunoMonitor>();

	public Disciplina() {
		
	}
	
	public Disciplina(int codigo, String nome, Curso curso) {
		this.codigo = codigo;
		this.nome = nome;
		this.curso = curso;
	}

	public void addTurma(Turma turma) {
		turmas.add(turma);
	}

	public void removeTurma(Turma turma) {
		turmas.remove(turma);
	}

}
