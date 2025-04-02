package conta;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArraySet;

import conta.util.Cores;
import conta.controller.ContaController;
import conta.model.ContaCorrente;
import conta.model.ContaPoupanca;

public class Menu {

	
	public static void main(String[] args) {

		ContaController contas = new ContaController();

		Scanner leia = new Scanner(System.in);

		int opcao, numero, agencia, tipo, aniversario, numeroDestino;
		String titular;
		float saldo, limite, valor;

		System.out.println("\nCriar Contas\n");

		ContaCorrente cc1 = new ContaCorrente(contas.gerarNumero(), 123, 1, "Joao da silva", 1000f, 100.0f);
		contas.cadastrar(cc1);

		ContaCorrente cc2 = new ContaCorrente(contas.gerarNumero(), 124, 1, "Maria da silva", 2000f, 100.0f);
		contas.cadastrar(cc2);

		ContaPoupanca cp1 = new ContaPoupanca(contas.gerarNumero(), 125, 2, "Mariana dos santos", 4000f, 12);
		contas.cadastrar(cp1);

		ContaPoupanca cp2 = new ContaPoupanca(contas.gerarNumero(), 125, 2, "juliana ramos", 8000f, 15);
		contas.cadastrar(cp2);

		contas.listarTodas();

		while (true) {

			System.out.println(Cores.TEXT_YELLOW + Cores.ANSI_BLACK_BACKGROUND
					+ "*******************************************************");
			System.out.println("                                                       ");
			System.out.println("             BANCO DO BRAZIL COM Z                     ");
			System.out.println("                                                       ");
			System.out.println("*******************************************************");
			System.out.println("                                                       ");
			System.out.println("                1 - Criar Conta                        ");
			System.out.println("                2 - Listar todas as Contas             ");
			System.out.println("                3 - Buscar Conta por Numero            ");
			System.out.println("                4 - Atualizar Dados da Conta           ");
			System.out.println("                5 - Apagar Conta                       ");
			System.out.println("                6 - Sacar                              ");
			System.out.println("                7 - Depositar                          ");
			System.out.println("                8 - Transferir                         ");
			System.out.println("                9 - Sair                               ");
			System.out.println("                                                       ");
			System.out.println("*******************************************************");
			System.out.println("Entre Com a Opção Desejada:                            ");
			System.out.println("                                                       " + Cores.TEXT_RESET);

			try {
				opcao = leia.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("\nDigite valores inteiros!");
				leia.nextLine();
				opcao = 0;

			}

			if (opcao == 9) {
				System.out.println(Cores.TEXT_WHITE_BOLD + "\nBanco do Brazil com Z - O Seu Futuro Comça Aqui");
				sobre();
				leia.close();
				System.exit(0);
			}

			switch (opcao) {
			case 1:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Criar Conta\n\n");

				System.out.println("Digite o numero da Agencia: ");
				agencia = leia.nextInt();
				System.out.println("Digite o numero do Titular: ");
				leia.skip("\\R?");
				titular = leia.nextLine();

				do {
					System.out.println("Digite o tipo de Conta (1-CC ou 2-CP): ");
					tipo = leia.nextInt();

				} while (tipo < 1 && tipo > 2);

				System.out.println("Digite o saldo da conta (R$): ");
				saldo = leia.nextFloat();

				switch (tipo) {
				case 1 -> {

					System.out.println("Digite o limite de Credito (R$): ");
					limite = leia.nextFloat();
					contas.cadastrar(new ContaCorrente(contas.gerarNumero(), agencia, tipo, titular, saldo, limite));
				}
				case 2 -> {
					System.out.println("Digite o dia do aniversario da Conta: ");
					aniversario = leia.nextInt();
					contas.cadastrar(
							new ContaPoupanca(contas.gerarNumero(), agencia, tipo, titular, saldo, aniversario));
				}

				}

				KeyPress();
				break;

			case 2:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Listar todas as Contas\n\n");
				contas.listarTodas();
				KeyPress();
				break;

			case 3:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Consultar dados da Conta - por numero\n\n");

				System.out.println("Digite o número da conta: ");
				numero = leia.nextInt();

				contas.procurarPorNumero(numero);

				KeyPress();
				break;

			case 4:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Atualizar dados da Conta\n\n");

				System.out.println("Digite o numero da conta: ");
				numero = leia.nextInt();

				var buscaConta = contas.buscarNaColletion(numero);

				if (buscaConta != null) {

					tipo = buscaConta.getTipo();

					System.out.println("Digite o Numero da Agencia: ");
					agencia = leia.nextInt();
					System.out.println("Digite o Nome do Titular: ");
					leia.skip("\\R?");
					titular = leia.nextLine();

					System.out.println("Digite o Saldo da Conta (R$): ");
					saldo = leia.nextFloat();

					switch (tipo) {
					case 1 -> {
						System.out.println("Digite o limite de credito (R$): ");
						limite = leia.nextFloat();

						contas.atualizar(new ContaCorrente(numero, agencia, tipo, titular, saldo, limite));
					}
					case 2 -> {
						System.out.println("Digite o dia do aniversario da Conta: ");
						aniversario = leia.nextInt();

						contas.atualizar(new ContaPoupanca(numero, agencia, tipo, titular, saldo, aniversario));
						
						
					}
					default -> {
						System.out.println("Tipo de conta Invalido!");
					}

					}
				}else {
					System.out.println("A conta não foi encontrada!");
				}
				
				KeyPress();
				break;

			case 5:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Apagar a Conta\n\n");

				System.out.println("Digite o numero da conta: ");
				numero = leia.nextInt();
				
				contas.deletar(numero);
				
				KeyPress();
				break;

			case 6:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Saque");

				System.out.println("Digite o numero da conta: ");
				numero = leia.nextInt();
				
				do {
					System.out.println("Digite o valor do saque (R$): ");
					valor = leia.nextFloat();
				}while (valor <=0); 
					
				contas.sacar(numero, valor);
							
				KeyPress();
				break;

			case 7:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Deposito\n\n");

				System.out.println("Digite o numero da conta: ");
				numero = leia.nextInt();
				
				do {
					System.out.println("Digite o valor do Deposito (R$): ");
					valor = leia.nextFloat();
				}while (valor <= 0);
				
				contas.depositar(numero, valor);
				
				KeyPress();
				break;

			case 8:
				System.out.println(Cores.TEXT_WHITE_BOLD + "Tranferências Entre  Contas\n\n");

				System.out.println("Digite o numero da conta de origem: ");
				numero = leia.nextInt();
				
				System.out.println("Digite o numero da conta de destino: ");
				numeroDestino = leia.nextInt();
				
				do {
					System.out.println("Digite o valor da transferencia (R$): ");
					valor = leia.nextFloat();
				}while (valor <= 0);
				
				contas.transferir(numero, numeroDestino, valor);
				
				KeyPress();
				break;

			default:
				System.out.println(Cores.TEXT_RED_BOLD + "Opcão Invalida" + Cores.TEXT_RESET);

				KeyPress();
				break;
			}

		}

	}

	public static void sobre() {
		System.out.println("\n*************************************************");
		System.out.println("Projeto desenvolvido por: Maria de Fatima");
		System.out.println("fatimaolliveira428@gmail.com");
		System.out.println("https://github.com/fatima330");
		System.out.println("\n*************************************************");

	}

	public static void KeyPress() {

		try {

			System.out.println(Cores.TEXT_RESET + "\n\nPressione Enter para Continuar...");
			System.in.read();

		} catch (IOException e) {

			System.out.println("Você pressionou uma tecla diferente de enter!");
		}

	}
}
