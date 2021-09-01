package extensions

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