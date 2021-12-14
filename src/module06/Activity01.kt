package module06

/*
To run this program in command line, run these two commands:
kotlinc -include-runtime -d mod06-activity01.jar src/module06/Activity01.kt src/extensions/graph
java -jar mod06-activity01.jar
Note: make sure you have the latest Kotlin compiler and JRE installed.

Also, it uses BigInteger to be able to get long Fibonacci sequences, though the time complexity is exponential.
Tested to work quickly for up to n = 30.
*/

import extensions.graph.*

object CommandPrefixes{
    const val ADD_VERTEX = "add vertex"
    const val ADD_EDGE = "add edge"
    const val REMOVE_VERTEX = "remove vertex"
    const val REMOVE_EDGE = "remove edge"
    const val PRINT_GRAPH = "print"
    const val PRINT_HELP = "help"
    const val EXIT = "exit"
}

object CommandFormats{
    const val ADD_VERTEX = "\"${CommandPrefixes.ADD_VERTEX} [vertexName: String]\""
    const val ADD_EDGE = "\"${CommandPrefixes.ADD_EDGE} [vertexName: String] [adjacentVertexName: String] [weight: Int]\""
    const val REMOVE_VERTEX = "\"${CommandPrefixes.REMOVE_VERTEX} [vertexName: String]\""
    const val REMOVE_EDGE = "\"${CommandPrefixes.REMOVE_EDGE} [vertexName: String] [adjacentVertexName: String]\""
    const val PRINT_GRAPH = "\"${CommandPrefixes.PRINT_GRAPH}\""
    const val PRINT_HELP = "\"${CommandPrefixes.PRINT_HELP}\""
    const val EXIT = "\"${CommandPrefixes.EXIT}\""
}

const val COMMAND_FAILED = "Command failed!"
val graph = DirectedGraph<String>()

fun main() {
    printHelp()
    while (true) {
        println("Enter your command: ")
        val command = readLine()!!
        val commandParts = command.split(' ')
        when {
            command.startsWith(CommandPrefixes.ADD_VERTEX) -> addVertex(commandParts)
            command.startsWith(CommandPrefixes.ADD_EDGE) -> addEdge(commandParts)
            command.startsWith(CommandPrefixes.REMOVE_VERTEX) -> removeVertex(commandParts)
            command.startsWith(CommandPrefixes.REMOVE_EDGE) -> removeEdge(commandParts)
            command == CommandPrefixes.PRINT_GRAPH -> printGraph()
            command == CommandPrefixes.PRINT_HELP -> printHelp()
            command == CommandPrefixes.EXIT -> break
            else -> println("$COMMAND_FAILED The program didn't recognize your command. " +
                    "Enter command \"help\" to see all the commands.")
        }
    }
    println("Bye!")
}

fun addVertex(commandParts: List<String>) {
    try {
        val vertexName = commandParts[2]
        graph.addVertex(vertexName)
        println("Vertex $vertexName added")
    } catch (e: IndexOutOfBoundsException) {
        println("$COMMAND_FAILED Missing arguments. ${CommandFormats.ADD_VERTEX}")
    } catch (e: RedundantVertexException) {
        println("$COMMAND_FAILED A vertex with that name already exists.")
    }
    println()
}

fun addEdge(commandParts: List<String>) {
    try {
        val vertex = graph.getVertex(commandParts[2])
        val adjacentVertex = graph.getVertex(commandParts[3])
        val weight = commandParts[4].toInt()
        vertex.addEdge(adjacentVertex, weight)
        println("Edge from Vertex ${vertex.value} to ${adjacentVertex.value} of weight $weight added.")
    } catch (e: IndexOutOfBoundsException) {
        println("$COMMAND_FAILED Missing arguments. ${CommandFormats.ADD_EDGE}")
    } catch (e: VertexNotFoundException) {
        println("$COMMAND_FAILED One or both the vertices are not in the graph.")
    } catch (e: RedundantEdgeException) {
        println("$COMMAND_FAILED Edge from ${commandParts[2]} to ${commandParts[3]} already exists.")
    } catch (e: IllegalEdgeWeightException) {
        println("$COMMAND_FAILED ${e.message}")
    } catch (e: NumberFormatException) {
        println("$COMMAND_FAILED Argument for weight must be an Int")
    }
    println()
}

fun removeVertex(commandParts: List<String>) {
    try {
        val vertex = graph.getVertex(commandParts[2])
        graph.removeVertex(vertex)
        println("Vertex ${vertex.value} removed, including edges from the graph referencing Vertex ${vertex.value}")
    } catch (e: IndexOutOfBoundsException) {
        println("$COMMAND_FAILED Missing arguments. ${CommandFormats.REMOVE_VERTEX}")
    } catch (e: VertexNotFoundException) {
        println("$COMMAND_FAILED That vertex is not in the graph.")
    }
    println()
}

fun removeEdge(commandParts: List<String>) {
    try {
        val vertex = graph.getVertex(commandParts[2])
        val adjacentVertex = graph.getVertex(commandParts[3])
        val edge = vertex.getEdge(adjacentVertex)
        vertex.removeEdge(edge)
        println("Edge from ${vertex.value} to ${adjacentVertex.value} with weight ${edge.weight} removed.")
    } catch (e: IndexOutOfBoundsException) {
        println("$COMMAND_FAILED Missing arguments. ${CommandFormats.REMOVE_EDGE}")
    } catch (e: VertexNotFoundException) {
        println("$COMMAND_FAILED That vertex is not in the graph.")
    } catch (e: EdgeNotFoundException) {
        println("$COMMAND_FAILED There is no edge from ${commandParts[2]} to ${commandParts[3]}.")
    }
    println()
}

fun printGraph() {
    println(graph)
    println()
}

fun printHelp() {
    println("Here are the available commands: ")
    println("${CommandFormats.ADD_VERTEX} to add a vertex")
    println("${CommandFormats.ADD_EDGE} to add an edge from vertex to adjacentVertex")
    println("${CommandFormats.REMOVE_VERTEX} to remove a vertex")
    println("${CommandFormats.REMOVE_EDGE} to remove an edge")
    println("${CommandFormats.PRINT_GRAPH} to print your graph's adjacency list")
    println("${CommandFormats.PRINT_HELP} to see this list again")
    println("${CommandFormats.EXIT} to exit the program")
    println("Note: You cannot use spaces in inside your vertex names.")
    println()
}