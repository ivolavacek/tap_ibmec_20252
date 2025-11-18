package br.edu.ibmec.universidade.dto;


import lombok.Data;

@Data
public class AtualizarNotasDTO {
    private Float avaliacao1;
    private Float avaliacao2;
    private Float media;
    private Integer numFaltas;
    private String situacao; // "Aprovado" / "Reprovado" / etc (opcional)
}
