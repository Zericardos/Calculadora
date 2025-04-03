package com.ze;

import lombok.extern.slf4j.Slf4j;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;

/**
 * A classe {@code Calculadora} fornece métodos para realizar operações aritméticas
 * (soma, subtração, multiplicação e divisão) de forma precisa, utilizando {@link BigDecimal}
 * para preservar a precisão dos números. Os métodos aceitam argumentos de tipos variados
 * (por exemplo, {@code Number}, {@code String} numérica ou {@code Character}).
 *
 * <p>Além disso, a classe realiza validações para garantir que os argumentos fornecidos sejam válidos
 * e ajusta a precisão dos resultados conforme necessário para cada operação.
 *
 * <p>Esta classe é stateless e utiliza SLF4J para registro de log.
 *
 * @author José Ricardo Silveira
 * @version 1.0
 */
@Slf4j
public class Calculadora {

    /**
     * Valida que pelo menos dois argumentos foram fornecidos e que nenhum deles é nulo.
     *
     * @param operacao o nome da operação a ser realizada (usado na mensagem de erro)
     * @param args os argumentos a serem validados
     * @throws IllegalArgumentException se nenhum ou menos de dois argumentos forem fornecidos,
     *         ou se algum dos argumentos for nulo
     */
    private void validarNumeros(String operacao, Object... args) {
        if (args == null || args.length < 2) {
            throw new IllegalArgumentException("Pelo menos dois argumentos devem ser fornecidos para a " + operacao);
        }
        if (Arrays.stream(args).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Nenhum argumento pode ser nulo");
        }
    }

    /**
     * Converte um único argumento para {@link BigDecimal}.
     * <p>
     * Se o argumento for uma instância de {@code Number}, {@code String} ou {@code Character},
     * ele será convertido para {@code BigDecimal} utilizando sua representação textual.
     *
     * @param arg o argumento a ser convertido
     * @return o valor convertido para {@code BigDecimal}
     * @throws IllegalArgumentException se o argumento não for numérico ou não puder ser convertido
     */
    private BigDecimal converterParaBigDecimal(Object arg) {
        if (arg instanceof Number) {
            // Usa toString para preservar a representação textual e evitar imprecisão de double
            return new BigDecimal(arg.toString());
        } else if (arg instanceof String string) {
            try {
                return new BigDecimal(string);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("String não numérica: " + arg);
            }
        } else if (arg instanceof Character) {
            try {
                return new BigDecimal(arg.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Character não numérico: " + arg);
            }
        } else {
            throw new IllegalArgumentException("Tipo inválido: " + arg.getClass().getName());
        }
    }

    /**
     * Converte um conjunto de argumentos para um array de {@link BigDecimal}.
     *
     * @param args os argumentos a serem convertidos
     * @return um array de {@code BigDecimal} representando os valores convertidos
     */
    private BigDecimal[] converterParaBigDecimals(Object... args) {
        return Arrays.stream(args)
            .map(this::converterParaBigDecimal)
            .toArray(BigDecimal[]::new);
    }

    /**
     * Retorna a escala máxima (número de casas decimais) encontrada entre os valores do array.
     *
     * @param bdArray o array de {@code BigDecimal}
     * @return a escala máxima, ou 0 se o array estiver vazio
     */
    private int getMaxScale(BigDecimal[] bdArray) {
        return Arrays.stream(bdArray)
            .mapToInt(BigDecimal::scale)
            .max()
            .orElse(0);
    }

    /**
     * Ajusta a precisão de um valor {@link BigDecimal} para a escala especificada,
     * utilizando arredondamento do tipo {@link RoundingMode#HALF_UP}.
     *
     * @param resultado o {@code BigDecimal} cujo valor será ajustado
     * @param escalaMaxima a escala desejada
     * @return o {@code BigDecimal} com a precisão ajustada
     */
    private BigDecimal ajustarPrecisao(BigDecimal resultado, int escalaMaxima) {
        return resultado.setScale(escalaMaxima, RoundingMode.HALF_UP);
    }

    /**
     * Realiza a soma de uma lista de argumentos numéricos.
     * <p>
     * Os argumentos podem ser de tipos variados (por exemplo, {@code Number}, {@code String} numérica ou {@code Character}).
     * A precisão do resultado é ajustada para a escala máxima dos argumentos.
     *
     * @param args os argumentos a serem somados
     * @return o resultado da soma como {@code double}
     * @throws IllegalArgumentException se os argumentos forem inválidos ou insuficientes
     */
    public double somar(Object... args) {
        validarNumeros("soma", args);
        BigDecimal[] bdValues = converterParaBigDecimals(args);
        int escalaMaxima = getMaxScale(bdValues);
        BigDecimal soma = Arrays.stream(bdValues)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        soma = ajustarPrecisao(soma, escalaMaxima);
        log.info("Somando {} = {}", Arrays.toString(args), soma);
        return soma.doubleValue();
    }

    /**
     * Realiza a subtração sequencial dos argumentos.
     * <p>
     * O primeiro argumento é considerado o minuendo, e os demais são subtraídos dele.
     *
     * @param args os argumentos para a subtração, onde o primeiro é o minuendo
     *             e os demais são subtraendos
     * @return o resultado da subtração como {@code double}
     * @throws IllegalArgumentException se os argumentos forem inválidos ou insuficientes
     */
    public double subtrair(Object... args) {
        validarNumeros("subtração", args);
        // O primeiro argumento é o minuendo
        BigDecimal bdPrimeiro = converterParaBigDecimal(args[0]);
        // Os demais argumentos são os subtraendos
        BigDecimal[] bdOutros = Arrays.stream(args, 1, args.length)
            .map(this::converterParaBigDecimal)
            .toArray(BigDecimal[]::new);
        int escalaMaxima = Math.max(bdPrimeiro.scale(), getMaxScale(bdOutros));
        BigDecimal somaOutros = Arrays.stream(bdOutros)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal resultado = bdPrimeiro.subtract(somaOutros);
        resultado = ajustarPrecisao(resultado, escalaMaxima);
        log.info("Subtraindo {} - ({}) = {}", args[0],
            Arrays.toString(Arrays.copyOfRange(args, 1, args.length)), resultado);
        return resultado.doubleValue();
    }

    /**
     * Realiza a multiplicação de uma lista de argumentos numéricos.
     * <p>
     * A precisão do resultado é determinada pela soma das escalas dos fatores.
     *
     * @param args os argumentos a serem multiplicados
     * @return o resultado da multiplicação como {@code double}
     * @throws IllegalArgumentException se os argumentos forem inválidos ou insuficientes
     */
    public double multiplicar(Object... args) {
        validarNumeros("multiplicação", args);
        // Converte os argumentos para BigDecimal
        BigDecimal[] bdValues = converterParaBigDecimals(args);
        // Calcula a soma das escalas de cada fator
        int escalaTotal = Arrays.stream(bdValues)
            .mapToInt(BigDecimal::scale)
            .sum();
        // Multiplica todos os números (produto exato)
        BigDecimal produto = Arrays.stream(bdValues)
            .reduce(BigDecimal.ONE, BigDecimal::multiply);
        // Ajusta a escala do resultado para a soma das escalas dos fatores
        produto = produto.setScale(escalaTotal, RoundingMode.HALF_UP);
        log.info("Multiplicando {} = {}", Arrays.toString(args), produto);
        return produto.doubleValue();
    }

    /**
     * Realiza a divisão sequencial dos argumentos numéricos.
     * <p>
     * O primeiro argumento é considerado o dividendo, e os demais são divisores.
     * Caso algum divisor seja zero, lança {@link ArithmeticException}.
     * A precisão do resultado é ajustada para a escala máxima dos argumentos.
     *
     * @param args os argumentos para a divisão, onde o primeiro é o dividendo
     *             e os demais são divisores
     * @return o resultado da divisão como {@code double}
     * @throws IllegalArgumentException se os argumentos forem inválidos ou insuficientes
     * @throws ArithmeticException se ocorrer divisão por zero
     */
    public double dividir(Object... args) {
        validarNumeros("divisão", args);
        BigDecimal bdPrimeiro = converterParaBigDecimal(args[0]);
        BigDecimal[] bdOutros = Arrays.stream(args, 1, args.length)
            .map(this::converterParaBigDecimal)
            .toArray(BigDecimal[]::new);
        int escalaMaxima = Math.max(bdPrimeiro.scale(), getMaxScale(bdOutros));
        BigDecimal resultado = bdPrimeiro;
        for (BigDecimal divisor : bdOutros) {
            if (divisor.compareTo(BigDecimal.ZERO) == 0) {
                log.error("Tentativa de divisão por zero com divisor {}", divisor);
                throw new ArithmeticException("Divisão por zero não permitida");
            }
            // Para divisão, especifica uma escala e modo de arredondamento
            resultado = resultado.divide(divisor, escalaMaxima + 2, RoundingMode.HALF_UP);
        }
        resultado = ajustarPrecisao(resultado, escalaMaxima);
        log.info("Dividindo {} / ({}) = {}", args[0],
            Arrays.toString(Arrays.copyOfRange(args, 1, args.length)), resultado);
        return resultado.doubleValue();
    }
}
