package br.edu.ibmec.universidade.service.strategy;

import org.springframework.stereotype.Component;
import br.edu.ibmec.universidade.entity.Aluno;
import lombok.RequiredArgsConstructor;

/**
 * Strategy que aplica o cálculo por disciplina e depois aplica 30% de desconto.
 * Não registra como @Primary: nós escolhemos no serviço qual strategy usar com base em aluno.isBolsista().
 */
@Component
@RequiredArgsConstructor
public class MensalidadeBolsistaStrategy implements MensalidadeStrategy {

    private final MensalidadePorDisciplinaStrategy delegate;

    @Override
    public double calcularMensalidade(Aluno aluno) {
        double valorNormal = delegate.calcularMensalidade(aluno);
        return valorNormal * 0.70; // 30% de desconto
    }
}
