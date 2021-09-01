package module01

import extensions.getArrayFromUser
import extensions.print

fun main() {
    val array1Name = "array1"
    val array1 = getArrayFromUser(array1Name)
    println()
    val array2Name = "array2"
    val array2 = getArrayFromUser(array2Name)
    println()
    (array1 + array2).sortedArray().print("mergedArray")
}