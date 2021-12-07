package extensions.binarytree


internal typealias Node<T> = BinaryTreeNode<T>

data class BinaryTreeNode<T> internal constructor(
    var value: T,
    var left: Node<T>? = null,
    var right: Node<T>? = null
) {

    override fun toString(): String {
        val buffer = StringBuilder(50)
        print(buffer, "", "")
        return buffer.toString()
    }

    private fun print(buffer: StringBuilder, prefix: String, childrenPrefix: String) {
        buffer.append(prefix)
        buffer.append(value)
        buffer.append('\n')
        val children = mutableListOf(left, right)
        children.removeIf { it == null }
        val iterator = children.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            if (iterator.hasNext()) {
                next?.print(buffer, "$childrenPrefix├── ", "$childrenPrefix│   ")
            } else {
                next?.print(buffer, "$childrenPrefix└── ", "$childrenPrefix    ")
            }
        }
    }
}

internal fun <T> getPreorderList(node: Node<T>?): List<T> {
    if (node == null) return listOf()
    return listOf(node.value) + getPreorderList(node.left) + getPreorderList(node.right)
}

internal fun <T> getInorderList(node: Node<T>?): List<T> {
    if (node == null) return listOf()
    return getInorderList(node.left) + listOf(node.value) + getInorderList(node.right)
}

internal fun <T> getPostorderList(node: Node<T>?): List<T> {
    if (node == null) return listOf()
    return getPostorderList(node.left) + getPostorderList(node.right) + listOf(node.value)
}

class BinaryTree<T> (rootValue: T) {
    var root: Node<T> = Node(rootValue)

    val preorderList get() = getPreorderList(root)
    val inorderList get() = getInorderList(root)
    val postorderList get() = getPostorderList(root)

    override fun toString() = root.toString()
}