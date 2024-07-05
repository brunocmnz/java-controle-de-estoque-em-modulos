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
public class Item {

    private Produto produto;
    private double preco;
    private double quantidade;
    
    private Item(Produto produto, double preco, double quantidade){
        this.preco = preco;
        this.produto = produto;
        this.quantidade = quantidade;
    }
    
    public static Item getInstance(Produto prod, double preco, double quantidade){
        Item novoItem = null;
        if (prod != null && preco > 0 && quantidade > 0) {
            novoItem = new Item(prod, preco, quantidade);
        }
        return novoItem;
    }
    
    public Produto getProduto() {
        int codigo = produto.getCodigo();
        String marca = produto.getMarca();
        String nome = produto.getNome();
        double preço = produto.getPreco();
        double quantEstoque = produto.getQuantEstoque();
        Produto prod = Produto.getInstance(codigo, nome, marca, preco, quantEstoque);
        return prod;
    }

    public boolean setProduto(Produto produto) {
        if (produto != null && produto.getNome().length() > 1) {
            this.produto = produto;
            return true;
        }
        return false;
    }

    public double getPreco() {
        return preco;
    }

    public boolean setPreco(double preco) {
        if (preco > 0) {
            this.preco = preco;
            return true;
        }
        return false;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public boolean setQuantidade(double quantidade) {
        if (quantidade >= 0 && quantidade < this.produto.getQuantEstoque()) {
            this.quantidade = quantidade;
            return true;
        }
        return false;
    }

}
