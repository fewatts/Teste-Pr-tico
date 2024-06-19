import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    private static List<Funcionario> funcionarios = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("\nInserindo Funcionários: 3.1");
        adicionarFuncionarios();

        System.out.println("\nRemovendo João: 3.2");
        removerFuncionario("João");

        System.out.println("\nImprimindo todos os funcionários: 3.3");
        imprimirTodosOsFuncionarios();

        System.out.println("\nAtualizando a lista com novo aumento de 10%: 3.4");
        darDezPorCentoDeAumentoNoSalario();
        imprimirTodosOsFuncionarios();

        System.out.println("\nAgrupar os funcionários por função em um MAP: 3.5");
        var funcionariosPorFuncao = agruparFuncionariosPorFuncao();

        System.out.println("\nImprimir os funcionários, agrupados por função: 3.6");
        imprimirFuncionariosAgrupadosPorFuncao(funcionariosPorFuncao);

        System.out.println("\nImprimir funcionários que fazem aniversário nos meses 10 e 12: 3.8");
        imprimirFuncionariosAniversariantes(10, 12);

        System.out.println("\nImprimir o funcionário com a maior idade: 3.9");
        imprimirFuncionarioMaisVelho();

        System.out.println("\nFuncionários em ordem alfabética: 3.10");
        imprimirFuncionariosOrdemAlfabetica();

        System.out.println("\nImprimir o total dos salários dos funcionários: 3.11");
        imprimirTotalSalarios();

        System.out.println("\nImprimir quantos salários mínimos ganha cada funcionário: 3.12");
        imprimirSalariosMinimosPorFuncionario();
    }

    private static void adicionarFuncionarios() {
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios
                .add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios
                .add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));
    }

    private static void removerFuncionario(String nome) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals(nome));
    }

    private static void imprimirTodosOsFuncionarios() {
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var decimalFormat = new DecimalFormat("#,##0.00");

        for (var funcionario : funcionarios) {
            var dataNascimento = funcionario.getDataNascimento().format(formatter);
            var salario = decimalFormat.format(funcionario.getSalario());
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Nome: " + funcionario.getNome() +
                    "\nData de Nascimento: " + dataNascimento +
                    "\nSalário: " + salario +
                    "\nFunção: " + funcionario.getFuncao());
            System.out.println("--------------------------------------------------------------------");
        }

    }

    private static void darDezPorCentoDeAumentoNoSalario() {
        for (var funcionario : funcionarios) {
            var novoSalario = funcionario.getSalario().multiply(new BigDecimal("1.10"));
            funcionario.setSalario(novoSalario);
        }

    }

    private static Map<String, List<Funcionario>> agruparFuncionariosPorFuncao() {
        return funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));
    }

    private static void imprimirFuncionariosAgrupadosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        for (var funcao : funcionariosPorFuncao.keySet()) {
            System.out.println("--------------------------------------------------------------------");
            System.out.println("Função: " + funcao);
            for (var funcionario : funcionariosPorFuncao.get(funcao)) {
                System.out.println("-  " + funcionario.getNome());
            }
            System.out.println("--------------------------------------------------------------------");
        }

    }

    private static void imprimirFuncionariosAniversariantes(int... meses) {
        System.out.println("--------------------------------------------------------------------");
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (var funcionario : funcionarios) {
            var mes = funcionario.getDataNascimento().getMonthValue();
            for (var mesProcurado : meses) {
                if (mes == mesProcurado) {
                    System.out.println("\nNome: " + funcionario.getNome() +
                            "\nData de Nascimento: " + funcionario.getDataNascimento().format(formatter));
                }

            }

        }
        System.out.println("--------------------------------------------------------------------");
    }

    private static void imprimirFuncionarioMaisVelho() {
        System.out.println("--------------------------------------------------------------------");
        var maisVelho = funcionarios.stream()
                .min((f1, f2) -> f1.getDataNascimento().compareTo(f2.getDataNascimento()))
                .orElse(null);
        if (maisVelho != null) {
            var idade = LocalDate.now().getYear() - maisVelho.getDataNascimento().getYear();
            System.out.println("Funcionário com a maior idade: \nNome: " +
                    maisVelho.getNome() + "\nIdade: " + idade);
        }
        System.out.println("--------------------------------------------------------------------");
    }

    private static void imprimirFuncionariosOrdemAlfabetica() {
        System.out.println("--------------------------------------------------------------------");
        var funcionariosOrdenados = funcionarios.stream()
                .sorted((f1, f2) -> f1.getNome().compareTo(f2.getNome()))
                .collect(Collectors.toList());
        for (var funcionario : funcionariosOrdenados) {
            System.out.println(funcionario.getNome());
        }
        System.out.println("--------------------------------------------------------------------");
    }

    private static void imprimirTotalSalarios() {
        System.out.println("--------------------------------------------------------------------");
        var decimalFormat = new DecimalFormat("#,##0.00");
        var totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total dos salários: " + decimalFormat.format(totalSalarios));
        System.out.println("--------------------------------------------------------------------");
    }

    private static void imprimirSalariosMinimosPorFuncionario() {
        System.out.println("--------------------------------------------------------------------");
        var decimalFormat = new DecimalFormat("#,##0.00");
        var salarioMinimo = new BigDecimal("1212.00");
        System.out.println("Quantidade de salários mínimos por funcionário:");
        for (var funcionario : funcionarios) {
            var quantidadeSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println("\nNome: " + funcionario.getNome() +
                    "\nSalários Mínimos: " + decimalFormat.format(quantidadeSalariosMinimos));
        }
        System.out.println("--------------------------------------------------------------------");
    }

}
