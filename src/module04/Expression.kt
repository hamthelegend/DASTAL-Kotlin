package module04

import extensions.stack.EmptyStackException
import extensions.stack.linkedStackOf

data class Expression(val symbols: List<Symbol>) {
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        for (symbol in symbols) {
            stringBuilder.append("${symbol.value} ")
        }
        return stringBuilder.toString()
    }
}

abstract class Symbol(val value: String) {
    override fun toString() = value
}

open class Operator(value: String, val precedence: Int): Symbol(value)
class OpeningParenthesis: Operator("(", 0)
class ClosingParenthesis: Operator(")", 0)
class Operand(value: String): Symbol(value)

fun String.toSymbol() = when (this) {
    "(" -> OpeningParenthesis()
    ")" -> ClosingParenthesis()
    "+", "-" -> Operator(this, 1)
    "*", "/", "%" -> Operator(this, 2)
    else -> Operand(this)
}

fun String.toExpression(): Expression {
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
    return Expression(symbols)
}

fun calculate(firstOperand: Operand, secondOperand: Operand, operator: Operator): Operand {
    val a = firstOperand.value.toDouble()
    val b = secondOperand.value.toDouble()
    return Operand (when (operator.value) {
        "+" -> a + b
        "-" -> a - b
        "*" -> a * b
        "/" -> a / b
        "%" -> a % b
        else -> throw IllegalArgumentException("Operator ${operator.value} is not recognized.")
    }.toString())
}

fun Expression.evaluatePostfix(): Double {
    val operandStack = linkedStackOf<Symbol>()
    for (symbol in symbols) {
        if (symbol is Operand) {
            operandStack.push(symbol)
        } else if (symbol is Operator) {
            val a = operandStack.pop() as Operand
            val b = operandStack.pop() as Operand
            val result = calculate(b, a, symbol)
            operandStack.push(result)
        }
    }
    return if (operandStack.size == 1) operandStack.pop().value.toDouble()
    else throw IllegalArgumentException("Invalid number of operands/operators.")
}

/*
Still broken.
 */

//fun Expression.infixToPostfix(): Expression {
//    val infixSymbols = symbols
//    val operatorStack = linkedStackOf<Operator>()
//    val postfixExpression = mutableListOf<Symbol>()
//    for (symbol in infixSymbols) {
//        when {
//            symbol is Operand -> postfixExpression.add(symbol)
//            symbol is OpeningParenthesis -> operatorStack.push(symbol)
//            symbol is ClosingParenthesis -> {
//                var operator = operatorStack.pop()
//                while (operator !is OpeningParenthesis) {
//                    postfixExpression.add(operator)
//                    operator = operatorStack.pop()
//                }
//            }
//            symbol is Operator -> {
//                try {
//                    while (symbol.precedence <= operatorStack.check().precedence &&
//                        operatorStack.check() !is OpeningParenthesis
//                    ) {
//                        postfixExpression.add(operatorStack.pop())
//                    }
//                } catch (e: EmptyStackException) { }
//                operatorStack.push(symbol)
//            }
//        }
//    }
//    while (operatorStack.size > 0) {
//        postfixExpression.add(operatorStack.pop())
//    }
//    return Expression(postfixExpression)
//}
//
//fun Expression.infixToPrefix(): Expression {
//    val reversedInfixSymbols = symbols.reversed().toMutableList()
//    reversedInfixSymbols.replaceAll {
//            operand -> if (operand is OpeningParenthesis) {
//        ClosingParenthesis()
//    } else if (operand is ClosingParenthesis) {
//        OpeningParenthesis()
//    } else operand
//    }
//    val postfixExpression = Expression(reversedInfixSymbols).infixToPostfix()
//    val prefixExpression = postfixExpression.symbols.reversed()
//    return Expression(prefixExpression)
//}