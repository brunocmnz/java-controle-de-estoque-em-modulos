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
public class Produto {

    private int codigo;
    private String nome;
    private String marca;
    private double preco;
    private double quantEstoque;

    private boolean situacao;

    private Produto(int cod, String nome, String marca, double preco, double quantEstoque) {
        this.codigo = cod;
        this.nome = nome;
        this.marca = marca;
        this.preco = preco;
        this.quantEstoque = quantEstoque;
        this.situacao = true;
    }
    
    public static Produto getInstance(int cod, String nome, String marca, double preco, double quantEstoque){
        Produto prod = null;
        if (cod > 0 && preco > 0 && quantEstoque >= 0) {
            if (nome .length() > 2 && nome != null && marca.length() > 2 && marca != null) {
                prod = new Produto(cod, nome, marca, preco, quantEstoque);
            }
        }
        return prod;
    }

    public int getCodigo() {
        return codigo;
    }

    public boolean setCodigo(int código) {
        this.codigo = código;
        return true;
    }

    public String getNome() {
        return nome;
    }

    public boolean setNome(String nome) {
        if (nome.length() > 3) {
            this.nome = nome;
            return true;
        }
        return false;
    }

    public String getMarca() {
        return marca;
    }

    public boolean setMarca(String marca) {
        if (marca.length() > 3) {
            this.marca = marca;
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

    public double getQuantEstoque() {
        return quantEstoque;
    }

    public boolean setQuantEstoque(double quantEstoque) {
        if (quantEstoque >= 0) {
            this.quantEstoque = quantEstoque;
            return true;
        }
        return false;
    }

    public boolean getSituacao() {
        return this.situacao;
    }

    public void setSituacao() {
        if (this.situacao) {
            this.situacao = false;
        } else {
            this.situacao = true;
        }
    }
}
