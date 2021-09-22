package extensions.doublylinkedlist

class DoublyLinkedListIndexOutOfBoundsException(index: Int, size: Int) : IndexOutOfBoundsException(
    "Index $index is outside the bounds of the " +
            "DoublyLinkedList with the size of $size."
)

class ElementNotFoundException(element: String): Exception("Cannot find element $element")

data class DoublyLinkedListNode<T> internal constructor(
    var previous: Node<T>?,
    var value: T,
    var next: Node<T>?
)

internal typealias Node<T> = DoublyLinkedListNode<T>

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

    private fun getNode(index: Int): Node<T> {
        if (index < 0 || index >= _size) throw DoublyLinkedListIndexOutOfBoundsException(index, size)
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
        while(node.next != null) {
            node = node.next!!
            if (node.value == element) return node
        }
        throw ElementNotFoundException(element.toString())
    }

    operator fun get(index: Int) = getNode(index).value

    fun add(element: T) {
        addAt(size, element)
    }

    fun addAt(index: Int, element: T) {
        if (index >= 0) {
            if (index == size) {
                val nodeBeforeIndex = getNode(index - 1)
                val newNode = Node(nodeBeforeIndex, element, nodeBeforeIndex.next)
                nodeBeforeIndex.next = newNode
                nodeBeforeIndex.next?.previous = newNode
            }
        } else throw DoublyLinkedListIndexOutOfBoundsException(index, size)
    }

    fun remove(element: T): Boolean {
        return try {
            val node = getNode(element)
            node.previous?.next = node.next
            node.next?.previous = node.previous
            true
        } catch (e: ElementNotFoundException) {
            return false
        }
    }

    fun removeAt(index: Int): T {
        val node = getNode(index)
        node.previous?.next = node.next
        node.next?.previous = node.previous
        return node.value
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder("[")
        for (i in indices) {
            stringBuilder.append(get(i))
            if (i != lastIndex) {
                stringBuilder.append(", ")
            }
        }
        stringBuilder.append(']')
        return stringBuilder.toString()
    }

}

fun <T> doublyLinkedListOf(vararg elements: T): DoublyLinkedList<T> {
    val doublyLinkedList = DoublyLinkedList<T>()
    for (element in elements) {
        doublyLinkedList.add(element)
    }
    return doublyLinkedList
}
