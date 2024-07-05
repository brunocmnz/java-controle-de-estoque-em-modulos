/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bruno_menezes;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author bruno
 */
public class Venda {

    private int codigo;
    private Date data;
    private Item[] itensVendidos;
    private String nomeCliente;
    private double valorTotal;

    private int numItens;

    private Venda(Item[] itensVendidos, int codigo, Date data, String nomeCliente) {
        this.itensVendidos = itensVendidos;
        this.data = data;
        this.codigo = codigo;
        this.nomeCliente = nomeCliente;
        valorTotal = 0;
        numItens = 0;
        double valor = 0;
        for (int i = 0; i < itensVendidos.length; i++) {
            Item item = itensVendidos[i];
            if (item != null) {
                numItens++;
                valor = item.getQuantidade() * item.getPreco();
                valorTotal += valor;
            }
        }
    }

    public static Venda getInstance(Item[] itensVendidos, int codigo, Date data, String nomeCliente) {
        if (nomeCliente == null) {
            return null;
        }
        int itens = 0;
        for (int i = 0; i < itensVendidos.length; i++) {
            if (itensVendidos[i] != null) {
                if (itensVendidos[i].getQuantidade() <= 0 || itensVendidos[i].getPreco() <= 0) {
                    return null;
                }else{
                    itens++;
                }
            }
        }
        if (itens > 0) {
            Venda novaVenda = new Venda(itensVendidos, codigo, data, nomeCliente);
            return novaVenda;
        }
        return null;
    }
    
    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDataEHoraString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
        String date = sdf.format(data);
        return date;
    }
    
    public String getDataString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(data);
        return date;
    }
    
    public Date getDate(){
        return data;
    }

    public void setDate(Date data) {
        if (data != null) {
            this.data = data;
        }
    }

    public Item[] getItensVendidos() {
        Item[] itens = new Item[itensVendidos.length];
        for (int i = 0; i < itensVendidos.length; i++) {
            if (itensVendidos[i] != null) {
                double preco = (itensVendidos[i].getPreco());
                double quantidade = (itensVendidos[i].getQuantidade());
                int codigo = itensVendidos[i].getProduto().getCodigo();
                String nome = itensVendidos[i].getProduto().getNome();
                String marca = itensVendidos[i].getProduto().getMarca();
                double precoProd = itensVendidos[i].getProduto().getPreco();
                double quantEstoque = itensVendidos[i].getProduto().getQuantEstoque();
                Produto prod = Produto.getInstance(codigo, nome, marca, precoProd, quantEstoque);
                itens[i] = Item.getInstance(prod, preco, quantidade);
            }
        }
        return itens;
    }

    public boolean adicionaItem(Item item) {
        if (item == null && item.getProduto().getNome() == null) {
            return false;
        }
        for (int i = 0; i < itensVendidos.length; i++) {
            if (itensVendidos[i] != null) {
                itensVendidos[i] = item;
                numItens++;
                return true;
            }
        }
        valorTotal += item.getPreco() * item.getQuantidade();
        return false;
    }

    public boolean cancelarVenda() {
        codigo = 0;
        data = null;
        for (int i = 0; i < numItens; i++) {
            itensVendidos[i] = null;
        }
        numItens = 0;
        return true;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public boolean setNomeCliente(String nomeCliente) {
        if (nomeCliente.length() >= 3) {
            this.nomeCliente = nomeCliente;
            return true;
        }
        return false;
    }
    
    public double getValorTotal(){
        return valorTotal;
    }

    public int getNumItens() {
        return numItens;
    }
}
