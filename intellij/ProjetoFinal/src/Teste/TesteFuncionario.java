/*
 * Copyright (c) 2025.
 * Criado por Joao Pedro Missiagia. Todos os direitos reservados.
 */

package Teste;

import model.rn.FuncionarioRN;
import model.vo.FuncionarioVO;
import model.vo.CargoVO;
import model.vo.SexoVO;

import java.sql.SQLException;
import java.time.LocalDate;

public class TesteFuncionario {
    public static void main(String[] args) {
        System.out.println("=== Teste da classe FuncionarioRN ===");

        // cria o objeto VO (Value Object)
        FuncionarioVO funcionario = new FuncionarioVO();
        funcionario.setPes_cpf("25476487392");
        funcionario.setPes_nome("Joao Pedro");
        funcionario.setPes_email("emailjp@email.com");
        funcionario.setPes_dt_nascimento(LocalDate.of(2004, 5, 15));
        funcionario.setFnc_dtContratacao(LocalDate.now());
        funcionario.setFnc_salario(3500.0);

        CargoVO cargo = new CargoVO();
        cargo.setCar_id(1);
        cargo.setCargo_descricao("Vendedor");
        funcionario.setFnc_cargo(cargo);

        SexoVO sexo = new SexoVO();
        sexo.setSex_id(1);
        funcionario.setPes_sexo(sexo);


        // instancia a regra de negócio
        FuncionarioRN rn = new FuncionarioRN();

        try {
            rn.adicionarFuncionario(funcionario);
            System.out.println("✅ Funcionário cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("❌ Erro ao cadastrar funcionário: " + e.getMessage());

            // Se for erro SQL, mostra detalhes
            if (e instanceof SQLException) {
                SQLException sqlEx = (SQLException) e;
                System.err.println("\n=== 💥 ERRO SQL DETALHADO ===");
                System.err.println("Mensagem: " + sqlEx.getMessage());
                System.err.println("Código de erro: " + sqlEx.getErrorCode());
                System.err.println("Estado SQL: " + sqlEx.getSQLState());
                System.err.println("Classe da exceção: " + sqlEx.getClass().getName());
                System.err.println("==============================\n");
                sqlEx.printStackTrace();
            } else {
                // Para qualquer outro tipo de exceção
                e.printStackTrace();
            }
        }


    }
}