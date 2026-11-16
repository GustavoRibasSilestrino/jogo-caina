/**
 * ============================================================================
 *  Arqueiro - o herói de ataque duplo, com poções limitadas.
 * ============================================================================
 *
 *  [HERANÇA]   "extends Personagem" -> o Arqueiro É UM Personagem.
 *  [ABSTRAÇÃO] "implements Curavel" -> o Arqueiro também promete saber curar,
 *              mas do SEU jeito (poções), diferente do Mago (mana).
 *              Duas classes diferentes cumprindo o mesmo contrato.
 */
public class Arqueiro extends Personagem implements Curavel {

    // [ENCAPSULAMENTO] Quantidade de poções é privada: só diminui quando o
    // próprio Arqueiro usa uma. Ninguém de fora consegue "ganhar" poções.
    private int pocoes = 2;

    public Arqueiro(String nome) {
        // [HERANÇA] Chama o construtor do pai: vida máxima = 100 e força = 9.
        super(nome, 100, 9);
    }

    // ------------------------------------------------------------------------
    //  [POLIMORFISMO] SOBRESCRITA de atacar()
    //  O Arqueiro atira DUAS flechas, e cada uma tem 20% de chance de errar.
    // ------------------------------------------------------------------------
    @Override
    public void atacar(Personagem alvo) {
        System.out.println(getNome() + " dispara duas flechas contra " + alvo.getNome() + "!");

        int danoTotal = 0;
        for (int flecha = 1; flecha <= 2; flecha++) {
            if (chance(20)) {
                System.out.println("   A flecha " + flecha + " errou o alvo.");
            } else {
                int dano = getForca() + sortear(4);
                System.out.println("   A flecha " + flecha + " acertou! (" + dano + " de dano)");
                danoTotal = danoTotal + dano;
            }
        }

        if (danoTotal > 0) {
            alvo.receberDano(danoTotal);
        }
    }

    // [ABSTRAÇÃO] Implementação do contrato Curavel, agora usando poções.
    @Override
    public void curar() {
        if (pocoes == 0) {
            System.out.println(getNome() + " procura uma poção, mas não tem mais nenhuma!");
            return;
        }
        pocoes = pocoes - 1;
        int curado = recuperarVida(40);
        System.out.println(getNome() + " bebe uma poção e recupera " + curado
                + " de vida. (restam " + pocoes + ")");
    }

    // [POLIMORFISMO] Sobrescrita: acrescenta as poções ao status padrão do pai.
    @Override
    public String getStatus() {
        return super.getStatus() + " [Poções: " + pocoes + "]";
    }
}
