/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bruno_menezes;

/**
 *
 * @author bruno
 */
public class Carrinho {

    private Item[] itens;
    private double precoTotal;
    private int numItens;

    private Carrinho() {
        itens = new Item[20];
        numItens = 0;
    }

    public static Carrinho getInstace() {
        Carrinho novoCarrinho = new Carrinho();
        return novoCarrinho;
    }

    private Item[] expandeVetorItens() {
        //"aumentando" o vetor caso ele esteja cheio para poder adicionar itens
        if (numItens == itens.length) {
            Item[] vetItens = itens;
            itens = new Item[numItens + 10];
            for (int i = 0; i < numItens; i++) {
                itens[i] = vetItens[i];
            }
        }
        return itens;
    }

    public boolean adicionarItem(Item item) {
        if (item != null && item.getQuantidade() <= item.getProduto().getQuantEstoque()) {
            itens = expandeVetorItens();
            //Verifica se há outro item com mesmo código e então aglutina ou adiciona
            if (numItens > 0) {
                for (int i = 0; i < numItens; i++) {
                    if (itens[i].getProduto().getCodigo() == item.getProduto().getCodigo()) {
                        double quantTotal = itens[i].getQuantidade() + item.getQuantidade();
                        if (quantTotal <= item.getProduto().getQuantEstoque()) {
                            itens[i].setQuantidade(itens[i].getQuantidade() + item.getQuantidade());
                            return true;
                        }
                        return false;
                    }
                }
            }
            if (item.getQuantidade() > 0 && item.getPreco() > 0 && item.getProduto() != null) {
                itens[numItens] = item;
                numItens++;
                return true;
            }
        }
        return false;
    }

    public boolean removerItem(Item item) {
        if (item != null) {
            boolean shift = false;
            int i;
            for (i = 0; i < numItens; i++) {
                if (shift && i < itens.length - 1) {
                    itens[i] = itens[i + 1];
                } else if (item == itens[i]) {
                    shift = true;
                    i--;
                }
            }
            if (!shift) {
                return false;
            }
            itens[numItens] = null;
            numItens--;
            return true;
        }
        return false;
    }

    public Item[] getItens() {
        Item[] itensCarrinho = new Item[numItens];
        for (int i = 0; i < numItens; i++) {
            Produto prod = itens[i].getProduto();
            double preco = itens[i].getPreco();
            double quantidade = itens[i].getQuantidade();
            Item novoItem = Item.getInstance(prod, preco, quantidade);
            itensCarrinho[i] = novoItem;
        }
        return itensCarrinho;
    }

    public double getPrecoTotal() {
        return precoTotal;
    }

    public int getNumProds() {
        return numItens;
    }

    public Item buscarItem(int codigo) {
        for (int i = 0; i < numItens; i++) {
            if (itens[i].getProduto().getCodigo() == codigo) {
                return itens[i];
            }
        }
        return null;
    }

    public Item buscarItem(String nome) {
        for (int i = 0; i < numItens; i++) {
            if (itens[i].getProduto().getNome().equalsIgnoreCase(nome)) {
                return itens[i];
            }
        }
        return null;
    }
}
