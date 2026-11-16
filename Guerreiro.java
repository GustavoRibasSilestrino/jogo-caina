/**
 * ============================================================================
 *  Guerreiro - o herói "tanque": muita vida e golpes críticos.
 * ============================================================================
 *
 *  [HERANÇA]
 *  "extends Personagem" = o Guerreiro É UM Personagem.
 *  Ele já nasce com nome, vida, força, receberDano(), estaVivo()... tudo
 *  herdado, sem escrever uma linha disso aqui.
 *  Observe que ele NÃO implementa Curavel: o Guerreiro não sabe se curar
 *  durante a luta (só descansando entre as batalhas).
 */
public class Guerreiro extends Personagem {

    public Guerreiro(String nome) {
        // [HERANÇA] super(...) chama o construtor da classe pai (Personagem)
        // passando: nome, vida máxima = 120 e força = 15.
        super(nome, 120, 15);
    }

    // ------------------------------------------------------------------------
    //  [POLIMORFISMO] SOBRESCRITA (@Override)
    //  A classe pai deixou atacar() abstrato; aqui o Guerreiro dá a SUA versão:
    //  golpe de espada com 25% de chance de crítico (dano dobrado).
    // ------------------------------------------------------------------------
    @Override
    public void atacar(Personagem alvo) {
        int dano = getForca() + sortear(6); // getForca() veio da classe pai (herança)

        if (chance(25)) {
            dano = dano * 2;
            System.out.println(getNome() + " acerta um GOLPE CRÍTICO de espada em "
                    + alvo.getNome() + "!");
        } else {
            System.out.println(getNome() + " golpeia " + alvo.getNome() + " com a espada.");
        }

        // [ENCAPSULAMENTO] Não mexemos na vida do alvo diretamente:
        // pedimos para ele receber o dano e ele aplica as regras dele.
        alvo.receberDano(dano);
    }
}
