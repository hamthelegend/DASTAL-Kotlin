package module01

import extensions.getArrayFromUser
import extensions.print

fun main() {
    val array = getArrayFromUser()
    println()
    print("Enter the number to be inserted: ")
    val numberToInsert = readLine()!!.toInt()
    println("The array after insertion of $numberToInsert is:")
    (array + numberToInsert).sortedArray().print()
}