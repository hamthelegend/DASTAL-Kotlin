package module05

/*
To run this program in command line, run these two commands:
kotlinc -include-runtime -d mod05-activity02.jar src/module05/Activity02.kt src/extensions/doublylinkedlist/DoublyLinkedList.kt src/extensions/queue/Queue.kt
java -jar mod05-activity02.jar
Note: make sure you have the latest Kotlin compiler and JRE installed
*/

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