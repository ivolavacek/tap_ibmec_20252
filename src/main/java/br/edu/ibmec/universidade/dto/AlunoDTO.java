package br.edu.ibmec.universidade.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@XmlRootElement(name = "aluno")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlunoDTO {
	private int matricula;
	private String nome;
	private String dtNascimento;
	private int idade;
	private boolean matriculaAtiva;
	private EstadoCivilDTO estadoCivilDTO;
	private List<String> telefones;
	private int curso;

	// If you need custom logic for idade calculation, keep this method:
	public static int getIdadeConvertida(String data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dataConvertida = null;
		try {
			dataConvertida = sdf.parse(data);
			Date hoje = new Date();
			return hoje.getYear() - dataConvertida.getYear();
		} catch (Exception e) {
			return 0;
		}
	}
}
