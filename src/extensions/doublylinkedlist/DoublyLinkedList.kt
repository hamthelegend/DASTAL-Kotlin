package extensions.doublylinkedlist

class DoublyLinkedListIndexOutOfBoundsException(index: Int, size: Int) : IndexOutOfBoundsException(
    "Index $index is outside the bounds of the " +
            "DoublyLinkedList with the size of $size."
)

class ElementNotFoundException(element: String) : Exception("Cannot find element $element")

data class DoublyLinkedListNode<T> internal constructor(
    var previous: Node<T>?,
    var value: T,
    var next: Node<T>?
) {
    override fun toString() = "previous = ${previous?.value}; current = $value; next = ${next?.value}"
}

internal typealias Node<T> = DoublyLinkedListNode<T>

/**
 * A personal implementation of the DoublyLinkedList data structure
 */
class DoublyLinkedList<T> {

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var _size = 0
    val size
        get() = _size
    val lastIndex
        get() = _size - 1
    val indices
        get() = 0 until _size

    private fun initialize(firstElement: T) {
        val firstNode = Node(null, firstElement, null)
        head = firstNode
        tail = firstNode
    }

    private fun getNodeAt(index: Int): Node<T> {
        if (index !in 0..lastIndex) throw DoublyLinkedListIndexOutOfBoundsException(index, size)
        return if (index <= _size / 2) {
            var node = head!!
            repeat(index) {
                node = node.next!!
            }
            node
        } else {
            var node = tail!!
            repeat(lastIndex - index) {
                node = node.previous!!
            }
            node
        }
    }

    private fun getNode(element: T): Node<T> {
        var node = head!!
        while (node.next != null) {
            node = node.next!!
            if (node.value == element) return node
        }
        throw ElementNotFoundException(element.toString())
    }

    /**
     * Gets the element at the given [index]
     */
    operator fun get(index: Int) = getNodeAt(index).value

    /**
     * Adds an element to the end of the linked list
     */
    fun add(element: T) {
        add(size, element)
    }

    /**
     * Adds the [element] to the given [index] of the linked list
     */
    fun add(index: Int, element: T) {
        if (index == 0) {
            if (size == 0) initialize(element)
            else {
                val nodeAtIndex = getNodeAt(index)
                val newNode = Node(null, element, nodeAtIndex)
                nodeAtIndex.previous = newNode
                head = newNode
            }
        }
        else if (index in 1..size) {
            val nodeBeforeIndex = getNodeAt(index - 1)
            val newNode = Node(nodeBeforeIndex, element, nodeBeforeIndex.next)
            nodeBeforeIndex.next?.previous = newNode
            nodeBeforeIndex.next = newNode
            if (index == size) tail = newNode
        } else throw DoublyLinkedListIndexOutOfBoundsException(index, size)
        _size++
    }

    /**
     * Removes the first instance of the [element] in the linked list.
     * @returns true if the element was found and removal was successful and false if otherwise.
     */
    fun remove(element: T): Boolean {
        return try {
            val node = getNode(element)
            node.previous?.next = node.next
            node.next?.previous = node.previous
            if (node.next == null) tail = node.previous
            _size--
            true
        } catch (e: ElementNotFoundException) {
            return false
        }
    }

    /**
     * Removes the element at the given [index].
     * @returns the element that was removed.
     */
    fun removeAt(index: Int): T {
        val node = getNodeAt(index)
        node.previous?.next = node.next
        node.next?.previous = node.previous
        if (node.next == null) tail = node.previous
        _size--
        return node.value
    }

    /**
     * @returns a String that represents the whole linked list.
     */
    override fun toString(): String {
        val stringBuilder = StringBuilder("[")
        var node = head
        for (i in indices) {
            stringBuilder.append(node?.value)
            node = node?.next
            if (i != lastIndex) {
                stringBuilder.append(", ")
            }
        }
        stringBuilder.append(']')
        return stringBuilder.toString()
    }

    /**
     * Prints every node and how they're linked.
     */
    fun printLinks() {
        for (i in indices) {
            println(getNodeAt(i))
        }
    }

}

/**
 * Creates a [DoublyLinkedList] with the given [elements]
 */
fun <T> doublyLinkedListOf(vararg elements: T): DoublyLinkedList<T> {
    val doublyLinkedList = DoublyLinkedList<T>()
    for (element in elements) {
        doublyLinkedList.add(element)
    }
    return doublyLinkedList
}
