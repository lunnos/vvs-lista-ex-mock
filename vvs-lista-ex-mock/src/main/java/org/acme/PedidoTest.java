package org.acme;

import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PedidoTest {
    @Mock
    DescontoService descontoService;

    @Test
    public void calcularValorTotalComDesconto() {
        when(descontoService.calcularDesconto(100)).thenReturn(10.0);
        Pedido pedido = new Pedido(100, descontoService);
        Assertions.assertEquals(90, pedido.calcularValorTotal()); 
    }

    @Test
    public void calcularValorTotalSemDesconto() {
        when(descontoService.calcularDesconto(100)).thenReturn(0.0);
        Pedido pedido = new Pedido(100, descontoService);
        Assertions.assertEquals(100, pedido.calcularValorTotal());
    }

    @Test
    public void lancarExcecaoSeNegativo(){
       when(descontoService.calcularDesconto(100)).thenReturn(110.0);
         Assertions.assertThrows(IllegalArgumentException.class, () -> {
              Pedido pedido = new Pedido(100, descontoService);
              pedido.calcularValorTotal();
         });
    }

    @Test
    public void calcularValorTotalSemItens() {
        when(descontoService.calcularDesconto(0.0)).thenReturn(0.0);
            Pedido pedido = new Pedido(new ArrayList<>(), descontoService);
            Assertions.assertEquals(0.0, pedido.calcularValorTotal());
    }

    @Test
    public void calcularValorTotalComDescontosDiferentes() {
        DescontoService descontoService1 = mock(DescontoService.class);
        DescontoService descontoService2 = mock(DescontoService.class);

        when(descontoService1.calcularDesconto(50.0)).thenReturn(5.0);
        when(descontoService1.calcularDesconto(100.0)).thenReturn(10.0);
        when(descontoService2.calcularDesconto(50.0)).thenReturn(10.0);
        when(descontoService2.calcularDesconto(100.0)).thenReturn(20.0);

        List<ItemPedido> itens = new ArrayList<>();
        itens.add(new ItemPedido("Item 1", 50.0));
        itens.add(new ItemPedido("Item 2", 100.0));

        Pedido pedido = new Pedido(itens, descontoService1, descontoService2);
        Assertions.assertEquals(115.0, pedido.calcularValorTotal());
    }

}
