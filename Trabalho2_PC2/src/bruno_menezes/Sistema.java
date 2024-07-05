/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bruno_menezes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author bruno
 */
public class Sistema {

    Random rdm = new Random();

    private Venda[] vendas;
    private Produto[] produtos;

    private static int numProdutos;
    private static int numVendasFeitas;
    private static Sistema instance;

    //Construtor com parâmetro do tamanho dos vetores de vendas e produtos
    private Sistema(int num) {
        numProdutos = 0;
        numVendasFeitas = 0;
        vendas = new Venda[num];
        produtos = new Produto[num];
        init();
    }

    //Método fábrica que chama o construtor e cria apenas um objeto (singleton)
    public static Sistema getInstance(int num) {
        if (instance == null) {
            instance = new Sistema(num);
        }
        return instance;
    }

    public static int getNumProdutos() {
        return numProdutos;
    }

    public static int getNumVendasFeitas() {
        return numVendasFeitas;
    }

    public boolean inserirVenda(Venda novaVenda) {
        boolean verificaCodigo = true;
        //"Aumentando" o vetor vendas caso ele esteja cheio
        if (numVendasFeitas == vendas.length) {
            Venda[] vendasFeitas = vendas;
            vendas = new Venda[numVendasFeitas + 10];
            for (int i = 0; i < numVendasFeitas - 1; i++) {
                vendas[i] = vendasFeitas[i];
                if (vendasFeitas[i].getCodigo() == novaVenda.getCodigo()) {
                    verificaCodigo = false;
                }
            }
        }
        //Verifica a validade e insere a nova venda no vetor de vendas
        if (verificaCodigo && novaVenda.getDataEHoraString() != null
                && novaVenda.getItensVendidos() != null) {
            vendas[numVendasFeitas] = novaVenda;
            numVendasFeitas++;
            return true;
        }
        return false;
    }

    public Venda[] getVendas() {
        Venda[] vendasJaFeitas = new Venda[Sistema.numVendasFeitas];
        for (int i = 0; i < numVendasFeitas; i++) {
            Item[] itensVendidos = vendas[i].getItensVendidos();
            int codigo = vendas[i].getCodigo();
            Date data = vendas[i].getDate();
            String nomeCliente = vendas[i].getNomeCliente();
            vendasJaFeitas[i] = Venda.getInstance(itensVendidos, codigo, data, nomeCliente);
        }
        return vendasJaFeitas;
    }

    public boolean cadastrarProduto(Produto novoProduto) {
        for (int i = 0; i < numProdutos; i++) {
            if (produtos[i].getNome().equalsIgnoreCase(novoProduto.getNome())) {
                return false;
            }
        }
        if (novoProduto != null && novoProduto.getNome() != null) {
            produtos[numProdutos] = novoProduto;
            numProdutos++;
            return true;
        }
        return false;
    }

    public boolean verificaValidadeCodigo(int codigo) {
        for (int i = 0; i < numProdutos; i++) {
            if (produtos[i].getCodigo() == codigo) {
                return false;
            }
        }
        return true;
    }

    private void init() {
        //Vetor com nomes de produtos de moda
        String[] nomesDeProds = {"Saia", "Relogio", "Camiseta", "Chinelo", "Blusa"};
        //Vetor com as marcas dos produtos de moda
        String[] marcasDeProds = {"Nike", "Adidas", "Coca-Cola", "Colcci", "Oakley"};

        int codigo;
        String nome;
        String marca;
        double preco;
        double quantEstoque;
        //Sorteio dos produtos iniciais
        for (int numProdutos = 0; numProdutos < 5;) {
            codigo = numProdutos + 1;
            nome = nomesDeProds[numProdutos];
            marca = marcasDeProds[numProdutos];
            if (nome != null) {
                preco = rdm.nextInt(5000) * 0.01f + 30;
                quantEstoque = rdm.nextInt(50) + 10;
                Produto novoProd = Produto.getInstance(codigo, nome, marca, preco, quantEstoque);
                cadastrarProduto(novoProd);
                numProdutos++;
            }
        }
        //Sorteio das vendas iniciais
        for (; numVendasFeitas < 5;) {
            int numProdsVendidos = 1;
            switch (numVendasFeitas) {
                case 0:
                    numProdsVendidos = 5;
                    break;
                case 1:
                    numProdsVendidos = 3;
                    break;
                case 2:
                    numProdsVendidos = 1;
                    break;
                case 3:
                    numProdsVendidos = 4;
                    break;
                case 4:
                    numProdsVendidos = 2;
                    break;
            }
            Item[] itensVendidos = new Item[numProdsVendidos];
            int[] indicesProd = {0,1,2,3,4};
            int prodVenda;
            for (int j = 0; j < numProdsVendidos; j++) {
                do {
                    prodVenda = rdm.nextInt(5);
                } while (indicesProd[prodVenda] == 5);
                int indice = indicesProd[prodVenda];
                Produto prod = produtos[indice];
                indicesProd[prodVenda] = 5;
                preco = (rdm.nextInt(10) + 1) * 10;
                int quantVenda = (rdm.nextInt(5) + 1);
                itensVendidos[j] = Item.getInstance(prod, preco, quantVenda);
            }
            String data = null;
            Date date = null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
            if (numVendasFeitas > 3) {
                date = new Date();
            } else {
                switch (numVendasFeitas) {
                    case 0:
                        data = "25/11/2021  16:34";
                        break;
                    case 1:
                        data = "25/11/2021  16:45";
                        break;
                    case 2:
                        data = "12/12/2021  17:40";
                        break;
                    case 3:
                        data = "15/12/2021  09:35";
                }
                try {
                    date = sdf.parse(data);
                } catch (Exception e) {
                    System.out.println("Não foi possível adicionar a data" + data);
                }
            }
            codigo = numVendasFeitas + 1;
            String nomeCliente = null;
            String[] nomes = {"Joaquim Silva", "Maria de Sousa", "Ana Andrade",
                "Jose Pereira", "Mateus Mendes"};
            nomeCliente = nomes[numVendasFeitas];
            Venda venda = Venda.getInstance(itensVendidos, codigo, date, nomeCliente);
            boolean insere = inserirVenda(venda);
            if (insere) {
                numVendasFeitas = codigo;
            }
        }
    }

    public boolean excluirProduto(int codigo) {
        //Identificar o indice do produto no vetor de produtos
        int indiceProduto = numProdutos;
        for (int i = 0; i < numProdutos; i++) {
            if (produtos[i].getCodigo() == codigo) {
                indiceProduto = i;
                break;
            }
        }
        //Identificar se há vendas com esse produto no vetor vendas
        if (produtos[indiceProduto].getSituacao()) {
            for (int i = 0; i < numVendasFeitas; i++) {
                Item[] itens = vendas[i].getItensVendidos();
                for (int j = 0; j < itens.length; j++) {
                    if (itens[j].getProduto().getCodigo() == codigo) {
                        produtos[indiceProduto].setSituacao();
                        return true;
                    }
                }
            }
            numProdutos--;
            for (int j = indiceProduto; j < numProdutos; j++) {
                produtos[j] = produtos[j + 1];
            }
            produtos[numProdutos] = null;
            return true;
        }
        return false;
    }

    public Produto buscarPorCodigo(int codigo) {
        for (int i = 0; i < numProdutos; i++) {
            if (produtos[i] != null && produtos[i].getCodigo() == codigo) {
                if (produtos[i].getSituacao()) {
                    return produtos[i];
                }
                break;
            }
        }
        return null;
    }

    public Produto buscarPorNome(String nome) {
        for (int i = 0; i < numProdutos; i++) {
            if (produtos[i] != null && produtos[i].getNome().equalsIgnoreCase(nome)) {
                if (produtos[i].getSituacao()) {
                    return produtos[i];
                }
                break;
            }
        }
        return null;
    }

    public Produto[] getProdutos() {
        Produto[] vetProds = new Produto[numProdutos];
        for (int i = 0; i < numProdutos; i++) {
            vetProds[i] = produtos[i];
        }
        return vetProds;
    }

    public Produto[] listarPorNome(Produto[] produtos) {
        int contaNomes = 0;
        for (int i = 0; i < produtos.length; i++) {
            if (produtos[i] != null) {
                if (produtos[i].getNome() != null) {
                    contaNomes++;
                }
            }
        }

        String[] vetNomes = new String[contaNomes];
        for (int i = 0; i < produtos.length; i++) {
            for (int j = 0; j < vetNomes.length; j++) {
                if (produtos[i] != null && vetNomes[j] == null) {
                    vetNomes[j] = produtos[i].getNome();
                    break;
                }
            }
        }

        for (int i = 0; i < vetNomes.length - 1; i++) {
            for (int j = vetNomes.length - 1; j > i; j--) {
                if (vetNomes[j].compareToIgnoreCase(vetNomes[j - 1]) < 0) {
                    String temp = vetNomes[j];
                    vetNomes[j] = vetNomes[j - 1];
                    vetNomes[j - 1] = temp;
                }
            }
        }

        Produto[] vetProdNome = new Produto[contaNomes];
        for (int i = 0; i < vetNomes.length; i++) {
            for (int j = 0; j < produtos.length; j++) {
                if (produtos[j] != null && produtos[j].getNome() != null) {
                    if (vetNomes[i].equals(produtos[j].getNome())) {
                        vetProdNome[i] = produtos[j];
                    }
                }
            }
        }
        return vetProdNome;
    }

    public Venda getVenda(int codigo) {
        for (int i = 0; i < numVendasFeitas; i++) {
            if (vendas[i].getCodigo() == codigo) {
                return vendas[i];
            }
        }
        return null;
    }

}
