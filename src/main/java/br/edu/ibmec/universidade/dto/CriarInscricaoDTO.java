package br.edu.ibmec.universidade.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriarInscricaoDTO {
    private Integer alunoId;
    private Long turmaId;
}

