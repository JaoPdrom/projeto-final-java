package model.vo;

import java.util.Date;

public class Holerite {
    private int holerite_id;
    private Date holerite_periodo;
    private Double holerite_proventos;
    private Double holerite_descontos;
    private Double holerite_valor_liquido;
    private FuncionarioVO holerite_fnc_id;
    private InfoEmpresaVO holerite_infoEmpresa_emp_cnpj;

    public Holerite() {}

    public Holerite(int holerite_id, Date holerite_periodo, Double holerite_proventos, Double holerite_descontos, Double holerite_valor_liquido, FuncionarioVO holerite_fnc_id, InfoEmpresaVO holerite_infoEmpresa_emp_cnpj) {
        this.holerite_id = holerite_id;
        this.holerite_periodo = holerite_periodo;
        this.holerite_proventos = holerite_proventos;
        this.holerite_descontos = holerite_descontos;
        this.holerite_valor_liquido = holerite_valor_liquido;
        this.holerite_fnc_id = holerite_fnc_id;
        this.holerite_infoEmpresa_emp_cnpj = holerite_infoEmpresa_emp_cnpj;
    }

    public int getHolerite_id() {
        return holerite_id;
    }

    public void setHolerite_id(int holerite_id) {
        this.holerite_id = holerite_id;
    }

    public Date getHolerite_periodo() {
        return holerite_periodo;
    }

    public void setHolerite_periodo(Date holerite_periodo) {
        this.holerite_periodo = holerite_periodo;
    }

    public Double getHolerite_proventos() {
        return holerite_proventos;
    }

    public void setHolerite_proventos(Double holerite_proventos) {
        this.holerite_proventos = holerite_proventos;
    }

    public Double getHolerite_descontos() {
        return holerite_descontos;
    }

    public void setHolerite_descontos(Double holerite_descontos) {
        this.holerite_descontos = holerite_descontos;
    }

    public Double getHolerite_valor_liquido() {
        return holerite_valor_liquido;
    }

    public void setHolerite_valor_liquido(Double holerite_valor_liquido) {
        this.holerite_valor_liquido = holerite_valor_liquido;
    }

    public FuncionarioVO getHolerite_fnc_id() {
        return holerite_fnc_id;
    }

    public void setHolerite_fnc_id(FuncionarioVO holerite_fnc_id) {
        this.holerite_fnc_id = holerite_fnc_id;
    }

    public InfoEmpresaVO getHolerite_infoEmpresa_emp_cnpj() {
        return holerite_infoEmpresa_emp_cnpj;
    }

    public void setHolerite_infoEmpresa_emp_cnpj(InfoEmpresaVO holerite_infoEmpresa_emp_cnpj) {
        this.holerite_infoEmpresa_emp_cnpj = holerite_infoEmpresa_emp_cnpj;
    }
}
