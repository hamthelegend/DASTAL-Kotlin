package extensions.graph

import module06.removeEdge

class VertexNotFoundException(val vertexValue: Any?) :
    Exception("Vertex $vertexValue does not exist.")

class EdgeNotFoundException(val vertexValue: Any?, val adjacentVertexValue: Any?) :
    Exception("An edge from Vertex $vertexValue to Vertex $adjacentVertexValue does not exist.")

class RedundantVertexException(val vertexValue: Any?) :
    Exception("A vertex with that value already exists.")

class RedundantEdgeException(val vertexValue: Any?, val adjacentVertexValue: Any?) :
    Exception("An edge from Vertex $vertexValue to $adjacentVertexValue already exists.")

class IllegalEdgeWeightException(val weight: Int) :
    Exception("Edge weight must be > 0.")

data class DirectedEdge<T>(val adjacentVertex: Vertex<T>, val weight: Int) {
    override fun toString() = "$weight -> Vertex ${adjacentVertex.value}"
}

data class Vertex<T> internal constructor(var value: T) {
    internal val edges = mutableListOf<DirectedEdge<T>>()

    fun addEdge(adjacentVertex: Vertex<T>, weight: Int) {
        if (weight <= 0) throw IllegalEdgeWeightException(weight)
        if (edges.find { adjacentVertex == it.adjacentVertex } == null) {
            edges.add(DirectedEdge(adjacentVertex, weight))
        } else throw RedundantEdgeException(value, adjacentVertex.value)
    }

    fun getEdge(adjacentVertex: Vertex<T>): DirectedEdge<T> {
        val edge = edges.find { adjacentVertex == it.adjacentVertex }
        if (edge != null) return edge else throw EdgeNotFoundException(value, adjacentVertex.value)
    }

    fun removeEdge(edge: DirectedEdge<T>) = edges.remove(edge)

    override fun toString() = "Vertex $value: $edges"
}

class DirectedGraph<T> {
    val vertices = mutableListOf<Vertex<T>>()

    fun addVertex(value: T) {
        if (vertices.find { value == it.value } == null) {
            vertices.add(Vertex(value))
        } else throw RedundantVertexException(value)
    }

    fun getVertex(value: T): Vertex<T> {
        val vertex = vertices.find { value == it.value }
        if (vertex != null) return vertex else throw VertexNotFoundException(value)
    }

    fun removeVertex(vertex: Vertex<T>): Boolean {
        for (_vertex in vertices) {
            val iterator = _vertex.edges.iterator()
            while (iterator.hasNext()) {
                val edge = iterator.next()
                if (edge.adjacentVertex == vertex) {
                    iterator.remove()
                }
            }
        }
        return vertices.remove(vertex)
    }

    override fun toString(): String {
        if (vertices.isEmpty()) return "The graph is empty."
        val stringBuilder = StringBuilder()
        for ((index, vertex) in vertices.withIndex()) {
            stringBuilder.append(vertex.toString())
            if (index != vertices.lastIndex) stringBuilder.append('\n')
        }
        return stringBuilder.toString()
    }

}