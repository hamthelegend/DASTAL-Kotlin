package module04

/*
To run this program in command line, run these two commands:
kotlinc -include-runtime -d mod04-activity02.jar src/module04/Activity02.kt src/module04/Expression.kt src/extensions/stack/Stack.kt
java -jar mod04-activity02.jar
Note: make sure you have the latest Kotlin compiler and JRE installed
*/

fun main() {
    println("Separate individual integers with a space. ")
    println("Enter /exit to quit the program. ")
    while (true) {
        println()
        println("Please enter your postfix expression: ")
        val input = readLine()!!
        if (input == "/exit") break
        println(input.toExpression().evaluatePostfix())
    }
}