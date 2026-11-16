/**
 * ============================================================================
 *  Mago - o herói que gasta mana para causar muito dano e se curar.
 * ============================================================================
 *
 *  [HERANÇA]       "extends Personagem"  -> o Mago É UM Personagem.
 *  [ABSTRAÇÃO]     "implements Curavel"  -> o Mago ASSINA o contrato de curar.
 *
 *  Em Java uma classe só pode ter UM pai (extends), mas pode implementar
 *  VÁRIAS interfaces. Por isso as duas palavras aparecem juntas aqui.
 */
public class Mago extends Personagem implements Curavel {

    // Constantes: evitam "números mágicos" espalhados pelo código.
    private static final int CUSTO_BOLA_DE_FOGO = 10;
    private static final int CUSTO_CURA = 15;

    // [ENCAPSULAMENTO] A mana é um atributo EXCLUSIVO do Mago e é privada.
    // Ninguém de fora altera a mana: ela só muda dentro dos métodos do Mago.
    private final int manaMaxima = 50;
    private int mana = manaMaxima;

    public Mago(String nome) {
        // [HERANÇA] Chama o construtor do pai: vida máxima = 90 e força = 10.
        super(nome, 90, 10);
    }

    // ------------------------------------------------------------------------
    //  [POLIMORFISMO] SOBRESCRITA de atacar()
    //  Mesmo método do Guerreiro, comportamento totalmente diferente:
    //  usa bola de fogo (forte, gasta mana) ou cajadada (fraca, sem mana).
    // ------------------------------------------------------------------------
    @Override
    public void atacar(Personagem alvo) {
        if (mana >= CUSTO_BOLA_DE_FOGO) {
            mana = mana - CUSTO_BOLA_DE_FOGO;
            int dano = getForca() * 2 + sortear(6);
            System.out.println(getNome() + " lança uma BOLA DE FOGO em "
                    + alvo.getNome() + "!");
            alvo.receberDano(dano);
        } else {
            // Sem mana: ataque fraco, mas recupera um pouco de mana.
            mana = Math.min(manaMaxima, mana + 8);
            System.out.println(getNome() + " está sem mana e dá uma cajadada em "
                    + alvo.getNome() + " (recupera 8 de mana).");
            alvo.receberDano(4);
        }
    }

    // ------------------------------------------------------------------------
    //  [ABSTRAÇÃO] Implementação do método da interface Curavel.
    //  O @Override aqui indica que estamos cumprindo o contrato.
    // ------------------------------------------------------------------------
    @Override
    public void curar() {
        if (mana < CUSTO_CURA) {
            System.out.println(getNome() + " não tem mana suficiente para curar!");
            return;
        }
        mana = mana - CUSTO_CURA;
        // recuperarVida() é "protected" na classe pai: o Mago pode usar por herança.
        int curado = recuperarVida(30);
        System.out.println(getNome() + " usa magia de cura e recupera " + curado + " de vida.");
    }

    // ------------------------------------------------------------------------
    //  [POLIMORFISMO] Sobrescrita aproveitando o código do pai.
    //  super.getStatus() devolve "Nome [Vida: x/y]" e o Mago só ACRESCENTA a mana.
    // ------------------------------------------------------------------------
    @Override
    public String getStatus() {
        return super.getStatus() + " [Mana: " + mana + "/" + manaMaxima + "]";
    }

    // [POLIMORFISMO] Ao descansar, o Mago faz o que todo personagem faz
    // (recupera vida, via super) e MAIS: restaura a mana inteira.
    @Override
    public void descansar() {
        super.descansar();
        mana = manaMaxima;
        System.out.println(getNome() + " medita e restaura toda a mana.");
    }
}
