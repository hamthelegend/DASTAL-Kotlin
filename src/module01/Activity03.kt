package module01

import extensions.getArrayFromUser
import extensions.print

fun main() {
    val array = getArrayFromUser("array")
    println()
    print("Enter the number to be deleted: ")
    val numberToDelete = readLine()!!.toInt()
    val position = array.indexOf(numberToDelete)
    if (position == -1) {
        println("$numberToDelete is not in the list.")
    } else {
        for (i in position until array.lastIndex) {
            array[i] = array[i + 1]
        }
    }
    println()
    println("The array after the deletion: ")
    array.print(range = 0 until array.lastIndex)
}