package module05

import extensions.queue.queueOf

fun main() {

    println("Instructions: \n")
    println("To enqueue, type 'e' and the string you want to enqueue separated by a space.")
    println("Example: \"e the quick brown fox\"\n")
    println("To dequeue, just type 'd'")
    println("Example: \"d\"\n")
    println("To exit, just type \"exit\"\n")

    val queue = queueOf<String>()

    while (true) {
        print("Enter your command: ")
        val input = readLine()!!
        when {
            input matches Regex("e .*") -> {
                val element = input.substringAfter(' ')
                queue.enqueue(element)
                println("\"$element\" enqueued.")
                println("Queue: $queue\n")
            }
            input matches Regex("d") -> {
                val element = queue.dequeue()
                println("\"$element\" dequeued.")
                println("Queue: $queue\n")
            }
            input matches Regex("exit") -> break
            else -> println("Unknown command. Please try again.\n")
        }
    }

}