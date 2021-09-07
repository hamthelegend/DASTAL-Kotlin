package module01

import extensions.getArrayFromUser
import extensions.print

/*
To run this program in command line, run these two commands:
kotlinc -include-runtime -d activity04.jar src/module01/Activity04.kt src/extensions/Extensions.kt
java -jar activity04.jar
Note: make sure you have the latest Kotlin compiler installed
 */

fun main() {
    val array1Name = "array1"
    val array1 = getArrayFromUser(array1Name)
    println()
    val array2Name = "array2"
    val array2 = getArrayFromUser(array2Name)
    println()

    val array = (array1 + array2).sortedArray()
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
    println("The merged array after the deletion: ")
    array.print(range = 0 until array.lastIndex)
}