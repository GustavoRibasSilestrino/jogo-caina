/**
 * ============================================================================
 *  Monstro - inimigo comum (Goblin, Orc...).
 * ============================================================================
 *
 *  [HERANÇA]
 *  Monstro também É UM Personagem. Repare que heróis e monstros compartilham
 *  a MESMA base: ambos têm vida, recebem dano e atacam. Isso é reutilização
 *  de código, o grande benefício da herança.
 *
 *  Esta classe NÃO é abstrata: dá para fazer "new Monstro(...)", porque ela
 *  já implementa o método atacar() que o pai exigia.
 */
public class Monstro extends Personagem {

    public Monstro(String nome, int vidaMaxima, int forca) {
        // [HERANÇA] Repassa os valores para o construtor do pai.
        super(nome, vidaMaxima, forca);
    }

    // [POLIMORFISMO] Mais uma versão de atacar(): o monstro dá uma pancada simples.
    @Override
    public void atacar(Personagem alvo) {
        int dano = getForca() + sortear(5);
        System.out.println(getNome() + " ataca " + alvo.getNome() + "!");
        alvo.receberDano(dano);
    }
}
