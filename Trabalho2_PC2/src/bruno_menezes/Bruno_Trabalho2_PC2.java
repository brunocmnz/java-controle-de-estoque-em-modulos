/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bruno_menezes;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author bruno
 */
public class Bruno_Trabalho2_PC2 {

    static Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8.name());

    static Random rdm = new Random();

    //Criação do sistema para operar com os dados
    static Sistema sistema = Sistema.getInstance(20);

    static String recebeString(){
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        return scanner.nextLine();
    }

    static int recebeInt(){
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        return scanner.nextInt();
    }

    static Float recebeFloat(){
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        return scanner.nextFloat();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        //Código da interface de interação do programa
        int operador = 0;

        linhaImprimeLayout("_");
        System.out.printf("%63s\n", "PROGRAMA INICIADO COM SUCESSO");
        linhaImprimeLayout("-");
        System.out.printf("%66s\n", "LISTA DE PRODUTOS JA PRE CADASTRADOS");
        imprimeProdutosQueNaoExcluido(sistema.getProdutos());
        do {
            //Menu Principal da iterface para o usuario
            operador = menuPrincipal(operador);
            if (operador == 1) {
                //Mòdulo atendente iniciando aqui
                menuAtendente(operador);
            } else if (operador == 2) {
                //Menu de administrador iniciando aqui
                operador = menuAdministrador(operador);
            } else if (operador != 3) {
                opcaoInvalida();
            }
        } while (operador != 3);
        imprimeLinha("=", "-");
        linhaImprimeLayout("=");
        System.out.printf("%65s\n", "PROGRAMA FINALIZADO COM SUCESSO");
        linhaImprimeLayout("=");
    }

    static boolean imprimeProduto(Produto p) {
        if (p != null && p.getSituacao()) {
            linhaImprimeLayout("=");
            System.out.println("");
            linhaImprimeLayout("=");
            System.out.printf("%8s \t%14s \t%14s \t%15s \t%18s\n",
                      "Codigo", "Nome", "Marca", "Preco", "QuantEstoque");
            linhaImprimeLayout("-");
            System.out.printf("%8s \t%14s \t%14s \t%-4s%-14.2f \t%10s\n",
                      p.getCodigo(), p.getNome(), p.getMarca(), "     R$   ",
                      p.getPreco(), p.getQuantEstoque());
            linhaImprimeLayout("=");
            System.out.println("");
            return true;
        }
        return false;
    }

    static void imprimeProdutosQueNaoExcluido(Produto[] vetProdutos) {
        imprimeProdutos(vetProdutos, false);
    }

    static void imprimeProdutos(Produto[] vetProdutos, boolean excluidos) {
        linhaImprimeLayout("=");
        System.out.printf("%8s \t%18s \t%15s \t%14s \t%18s\n",
                  "Codigo", "Nome", "Marca", "Preco", "QuantEstoque");
        linhaImprimeLayout("-");
        int imprimiu = 0;
        for (int i = 0; i < vetProdutos.length; i++) {
            boolean teste = vetProdutos[i].getSituacao() || excluidos;
            if (vetProdutos[i] != null && teste) {
                System.out.printf("%8s \t%18s \t%15s \t%-5s%8.2f \t%18s\n",
                          ("0000" + vetProdutos[i].getCodigo()), vetProdutos[i].getNome(),
                          vetProdutos[i].getMarca(), "  R$", vetProdutos[i].getPreco(),
                          vetProdutos[i].getQuantEstoque());
                imprimiu++;
            }
        }
        if (imprimiu == 0) {
            System.out.printf("%65s\n", "NAO HA PRODUTOS CADASTRADOS NO MOMENTO");
        }
    }

    static void imprimeCarrinho(Carrinho carrinho, String nomeCliente) {
        Item[] itensCarrinho = null;
        if (carrinho != null) {
            itensCarrinho = carrinho.getItens();
        }
        linhaImprimeLayout("=");
        System.out.printf("%68s", "CARRINHO COM OS ITENS JA ADICIONADOS\n");
        linhaImprimeLayout("=");
        System.out.println("Nome do cliente: \t" + nomeCliente);
        linhaImprimeLayout("-");
        System.out.printf("%9s %11s %11s %15s %13s %25s\n",
                  "Codigo", "Nome", "Marca", "Preco", "Quantidade",
                  "Quantidade de Estoque");
        linhaImprimeLayout("-");
        boolean naoHaProdCadastrado = true;
        if (itensCarrinho != null) {
            for (int i = 0; i < carrinho.getNumProds(); i++) {
                Produto prod = itensCarrinho[i].getProduto();
                System.out.printf("%9s %11s %11s     %-3s%7.2f %13s %25s\n",
                          ("0000" + prod.getCodigo()), prod.getNome(), prod.getMarca(), "  R$",
                          itensCarrinho[i].getPreco(), itensCarrinho[i].getQuantidade(),
                          prod.getQuantEstoque());
                naoHaProdCadastrado = false;
            }
        }
        if (naoHaProdCadastrado) {
            System.out.printf("%65s\n", "NAO HA PRODUTOS CADASTRADOS NO MOMENTO");
        }
    }

    static void linhaImprimeLayout(String dig) {
        for (int i = 0; i < 100; i++) {
            System.out.print(dig);
        }
        System.out.println("");
    }

    static void imprimeLinha(String dig, String dig2) {
        for (int i = 0; i < 45; i++) {
            if (i < 23) {
                System.out.print(dig + dig2);
            } else {
                System.out.print(dig2 + dig);
            }
        }
        System.out.println("");
    }

    static int formaDeBuscarItem() {
        System.out.println("Qual forma deseja buscar pelo item?");
        linhaImprimeLayout("-");
        System.out.println("1 - Por codigo  /  2 - Por nome");
        linhaImprimeLayout("-");
        System.out.print("Opcao desejada: ");
        int atendente = sc.nextInt();
        return atendente;
    }

    static Produto buscaProduto(int atendente, String texto, Sistema sistema) {
        Produto prod = null;
        if (atendente == 1) {
            System.out.print("Insira o codigo do produto a ser " + texto + ": ");
            int codigo = sc.nextInt();
            prod = sistema.buscarPorCodigo(codigo);
        } else if (atendente == 2) {
            System.out.print("Insira o nome do produto a ser " + texto + ": ");
            String nome = recebeString();
            prod = sistema.buscarPorNome(nome);
        }
        return prod;
    }

    static void opcaoInvalida() {
        System.out.println("");
        linhaImprimeLayout("=");
        System.out.printf("%55s\n", "OPCAO INVALIDA");
        linhaImprimeLayout("=");
        System.out.println("");
    }

    //Funcoes para os menus de interface para o usuario
    //MENU do modulo atendente
    static int menuAtendente(int operador) {
        String nomeCliente = null;
        Carrinho carrinho = null;
        System.out.printf("%69s\n", "MODULO ATENDENTE INICIALIZADO COM SUCESSO");
        while (operador != 3 && operador != 2) {
            linhaImprimeLayout("=");
            System.out.printf("%58s\n", "MENU MODULO ATENDENTE");
            linhaImprimeLayout("=");
            System.out.println("Insira um numero para realizar uma acao");
            linhaImprimeLayout("=");
            System.out.println("1 - Abrir novo carrinho  /  "
                      + "2 <-> voltar ao menu principal  /  "
                      + "3 (x) Encerrar programa");
            linhaImprimeLayout("-");
            System.out.print("Insira a opcao desejada: ");
            operador = sc.nextInt();
            if (operador == 1) {
                if (nomeCliente == null) {
                    linhaImprimeLayout("=");
                    System.out.print("Insira o nome do cliente: ");
                    sc = new Scanner(System.in, StandardCharsets.UTF_8.name());
                    nomeCliente = recebeString();
                }
                if (carrinho == null) {
                    carrinho = Carrinho.getInstace();
                } else {
                    imprimeCarrinho(carrinho, nomeCliente);
                }
                operador = menuCarrinho(operador, nomeCliente);
            }
        }
        return operador;
    }

    static boolean insereItemAoCarrinho(Produto prod, double preco,
              double quantidade, Carrinho carrinho, String nomeCliente) {
        boolean adicionar = false;
        if (quantidade > 0) {
            Item novoItem = Item.getInstance(prod, preco, quantidade);
            adicionar = carrinho.adicionarItem(novoItem);
            linhaImprimeLayout("=");
            imprimeCarrinho(carrinho, nomeCliente);
            linhaImprimeLayout("=");
            if (adicionar) {
                int len = prod.getNome().length();
                int espace = (100 - (57 + len)) / 2;
                for (int i = 0; i < espace; i++) {
                    System.out.print(" ");
                }
                System.out.println("O produto de nome " + prod.getNome()
                          + " foi adicionado ao carrinho com sucesso");
            } else {
                if (novoItem == null || novoItem.getPreco() <= 0) {
                    System.out.printf("%93s\n", "NAO FOI POSSIVEL ADICIONAR O ITEM AO "
                              + "CARRINHO, POIS SEU PRECO E MENOR OU IGUAL A ZERO");
                } else {
                    System.out.printf("%93s\n", "NAO FOI POSSIVEL ADICIONAR O ITEM AO "
                              + "CARRINHO, POIS IRIA ALEM DA QUANTIDADE DE ESTOQUE");
                }
            }
            linhaImprimeLayout("=");
        } else {
            linhaImprimeLayout("=");
            System.out.println(" NAO FOI POSSIVEL ADICINAR O ITEM AO CARRINHO, "
                      + "POIS A QUANTIDADE INSERIDA FOI MENOR OU IGUAL A ZERO");
        }
        return adicionar;
    }

    static void listarVenda(Venda venda) {
        linhaImprimeLayout("*");
        linhaImprimeLayout("=");
        System.out.printf("%58s", "VENDA CONCLUIDA COM SUCESSO\n");
        linhaImprimeLayout("=");
        //Parte que imprime as informacoes dos produtos vendidos
        System.out.printf("%17s %14s %11s %15s %18s\n",
                  "Codigo do Produto", "Nome", "Marca", "Preco", "Quantidade");
        linhaImprimeLayout("-");
        Item[] itenVendidos = venda.getItensVendidos();
        for (int i = 0; i < venda.getNumItens(); i++) {
            Item item = itenVendidos[i];
            Produto prod = item.getProduto();
            System.out.printf("%17s %14s %11s     %-3s%7.2f %18s\n",
                      ("0000" + prod.getCodigo()), prod.getNome(), prod.getMarca(), "  R$",
                      item.getPreco(), item.getQuantidade());
        }
        linhaImprimeLayout("_");
        //Parte que imprime as informacoes da venda
        System.out.printf("%17s %14s %27s",
                  "Codigo da venda", "Valor Total", "Data e horario da venda");
        int tamanhoNomeCliente = venda.getNomeCliente().length();
        if (tamanhoNomeCliente >= 19) {
            for (int i = 0; i < tamanhoNomeCliente - 10; i++) {
                System.out.print(" ");
            }
            System.out.println("Nome do cliente");
        } else {
            System.out.printf("%19s\n", "Nome do cliente");
        }
        linhaImprimeLayout("-");
        System.out.printf("%17s %7s %5.2f %27s",
                  ("0000" + venda.getCodigo()), "  R$", venda.getValorTotal(), venda.getDataEHoraString());
        if (tamanhoNomeCliente >= 19) {
            for (int i = 0; i < 5; i++) {
                System.out.print(" ");
            }
        } else {
            for (int i = 0; i < 19 - tamanhoNomeCliente; i++) {
                System.out.print(" ");
            }
        }
        System.out.println(venda.getNomeCliente());
        linhaImprimeLayout("=");
        linhaImprimeLayout("*");
        System.out.println("");
    }

    //Menu carrinho (submenu do menu atendente)
    static int menuCarrinho(int operador, String nomeCliente) {
        Produto prod;
        String nomeProd = null;
        int codigoProd = 0;
        Carrinho carrinho = Carrinho.getInstace();
        int itensAdicionados = 0;;
        while (operador != 2) {
            //Menu de carrinho iniciando aqui
            linhaImprimeLayout("=");
            System.out.printf("%60s\n", "MENU CARRINHO ABERTO");
            linhaImprimeLayout("*");
            System.out.println("   1 - Adicionar item "
                      + "   /   2 <-> Voltar ao menu anterior  /  "
                      + "3 - Cancelar venda / 4 - Remover item ");
            System.out.println(" 5 - Alterar dados de um item"
                      + "     /    6 ($) Fechar venda    /    "
                      + "7 - Listar carrinho");
            linhaImprimeLayout("-");
            System.out.print("Insira a opcao desejada: ");
            operador = sc.nextInt();
            linhaImprimeLayout("=");
//opcao 1
            if (operador == 1) {
                operador = formaDeBuscarItem();
                if (operador == 1 || operador == 2) {
                    linhaImprimeLayout("=");
                    imprimeProdutosQueNaoExcluido(sistema.getProdutos());
                    linhaImprimeLayout("=");
                    prod = buscaProduto(operador, "adicionado", sistema);
                    if (prod == null) {
                        linhaImprimeLayout("=");
                        System.out.printf("%75s\n", "ESSE PRODUTO FOI EXCLUIDO E"
                                  + " NAO ESTA MAIS DISPONIVEL");
                        linhaImprimeLayout("=");
                        System.out.println("");
                        break;
                    } else {
                        double preco = 0;
                        boolean adicionar = false;
                        if (prod != null) {
                            preco = prod.getPreco();
                            System.out.print("Insira a quantidade do produtos"
                                      + " a inserir no carrinho: ");
                            double quantidade = sc.nextDouble();
                            if (quantidade <= 0) {
                                linhaImprimeLayout("=");
                                System.out.printf("%62s\n", "QUANTIDADE INSERIDA INVALIDA");
                                linhaImprimeLayout("=");
                            } else {
                                adicionar = insereItemAoCarrinho(prod, preco,
                                          quantidade, carrinho, nomeCliente);
                                if (adicionar) {
                                    itensAdicionados++;
                                }
                            }
                        } else {
                            linhaImprimeLayout("-");
                            if (operador == 1) {
                                System.out.println("Nao foi encontrado"
                                          + " nenhum produto com o codigo " + codigoProd);
                            } else if (operador == 2) {
                                System.out.println("Nao foi encontrado"
                                          + " nenhum produto com o nome " + nomeProd);
                            }
                        }
                        if (operador == 2) {
                            operador = 0;
                        }
                    }
                } else {
                    linhaImprimeLayout("=");
                    opcaoInvalida();
                }
//Opcao 2
            } else if (operador == 2) {
                if (carrinho != null) {
                    do {
                        linhaImprimeLayout("=");
                        System.out.println("Ao voltar ao menu anterior o carrinho "
                                  + "que esta aberto sera cancecelado");
                        System.out.println("Deseja mesmo voltar?    1 - Sim  /  2 - Nao");
                        linhaImprimeLayout("-");
                        System.out.print("Opcao desejada: ");
                        operador = sc.nextInt();
                        linhaImprimeLayout("=");
                        if (operador == 1) {
                            operador = 2;
                        } else if (operador == 2) {
                            operador = 0;
                            break;
                        } else {
                            opcaoInvalida();
                        }
                    } while (operador != 1 && operador != 2);
                }
//Opcao 3
            } else if (operador == 3) {
                carrinho = null;
                nomeCliente = null;
                linhaImprimeLayout("=");
                System.out.printf("%63s\n", "Venda cancelada com sucesso");
                linhaImprimeLayout("=");
                break;
//Opcao 4
            } else if (operador == 4) {
                if (itensAdicionados > 0) {
                    operador = formaDeBuscarItem();
                    Item itemParaRemover = null;
                    imprimeCarrinho(carrinho, nomeCliente);
                    linhaImprimeLayout("=");
                    if (operador == 1) {
                        System.out.print("Insira o codigo do produto"
                                  + " a ser removido: ");
                        codigoProd = sc.nextInt();
                        itemParaRemover = carrinho.buscarItem(codigoProd);
                    } else if (operador == 2) {
                        System.out.print("Insira o nome do produto"
                                  + "a ser removido: ");
                        nomeProd = recebeString();
                        itemParaRemover = carrinho.buscarItem(nomeProd);
                        operador = 0;
                    }
                    boolean remover = carrinho.removerItem(itemParaRemover);
                    imprimeCarrinho(carrinho, nomeCliente);
                    if (remover) {
                        linhaImprimeLayout("=");
                        System.out.printf("%65s\n", "Item removido do carrinho com sucesso");
                        linhaImprimeLayout("=");
                    } else {
                        linhaImprimeLayout("=");
                        System.out.printf("%70s", "Nao foi possivel fazer a remocao");
                        linhaImprimeLayout("=");
                    }
                } else {
                    linhaImprimeLayout("=");
                    System.out.printf("%84s\n", "NAO E POSSIVEL EXCLUIR ITENS, POIS "
                              + "AINDA NAO FOI ADICIONADO NENHUM ITEM");
                    linhaImprimeLayout("=");
                }
//Opcao 5
            } else if (operador == 5) {
                if (itensAdicionados > 0) {
                    Item alterar = null;
                    do {
                        operador = formaDeBuscarItem();
                        if (operador == 1 || operador == 2) {
                            linhaImprimeLayout("=");
                            imprimeCarrinho(carrinho, nomeCliente);
                            linhaImprimeLayout("=");
                            linhaImprimeLayout("-");
                        }
                        if (operador == 1) {
                            System.out.print("Insira o codigo do "
                                      + "item a ser alterado: ");
                            codigoProd = sc.nextInt();
                            alterar = carrinho.buscarItem(codigoProd);
                        } else if (operador == 2) {
                            System.out.print("Insira o nome do "
                                      + "item a ser alterado: ");
                            nomeProd = recebeString();
                            alterar = carrinho.buscarItem(nomeProd);
                        } else {
                            linhaImprimeLayout("=");
                            opcaoInvalida();
                            linhaImprimeLayout("=");
                        }
                    } while (operador != 1 && operador != 2);
                    linhaImprimeLayout("=");
                    System.out.println("O que deseja alterar\n"
                              + "1 - Preco  /  2 - Quantidade  /  "
                              + "3 - Produto");
                    linhaImprimeLayout("-");
                    System.out.print("Insira a opcao desejada: ");
                    operador = sc.nextInt();
                    boolean altera = false;
                    int decisao = 0;
                    if (operador == 1) {
                        System.out.print("Insira o novo preco do item: ");
                        altera = alterar.setPreco(sc.nextDouble());
                    } else if (operador == 2) {
                        System.out.print("Insira a nova quantidade do produto: ");
                        altera = alterar.setQuantidade(sc.nextInt());
                    } else if (operador == 3) {
                        do {
                            decisao = formaDeBuscarItem();
                            if (decisao == 1 || decisao == 2) {
                                linhaImprimeLayout("=");
                                imprimeProdutosQueNaoExcluido(sistema.getProdutos());
                                linhaImprimeLayout("=");
                                prod = null;
                                if (decisao == 1) {
                                    linhaImprimeLayout("=");
                                    System.out.print("Insira o codigo do novo produto: ");
                                    codigoProd = sc.nextInt();
                                    prod = sistema.buscarPorCodigo(codigoProd);
                                } else if (decisao == 2) {
                                    System.out.print("Insira o nome do novo produto: ");
                                    nomeProd = recebeString();
                                    prod = sistema.buscarPorNome(nomeProd);
                                }
                                if (prod == null) {
                                    linhaImprimeLayout("=");
                                    System.out.printf("%60s\n", "PRODUTO NAO ENCONTRADO");
                                    linhaImprimeLayout("=");
                                }
                                altera = alterar.setProduto(prod);
                                if (altera) {
                                    linhaImprimeLayout("=");
                                    System.out.printf("%60s\n", "ITEM ALTERADO COM SUCESSO");
                                } else {
                                    linhaImprimeLayout("=");
                                    System.out.printf("%68s\n", "NAO FOI POSSIVEL FAZER A ALTERACAO");
                                    linhaImprimeLayout("=");
                                    System.out.println("");
                                }
                            } else {
                                linhaImprimeLayout("=");
                                opcaoInvalida();
                                linhaImprimeLayout("=");
                            }
                        } while (decisao != 1 && decisao != 2);
                    } else {
                        opcaoInvalida();
                    }
                    linhaImprimeLayout("=");
                    if (altera) {
                        System.out.println("");
                        linhaImprimeLayout("=");
                        System.out.printf("%68s\n", ("ITEM DE CODIGO "
                                  + alterar.getProduto().getCodigo()
                                  + " ALTERADO COM SUCESSO"));
                        linhaImprimeLayout("=");
                        imprimeCarrinho(carrinho, nomeCliente);
                        linhaImprimeLayout("=");
                        System.out.println("");
                    } else {
                        System.out.printf("", "NAO FOI POSSIVEL FAZER A ALTERACAO");
                    }

                } else {
                    linhaImprimeLayout("=");
                    System.out.printf("%84s\n", "NAO E POSSIVEL ALTERAR ITENS, POIS "
                              + "AINDA NAO FOI ADICIONADO NENHUM ITEM");
                    linhaImprimeLayout("=");
                }
                operador = 0;
//Opcao 6
            } else if (operador == 6) {
                Date data = new Date();
                Item[] itens = carrinho.getItens();
                int numVendas = sistema.getNumVendasFeitas() + 1;
                Venda novaVenda = Venda.getInstance(itens, numVendas, data, nomeCliente);
                if (novaVenda != null) {
                    sistema.inserirVenda(novaVenda);
                    listarVenda(novaVenda);
                    carrinho = null;
                    operador = 2;
                } else {
                    linhaImprimeLayout("=");
                    if (carrinho.getNumProds() <= 0) {
                        System.out.printf("%78s\n", "Nao foi possivel concluir a venda"
                                  + " pois o carrinho esta vazio");
                    } else {
                        System.out.printf("%65s\n", "Nao foi possivel conclir a venda");
                    }
                    linhaImprimeLayout("=");
                }
//Opcao 7
            } else if (operador == 7) {
                if (itensAdicionados > 0) {
                    imprimeCarrinho(carrinho, nomeCliente);
                } else {
                    linhaImprimeLayout("=");
                    System.out.printf("%84s\n", "NAO E POSSIVEL LISTAR, POIS "
                              + "AINDA NAO HA ITENS ADICIONADOS");
                    linhaImprimeLayout("=");
                }
            } else {
                opcaoInvalida();
            }
        }
        return operador;
    }

    static void imprimeNomeECodigoProdutos() {
        Produto[] produtos = sistema.getProdutos();
        int numProds = sistema.getNumProdutos();
        linhaImprimeLayout("=");
        System.out.printf("%60s\n", "NOME E CODIGO DOS PRODUTOS");
        linhaImprimeLayout("-");
        System.out.printf("%-30s %-30s\n", "CODIGO", "NOME");
        linhaImprimeLayout("-");
        for (int i = 0; i < numProds; i++) {
            Produto prod = produtos[i];
            if (prod != null) {
                System.out.printf("%-30s %-30s\n", prod.getCodigo(), prod.getNome());
                if (i < numProds - 1) {
                    linhaImprimeLayout(".");
                }
            }
        }
        linhaImprimeLayout("=");
    }

    static void imprimeCodigoEValorTotalVendas() {
        Venda[] vendas = sistema.getVendas();
        int numVendas = Sistema.getNumVendasFeitas();
        linhaImprimeLayout("=");
        System.out.printf("%60s\n", "CODIGO E VALOR TOTAL DAS VENDAS");
        linhaImprimeLayout("-");
        System.out.printf("%-30s %-30s\n", "CODIGO", "VALOR TOTAL");
        linhaImprimeLayout("-");
        for (int i = 0; i < numVendas; i++) {
            Venda venda = vendas[i];
            if (venda != null) {
                System.out.printf("%-30s %-30.2f\n",
                          venda.getCodigo(), venda.getValorTotal());
                if (i < numVendas - 1) {
                    linhaImprimeLayout(".");
                }
            }
        }
        linhaImprimeLayout("=");
    }

    static void listarDeProdutos(int decisao) {
        Produto prod;
        System.out.println("");
        linhaImprimeLayout("=");
        System.out.println("Insira um número para realizar uma acao");
        linhaImprimeLayout("=");
        System.out.println("1 - Detalhar um produto pelo nome ou codigo");
        System.out.println("2 - Listar apenas produtos cadastrados que nao "
                  + "tenham sido 'excluidos'");
        System.out.println("3 - Listar TODOS os produtos que estao cadastrados, "
                  + "mesmo que ja tenham sido 'excluidos'");
        linhaImprimeLayout("-");
        System.out.print("Insira a opcao desejada: ");
        decisao = sc.nextInt();
        linhaImprimeLayout("-");
        if (decisao == 1) {
//Opcao 1
            System.out.println("Como deseja buscar pelo produto?     "
                      + "1 - Por codigo  /  2 - Por nome");
            linhaImprimeLayout("-");
            System.out.print("Insira a opcao escolhida: ");
            decisao = sc.nextInt();
            if (decisao == 1 || decisao == 2) {
                imprimeNomeECodigoProdutos();
            } else {
                linhaImprimeLayout("=");
                opcaoInvalida();
            }
            if (decisao == 1) {
                System.out.print("Insira o codigo do produto para "
                          + "ver seus dados: ");
                int codigoProd = sc.nextInt();
                prod = sistema.buscarPorCodigo(codigoProd);
                if (prod != null) {
                    imprimeProduto(prod);
                } else {
                    linhaImprimeLayout("=");
                    System.out.printf("%82s\n", "Nao foi possivel localizar "
                              + "o produto pois o codigo inserido não"
                              + " esta cadastrado");
                }
            } else if (decisao == 2) {
                System.out.print("Insira o nome do produto desejado: ");
                String nomeProd = recebeString();
                prod = sistema.buscarPorNome(nomeProd);
                if (prod != null) {
                    imprimeProduto(prod);
                } else {
                    linhaImprimeLayout("=");
                    System.out.println("Nao foi possivel localizar "
                              + "o produto, pois seu nome pode ter "
                              + "sido inserido incorretamente");
                }
            }
        } else if (decisao == 2 || decisao == 3) {
//Opcao 2 e Opcao 3
            int opcao = decisao;
            linhaImprimeLayout("-");
            System.out.print("1 - Listar pela ordem de cadastro"
                      + "              /      ");
            System.out.println("2 - Listar por ordem alfabetica");
            linhaImprimeLayout("-");
            System.out.print("Insira a forma desejada: ");
            decisao = sc.nextInt();
            Produto[] produtos = sistema.getProdutos();
            boolean excluidos = false;
            if (opcao == 3) {
                excluidos = true;
            }
            if (decisao == 1) {
                linhaImprimeLayout("=");
                System.out.println("");
                linhaImprimeLayout("=");
                System.out.printf("%68s\n", "LISTA DE PRODUTOS EM ORDEM DE CADASTRO");
                imprimeProdutos(produtos, excluidos);
                linhaImprimeLayout("=");
                System.out.println("");
            } else if (decisao == 2) {
                Produto[] vetPorNome = sistema.listarPorNome(produtos);
                linhaImprimeLayout("=");
                System.out.println("");
                linhaImprimeLayout("=");
                System.out.printf("%65s\n", "LISTA DE PRODUTOS EM ORDEM ALFABETICA");
                imprimeProdutos(vetPorNome, excluidos);
                linhaImprimeLayout("=");
                System.out.println("");
            } else {
                opcaoInvalida();
            }
        } else {
            opcaoInvalida();
        }
    }

    static void listarTodasVendas(Venda[] vendas) {
        linhaImprimeLayout("=");
        System.out.printf("%20s %34s  %26s\n",
                  "Codigo", "Data e Horario", "Valor da venda");
        linhaImprimeLayout("-");
        for (int i = 0; i < vendas.length; i++) {
            if (vendas[i] != null) {
                Venda venda = vendas[i];
                System.out.printf("%20s %34s %18s  %7.2f\n",
                          ("000" + venda.getCodigo()), venda.getDataEHoraString(),
                          "R$  ", venda.getValorTotal());
                linhaImprimeLayout(".");
            }
        }
    }

    static void listarVendasDeUmDia(String dataImprimirVenda) {
        Venda[] vendas = sistema.getVendas();
        int numVendas = Sistema.getNumVendasFeitas();
        System.out.printf("%10s %20s %15s\n", "CODIGO", "DATA E HORARIO",
                  "VALOR TOTAL");
        linhaImprimeLayout("-");
        Venda[] vendasDia = new Venda[sistema.getNumVendasFeitas()];
        int j = 0;
        for (int i = 0; i < numVendas; i++) {
            String data = vendas[i].getDataString();
            if (data.equals(dataImprimirVenda)) {
                vendasDia[j] = vendas[i];
                j++;
            }
        }
        if (j == 0) {
            System.out.printf("%75s\n", ("NAO HA NENHUMA VENDA CADASTRADA PARA O"
                      + " DIA " + dataImprimirVenda));
        } else {
            System.out.println("");
            linhaImprimeLayout("=");
            System.out.printf("%68s\n", "LISTAGEM DE TODAS AS VENDAS DO DIA "
                      + dataImprimirVenda);
            listarTodasVendas(vendasDia);
        }
        linhaImprimeLayout("=");
    }

    static void listarDatasDeVendas(Venda[] vendas) {
        linhaImprimeLayout("=");
        System.out.printf("%63s\n", "DATAS DE VENDAS CADASTRADAS");
        linhaImprimeLayout("=");
        System.out.printf("%35s %40s\n", "Codigo da venda", "Data da venda");
        linhaImprimeLayout("-");
        for (int i = 0; i < vendas.length; i++) {
            if (vendas[i] != null) {
                Venda venda = vendas[i];
                System.out.printf("%35s %40s\n", venda.getCodigo(), venda.getDataString());
                linhaImprimeLayout(".");
            }
        }
        linhaImprimeLayout("=");
    }

    static int listarDeVendas(int operador) {
        System.out.println("");
        linhaImprimeLayout("=");
        System.out.println("Insira um número para realizar uma acao");
        linhaImprimeLayout("=");
        System.out.println("1 - Listar todas as vendas realizadas");
        System.out.println("2 - Listar todas as vendas de um determiado dia");
        System.out.println("3 - Listar uma determinada venda");
        linhaImprimeLayout("-");
        System.out.print("Opcao desejada: ");
        operador = sc.nextInt();
        linhaImprimeLayout("-");
        switch (operador) {
            case 1:
                System.out.println("");
                linhaImprimeLayout("=");
                System.out.printf("%68s\n", "LISTAGEM DE TODAS AS VENDAS "
                          + "JA REALIZADAS");
                listarTodasVendas(sistema.getVendas());
                break;
            case 2:
                listarDatasDeVendas(sistema.getVendas());
                linhaImprimeLayout("=");
                System.out.print("Insira a data desejada no formato (xx/xx/xxxx): ");
                sc = new Scanner(System.in);
                String dataRecebida = recebeString();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                boolean listar = true;
                //String data = null;
                Date data;
                try {
                    data = sdf.parse(dataRecebida);
                } catch (Exception e) {
                    listar = false;
                }
                if (listar && dataRecebida.length() == 10) {
                    listarVendasDeUmDia(dataRecebida);
                } else {
                    linhaImprimeLayout("=");
                    System.out.printf("%58s\n", "FORMATO DE DATA INVALIDO");
                    linhaImprimeLayout("=");
                    System.out.println("");
                }
                break;
            case 3:
                linhaImprimeLayout("=");
                imprimeCodigoEValorTotalVendas();
                linhaImprimeLayout("=");
                System.out.print("Insira o codigo da venda desejada: ");
                int codigo = sc.nextInt();
                Venda venda = sistema.getVenda(codigo);
                listarVenda(venda);
                break;
            default:
                opcaoInvalida();
        }
        return operador;
    }

    //MENU do modulo administrador
    static int menuAdministrador(int operador) {
        int decisao = 0;
        System.out.printf("%69s\n", "MODULO ADMINISTRADOR INICIALIZADO COM SUCESSO");
        while (decisao != 5) {
            linhaImprimeLayout("=");
            System.out.println("Insira um número para realizar uma acao");
            linhaImprimeLayout("=");
            System.out.println(" 1  -  Inserir Produtos /  2 -  Alterar dados de um produto"
                      + "   / 3  -  Excluir Produtos");
            System.out.println("4  -  Gerar listagens  /  5 <=> Voltar ao menu principal"
                      + "     / 6 (x) Encerrar o programa");
            linhaImprimeLayout("-");
            System.out.print("Insira a opcao desejada: ");
            decisao = sc.nextInt();
            int codigoProd;
            Produto prod;
            String nomeProd;
//Opcao 1
            if (decisao == 1) {
                linhaImprimeLayout("=");
                System.out.println("Insira abaixo as informacoes do novo produto:");
                linhaImprimeLayout("-");
                System.out.print("Codigo: ");
                codigoProd = sc.nextInt();
                boolean validaCodigo = sistema.verificaValidadeCodigo(codigoProd);
                if (validaCodigo) {
                    System.out.print("Nome: ");
                    nomeProd = recebeString();
                    System.out.print("Marca: ");
                    String marca = recebeString();
                    System.out.print("Preco: ");
                    double preco = sc.nextFloat();
                    System.out.print("Quantidade de estoque: ");
                    int quantEstoque = sc.nextInt();
                    prod = Produto.getInstance(codigoProd, nomeProd, marca, preco, quantEstoque);
                    boolean insert = sistema.cadastrarProduto(prod);
                    if (insert) {
                        imprimeProdutosQueNaoExcluido(sistema.getProdutos());
                        linhaImprimeLayout("=");
                        System.out.printf("%70s\n", ("O produto de nome " + prod.getNome()
                                  + " foi inserido com sucesso"));
                    } else {
                        if (preco <= 0 || quantEstoque <= 0) {
                            if (preco <= 0) {
                                linhaImprimeLayout("=");
                                System.out.printf("%62s\n", "PRECO INSERIDO INVALIDO");
                                linhaImprimeLayout("=");
                            }
                            if (quantEstoque <= 0) {
                                linhaImprimeLayout("=");
                                System.out.printf("%62s\n", "PRECO INSERIDO INVALIDO");
                                linhaImprimeLayout("=");
                            }
                        } else if (prod != null && prod.getNome() != null) {
                            linhaImprimeLayout("=");
                            System.out.printf("%92s\n", "NAO FOI POSSIVEL FAZER"
                                      + " O CADASTRO POIS JA HA UM PRODUTO CADASTRADO"
                                      + " COM ESSE NOME");
                            linhaImprimeLayout("=");
                        }
                    }
                } else {
                    linhaImprimeLayout("=");
                    System.out.printf("%70s\n", "JA EXISTE UM PRODUTO CADASTRADO COM ESSE CODIGO");
                    linhaImprimeLayout("=");
                }
//Opcao 2
            } else if (decisao == 2) {
                alterarProdutoAdmin();
//Opcao 3
            } else if (decisao == 3) {
                linhaImprimeLayout("=");
                imprimeProdutosQueNaoExcluido(sistema.getProdutos());
                linhaImprimeLayout("=");
                System.out.print("Insira o codigo do produto que deseja excluir: ");
                codigoProd = sc.nextInt();
                prod = sistema.buscarPorCodigo(codigoProd);
                boolean delet;
                if (prod == null) {
                    delet = false;
                } else {
                    delet = sistema.excluirProduto(prod.getCodigo());
                }
                if (delet == false) {
                    linhaImprimeLayout("-");
                    System.out.printf("%78s\n", "NAO FOI POSSIVEL DELETAR O "
                              + "PRODUTO PORQUE ELE NAO ESTA CADASTRADO");
                } else {
                    imprimeProdutosQueNaoExcluido(sistema.getProdutos());
                    linhaImprimeLayout("=");
                    System.out.printf("%75s\n", ("O produto " + prod.getNome() + " de codigo "
                              + prod.getCodigo() + " foi excluido com sucesso"));
                }
//Opcao 4
            } else if (decisao == 4) {
                linhaImprimeLayout("=");
                System.out.println("");
                linhaImprimeLayout("=");
                System.out.println("Insira um numero para realizar uma acao");
                linhaImprimeLayout("=");
                System.out.println("1 - Listagem de produtos");
                System.out.println("2 - Listagem de vendas");
                linhaImprimeLayout("-");
                System.out.print("Insira a opcao desejada: ");
                decisao = sc.nextInt();
                linhaImprimeLayout("=");
                if (decisao == 1) {
                    listarDeProdutos(decisao);
                } else if (decisao == 2) {
                    listarDeVendas(operador);
                } else {
                    opcaoInvalida();
                }
//Opcao 5
                //Quando a  opcao eh 5 o while eh interrompido

//Opcao 6
            } else if (decisao == 6) {
                decisao = 5;
                operador = 3;
//Opcao invalida
            } else if (decisao != 5) {
                linhaImprimeLayout("=");
                opcaoInvalida();
            }
        }
        return operador;
    }

    static void alterarProdutoAdmin() {
        float preco;
        String nomeProd, marca;
        int codigoProd, quantEstoque;
        int alterar = formaDeBuscarItem();
        linhaImprimeLayout("-");
        Produto prodAlterar = null;
        imprimeProdutosQueNaoExcluido(sistema.getProdutos());
        linhaImprimeLayout("=");
        if (alterar == 1) {
            System.out.print("Insira o codigo do produto que "
                      + "deseja alterar: ");
            codigoProd = sc.nextInt();
            prodAlterar = sistema.buscarPorCodigo(codigoProd);
        } else if (alterar == 2) {
            System.out.print("Insira o nome do produto que "
                      + "deseja alterar: ");
            nomeProd = recebeString();
            prodAlterar = sistema.buscarPorNome(nomeProd);
        } else {
            opcaoInvalida();
        }
        if (prodAlterar == null) {
            linhaImprimeLayout("-");
            if (alterar == 1) {
                System.out.printf("%70s\n", "Nao foi encontrado nenhum "
                          + "produto com esse codigo");
                linhaImprimeLayout("=");
                System.out.println("");
            } else if (alterar == 2) {
                System.out.printf("%70s\n", "Nao foi encontrado "
                          + "nenhum produto com esse nome");
            }
        } else {
            linhaImprimeLayout("-");
            System.out.println("Insira abaixo o dado que deseja alterar "
                      + "do produto");
            linhaImprimeLayout("-");
            System.out.println("1 - Alterar Codigo");
            System.out.println("2 - Alterar Nome");
            System.out.println("3 - Alterar Marca");
            System.out.println("4 - Alterar Preco");
            System.out.println("5 - Alterar Quantidade de estoque");
            linhaImprimeLayout("-");
            System.out.print("Insira a opcao desejada: ");
            int opcao = sc.nextInt();
            boolean alteracao = true;
            if (opcao == 1) {
                System.out.print("Insira o novo codigo do produto: ");
                codigoProd = sc.nextInt();
                Produto[] prods = sistema.getProdutos();
                alteracao = true;
                for (int i = 0; i < sistema.getNumProdutos(); i++) {
                    if (prods[i].getCodigo() == codigoProd) {
                        alteracao = false;
                        break;
                    }
                }
                if (alteracao) {
                    alteracao = prodAlterar.setCodigo(codigoProd);
                } else {
                    linhaImprimeLayout("=");
                    System.out.printf("%85s\n", "NAO FOI POSSIVEL FAZER A ALTERACAO, "
                              + "POIS JA HA UM PRODUTO COM O CODIGO " + codigoProd);
                    linhaImprimeLayout("=");
                }
            } else if (opcao == 2) {
                System.out.print("Insira o novo nome do produto: ");
                nomeProd = recebeString();
                alteracao = prodAlterar.setNome(nomeProd);
            } else if (opcao == 3) {
                System.out.print("Insira a nova marca do produto: ");
                marca = recebeString();
                alteracao = prodAlterar.setMarca(marca);
            } else if (opcao == 4) {
                System.out.print("Insira o novo preco do produto: ");
                preco = sc.nextFloat();
                alteracao = prodAlterar.setPreco(preco);
            } else if (opcao == 5) {
                System.out.print("Insira a nova quantidade de estoque: ");
                quantEstoque = sc.nextInt();
                alteracao = prodAlterar.setQuantEstoque(quantEstoque);
            } else {
                opcaoInvalida();
            }
            if (alteracao) {
                imprimeProdutosQueNaoExcluido(sistema.getProdutos());
                linhaImprimeLayout("=");
                if (opcao == 1) {
                    System.out.printf("%70s\n", ("O codigo do produto de nome "
                              + prodAlterar.getNome() + " foi alterado com sucesso"));
                } else if (opcao == 2) {
                    System.out.printf("%70s\n", ("O nome do produto de codigo "
                              + prodAlterar.getCodigo() + " foi alterado com sucesso"));
                } else if (opcao == 3) {
                    System.out.printf("%70s\n", ("A marca do produto de nome "
                              + prodAlterar.getNome() + " foi alterada com sucesso"));
                } else if (opcao == 4) {
                    System.out.printf("%70s\n", ("O preco do produto de nome "
                              + prodAlterar.getNome() + " foi alterado com sucesso"));
                } else if (opcao == 5) {
                    System.out.printf("%78s\n", ("A quantidade de estoque do produto"
                              + " de nome " + prodAlterar.getNome() + " foi alterada com sucesso"));
                }
            } else if (opcao > 1) {
                System.out.printf("%50s\n", "NAO FOI POSSIVEL FAZER A ALTERACAO");
            }
        }
    }

    //MENU principal do programa
    static int menuPrincipal(int operador) {
        linhaImprimeLayout("=");
        System.out.printf("%55s\n", "MENU PRINCIPAL");
        linhaImprimeLayout("-");
        System.out.println("Insira um numero para realizar uma acao");
        linhaImprimeLayout("=");
        System.out.println("1  -  Modulo Atendente    /   2  -  Modulo Administrador"
                  + "    /   3 (x) Encerrar o programa");
        linhaImprimeLayout("-");
        System.out.print("Insira a opcao desejada: ");
        operador = sc.nextInt();
        if (operador == 1 || operador == 2) {
            imprimeLinha("=", "-");
            System.out.println("");
            linhaImprimeLayout("=");
        } else {
            linhaImprimeLayout("=");
        }
        return operador;
    }
}
