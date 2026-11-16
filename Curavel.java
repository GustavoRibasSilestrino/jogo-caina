/**
 * ============================================================================
 *  [ABSTRAÇÃO] - INTERFACE
 * ============================================================================
 * Uma interface é o nível MÁXIMO de abstração: ela só diz "O QUE" deve ser
 * feito (curar), e não "COMO" (isso fica por conta de cada classe).
 *
 * Quem implementa Curavel é OBRIGADO a escrever o método curar().
 *  - O Mago cura gastando mana.
 *  - O Arqueiro cura usando poções.
 *  - O Guerreiro NÃO implementa Curavel, então ele não cura.
 *
 * Isso mostra que a interface funciona como um "contrato": quem assina o
 * contrato (implements) promete que sabe curar.
 */
public interface Curavel {

    // [ABSTRAÇÃO] Método sem corpo: cada classe define o seu jeito de curar.
    void curar();
}
