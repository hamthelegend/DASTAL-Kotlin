package module05

/*
To run this program in command line, run these two commands:
kotlinc -include-runtime -d mod05-activity03.jar src/module05/Activity03.kt src/extensions/Extensions.kt
java -jar mod05-activity03.jar
Note: make sure you have the latest Kotlin compiler and JRE installed.
*/

import extensions.factorial

fun main() {
    println("Enter \"exit\" to quit the program.\n")
    while (true) {
        print("Get the factorial of: ")
        val input = readLine()!!
        if (input == "exit") {
            break
        } else {
            try {
                println("$input! = ${input.toBigInteger().factorial()}")
            } catch (e: IllegalArgumentException) {
                println("You can only get the factorial of a positive integer.")
            } catch (e: NumberFormatException) {
                println("You can only get the factorial of a positive integer.")
            }
            println()
        }
    }
    println("Bye bye!")
}