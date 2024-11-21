package com.rodrigomiranda.demoapp

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar
import java.util.Date
import java.util.LinkedList
import java.util.PriorityQueue
import java.util.Queue
import java.util.Stack
import kotlin.math.ceil
import kotlin.math.min

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest2 {

    class TreeNode(var `val`: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }

    fun maxDepthDFS(root: TreeNode?): Int {
        return if (root == null) 0 else {
            val left = maxDepthDFS(root.left)
            val right = maxDepthDFS(root.right)
            (if (left > right) left else right) + 1
        }
    }

    fun maxDepthBFS(root: TreeNode?): Int {
        val queue = LinkedList<Pair<TreeNode?, Int>>()
        var depth = 0
        queue.add(Pair(root, 1))

        while (queue.isNotEmpty()) {
            val polled = queue.pollFirst()
            polled?.first?.let {
                depth = polled.second
                queue.add(Pair(it.left, depth + 1))
                queue.add(Pair(it.right, depth + 1))
            }
        }

        return depth
    }

    @Test
    fun consolidateSortedLists_isCorrect() {
        val result = consolidateSortedLists(
            listOf(
                listOf(1, 4, 7),
                listOf(2, 5, 8),
                listOf(3, 6, 9)
            )
        )
        assertArrayEquals(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9), result.toIntArray())
    }

    fun consolidateSortedLists(catalogues: List<List<Int>>): List<Int> {
        // Min-heap to store the elements in sorted order
        val minHeap = PriorityQueue<Triple<Int, Int, Int>>(compareBy { it.first })

        // Populate the heap with the first element of each list
        catalogues.forEachIndexed { index, catalogue ->
            if (catalogue.isNotEmpty()) {
                minHeap.add(Triple(catalogue[0], index, 0)) // (value, listIndex, elementIndex)
            }
        }

        val consolidatedList = mutableListOf<Int>()

        // Process the heap
        while (minHeap.isNotEmpty()) {
            val (value, listIndex, elementIndex) = minHeap.poll()
            consolidatedList.add(value)

            // If there's a next element in the same list, add it to the heap
            if (elementIndex + 1 < catalogues[listIndex].size) {
                val nextValue = catalogues[listIndex][elementIndex + 1]
                minHeap.add(Triple(nextValue, listIndex, elementIndex + 1))
            }
        }

        return consolidatedList
    }

    class LRUCache(val capacity: Int) {

        val keys = LinkedList<Int>()
        val values = LinkedList<Int>()

        fun get(key: Int): Int {
            if (keys.contains(key)) {
                val index = keys.indexOf(key)
                val value = values[index]
                keys.removeAt(index)
                values.removeAt(index)
                keys.add(key)
                values.add(value)
                return value
            } else {
                return -1
            }
        }

        fun put(key: Int, value: Int) {
            if (keys.contains(key)) {
                val index = keys.indexOf(key)
                keys.removeAt(index)
                values.removeAt(index)
                keys.add(key)
                values.add(value)
            } else  {
                if (keys.size == capacity) {
                    keys.removeAt(0)
                    values.removeAt(0)
                }
                keys.add(key)
                values.add(value)
            }
        }
    }

    fun isValidParenthesis(s: String): Boolean {
        val openChars = hashMapOf('(' to 0, '{' to 1, '[' to 2)
        val closeChars = hashMapOf(')' to 0, '}' to 1, ']' to 2)

        val stack = Stack<Int>()

        s.forEach { current ->
            if (openChars.containsKey(current)) {
                stack.add(openChars[current])
            } else {
                if (!stack.isEmpty() && stack.peek() == closeChars[current]) {
                    stack.pop()
                } else {
                    return false
                }
            }
        }

        return stack.isEmpty()
    }

    fun graph() {
        // Represent the graph as an adjacency list
        val graph = mutableMapOf<String, MutableList<String>>()

        // Add edges to the graph
        graph["A"] = mutableListOf("B", "C") // A points to B and C
        graph["B"] = mutableListOf("D")      // B points to D
        graph["C"] = mutableListOf("D")      // C points to D
        graph["D"] = mutableListOf()         // D has no outgoing edges

        // Print the graph
        println("Graph representation:")
        for ((node, edges) in graph) {
            println("$node -> $edges")
        }

        // Example: Traverse the graph (Depth-First Search)
        println("\nDFS Traversal starting from A:")
        val visited = mutableSetOf<String>()
        graph_dfs(graph, "A", visited)
    }

    fun graph_dfs(graph: Map<String, List<String>>, node: String, visited: MutableSet<String>) {
        if (node in visited) return
        visited.add(node)
        println(node) // Process the current node
        for (neighbor in graph[node] ?: emptyList()) {
            graph_dfs(graph, neighbor, visited)
        }
    }

    @Test
    fun testCompress() {
        assertEquals("A4a3b5c4", compress("AAAAaaabbbbbcccc"))
        assertEquals("a2b4c3", compress("aabbbbccc"))
        assertEquals("abc", compress("abc"))
    }

    /**
     * Compresses a sorted string by replacing adjacent identical
     * characters with their cardinality if it's >= 2.
     *
     * @param input Sorted string input.
     * @return Compressed string output.
     */
    fun compress(input: String): String {
        if (input.isEmpty() || input.length == 1)
            return input

        val sb = StringBuilder()

        var currentChar = input[0]
        var currentCount = 0

        input.forEach {
            if (it == currentChar) {
                currentCount++
            } else {
                sb.append(currentChar)
                if (currentCount > 1) sb.append(currentCount)
                currentChar = it
                currentCount = 1
            }
        }
        sb.append(currentChar)
        if (currentCount > 1) sb.append(currentCount)
        return sb.toString()
    }

    @Test
    fun howLongAgoTest() {
        val time = Calendar.getInstance()
        assertEquals(JUST_NOW, howLongAgo(time.time))

        time.add(Calendar.SECOND, -30)
        assertEquals(LESS_THAN_A_MINUTE, howLongAgo(time.time))

        time.add(Calendar.MINUTE, -1)
        assertEquals(String.format(N_MINUTES, 1, ""), howLongAgo(time.time))

        time.add(Calendar.MINUTE, -9)
        assertEquals(String.format(N_MINUTES, 10, "s"), howLongAgo(time.time))

        time.add(Calendar.HOUR, -8)
        assertEquals(String.format(N_HOURS, 8), howLongAgo(time.time))

        time.add(Calendar.HOUR, -49)
        assertEquals(String.format(N_DAYS, 2), howLongAgo(time.time))
    }

    private fun howLongAgo(date: Date): String {
        var result = ""
        val diffMillis = Calendar.getInstance().time.time - date.time
        result = when(diffMillis) {
            in 0 until 1*1000 -> JUST_NOW
            in 1*1000 until  60*1000 -> LESS_THAN_A_MINUTE
            in 60*1000 until  60*1000*60 -> {
                val minutes = diffMillis/1000/60
                String.format(N_MINUTES, minutes, if (minutes == 1L) "" else "s")
            }
            in 60*1000*60 until  60*1000*60*24 -> String.format(N_HOURS, diffMillis/1000/60/60)
            in 60*1000*60*24 until  60*1000*60*24*10 -> String.format(N_DAYS, diffMillis/1000/60/60/24)
            else -> UNKOWN
        }
        return result
    }

    companion object {
        const val JUST_NOW = "Just now."
        const val LESS_THAN_A_MINUTE = "Less than a minute ago."
        const val N_MINUTES = "%d minute%s ago."
        const val N_HOURS = "%d hours ago."
        const val N_DAYS = "%d days ago."
        const val UNKOWN = "Hasn't happened yet."
    }
}