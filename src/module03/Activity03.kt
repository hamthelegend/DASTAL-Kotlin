package module03

/*
To run this program in command line, run these two commands:
kotlinc -include-runtime -d mod03-activity03.jar src/module03/Activity03.kt src/extensions/doublylinkedlist/DoublyLinkedList.kt src/extensions/Extensions.kt
java -jar mod03-activity03.jar
Note: make sure you have the latest Kotlin compiler and JRE installed
*/

import extensions.doublylinkedlist.doublyLinkedListOf
import extensions.find

val linkedList = doublyLinkedListOf<String>()

const val index = "\\d+"
const val element = "\".*\""

fun main() {
    printList()
    printCommands()
    while (true) {
        print("Enter your command: ")
        val command = readLine()!!
        try {
            when {
                command matches Regex("add $element") -> {
                    val element = command.find(Regex(element))!!.removeQuotes()
                    linkedList.add(element)
                }
                command matches Regex("add $index $element") -> {
                    val index = command.find(Regex(index))!!.toInt()
                    val element = command.find(Regex(element))!!.removeQuotes()
                    linkedList.add(index, element)
                }
                command matches Regex("remove $element") -> {
                    val element = command.find(Regex(element))!!.removeQuotes()
                    val isFound = linkedList.remove(element)
                    if (!isFound) println("\"$element\" was not in the list.")
                }
                command matches Regex("remove $index") -> {
                    val index = command.find(Regex(index))!!.toInt()
                    linkedList.removeAt(index)
                }
                command == "remove first" -> linkedList.removeAt(0)
                command == "remove last" -> linkedList.removeAt(linkedList.lastIndex)
                command == "help" -> printCommands()
                command == "exit" -> {
                    println("Here is your final linked list: $linkedList")
                    println("Bye bye, now. Take care.")
                    return
                }
                else -> {
                    println("Sorry, I didn't recognize your command. Enter \"help\" to see the available commands again. ")
                }
            }
        } catch (e: IndexOutOfBoundsException) {
            println(e.message)
        }
        printList()
    }
}

fun printList() {
    println("Here is your linked ${if (linkedList.size == 0) "empty " else ""}list of strings: $linkedList\n")
}

fun printCommands() {
    println("Here is the list of commands: ")
    println("add \"<element: String>\" (to add the element at the end of the list)")
    println("add <index: Int> \"<element: String>\" (to add the element at the given index)")
    println("remove \"<element: String>\" (to remove the first instance of the given element)")
    println("remove <index: Int> (to remove the element at the given index)")
    println("remove first (to delete the first element)")
    println("remove last (to delete the last element)")
    println("help (to see this list again)")
    println("exit (to close the program)\n")
    println("Note: the quotation marks \"<String>\" are necessary.")
    println()
}

fun String.removeQuotes(): String {
    var noQuotes = removePrefix("\"")
    noQuotes = noQuotes.removeSuffix("\"")
    return noQuotes
}