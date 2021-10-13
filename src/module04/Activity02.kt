package module04

fun main() {
    println("Separate individual integers with a space. ")
    println("Enter /exit to quit the program. ")
    while (true) {
        println()
        println("Please enter your postfix expression: ")
        val input = readLine()!!
        if (input == "/exit") break
        println(input.splitToSymbols().evaluatePostfix())
    }
}