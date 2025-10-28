/*
 * Copyright (c) 2025.
 * Criado por Joao Pedro Missiagia. Todos os direitos reservados.
 */

package testes.funcionario;

import model.rn.FuncionarioRN;
import model.vo.FuncionarioVO;

import java.util.List;

public class BuscaFuncionarioTESTE {
    public static void main(String[] args) throws Exception {
        FuncionarioRN func = new FuncionarioRN();

        FuncionarioVO fncCPF = (FuncionarioVO) func.buscarPorCpf("12345678911");
        if(fncCPF == null){
            System.out.println("CPF nao encontrado");
        } else {
            System.out.println("-- BUSCA POR CPF --");
                System.out.println(
                        "CPF: " + fncCPF.getPes_cpf() +
                        " Nome: " + fncCPF.getPes_nome());
            }


        List<FuncionarioVO> funcNome = func.buscarPorNome("Joao");

        if (funcNome.isEmpty()) {
            System.out.println("Funcionario nao encontrado");
        } else {
            System.out.println("--BUSCA POR NOME --");
            for (FuncionarioVO funcionario : funcNome) {
                System.out.println(
                        "CPF: " + funcionario.getPes_cpf() +
                        " Nome: " + funcionario.getPes_nome() +
                        " Ativo: " + funcionario.getPes_ativo()
                );
            }
        }

        List<FuncionarioVO> funcTodo = func.buscarTodos();
        if (funcTodo.isEmpty()) {
            System.out.println("Funcionario nao encontrado");
        } else {
            System.out.println("--BUSCA TODOS --");
            for (FuncionarioVO funcioTodo : funcTodo) {
                System.out.println( "CPF: " + funcioTodo.getPes_cpf() + " Nome: " + funcioTodo.getPes_nome() + " Ativo: " + funcioTodo.getPes_ativo()
                );
            }
        }
    }
}
