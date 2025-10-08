package br.edu.ibmec.universidade.entity;

import lombok.Data;

@Data
public class Avaliacao {
	private long id;
	private float avaliacao1;
	private float avaliacao2;
	private float media;
	private int numFaltas;
	private String situacao;

	private Inscricao inscricao;

	public Avaliacao() {

	}

	public Avaliacao(Inscricao inscricao) {
		this.inscricao = inscricao;
	}

}
