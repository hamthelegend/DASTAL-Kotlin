package module01

fun main() {
    val array1Name = "array1"
    val array1 = getArrayFromUser(array1Name)
    println()
    val array2Name = "array2"
    val array2 = getArrayFromUser(array2Name)
    println()
    (array1 + array2).sortedArray().print("mergedArray")
}

fun <T> Array<T>.print(arrayName: String) {
    for ((index, element) in this.withIndex()) {
        println("$arrayName[$index] = $element")
    }
}

fun getArrayFromUser(arrayName: String): Array<Int> {
    while (true) {
        try {
            print("Enter the number of elements in the $arrayName: ")
            val size = readLine()!!.trim().toInt()
            var index = 0
            val array = Array(size) {
                print("$arrayName[$index] = ")
                index++
                readLine()!!.toInt()
            }
            return array
        } catch (e: NumberFormatException) {
            println()
            println("That's not a number. Try again.")
        }
    }
}