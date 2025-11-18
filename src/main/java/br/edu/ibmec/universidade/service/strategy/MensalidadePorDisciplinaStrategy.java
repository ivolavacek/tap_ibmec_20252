package br.edu.ibmec.universidade.service.strategy;

import org.springframework.stereotype.Component;
import br.edu.ibmec.universidade.entity.Aluno;
import br.edu.ibmec.universidade.entity.Inscricao;

@Component
public class MensalidadePorDisciplinaStrategy implements MensalidadeStrategy {

    @Override
    public double calcularMensalidade(Aluno aluno) {
        double totalMensalidade = 0;
        if (aluno == null || aluno.getCurso() == null) {
            return 0;
        }
        for (Inscricao inscricao : aluno.getInscricoes()) {
            if (inscricao != null && inscricao.getTurma() != null
                    && inscricao.getTurma().getDisciplina() != null
                    && inscricao.getTurma().getDisciplina().getCurso() != null) {
                Double valor = inscricao.getTurma().getDisciplina().getCurso().getValorPorDisciplina();
                if (valor != null) {
                    totalMensalidade += valor;
                }
            }
        }
        return totalMensalidade;
    }
}
