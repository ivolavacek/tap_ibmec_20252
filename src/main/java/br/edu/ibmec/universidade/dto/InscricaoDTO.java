package br.edu.ibmec.universidade.dto;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@XmlRootElement(name = "inscricao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscricaoDTO {
	private float avaliacao1;
	private float avaliacao2;
	private float media;
	private int numFaltas;
	private String situacao;
	private int aluno;
	private int codigo;
	private int ano;
	private int semestre;
}
