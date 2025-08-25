CREATE DATABASE IF NOT EXISTS db_poo;

USE db_poo;

CREATE TABLE IF NOT EXISTS tb_cidade(
    cid_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    cid_descricao VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_estado(
    est_sigla CHAR(2) NOT NULL PRIMARY KEY,
    est_descricao VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_pais(
    pais_sigla CHAR(3) NOT NULL PRIMARY KEY,
    pais_descricao VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_bairro(
    bairro_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    bairro_descricao VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_cidEstPai(
    cidEstPai_cid_id INT,
    cidEstPai_est_sigla CHAR(2),
    cidEstPai_pais_sigla CHAR(3),
    PRIMARY KEY (cidEstPai_cid_id, cidEstPai_est_sigla,cidEstPai_pais_sigla),
    FOREIGN KEY (cidEstPai_cid_id) REFERENCES tb_cidade (cid_id),
    FOREIGN KEY (cidEstPai_est_sigla) REFERENCES tb_estado (est_sigla),
    FOREIGN KEY (cidEstPai_pais_sigla) REFERENCES tb_pais (pais_sigla)
);

CREATE TABLE IF NOT EXISTS tb_logradouro(
    logradouro_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    log_descricao VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_endPostal(
    endP_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    endP_logradouro_id INT NOT NULL,
    endP_nomeRua VARCHAR(45) NOT NULL,
    endP_cep CHAR(9) NOT NULL,
    endP_cid_id INT NOT NULL,
    endP_est_sigla CHAR(2) NOT NULL,
    endP_pais_sigla CHAR(3) NOT NULL,
    endP_bairro_id INT NOT NULL,
    FOREIGN KEY (endP_logradouro_id) REFERENCES tb_logradouro (logradouro_id),
    FOREIGN KEY (endP_cid_id) REFERENCES tb_cidEstPai (cidEstPai_cid_id),
    FOREIGN KEY (endP_est_sigla) REFERENCES tb_cidEstPai (cidEstPai_est_sigla),
    FOREIGN KEY (endP_pais_sigla) REFERENCES tb_cidEstPai (cidEstPai_pais_sigla),
    FOREIGN KEY (endP_bairro_id) REFERENCES tb_bairro(bairro_id)
);


CREATE TABLE IF NOT EXISTS tb_endereco(
    end_id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    end_endP_id INT NOT NULL,
    end_numero VARCHAR(10) NOT NULL,
    end_complemento VARCHAR(45),
    FOREIGN KEY (end_endP_id) REFERENCES tb_endPostal (endP_id)
);

CREATE TABLE IF NOT EXISTS tb_telefone(
    tel_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    tel_codPais CHAR(2) NOT NULL,
    tel_ddd CHAR(2),
    tel_numero VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS tb_sexo(
    sex_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    sex_descricao VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_pessoa(
    pes_cpf CHAR(11) NOT NULL PRIMARY KEY,
    pes_nome VARCHAR(100) NOT NULL,
    pes_end_id INT NOT NULL,
    pes_sex_id INT NOT NULL,
    pes_tel_id INT,
    pes_dtNascimento DATE NOT NULL,
    pes_email VARCHAR(100),
    FOREIGN KEY (pes_end_id) REFERENCES tb_endereco (end_id),
    FOREIGN KEY (pes_sex_id) REFERENCES tb_sexo (sex_id),
    FOREIGN KEY (pes_tel_id) REFERENCES tb_telefone (tel_id)
);

CREATE TABLE IF NOT EXISTS tb_cargo(
    cargo_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    cargo_descricao VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_funcionario(
    fnc_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    data_contratacao DATE NOT NULL,
    data_demissao DATE NULL,
    fnc_cargo_id INT NOT NULL,
    fnc_pes_cpf CHAR(11) NOT NULL,
    FOREIGN KEY (fnc_cargo_id) REFERENCES tb_cargo(cargo_id),
    FOREIGN KEY (fnc_pes_cpf) REFERENCES tb_pessoa(pes_cpf)
);

CREATE TABLE IF NOT EXISTS tb_statusVenda(
    statusVenda_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    statusVenda_descricao VARCHAR(45) NOT NULL
); -- finalizada ou pendente

CREATE TABLE IF NOT EXISTS tb_venda(
    venda_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    venda_data DATE NOT NULL,
    venda_pes_cpf CHAR(11) NOT NULL,
    venda_fnc_id INT NOT NULL,
    venda_statusVenda INT NOT NULL,
    venda_tipo_pagamento VARCHAR(45) NOT NULL,
    FOREIGN KEY (venda_pes_cpf) REFERENCES tb_pessoa(pes_cpf),
    FOREIGN KEY (venda_fnc_id) REFERENCES tb_funcionario(fnc_id),
    FOREIGN KEY (venda_statusVenda) REFERENCES tb_statusVenda(statusVenda_id)
);

CREATE TABLE IF NOT EXISTS tb_fornecedor(
    forn_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    forn_nome VARCHAR(45) NOT NULL,
    forn_cnpj VARCHAR(45) NOT NULL,
    forn_end_id INT NOT NULL,
    FOREIGN KEY (forn_end_id) REFERENCES tb_endereco(end_id)
);

CREATE TABLE IF NOT EXISTS tb_tipoPdt(
    tipoPdt_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tipoPdt_descricao VARCHAR(45)
); -- racao pronta ou formula

CREATE TABLE IF NOT EXISTS tb_produto(
    produto_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    produto_nome VARCHAR(45) NOT NULL,
    produto_qtdMax DOUBLE NOT NULL,
    produto_qtdMin DOUBLE NOT NULL,
    produto_tipoPdt INT NOT NULL,
    produto_forn_id INT NOT NULL,
    FOREIGN KEY (produto_forn_id) REFERENCES tb_fornecedor (forn_id),
    FOREIGN KEY (produto_tipoPdt) REFERENCES tb_tipoPdt (tipoPdt_id)
);

CREATE TABLE IF NOT EXISTS tb_itemVenda(
    itemVenda_venda_id INT NOT NULL,
    itemVenda_produto_id INT NOT NULL,
    itemVenda_qtd INT NOT NULL,
    itemVenda_preco DOUBLE NOT NULL,
    PRIMARY KEY (itemVenda_venda_id, itemVenda_produto_id),
    FOREIGN KEY (itemVenda_venda_id) REFERENCES tb_venda (venda_id),
    FOREIGN KEY (itemVenda_produto_id) REFERENCES tb_produto (produto_id)
);

CREATE TABLE IF NOT EXISTS tb_debito(
    debito_venda INT NOT NULL PRIMARY KEY,
    FOREIGN KEY (debito_venda) REFERENCES tb_venda(venda_id)
);

CREATE TABLE IF NOT EXISTS tb_statusPedido(
    ststus_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    status_descricao VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_tipoEntrega(
    tipoEntrega_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tipoEntrega_descricao VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_pedido(
    pedido_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    pedido_statusPedido INT NOT NULL,
    pedido_tipoEntrega INT NOT NULL,
    pedido_venda_id INT NOT NULL,
    pedido_fnc_id INT NOT NULL,
    FOREIGN KEY (pedido_venda_id) REFERENCES tb_venda (venda_id),
    FOREIGN KEY (pedido_fnc_id) REFERENCES tb_funcionario (fnc_id),
    FOREIGN KEY (pedido_statusPedido) REFERENCES tb_statuspedido (ststus_id),
    FOREIGN KEY (pedido_tipoEntrega) REFERENCES tb_tipoEntrega (tipoEntrega_id)
);

CREATE TABLE IF NOT EXISTS tb_log(
    log_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    log_acao VARCHAR(100) NOT NULL,
    log_dataHora DATETIME NOT NULL,
    log_fnc_id INT NOT NULL,
    FOREIGN KEY (log_fnc_id) REFERENCES tb_funcionario(fnc_id)
);

CREATE TABLE IF NOT EXISTS tb_formula(
    formula_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    formula_dtValidade DATE NOT NULL,
    formIngre_qtd DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS tb_formIngre(
    formIngre_formula_id INT NOT NULL,
    formIgre_produto_id INT NOT NULL,
    PRIMARY KEY(formIngre_formula_id, formIgre_produto_id),
    FOREIGN KEY (formIngre_formula_id) REFERENCES tb_formula(formula_id),
    FOREIGN KEY (formIgre_produto_id) REFERENCES tb_produto(produto_id)
);

CREATE TABLE IF NOT EXISTS tb_estoque(
    est_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    est_dtCompra DATE NOT NULL,
    est_produto_id INT NOT NULL,
    est_custo DOUBLE NOT NULL,
    est_qtdToal DOUBLE NOT NULL,
    est_lote VARCHAR(45) NOT NULL,
    est_dtValidade DATE NOT NULL,
    FOREIGN KEY (est_produto_id) REFERENCES tb_produto(produto_id)
);

CREATE TABLE IF NOT EXISTS td_despesa(
    despesa_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    despesa_descricao VARCHAR(45) NOT NULL,
    despesa_dtRealiazacao DATE NOT NULL,
    despesa_valor_pago DOUBLE NOT NULL
);


CREATE TABLE IF NOT EXISTS tb_infoEmpresa(
    emp_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    emp_nome VARCHAR(45) NOT NULL,
    emp_cnpj VARCHAR(45) NOT NULL,
    emp_end_id INT NOT NULL,
    FOREIGN KEY (emp_end_id) REFERENCES tb_endereco(end_id)
);

CREATE TABLE IF NOT EXISTS tb_holerite(
    holerite_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    holerite_periodo DATE NOT NULL,
    holerite_proventos DOUBLE NOT NULL,
    holerite_descontos DOUBLE NOT NULL,
    holerite_valor_liquido DOUBLE NOT NULL,
    holerite_fnc_id INT NOT NULL,
    holerite_infoEmpresa_id INT NOT NULL,
    FOREIGN KEY (holerite_fnc_id) REFERENCES tb_funcionario(fnc_id),
    FOREIGN KEY (holerite_infoEmpresa_id) REFERENCES tb_infoempresa(emp_id)
);
