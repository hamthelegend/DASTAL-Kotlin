package module05

/*
To run this program in command line, run these two commands:
kotlinc -include-runtime -d mod05-activity05.jar src/module05/Activity05.kt src/extensions/binarytree
java -jar mod05-activity05.jar
Note: make sure you have the latest Kotlin compiler and JRE installed.

Also, it uses BigInteger to be able to get long Fibonacci sequences, though the time complexity is exponential.
Tested to work quickly for up to n = 30.
*/

import extensions.binarytree.BinaryTree
import extensions.binarytree.Node

fun main() {
    val binaryTree = BinaryTree('A')
    binaryTree.root.left = Node('B')
    binaryTree.root.right = Node('C')
    binaryTree.root.left!!.left = Node('D')
    binaryTree.root.left!!.right = Node('E')
    binaryTree.root.right!!.left = Node('F')
    binaryTree.root.left!!.left!!.left = Node('G')
    binaryTree.root.left!!.right!!.left = Node('H')
    binaryTree.root.left!!.right!!.right = Node('I')
    println("Here is the binary tree:")
    println(binaryTree)
    println("Note that the top one is the \"left node\" and the bottom one is the \"right node\"\n")
    println("Preorder Traversal: ${binaryTree.preorderList}")
    println("Inorder Traversal: ${binaryTree.inorderList}")
    println("Postorder Traversal: ${binaryTree.postorderList}")
}