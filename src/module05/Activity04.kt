package module05

/*
To run this program in command line, run these two commands:
kotlinc -include-runtime -d mod05-activity04.jar src/module05/Activity04.kt src/extensions/doublylinkedlist/DoublyLinkedList.kt
java -jar mod05-activity04.jar
Note: make sure you have the latest Kotlin compiler and JRE installed.

Also, it uses BigInteger to be able to get long Fibonacci sequences, though the time complexity is exponential.
Tested to work quickly for up to n = 30.
*/

import extensions.doublylinkedlist.*
import java.math.BigInteger

fun main() {
    activity04()
}

fun activity04() {
    print("Print Fibonacci sequence from F0 to Fn. Enter n or \"exit\" to quit: ")
    val input = readLine()!!
    if (input == "exit") {
        println("Bye bye!")
        return
    } else {
        println("Here is your Fibonacci sequence ${getFibonacciSequence(input.toInt())}\n")
        activity04()
    }
}

fun getFibonacciSequence(n: Int, sequence: DoublyLinkedList<BigInteger> = doublyLinkedListOf()): DoublyLinkedList<BigInteger> {
    return if (n <= 0) {
        sequence.add(0, BigInteger.ZERO)
        sequence
    } else {
        sequence.add(0, getFibonacciNumber(BigInteger(n.toString())))
        getFibonacciSequence(n - 1, sequence)
    }
}

fun getFibonacciNumber(n: BigInteger): BigInteger =
    if (n <= BigInteger.ONE) n else getFibonacciNumber(n - BigInteger.ONE) + getFibonacciNumber(n - BigInteger.TWO)