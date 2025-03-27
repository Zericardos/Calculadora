package com.ze;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class Calculadora {
    private void validarNumeros(String operacao, Object... args) {
        if (args == null || args.length < 2) {
            throw new IllegalArgumentException("Pelo menos dois argumentos devem ser fornecidos para a " + operacao);
        }
        if (Arrays.stream(args).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Nenhum argumento pode ser nulo");
        }
    }

    // Converte um único argumento para BigDecimal
    private BigDecimal converterParaBigDecimal(Object arg) {
        if (arg instanceof Number) {
            // Usamos o toString para preservar a representação textual, evitando imprecisões do double
            return new BigDecimal(arg.toString());
        } else if (arg instanceof String string) {
            try {
                return new BigDecimal(string);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("String não numérica: " + arg);
            }
        }

        else if (arg instanceof Character) {
            try {
                return new BigDecimal(arg.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Character não numérico: " + arg);
            }
            // Character é apenas um caractere, logo '1' é válido, mas "12" não é possível com char.
            // Se for um Character, converte para String e tenta parsear.
        } else {
            throw new IllegalArgumentException("Tipo inválido: " + arg.getClass().getName());
        }
    }

    // Converte um conjunto de argumentos para um array de BigDecimal
    private BigDecimal[] converterParaBigDecimals(Object... args) {
        return Arrays.stream(args)
                .map(this::converterParaBigDecimal)
                .toArray(BigDecimal[]::new);
    }

    // Retorna a escala máxima (número de casas decimais) entre os BigDecimals
    private int getMaxScale(BigDecimal[] bdArray) {
        return Arrays.stream(bdArray)
                .mapToInt(BigDecimal::scale)
                .max()
                .orElse(0);
    }

    // Método genérico para ajustar o resultado à precisão (escala) máxima dos argumentos
    private BigDecimal ajustarPrecisao(BigDecimal resultado, int escalaMaxima) {
        return resultado.setScale(escalaMaxima, RoundingMode.HALF_UP);
    }

    // Operação de soma usando BigDecimal
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
        log.info("Subtraindo {} - ({}) = {}", args[0], Arrays.toString(Arrays.copyOfRange(args, 1, args.length)), resultado);
        return resultado.doubleValue();
    }


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
            // Para divisão, precisamos especificar uma escala e modo de arredondamento.
            resultado = resultado.divide(divisor, escalaMaxima + 2, RoundingMode.HALF_UP);
        }
        resultado = ajustarPrecisao(resultado, escalaMaxima);
        log.info("Dividindo {} / ({}) = {}", args[0], Arrays.toString(Arrays.copyOfRange(args, 1, args.length)), resultado);
        return resultado.doubleValue();
    }

}
