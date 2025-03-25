package com.ze;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;

class CalculadoraTest {

    private Calculadora calc;

    @BeforeEach
    void setUp() {
        calc = new Calculadora();
    }

    @Test
    void testSomarNeutroInteiro() {
        assertEquals(2.0, calc.somar(2, 0), "2 + 0 deveria ser 2.0");
    }

    @Test
    void testSomarInteirosPositivos() {
        assertEquals(5, calc.somar(2, 3), "2 + 3 deveria ser 5");
    }

    @Test
    void testSomarInteirosComutados() {
        assertEquals(5, calc.somar(3, 2), "3+ 2 deveria ser 5");
    }

    @Test
    void testSomarInteirosNegativos() {
        assertEquals(-5, calc.somar(-2, -3), "-2 + (-3) deveria ser -5");
    }

    @Test
    void testSomarTresInteiros() {
        assertEquals(-5, calc.somar(-2, -3, 0), "-2 + (-3) + 0 deveria ser -5");
    }

    @Test
    void testErroSomarNada() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calc.somar();
        });
        assertEquals("Pelo menos dois argumentos devem ser fornecidos para a soma", exception.getMessage());
    }

    @Test
    void testSomarCharacter() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calc.somar('f', 0);
        });
        assertEquals("Character não numérico: f", exception.getMessage());
    }

    @Test
    void testSomarNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calc.somar('0', null);
        });
        assertEquals("Nenhum argumento pode ser nulo", exception.getMessage());
    }

    @Test
    void testSomarNeutroFloat() {
        assertEquals(2.0, calc.somar(2.00000, 0.000), "2.0000 + 0.000 deveria ser 2.0000");
    }

    @Test
    void testSomarInteirosFloat() {
        assertEquals(5.6, calc.somar(2.3, 3.3), "2.3 + 3.3 deveria ser 5.6");
    }

    @Test
    void testSomarFloatComutados() {
        assertEquals(5.6, calc.somar(3.3, 2.3), "3.3 + 2.3 deveria ser 5.6");
    }

    @Test
    void testSomarFloatNegativos() {
        assertEquals(-5.6, calc.somar(-2.3, -3.3), "-2.3 + (-3.3) deveria ser -5.6");
    }

    @Test
    void testSomarTresFloats() {
        assertEquals(-4.7, calc.somar(-2.3, -3.3, 0.9), "-2.3 + (-3.3) + 0.9 deveria ser -4.7");
    }

    @Test
    void testSubtrairNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calc.subtrair('0', null);
        });
        assertEquals("Nenhum argumento pode ser nulo", exception.getMessage());
    }

    @Test
    void testSubtrairNeutroInteiro() {
        assertEquals(2.0, calc.subtrair(2, 0), "2 - 0 deveria ser 2.0");
    }

    @Test
    void testSubtrairInteirosPositivos() {
        assertEquals(-1, calc.subtrair(2, 3), "2 - 3 deveria ser -1");
    }

    @Test
    void testSubtrairInteirosComutados() {
        assertEquals(1, calc.subtrair(3, 2), "3- 2 deveria ser 1");
    }

    @Test
    void testSubtrairInteirosNegativos() {
        assertEquals(1, calc.subtrair(-2, -3), "-2 - (-3) deveria ser 1");
    }

    @Test
    void testSubtrairTresInteiros() {
        assertEquals(1, calc.subtrair(-2, -3, 0), "-2 - (-3) - 0 deveria ser 1");
    }

    @Test
    void testErroSubtrairNada() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calc.subtrair();
        });
        assertEquals("Pelo menos dois argumentos devem ser fornecidos para a subtração", exception.getMessage());
    }

    @Test
    void testSubtrairNeutroFloat() {
        assertEquals(2.0, calc.subtrair(2.00000, 0.000), "2.0000 - 0.000 deveria ser 2.0000");
    }

    @Test
    void testSubtrairInteirosFloat() {
        assertEquals(-1.0, calc.subtrair(2.3, 3.3), "2.3 - 3.3 deveria ser -1.0");
    }

    @Test
    void testSubtrairFloatComutados() {
        assertEquals(1.0, calc.subtrair(3.3, 2.3), "3.3 - 2.3 deveria ser 1.0");
    }

    @Test
    void testSubtrairFloatNegativos() {
        assertEquals(1, calc.subtrair(-2.3, -3.3), "-2.3 - (-3.3) deveria ser 1");
    }

    @Test
    void testSubtrairTresFloats() {
        assertEquals(0.1, calc.subtrair(-2.3, -3.3, 0.9), "-2.3 - (-3.3) - 0.9 deveria ser 0.1");
    }

    @Test
    void testMultiplicarNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calc.multiplicar('0', null);
        });
        assertEquals("Nenhum argumento pode ser nulo", exception.getMessage());
    }

    @Test
    void testMultiplicarNuloInteiro() {
        assertEquals(0, calc.multiplicar(2, 0), "2 * 0 deveria ser 0");
    }

    @Test
    void testMultiplicarNeutroInteiro() {
        assertEquals(2.0, calc.multiplicar(2, 1), "2 * 1 deveria ser 2.0");
    }

    @Test
    void testMultiplicarInteirosPositivos() {
        assertEquals(6, calc.multiplicar(2, 3), "2 * 3 deveria ser 6");
    }

    @Test
    void testMultiplicarInteirosComutados() {
        assertEquals(6, calc.multiplicar(3, 2), "3 * 2 deveria ser 6");
    }

    @Test
    void testMultiplicarInteirosNegativos() {
        assertEquals(6, calc.multiplicar(-2, -3), "-2 * (-3) deveria ser 6");
    }

    @Test
    void testMultiplicarTresInteirosNulo() {
        assertEquals(0, calc.multiplicar(-2, -3, 0), "-2 * (-3) * 0 deveria ser 0");
    }

    @Test
    void testMultiplicarTresInteiros() {
        assertEquals(3, calc.multiplicar(-2, -3, 0.5), "-2 * (-3) * 0.5 deveria ser 3");
    }

    @Test
    void testErroMultiplicarNada() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calc.multiplicar();
        });
        assertEquals("Pelo menos dois argumentos devem ser fornecidos para a multiplicação", exception.getMessage());
    }

    @Test
    void testMultiplicarNeutroFloat() {
        assertEquals(0.0, calc.multiplicar(2.00000, 0.000), "2.0000 * 0.000 deveria ser 0");
    }

    @Test
    void testMultiplicarInteirosFloat() {
        assertEquals(7.59, calc.multiplicar(2.3, 3.3), "2.3 * 3.3 deveria ser 7.59");
    }

    @Test
    void testMultiplicarFloatComutados() {
        assertEquals(7.59, calc.multiplicar(3.3, 2.3), "3.3 * 2.3 deveria ser 7.59");
    }

    @Test
    void testMultiplicarFloatNegativos() {
        assertEquals(7.59, calc.multiplicar(-2.3, -3.3), "-2.3 * (-3.3) deveria ser 7.59");
    }

    @Test
    void testMultiplicarTresFloats() {
        assertEquals(6.831, calc.multiplicar(-2.3, -3.3, 0.9), "-2.3 * (-3.3) * 0.9 deveria ser 6.831");
    }

    @Test
    void testDividirNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calc.dividir('0', null);
        });
        assertEquals("Nenhum argumento pode ser nulo", exception.getMessage());
    }

    @Test
    void testDividirNuloInteiro() {
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            calc.dividir(2, 0);
        });
        assertEquals("Divisão por zero não permitida", exception.getMessage());
    }

    @Test
    void testDividirNeutroInteiro() {
        assertEquals(2.0, calc.dividir(2, 1), "2 / 1 deveria ser 2.0");
    }

    @Test
    void testDividirInteirosPositivos() {
        assertEquals(1, calc.dividir(2, 3), "2 / 3 deveria ser 1");
    }

    @Test
    void testDividirInteirosComutados() {
        assertEquals(2, calc.dividir(3, 2), "3 / 2 deveria ser 6");
    }

    @Test
    void testDividirInteirosNegativos() {
        assertEquals(1.0, calc.dividir(-2, -3), "-2 / (-3) deveria ser 1.0");
    }

    @Test
    void testDividirTresInteirosNulo() {
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            calc.dividir(-2, -3, 0);
        });
        assertEquals("Divisão por zero não permitida", exception.getMessage());
    }

    @Test
    void testDividirTresInteiros() {
        assertEquals(1.3, calc.dividir(-2, -3, 0.5), "-2 / (-3) / 0.5 deveria ser 1.3");
    }

    @Test
    void testErroDividirNada() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            calc.dividir();
        });
        assertEquals("Pelo menos dois argumentos devem ser fornecidos para a divisão", exception.getMessage());
    }

    @Test
    void testDividirNuloFloat() {
        ArithmeticException exception = assertThrows(ArithmeticException.class, () -> {
            calc.dividir(2.00000, 0.000);
        });
        assertEquals("Divisão por zero não permitida", exception.getMessage());
    }

    @Test
    void testDividirFloat() {
        assertEquals(0.7, calc.dividir(2.3, 3.3), "2.3 / 3.3 deveria ser 0.7");
    }

    @Test
    void testDividirFloatComutados() {
        assertEquals(1.4, calc.dividir(3.3, 2.3), "3.3 / 2.3 deveria ser 1.4");
    }

    @Test
    void testDividirFloatNegativos() {
        assertEquals(0.7, calc.dividir(-2.3, -3.3), "-2.3 / (-3.3) deveria ser 0.7");
    }

    @Test
    void testDividirTresFloats() {
        assertEquals(7.74, calc.dividir(-2.3, -3.3, 0.09), "-2.3 / (-3.3) / 0.9 deveria ser 7.74");
    }
}
