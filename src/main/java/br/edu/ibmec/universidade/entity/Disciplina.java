package br.edu.ibmec.universidade.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Disciplina {
    @Id
	private int codigo;
	private String nome;
    @ManyToOne
	private Curso curso;

//	private List<Turma> turmas = new ArrayList<>();
	//private List<AlunoMonitor> monitores = new ArrayList<AlunoMonitor>();

	public Disciplina() {
		
	}
	
	public Disciplina(int codigo, String nome, Curso curso) {
		this.codigo = codigo;
		this.nome = nome;
		this.curso = curso;
	}

//	public void addTurma(Turma turma) {
//		turmas.add(turma);
//	}
//
//	public void removeTurma(Turma turma) {
//		turmas.remove(turma);
//	}

}
