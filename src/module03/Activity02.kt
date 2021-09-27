package module03

import extensions.doublylinkedlist.doublyLinkedListOf

/*
To run this program in command line, run these two commands:
kotlinc -include-runtime -d mod03-activity02.jar src/module03/Activity02.kt src/extensions/doublylinkedlist/DoublyLinkedList.kt
java -jar mod03-activity02.jar
Note: make sure you have the latest Kotlin compiler and JRE installed
*/

fun main() {
    val linkedList = doublyLinkedListOf<String>()
    println("Here is your empty linked list of strings: $linkedList")
    println("Note: you could always enter \"/exit\" to quit the program.\n")
    while (true) {
        print("Enter a string to add to the start of your linked list: ")
        val input = readLine()!!
        if (input != "/exit") linkedList.add(0, input) else break
        println("Here is your new linked list: $linkedList\n")
    }
    println("\nHere is your final linked list: $linkedList")
    println("Goodbye, now.")
}