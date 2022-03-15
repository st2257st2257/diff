package new

import space.kscience.kmath.complex.Complex
import space.kscience.kmath.complex.ComplexField
import jdk.internal.vm.vector.VectorSupport.test
import space.kscience.kmath.expressions.interpret
import space.kscience.kmath.operations.Algebra
import space.kscience.kmath.operations.DoubleField
import kotlin.test.Test
//import kotlin.1qtest.assertEquals

internal class TestParser {
    @Test
    fun evaluateParsedMst() {
        val mst = "2+2*(2+2)".parseMath()
        val res = mst.interpret(ComplexField)
        assertEquals(Complex(10.0, 0.0), res)
    }

    @Test
    fun evaluateMstSymbol() {
        val mst = "i".parseMath()
        val res = mst.interpret(ComplexField)
        assertEquals(ComplexField.i, res)
    }


    @Test
    fun evaluateMstUnary() {
        val mst = "sin(0)".parseMath()
        val res = mst.interpret(DoubleField)
        assertEquals(0.0, res)
    }

    @Test
    fun evaluateMstBinary() {
        val magicalAlgebra = object : Algebra<String> {
            override fun bindSymbolOrNull(value: String): String = value

            override fun unaryOperationFunction(operation: String): (arg: String) -> String {
                throw NotImplementedError()
            }

            override fun binaryOperationFunction(operation: String): (left: String, right: String) -> String =
                when (operation) {
                    "magic" -> { left, right -> "$left ★ $right" }
                    else -> throw NotImplementedError()
                }
        }

        val mst = "magic(a, b)".parseMath()
        val res = mst.interpret(magicalAlgebra)
        assertEquals("a ★ b", res)
    }
}