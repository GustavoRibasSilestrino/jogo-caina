import java.util.Random;

/**
 * ============================================================================
 *  CLASSE BASE DO JOGO: Personagem
 * ============================================================================
 *
 *  [ABSTRAÇÃO]
 *  A palavra "abstract" na declaração da classe significa que NÃO existe
 *  "um personagem genérico" no jogo: só existem Guerreiro, Mago, Monstro...
 *  Por isso não dá para fazer "new Personagem(...)". A classe serve apenas de
 *  molde, com o que é COMUM a todos (nome, vida, força) e deixa o que é
 *  DIFERENTE (como atacar) para as classes filhas definirem.
 *
 *  [HERANÇA]
 *  Esta é a classe PAI (superclasse). Guerreiro, Mago, Arqueiro e Monstro
 *  herdam daqui com "extends" e ganham de graça todos os atributos e métodos
 *  abaixo, sem precisar reescrever nada.
 */
public abstract class Personagem {

    // ------------------------------------------------------------------------
    //  [ENCAPSULAMENTO]
    //  Todos os atributos são "private": ninguém de fora da classe consegue
    //  mexer neles diretamente (nada de heroi.vida = 9999).
    //  O único caminho é pelos métodos públicos, que aplicam as regras do jogo.
    // ------------------------------------------------------------------------
    private final String nome;
    private int vida;
    private final int vidaMaxima;
    private final int forca;

    // [ENCAPSULAMENTO] Até o gerador de números aleatórios é escondido.
    // As filhas usam os métodos sortear() e chance() em vez de acessá-lo.
    private static final Random SORTEIO = new Random();

    // Construtor: monta o personagem já com valores válidos.
    public Personagem(String nome, int vidaMaxima, int forca) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vida = vidaMaxima; // começa com a vida cheia
        this.forca = forca;
    }

    // ------------------------------------------------------------------------
    //  [ENCAPSULAMENTO] Getters: permitem LER o valor, mas não alterar.
    //  (Não existe setVida(): a vida só muda por receberDano() e recuperarVida())
    // ------------------------------------------------------------------------
    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    // "protected": só as classes filhas precisam saber da força base.
    protected int getForca() {
        return forca;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    // ------------------------------------------------------------------------
    //  [ENCAPSULAMENTO] Método que protege a regra "vida nunca fica negativa".
    //  Quem ataca NÃO mexe na vida do alvo: ele pede ao alvo para receber dano,
    //  e o alvo decide o que fazer com isso.
    // ------------------------------------------------------------------------
    public void receberDano(int dano) {
        if (dano < 0) {
            dano = 0; // validação: dano negativo curaria o alvo!
        }
        vida = vida - dano;
        if (vida < 0) {
            vida = 0; // validação: a vida nunca fica abaixo de zero
        }
        System.out.println("   -> " + nome + " perdeu " + dano + " de vida.");
        if (vida == 0) {
            System.out.println("   -> " + nome + " foi derrotado!");
        }
    }

    // [ENCAPSULAMENTO] "protected": só a própria classe e as filhas podem
    // curar. Código de fora não consegue chamar isto diretamente.
    // Retorna quanto de vida foi realmente recuperado (respeitando o máximo).
    protected int recuperarVida(int quantidade) {
        int vidaAntes = vida;
        vida = Math.min(vidaMaxima, vida + quantidade);
        return vida - vidaAntes;
    }

    // Métodos auxiliares de sorteio, usados pelas classes filhas.
    protected int sortear(int limite) {
        return SORTEIO.nextInt(limite); // devolve de 0 até limite-1
    }

    protected boolean chance(int porcentagem) {
        return SORTEIO.nextInt(100) < porcentagem;
    }

    // ------------------------------------------------------------------------
    //  [ABSTRAÇÃO] MÉTODO ABSTRATO
    //  Sem corpo e terminado em ";" - é uma OBRIGAÇÃO para as filhas.
    //  Todo personagem sabe atacar, mas cada tipo ataca de um jeito.
    //
    //  [POLIMORFISMO] Este é o ponto de partida do polimorfismo: a mesma
    //  chamada "personagem.atacar(alvo)" vai ter comportamentos diferentes,
    //  dependendo de QUEM é o personagem (veja as classes filhas e a Main).
    // ------------------------------------------------------------------------
    public abstract void atacar(Personagem alvo);

    // ------------------------------------------------------------------------
    //  [POLIMORFISMO] Método com implementação padrão que as filhas PODEM
    //  sobrescrever (@Override) se quiserem mudar o comportamento.
    //  O Mago, por exemplo, sobrescreve getStatus() para mostrar a mana.
    // ------------------------------------------------------------------------
    public String getStatus() {
        return nome + " [Vida: " + vida + "/" + vidaMaxima + "]";
    }

    // Descanso entre as batalhas. O Mago sobrescreve para recuperar a mana também.
    public void descansar() {
        int recuperado = recuperarVida(vidaMaxima * 30 / 100); // 30% da vida máxima
        System.out.println(nome + " descansa e recupera " + recuperado + " de vida.");
    }
}
