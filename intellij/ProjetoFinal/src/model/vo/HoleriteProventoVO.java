package model.vo;

public class HoleriteProventoVO {
    private int holeriteProvento_id;
    private ProventoVO provento;
    private double holeriteProvento_valor;

    public HoleriteProventoVO() {}

    public HoleriteProventoVO(int holeriteProvento_id, ProventoVO provento, double holeriteProvento_valor) {
        this.holeriteProvento_id = holeriteProvento_id;
        this.provento = provento;
        this.holeriteProvento_valor = holeriteProvento_valor;
    }

    public int getHoleriteProvento_id() {
        return holeriteProvento_id;
    }

    public void setHoleriteProvento_id(int holeriteProvento_id) {
        this.holeriteProvento_id = holeriteProvento_id;
    }

    public ProventoVO getProvento() {
        return provento;
    }

    public void setProvento(ProventoVO provento) {
        this.provento = provento;
    }

    public double getHoleriteProvento_valor() {
        return holeriteProvento_valor;
    }

    public void setHoleriteProvento_valor(double holeriteProvento_valor) {
        this.holeriteProvento_valor = holeriteProvento_valor;
    }
}
