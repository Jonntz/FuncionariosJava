import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Funcionario> funcionarios = new ArrayList<>();
        DateTimeFormatter formatarData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));

        // REMOVER O JOÃO DA LISTA
        funcionarios.removeIf(f -> f.getNome().equals("João"));

        // IMPRIMIR TODOS OS FUNCIONARIOS
        System.out.println("Funcionários: \n");
        for (Funcionario f : funcionarios) {
            System.out.println(f.getNome() + " | " + f.getDataNascimento().format(formatarData) + " | " +
                    String.format("%,.2f", f.getSalario()) + " | " + f.getFuncao());
        }

        // AUMENTO DE 10% NO SALARIO
        for (Funcionario f : funcionarios){
            BigDecimal novoSalario = f.getSalario().multiply(new BigDecimal("1.1"));
            f.setSalario(novoSalario);
        }

        // AGRUPAR FUNCIONARIOS VIA MAP E IMPRIMIR
        Map<String, List<Funcionario>> funcionarioPorFuncao = funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));
        System.out.println("\n Funcionarios por função: \n");

        for (Map.Entry<String, List<Funcionario>> entry : funcionarioPorFuncao.entrySet()) {
            System.out.println(entry.getKey() + ":");
            for (Funcionario f : entry.getValue()) {
                System.out.println("  " + f.getNome() + String.format("%,.2f", f.getSalario()));
            }
        }

        // FUNCIONARIOS QUE FAZEM ANIVERSARIO NOS MESES 10 e 12
        System.out.println("\nFuncionários que fazem aniversário nos meses 10 e 12:\n");
        for (Funcionario f : funcionarios) {
            int mes = f.getDataNascimento().getMonthValue();
            if (mes == 10 || mes == 12){
                System.out.println(f.getNome() + " | " + f.getDataNascimento().format(formatarData) + "|" + " | " + f.getFuncao());
            }
        }

        // FUNCIONARIO MAIS VELHO
        Funcionario funcionarioMaisVelho = funcionarios.stream().min(Comparator.comparing(Funcionario::getDataNascimento)).orElse(null);
        if (funcionarioMaisVelho != null) {
            int idade = Period.between(funcionarioMaisVelho.getDataNascimento(), LocalDate.now()).getYears();
            System.out.println("\nFuncionário com maior idade:\n");
            System.out.println("Nome:" + funcionarioMaisVelho.getNome() + " | Idade: " + idade);
        }

        // TODOS OS FUNCIONÁRIOS EM ORDEM ALFABÉTICA
        System.out.println("\nFuncionários em ordem alfabética:\n");
        funcionarios.stream().sorted(Comparator.comparing(Funcionario::getNome)).forEach(f -> {
            System.out.println(f.getNome() + " | " + f.getDataNascimento().format(formatarData) + " | " +
                    String.format("%,.2f", f.getSalario()) + " | " + f.getFuncao());
        });

        // IMPRIMIR TOTAL DE SALÁRIOS
        BigDecimal total = funcionarios.stream().map(Funcionario::getSalario).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("\nTotal dos salários dos funcionários: " + String.format("%,.2f", total));


        // IMPRIMIR QUANTOS SALÁRIOS MÍNIMO GANHAM CADA FUNCIONÁRIO
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nQuantidade de salários mínimos por funcionário: \n");
        for (Funcionario f : funcionarios) {
            BigDecimal salariosMinimos = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + " | " + salariosMinimos + " salários mínimos");
        }
    }
}