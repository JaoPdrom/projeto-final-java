package model.vo;

public class EndPostalVO {
    private int endP_id;
    private LogradouroVO logradouro;
    private String endP_nomeRua;
    private String endP_cep;
    private BairroVO bairro;
    private CidadeVO cidade;

    public EndPostalVO() {}

    public EndPostalVO(int endP_id, LogradouroVO logradouro, String endP_nomeRua, String endP_cep, BairroVO bairro, CidadeVO cidade) {
        this.endP_id = endP_id;
        this.logradouro = logradouro;
        this.endP_nomeRua = endP_nomeRua;
        this.endP_cep = endP_cep;
        this.bairro = bairro;
        this.cidade = cidade;
    }

    public int getEndP_id() {
        return endP_id;
    }

    public void setEndP_id(int endP_id) {
        this.endP_id = endP_id;
    }

    public LogradouroVO getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(LogradouroVO logradouro) {
        this.logradouro = logradouro;
    }

    public String getEndP_nomeRua() {
        return endP_nomeRua;
    }

    public void setEndP_nomeRua(String endP_nomeRua) {
        this.endP_nomeRua = endP_nomeRua;
    }

    public String getEndP_cep() {
        return endP_cep;
    }

    public void setEndP_cep(String endP_cep) {
        this.endP_cep = endP_cep;
    }

    public BairroVO getBairro() {
        return bairro;
    }

    public void setBairro(BairroVO bairro) {
        this.bairro = bairro;
    }

    public CidadeVO getCidade() {
        return cidade;
    }

    public void setCidade(CidadeVO cidade) {
        this.cidade = cidade;
    }
}
