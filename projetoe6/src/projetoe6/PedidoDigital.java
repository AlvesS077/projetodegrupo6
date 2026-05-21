package projetoe6;

public class PedidoDigital extends Pedido {
	 
    private String qrCode;
    private boolean pago;
 
    public PedidoDigital(int id, String qrCode) {
        super(id);
        this.qrCode = qrCode;
        this.pago = false;
    }
 
    public String getQrCode() {
        return qrCode;
    }
 
    public boolean isPago() {
        return pago;
    }
 
    public void confirmarPagamento() {
        this.pago = true;
        System.out.println("Pagamento confirmado para o pedido via QR Code: " + qrCode);
    }
 
    @Override
    public String toString() {
        return "PedidoDigital{" + super.toString() +
               ", qrCode='" + qrCode + "', pago=" + pago + "}";
    }
}
