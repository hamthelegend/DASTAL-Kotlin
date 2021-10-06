package extensions

fun <T> Array<T>.print(arrayName: String = "array", range: IntRange? = null) {
    if (range == null) {
        for ((index, element) in this.withIndex()) {
            println("$arrayName[$index] = $element")
        }
    } else {
        for (index in range) {
            println("$arrayName[$index] = ${this[index]}")
        }
    }
}

fun getArrayFromUser(arrayName: String = "array"): Array<Int> {
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

fun String.find(regex: Regex) = regex.find(this)?.value
fun String.findAll(regex: Regex) = regex.findAll(this).map { it.value }.toList()