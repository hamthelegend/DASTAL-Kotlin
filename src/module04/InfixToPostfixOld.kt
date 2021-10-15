package module04

import extensions.stack.EmptyStackException
import extensions.stack.linkedStackOf

enum class Type {
    OPERATOR,
    OPERAND,
    OPENING_PARENTHESIS,
    CLOSING_PARENTHESIS
}

val Char.precedence: Int
    get() {
        return when (this) {
            '+', '-' -> 1
            '*', '/', '%' -> 2
            else -> 0
        }
    }

val Char.type: Type
    get() {
        return when (this) {
            '+', '-', '*', '/', '%' -> Type.OPERATOR
            '(' -> Type.OPENING_PARENTHESIS
            ')' -> Type.CLOSING_PARENTHESIS
            else -> Type.OPERAND
        }
    }

fun String.toPostfix(): String {
    val infixExpression = replace(Regex("\\s*"), "")
    val operatorStack = linkedStackOf<Char>()
    val postfixExpressionBuilder = StringBuilder("")
    for (symbol in infixExpression) {
        when (symbol.type) {
            Type.OPERAND -> postfixExpressionBuilder.append(symbol)
            Type.OPENING_PARENTHESIS -> operatorStack.push(symbol)
            Type.CLOSING_PARENTHESIS -> {
                var operator = operatorStack.pop()
                while (operator.type != Type.OPENING_PARENTHESIS) {
                    postfixExpressionBuilder.append(operator)
                    operator = operatorStack.pop()
                }
            }
            Type.OPERATOR -> {
                try {
                    while (symbol.precedence <= operatorStack.check().precedence &&
                        operatorStack.check().type != Type.OPENING_PARENTHESIS
                    ) {
                        postfixExpressionBuilder.append(operatorStack.pop())
                    }
                } catch (e: EmptyStackException) {
                }
                operatorStack.push(symbol)
            }
        }
    }
    while (operatorStack.size > 0) {
        postfixExpressionBuilder.append(operatorStack.pop())
    }
    return postfixExpressionBuilder.toString()
}

fun String.toPrefix(): String {
    val reversedInfix = reversed().toMutableList()
    reversedInfix.replaceAll { symbol ->
        if (symbol.type == Type.OPENING_PARENTHESIS) {
            ')'
        } else if (symbol.type == Type.CLOSING_PARENTHESIS) {
            '('
        } else {
            symbol
        }

    }
    return reversedInfix.joinToString("").toPostfix().reversed()
}