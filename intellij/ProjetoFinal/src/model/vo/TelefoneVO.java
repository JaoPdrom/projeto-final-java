package model.vo;

public class TelefoneVO {
    private int tel_id;
    private String tel_codPais;
    private String tel_ddd;
    private String tel_numero;

    public TelefoneVO(int tel_id, String tel_codPais, String tel_ddd, String tel_numero) {
        this.tel_id = tel_id;
        this.tel_codPais = tel_codPais;
        this.tel_ddd = tel_ddd;
        this.tel_numero = tel_numero;
    }

    public int getTel_id() {
        return tel_id;
    }

    public void setTel_id(int tel_id) {
        this.tel_id = tel_id;
    }

    public String getTel_codPais() {
        return tel_codPais;
    }

    public void setTel_codPais(String tel_codPais) {
        this.tel_codPais = tel_codPais;
    }

    public String getTel_ddd() {
        return tel_ddd;
    }

    public void setTel_ddd(String tel_ddd) {
        this.tel_ddd = tel_ddd;
    }

    public String getTel_numero() {
        return tel_numero;
    }

    public void setTel_numero(String tel_numero) {
        this.tel_numero = tel_numero;
    }
}
