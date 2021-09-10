package module02

import java.io.File
import java.math.BigInteger

/*
To run this program in command line, run these two commands:
kotlinc -include-runtime -d mod02-activity02.jar src/module02/Activity02.kt
java -jar mod02-activity02.jar.jar
Note: make sure you have the latest Kotlin compiler installed

Note: There are no integer size limits since I used Java's BigInteger class.
The only size limit is your computer's resources.
Also, you could export the triangle to a .csv file,
in case your console had issues printing it or just because you want to.

In my own experience with my IDE's integrated console, it started deleting the first rows at around 200 rows and
started wrapping the text to the next line. However, the CSV file is fine when opened with Excel (tested to work for
1000 rows -- could probably support even larger triangles).
However, beware; my IDE used 6 GB (total) while printing and writing a 1000-row triangle and took about a minute.
*/

fun main() {
    println("Pascal's Triangle Generator")
    println()
    print("Enter the number of rows to generate: ")
    val rows = readLine()!!.toInt()
    try {
        printPascalsTriangle(rows)
    } catch (e: OutOfMemoryError) {
        println("Oops, sorry, but your machine does not allow that long of a Pascal's Triangle.")
    }
}

fun printPascalsTriangle(rows: Int) {
    val triangle = mutableListOf(mutableListOf(BigInteger.ONE))
    for (rowIndex in 0 until rows - 1) {
        val row = mutableListOf(BigInteger.ONE)
        for (numberIndex in 0 until triangle[rowIndex].lastIndex) {
            val addendA = triangle[rowIndex][numberIndex]
            val addendB = triangle[rowIndex][numberIndex + 1]
            val sum = addendA + addendB
            row.add(sum)
        }
        row.add(BigInteger.ONE)
        triangle.add(row)
    }

    val largestNumber = triangle.last()[triangle.last().size / 2]
    val spaceWidth = largestNumber.toString().length + 1

    for (row in triangle) {
        for (number in row) {
            print(number.toString().padEnd(spaceWidth))
        }
        println()
    }
    println()
    println("In case your console had trouble showing the output, you could choose to write the output to a file.")
    print("Do you want to export it as a .csv file? Type yes/no: ")
    val response = readLine()!!.trim().lowercase()
    when (response) {
        "yes", "y" -> {
            println("Writing file...")
            val fileName = "activity_02_output.csv"
            val file = File(fileName)
            file.writeText("")
            for (row in triangle) {
                val stringBuilder = StringBuilder("")
                for (number in row) {
                    stringBuilder.append("$number,")
                }
                stringBuilder.append("\n")
                file.appendText(stringBuilder.toString())
            }
            println("$fileName created. You could use a spreadsheet program to view it in columns.")
        }
        "no", "n" -> {
            println("Okay.")
        }
        else -> {
            println("Sorry, I don't know what you mean.")
        }
    }
}