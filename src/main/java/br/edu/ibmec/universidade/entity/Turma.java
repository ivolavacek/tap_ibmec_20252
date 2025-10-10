package br.edu.ibmec.universidade.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class Turma {
	private int codigo;
	private int ano;
	private int semestre;

	private Disciplina disciplina;
	private List<Inscricao> inscricoes = new ArrayList<>();

	public Turma() {
		
	}
	
	public Turma(int codigo, int ano, int semestre, Disciplina disciplina) {
		this.codigo = codigo;
		this.ano = ano;
		this.semestre = semestre;
		this.disciplina = disciplina;
	}

	public void addInscricao(Inscricao inscricao) {
		inscricoes.add(inscricao);
	}

	public void removeInscricao(Inscricao inscricao) {
		inscricoes.remove(inscricao);
	}

}
