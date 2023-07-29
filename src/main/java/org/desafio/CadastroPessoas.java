package org.desafio;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CadastroPessoas {
    private List<Pessoa> pessoas;
    private Scanner scanner;

    public CadastroPessoas() {
        pessoas = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        printHeader();
        int option;

        do {
            option = printMenuOptions();

            switch (option) {
                case 1 -> cadastrarPessoa();
                case 2 -> buscarPessoa();
                case 0 -> {
                    System.out.println("Saindo do programa...");
                    return;
                }
                default -> System.out.println("Opção inválida. Por favor, tente novamente.");
            }

        } while (true);
    }

    private void cadastrarPessoa() {
        Pessoa pessoa = new Pessoa();
        System.out.println("=================================");
        System.out.println("\nInsira o Nome da Pessoa (ou digite 0 para cancelar):");
        String inputNome = scanner.nextLine().trim();

        if (inputNome.equals("0")) {
            System.out.println("Cadastro cancelado.");
            return;
        }

        pessoa.setNome(inputNome);
        System.out.printf("\nInsira a Idade de %s (ou digite 0 para cancelar):%n", pessoa.getNome());
        int inputIdade = scanner.nextInt();
        scanner.nextLine();

        if (inputIdade == 0) {
            System.out.println("Cadastro cancelado.");
            return;
        }

        pessoa.setIdade(inputIdade);

        while (true) {
            System.out.printf("\nInsira o CPF de %s (no formato xxx.xxx.xxx-xx) (ou digite 0 para cancelar):%n", pessoa.getNome());
            String cpf = scanner.nextLine().trim();

            if (cpf.equals("0")) {
                System.out.println("Cadastro cancelado.");
                return;
            }

            if (isValidCPF(cpf)) {
                pessoa.setCpf(cpf);
                break;
            } else {
                System.out.println("CPF inválido. Por favor, insira um CPF válido.");
            }
        }

        System.out.printf("\nPor favor, insira a quantidade de endereços a serem cadastrados para %s:%n",
                pessoa.getNome());
        int numEnderecos = scanner.nextInt();
        scanner.nextLine();
        List<Endereco> enderecos = new ArrayList<>();
        for (int j = 0; j < numEnderecos; j++) {
            Endereco endereco = new Endereco();
            System.out.println("=================================");
            System.out.println("\nInsira o CEP do endereço:");
            endereco.setCep(scanner.nextLine());
            System.out.println("\nInsira o Logradouro do endereço:");
            endereco.setLogradouro(scanner.nextLine());

            int numero;
            while (true) {
                try {
                    System.out.println("\nInsira o número da rua do endereço:");
                    numero = scanner.nextInt();
                    scanner.nextLine();
                    break;
                } catch (java.util.InputMismatchException e) {
                    scanner.nextLine();
                    System.out.println("Por favor, insira um número válido.");
                }
            }
            endereco.setNumero(numero);

            System.out.println("\nInsira o Complemento do endereço:");
            endereco.setComplemento(scanner.nextLine());
            System.out.println("\nInsira o Bairro do endereço:");
            endereco.setBairro(scanner.nextLine());
            System.out.println("\nInsira o Estado do endereço:");
            endereco.setEstado(scanner.nextLine());
            enderecos.add(endereco);
        }
        pessoa.setEnderecos(enderecos);
        pessoas.add(pessoa);
        System.out.println("Pessoa cadastrada com sucesso.");
    }

    private void buscarPessoa() {
        System.out.println("=================================");
        if (pessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada.");
        } else {
            System.out.println("Digite o nome da pessoa que deseja buscar:");
            String nome = scanner.nextLine().trim();
            if (nome.equals("0")) {
                System.out.println("Busca cancelada.");
                return;
            }
            if (nome.isEmpty()) {
                System.out.println("O nome da pessoa não pode ser vazio.");
                return;
            }

            List<Pessoa> pessoasEncontradas = new ArrayList<>();

            for (Pessoa p : pessoas) {
                if (p.getNome().equalsIgnoreCase(nome)) {
                    pessoasEncontradas.add(p);
                }
            }

            if (!pessoasEncontradas.isEmpty()) {
                if (pessoasEncontradas.size() == 1) {
                    Pessoa pessoaEncontrada = pessoasEncontradas.get(0);
                    displayPersonInfo(pessoaEncontrada);
                } else {
                    System.out.println("Foram encontradas várias pessoas com o mesmo nome.");
                    System.out.println("Digite o CPF da pessoa que deseja buscar:");
                    String cpf = scanner.nextLine().trim();
                    Pessoa pessoaEncontrada = searchByCpf(pessoasEncontradas, cpf);
                    if (pessoaEncontrada != null) {
                        displayPersonInfo(pessoaEncontrada);
                    } else {
                        System.out.println("Nenhuma pessoa com o CPF '" + cpf + "' encontrada.");
                    }
                }
            } else {
                System.out.println("Nenhuma pessoa com o nome '" + nome + "' cadastrada.");
            }
        }
    }

    private static void displayPersonInfo(Pessoa pessoa) {
        System.out.println("======== Informações da Pessoa ========");
        System.out.println("Nome: " + pessoa.getNome()
                + "\nIdade: " + pessoa.getIdade()
                + "\nCPF: " + pessoa.getCpf()
                + "\n======== Endereços ========");
        List<Endereco> enderecoList = pessoa.getEnderecos();
        for (Endereco endereco : enderecoList) {
            System.out.println("===============================");
            System.out.println("CEP: " + endereco.getCep());
            System.out.println("Logradouro: " + endereco.getLogradouro());
            System.out.println("Número: " + endereco.getNumero());
            System.out.println("Complemento: " + endereco.getComplemento());
            System.out.println("Bairro: " + endereco.getBairro());
            System.out.println("Estado: " + endereco.getEstado());
        }
    }

    private static Pessoa searchByCpf(List<Pessoa> pessoas, String cpf) {
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getCpf().equalsIgnoreCase(cpf)) {
                return pessoa;
            }
        }
        return null;
    }

    private static boolean isValidCPF(String cpf) {
        return cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    }

    private static void printHeader() {
        System.out.println("=================================");
        System.out.println("  Cadastro e Busca de Pessoas    ");
        System.out.println("=================================");
    }

    private static int printMenuOptions() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n======= MENU =======\n");
        System.out.println("1. Cadastrar Pessoa");
        System.out.println("2. Buscar Pessoa");
        System.out.println("0. Sair\n");
        System.out.println("====================");
        return scanner.nextInt();
    }
}
