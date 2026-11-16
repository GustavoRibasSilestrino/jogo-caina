import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ============================================================================
 *  BATALHA DOS HERÓIS - jogo de texto simples para demonstrar os 4 pilares da POO
 * ============================================================================
 *
 *  Como funciona: você escolhe um herói e enfrenta 3 inimigos em sequência
 *  (Goblin, Orc e Dragão). Em cada turno você ataca (ou cura, se o seu herói
 *  souber) e depois o inimigo ataca você. Zerou a vida, perdeu.
 *
 *  ----------------------------------------------------------------------------
 *  MAPA DOS 4 PILARES (para consultar na hora de explicar)
 *  ----------------------------------------------------------------------------
 *  1) ABSTRAÇÃO
 *       - Personagem.java : classe "abstract" + método abstrato atacar()
 *       - Curavel.java    : interface (contrato que diz O QUE, não COMO)
 *
 *  2) ENCAPSULAMENTO
 *       - Personagem.java : atributos "private", getters, receberDano() validando
 *       - Mago.java       : mana privada; Arqueiro.java: poções privadas
 *
 *  3) HERANÇA
 *       - Guerreiro, Mago, Arqueiro e Monstro "extends Personagem"
 *       - Dragao "extends Monstro" (herança em dois níveis)
 *       - super(...) nos construtores e super.método() nas sobrescritas
 *
 *  4) POLIMORFISMO
 *       - Cada filha sobrescreve atacar() com @Override (sobrescrita)
 *       - Aqui na Main: List<Personagem>, heroi.atacar() e inimigo.atacar()
 *         funcionam para QUALQUER tipo de personagem (veja o método batalhar)
 *
 *  Dica: procure no código pelas marcas [ABSTRAÇÃO], [ENCAPSULAMENTO],
 *  [HERANÇA] e [POLIMORFISMO] (Ctrl+F) para achar cada ponto rapidamente.
 */
public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.println("=====================================");
        System.out.println("        BATALHA DOS HERÓIS");
        System.out.println("=====================================");

        // ---------------------------------------------------------------------
        //  [POLIMORFISMO] A variável é do tipo Personagem (o PAI), mas dentro
        //  dela pode estar um Guerreiro, um Mago ou um Arqueiro (as FILHAS).
        //  O Java só descobre qual é na hora de executar.
        // ---------------------------------------------------------------------
        Personagem heroi = escolherHeroi(teclado);

        // ---------------------------------------------------------------------
        //  [POLIMORFISMO] Uma única lista guarda inimigos de TIPOS diferentes
        //  (dois Monstro e um Dragao), todos tratados como Personagem.
        //  [HERANÇA] Isso só é possível porque todos herdam de Personagem.
        // ---------------------------------------------------------------------
        List<Personagem> inimigos = new ArrayList<Personagem>();
        inimigos.add(new Monstro("Goblin", 40, 6));
        inimigos.add(new Monstro("Orc", 70, 9));
        inimigos.add(new Dragao("Dragão Vermelho", 90, 10));

        for (int i = 0; i < inimigos.size(); i++) {
            Personagem inimigo = inimigos.get(i);

            boolean venceu = batalhar(heroi, inimigo, teclado);
            if (!venceu) {
                System.out.println("\n*** " + heroi.getNome() + " caiu em combate. FIM DE JOGO. ***");
                return;
            }

            System.out.println("\n*** " + inimigo.getNome() + " foi derrotado! ***");

            // Descansa entre as lutas (menos depois do último inimigo).
            if (i < inimigos.size() - 1) {
                // [POLIMORFISMO] Se o herói for um Mago, roda a versão do Mago
                // (vida + mana). Nos outros, roda a versão padrão do Personagem.
                heroi.descansar();
            }
        }

        System.out.println("\n=====================================");
        System.out.println(" VITÓRIA! " + heroi.getNome() + " salvou o reino!");
        System.out.println("=====================================");
    }

    // -------------------------------------------------------------------------
    //  Uma batalha completa entre o herói e UM inimigo. Retorna true se o herói venceu.
    //
    //  [POLIMORFISMO] Os parâmetros são do tipo Personagem. Este método não sabe
    //  (e não precisa saber) se o herói é Guerreiro ou Mago, nem se o inimigo é
    //  Goblin ou Dragão. Ele só chama atacar() e cada objeto reage do seu jeito.
    //  Se amanhã criarmos um "Ladino", este método continua funcionando sem
    //  nenhuma alteração.
    // -------------------------------------------------------------------------
    private static boolean batalhar(Personagem heroi, Personagem inimigo, Scanner teclado) {
        System.out.println("\n-------------------------------------");
        System.out.println(" Um " + inimigo.getNome() + " apareceu!");
        System.out.println("-------------------------------------");

        while (heroi.estaVivo() && inimigo.estaVivo()) {
            // [POLIMORFISMO] getStatus() mostra mana para o Mago, poções para o
            // Arqueiro e só a vida para os outros: cada classe tem a sua versão.
            System.out.println("\n" + heroi.getStatus());
            System.out.println(inimigo.getStatus());

            // [ABSTRAÇÃO] instanceof pergunta: "este objeto assinou o contrato
            // Curavel?". Só nesse caso mostramos a opção de curar.
            boolean podeCurar = heroi instanceof Curavel;

            System.out.println("\nO que fazer?");
            System.out.println("  1 - Atacar");
            if (podeCurar) {
                System.out.println("  2 - Curar");
            }
            int opcao = lerOpcao(teclado, 1, podeCurar ? 2 : 1);

            System.out.println();
            if (opcao == 1) {
                // [POLIMORFISMO] Mesma chamada, resultado diferente conforme o herói:
                // Guerreiro = golpe/crítico, Mago = bola de fogo, Arqueiro = 2 flechas.
                heroi.atacar(inimigo);
            } else {
                // Tratamos o herói pelo CONTRATO (Curavel). Não importa se é Mago
                // ou Arqueiro: ambos sabem curar(), cada um do seu jeito.
                Curavel curador = (Curavel) heroi;
                curador.curar();
            }

            // Turno do inimigo (só se ele sobreviveu).
            if (inimigo.estaVivo()) {
                // [POLIMORFISMO] Para um Monstro roda o ataque simples; para o
                // Dragão roda a versão dele (com sopro de fogo).
                inimigo.atacar(heroi);
            }
        }

        return heroi.estaVivo();
    }

    // Menu inicial: pede o nome e a classe e devolve o herói criado.
    private static Personagem escolherHeroi(Scanner teclado) {
        System.out.print("\nDigite o nome do seu herói: ");
        String nome = "Herói";
        if (teclado.hasNextLine()) {
            String digitado = teclado.nextLine().trim();
            if (!digitado.isEmpty()) {
                nome = digitado;
            }
        }

        System.out.println("\nEscolha sua classe:");
        System.out.println("  1 - Guerreiro (muita vida, golpes críticos)");
        System.out.println("  2 - Mago      (bola de fogo e cura, gasta mana)");
        System.out.println("  3 - Arqueiro  (duas flechas por turno, poções)");
        int classe = lerOpcao(teclado, 1, 3);

        // [POLIMORFISMO] O resultado é guardado numa variável do tipo PAI
        // (Personagem), mas o objeto criado é de uma classe FILHA.
        Personagem heroi;
        switch (classe) {
            case 1:
                heroi = new Guerreiro(nome);
                break;
            case 2:
                heroi = new Mago(nome);
                break;
            default:
                heroi = new Arqueiro(nome);
                break;
        }
        return heroi;
    }

    // Lê um número do teclado entre "minimo" e "maximo", repetindo até ser válido.
    private static int lerOpcao(Scanner teclado, int minimo, int maximo) {
        while (true) {
            System.out.print("> ");
            if (!teclado.hasNextLine()) {
                System.out.println("\nEntrada encerrada. Saindo do jogo.");
                System.exit(0);
            }
            String linha = teclado.nextLine().trim();
            try {
                int valor = Integer.parseInt(linha);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException e) {
                // Não era um número: cai na mensagem de erro logo abaixo.
            }
            System.out.println("Opção inválida. Digite um número de " + minimo + " a " + maximo + ".");
        }
    }
}
