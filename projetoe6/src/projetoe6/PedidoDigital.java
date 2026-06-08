package projetoe6;


public class PedidoDigital extends Pedido {

    private boolean pago;

    // Construtor - define o pedido inicialmente como nao pago
    public PedidoDigital() {
        super();
        this.pago = false;
    }

    // Devolve true se o pedido ja foi pago
    public boolean isPago() {
        return pago;
    }

    // Marca o pedido como pago e mostra uma confirmacao
    public void confirmarPagamento() {
        if (pago) {
            System.out.println("Este pedido ja foi pago.");
            return;
        }
        this.pago = true;
        System.out.println("Pagamento confirmado para o pedido #" + getId());
    }

    // Representacao textual do pedido digital (inclui apenas o estado de pagamento)
    public String toString() {
        String estadoPagamento = pago ? "Pago" : "Por pagar";
        return super.toString() + " | " + estadoPagamento;
    }
} //