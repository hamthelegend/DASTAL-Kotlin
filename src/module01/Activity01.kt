package module01

import extensions.getArrayFromUser

fun main() {
    val array = getArrayFromUser()
    println("The sum of the array elements = ${array.sum()}")
}