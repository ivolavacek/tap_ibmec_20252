package br.edu.ibmec.universidade.dto;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlunoDTO {

    private int matricula;
    private String nome;

    // Mantemos como String porque o service já formata "dd/MM/yyyy"
    // (se preferir LocalDate, eu ajusto o service.)
    private String dtNascimento; // formato: dd/MM/yyyy

    private int idade;
    private boolean matriculaAtiva;
    private EstadoCivilDTO estadoCivilDTO;
    private List<String> telefones;
    private int curso;

    // Cálculo de idade correto usando java.time
    public static int getIdadeConvertida(String data) {
        if (data == null || data.isBlank()) return 0;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate nascimento = LocalDate.parse(data, fmt);
            return Period.between(nascimento, LocalDate.now()).getYears();
        } catch (Exception e) {
            return 0;
        }
    }
}
