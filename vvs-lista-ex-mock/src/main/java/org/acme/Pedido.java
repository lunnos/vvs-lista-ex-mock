package org.acme;

import java.util.List;

public class Pedido {

    private List<ItemPedido> itens;
    private DescontoService descontoService;
    private int contagem = 0;

    public Pedido(List<ItemPedido> itens, DescontoService descontoService) {
        this.itens = itens;
        this.descontoService = descontoService;
    }

    public Pedido(DescontoService descontoService) {
        this.itens = new ArrayList<>();
        this.descontoService = descontoService;
    }

    public double calcularValorTotal() {
        double valorTotal = 0.0;
        for (ItemPedido item : itens) {
            valorTotal += item.getSubtotal();
        }

        double desconto = descontoService.calcularDesconto(valorTotal);
        contagem++;
        return valorTotal - desconto;
    }
}