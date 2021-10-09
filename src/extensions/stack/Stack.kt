package extensions.stack

import module03.linkedList

internal typealias Node<T> = StackNode<T>

class EmptyStackException(): IndexOutOfBoundsException("You are trying to pop from an empty stack")

data class StackNode<T>(
    val previous: StackNode<T>?,
    val value: T
)

class LinkedStack<T> internal constructor() {
    private var top: Node<T>? = null
    var size = 0

    fun push(element: T) {
        val newNode = Node(top, element)
        top = newNode
        size++
    }

    fun check() = if (size != 0) top!!.value else throw EmptyStackException()

    fun pop(): T {
        if (size != 0) {
            val element = top!!.value
            top = top?.previous
            size--
            return element
        } else throw EmptyStackException()
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder("[")
        var node = top
        while (node != null) {
            stringBuilder.append(node.value)
            if (node.previous != null) stringBuilder.append(", ")
            node = node.previous
        }
        stringBuilder.append(']')
        return stringBuilder.toString()
    }

}

fun <T> linkedStackOf(vararg elements: T): LinkedStack<T> {
    val linkedStack = LinkedStack<T>()
    for (element in elements) {
        linkedStack.push(element)
    }
    return linkedStack
}