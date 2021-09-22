package module02

/*
To run this program in command line, run these two commands:
kotlinc -include-runtime -d mod02-exercise01.jar src/module02/Exercise01.kt
java -jar mod02-exercise01.jar
Note: make sure you have the latest Kotlin compiler installed
 */

typealias Matrix = Array<Array<Int>>

class IllegalMatrixArgumentSizeException(requiredArgumentSize: Int) :
    IllegalArgumentException("The matrix should contain exactly $requiredArgumentSize elements.")

class IllegalMatrixAdditionException(rowCount1: Int, columnCount1: Int, rowCount2: Int, columnCount2: Int) :
    IllegalArgumentException("You cannot add a matrix of $rowCount2 rows × $columnCount2 columns to " +
            "a matrix of $rowCount1 rows × $columnCount1 columns.")

fun main() {
    startOrResetProgram()
}

fun startOrResetProgram() {
    print("Enter the number of rows of your matrix: ")
    val rowCount = readLine()!!.toInt()

    print("Enter the number of columns of your matrix: ")
    val columnCount = readLine()!!.toInt()

    println()
    println("Enter all the ${rowCount * columnCount} elements of your matrix (separated by spaces): ")
    val elements = readLine()!!.split(' ').map { it.toInt() }

    try {

        var matrix = elements.toMatrix(rowCount, columnCount)

        while (true) {

            println()
            println("Current matrix: ")
            matrix.print()

            println()
            println("What do you want to do to this matrix?")
            println("- Transpose it.")
            println("- Add another matrix to it.")
            println("- Discard it and make a new one.")
            println("- Exit the program.")
            println()
            print("Enter \"transpose\", \"add\", \"discard\", or \"exit\": ")

            when (readLine()!!.trim().lowercase()) {
                "transpose", "t" -> {
                    println("Matrix transposed.")
                    matrix = matrix.transposed()
                }
                "add", "a", "+", "sum" -> {

                    fun add() {
                        println()
                        println("Enter all the ${rowCount * columnCount} elements of your matrix you want to add " +
                                "(separated by spaces): ")
                        val elements2 = readLine()!!.split(' ').map { it.toInt() }
                        val matrix2 = elements2.toMatrix(matrix.rowCount, matrix.columnCount)

                        println()
                        println("Added the matrix")
                        matrix2.print()
                        println("to the current matrix.")
                        matrix += matrix2
                    }

                    try {
                        add()
                    } catch (e: IllegalMatrixArgumentSizeException) {
                        println(e.message)
                        add()
                    }

                }
                "discard", "d", "reset", "r" -> {
                    println("Matrix reset.")
                    println()
                    startOrResetProgram()
                    return
                }
                "exit", "e", "close", "c", "quit", "q" -> {
                    println("Okay, bye.")
                    return
                }
                else -> println("Sorry, I didn't recognize your response.")
            }

        }

    } catch (e: IllegalMatrixArgumentSizeException) {
        println(e.message)
        println()
        startOrResetProgram()
    }
}

fun makeMatrix(rowCount: Int, columnCount: Int) = Array(rowCount) { Array(columnCount) { 0 } }

val Matrix.rowCount
    get() = size

val Matrix.columnCount
    get() = this[0].size

fun List<Int>.toMatrix(rowCount: Int, columnCount: Int): Matrix {
    val requiredArgumentSize = rowCount * columnCount
    if (size != requiredArgumentSize) throw IllegalMatrixArgumentSizeException(requiredArgumentSize)
    val elements = toMutableList()
    val matrix = makeMatrix(rowCount, columnCount)
    for (rowIndex in 0 until rowCount) {
        for (columnIndex in 0 until columnCount) {
            matrix[rowIndex][columnIndex] = elements.removeAt(0)
        }
    }
    return matrix
}

operator fun Matrix.plus(other: Matrix): Matrix {
    if (this.rowCount != other.rowCount || this.columnCount != this.columnCount) {
        throw IllegalMatrixAdditionException(this.rowCount, this.columnCount, other.rowCount, other.columnCount)
    }
    val resultantMatrix = makeMatrix(rowCount, columnCount)
    for (rowIndex in 0 until rowCount) {
        for (columnIndex in 0 until columnCount) {
            resultantMatrix[rowIndex][columnIndex] = this[rowIndex][columnIndex] + other[rowIndex][columnIndex]
        }
    }
    return resultantMatrix
}

fun Matrix.transposed(): Matrix {
    val transposedMatrix = makeMatrix(columnCount, rowCount)
    for (columnIndex in 0 until columnCount) {
        for (rowIndex in 0 until rowCount) {
            transposedMatrix[columnIndex][rowIndex] = this[rowIndex][columnIndex]
        }
    }
    return transposedMatrix
}

fun Matrix.print() {
    for (row in this) {
        for (element in row) {
            print("$element ")
        }
        println()
    }
}