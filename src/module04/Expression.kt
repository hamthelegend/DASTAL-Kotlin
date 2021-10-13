package module04

import extensions.stack.linkedStackOf

abstract class Symbol(val value: String) {
    override fun toString() = value
}
class OpeningParenthesis: Symbol("(")
class ClosingParenthesis: Symbol(")")
class Operator(value: String, val precedence: Int): Symbol(value)
class Operand(value: String): Symbol(value)

fun String.toSymbol() = when (this) {
    "(" -> OpeningParenthesis()
    ")" -> ClosingParenthesis()
    "+", "-" -> Operator(this, 1)
    "*", "/", "%" -> Operator(this, 2)
    else -> Operand(this)
}

fun String.splitToSymbols(): List<Symbol> {
    val expression = replace(Regex("\\s+"), " ")
    var symbolBuilder = StringBuilder()
    val symbols = mutableListOf<Symbol>()
    for (char in expression) {
        if (char.isDigit()) {
            symbolBuilder.append(char)
        } else {
            if (symbolBuilder.isNotBlank()) {
                symbols.add(symbolBuilder.toString().toSymbol())
                symbolBuilder = StringBuilder()
            }
            when (char) {
                '+', '-', '*', '/', '%' -> symbols.add(char.toString().toSymbol())
            }
            if (char.toString() matches Regex("\\w")) {
                symbols.add(char.toString().toSymbol())
            }
        }
    }
    if (symbolBuilder.isNotBlank()) symbols.add(symbolBuilder.toString().toSymbol())
    return symbols
}

fun calculate(firstOperand: Operand, secondOperand: Operand, operator: Operator): Operand {
    val a = firstOperand.value.toInt()
    val b = secondOperand.value.toInt()
    return Operand (when (operator.value) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> a / b
        "%" -> a % b
        else -> throw IllegalArgumentException("Operator ${operator.value} is not recognized.")
    }.toString())
}

fun List<Symbol>.evaluatePostfix(): Int {
    val operandStack = linkedStackOf<Symbol>()
    for (symbol in this) {
        if (symbol is Operand) {
            operandStack.push(symbol)
        } else if (symbol is Operator) {
            val a = operandStack.pop() as Operand
            val b = operandStack.pop() as Operand
            val result = calculate(b, a, symbol)
            operandStack.push(result)
        }
    }
    return if (operandStack.size == 1) operandStack.pop().value.toInt()
    else throw IllegalArgumentException("Invalid number of operands/operators.")
}