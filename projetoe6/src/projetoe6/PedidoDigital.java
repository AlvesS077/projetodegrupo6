package projetoe6;


public class PedidoDigital extends Pedido {

    private boolean pago;

    public PedidoDigital() {
        super();
        this.pago = false;
    }


    public boolean isPago() {
        return pago;
    }


    public void confirmarPagamento() {
        if (pago) {
            System.out.println("Este pedido ja foi pago.");
            return;
        }
        this.pago = true;
        System.out.println("Pagamento confirmado para o pedido #" + getId());
    }


    public String toString() {
        String estadoPagamento = pago ? "Pago" : "Por pagar";
        return super.toString() + " | " + estadoPagamento;
    }
} //