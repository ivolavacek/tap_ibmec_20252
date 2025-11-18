package br.edu.ibmec.universidade.service.strategy;

import org.springframework.stereotype.Component;
import br.edu.ibmec.universidade.entity.Inscricao;

/**
 * Implementação simples:
 * - se media != null e media >= 6.0 => "Aprovado"
 * - caso contrário => "Reprovado"
 *
 * Você pode estender para checar faltas, pesos diferentes etc.
 */
@Component
public class SimpleApprovalStrategy implements ApprovalStrategy {

    private static final double MEDIA_MINIMA = 6.0;

    @Override
    public String calcularSituacao(Inscricao inscricao) {
        if (inscricao == null) return "Reprovado";
        Float media = inscricao.getMedia();
        if (media == null) {
            return "Reprovado";
        }
        return media >= MEDIA_MINIMA ? "Aprovado" : "Reprovado";
    }
}
