package br.edu.ibmec.universidade.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DataNascimento {
    private int dia;
    private int mes;
    private int ano;

    public LocalDate toLocalDate() {
        return LocalDate.of(ano, mes, dia);
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", dia, mes, ano);
    }
}
