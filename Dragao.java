/**
 * ============================================================================
 *  Dragao - o chefão final.
 * ============================================================================
 *
 *  [HERANÇA EM VÁRIOS NÍVEIS]
 *  Personagem  ->  Monstro  ->  Dragao
 *  O Dragão herda do Monstro, que por sua vez herda de Personagem. Ele tem
 *  tudo dos dois e só escreve o que é especial nele: o sopro de fogo.
 */
public class Dragao extends Monstro {

    public Dragao(String nome, int vidaMaxima, int forca) {
        super(nome, vidaMaxima, forca);
    }

    // ------------------------------------------------------------------------
    //  [POLIMORFISMO] SOBRESCRITA reaproveitando o pai
    //  Em 1/3 das vezes o Dragão cospe fogo (dano dobrado). No resto, ele usa
    //  "super.atacar(alvo)", ou seja, o ataque normal do Monstro, sem precisar
    //  copiar aquele código.
    // ------------------------------------------------------------------------
    @Override
    public void atacar(Personagem alvo) {
        if (chance(33)) {
            int dano = getForca() * 2 + sortear(5);
            System.out.println(getNome() + " cospe FOGO em " + alvo.getNome() + "!");
            alvo.receberDano(dano);
        } else {
            super.atacar(alvo); // ataque comum herdado de Monstro
        }
    }
}
