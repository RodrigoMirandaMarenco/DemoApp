package com.rodrigomiranda.demoapp

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
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
class ExampleUnitTest {

    @Test
    fun merge_isCorrect() {
        val num1 = intArrayOf(-1, 0, 0, 3, 3, 3, 0, 0, 0)
        val num2 = intArrayOf(1, 2, 2)
        merge(num1, 6, num2, 3)
        assertArrayEquals(intArrayOf(-1, 0, 0, 1, 2, 2, 3, 3, 3), num1)
    }

    fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int) {
        var currentNum2 = 0
        for (i in 0 until m + n) {
            var lowerNumber = nums1[i]
            var carryOver = false
            if (currentNum2 < n && (nums2[currentNum2] <= lowerNumber || i >= m + currentNum2)) {
                lowerNumber = nums2[currentNum2]
                carryOver = true
                currentNum2++

            }
            if (carryOver) {
                for (j in m + currentNum2 - 1 downTo i + 1) {
                    nums1[j] = nums1[j - 1]
                }
            }
            nums1[i] = lowerNumber
        }
    }

    @Test
    fun removeElement_isCorrect() {
        val nums = intArrayOf(0, 1, 2, 2, 3, 0, 4, 2)
        val result = removeElement(nums, 2)
        val expectedNums = intArrayOf(0, 1, 3, 0, 4)
        assertEquals(expectedNums.size, result)
        for (i in 0 until expectedNums.size) {
            assertTrue(nums.contains(expectedNums[i]))
        }
    }

    fun removeElement(nums: IntArray, `val`: Int): Int {
        var removedItemCount = 0
        var i = 0
        while (i < nums.size) {
            var mustRestart = false
            if (nums[i] == `val`) {
                nums[i] = -1
                for (j in i until nums.size - 1) {
                    if (nums[j + 1] == `val`) {
                        mustRestart = true
                    }
                    nums[j] = nums[j + 1]
                }
                nums[nums.size - 1] = -1
                removedItemCount++
            }

            i = if (mustRestart) 0 else i + 1
        }
        return nums.size - removedItemCount
    }

    @Test
    fun removeDuplicates_isCorrect() {
        val nums = intArrayOf(1, 1, 1, 2, 2, 3)
        val result = removeDuplicates(nums)
        val expectedNums = intArrayOf(1, 1, 2, 2, 3)
        assertEquals(expectedNums.size, result)
        for (i in 0 until expectedNums.size) {
            assertTrue(nums.contains(expectedNums[i]))
        }
    }

    fun removeDuplicates(nums: IntArray): Int {
        val numMap = mutableMapOf<Int, Int>()
        for (i in 0 until nums.size) {
            val total = numMap.getOrPut(nums[i], { 0 })
            numMap[nums[i]] = total + 1
        }
        var i = 0
        numMap.forEach { (j, v) ->
            nums[i] = j
            i++
            if (v > 1) {
                nums[i] = j
                i++
            }
        }
        return i
    }

    @Test
    fun majorityElement_isCorrect() {
        val nums = intArrayOf(-1, 1, 1, 1, 2, 1)
        val result = majorityElement(nums)
        assertEquals(1, result)
    }

    fun majorityElement(nums: IntArray): Int {
        val numMap = mutableMapOf<Int, Int>()
        nums.forEach { num ->
            numMap.put(num, numMap.getOrDefault(num, 0) + 1)
        }
        val majority = nums.size / 2.0
        numMap.forEach { (num, v) ->
            if (v > majority) {
                return num
            }
        }
        return nums[0]
    }

    @Test
    fun maxProfit_isCorrect() {
        val nums = intArrayOf(7, 1, 5, 3, 6, 4)
        val result = maxProfit(nums)
        assertEquals(7, result)

        val nums2 = intArrayOf(1, 2, 3, 4, 5)
        val result2 = maxProfit(nums2)
        assertEquals(4, result2)

        val nums3 = intArrayOf(7, 6, 4, 3, 1)
        val result3 = maxProfit(nums3)
        assertEquals(0, result3)
    }

    fun maxProfit(prices: IntArray): Int {
        var maxProfit = 0
        for (i in 1 until prices.size) {
            val diff = prices[i] - prices[i - 1]
            if (diff > 0)
                maxProfit += diff
        }
        return maxProfit
    }

    @Test
    fun getMaxNegativePnL_isCorrect() {
        val nums = intArrayOf(5, 3, 1, 2)
        val result = getMaxNegativePnL(nums, 4)
        assertEquals(2, result)

        val nums2 = intArrayOf(1, 1, 1, 1, 1)
        val result2 = getMaxNegativePnL(nums2, 4)
        assertEquals(2, result2)

        val nums3 = intArrayOf(5, 2, 3, 5, 2, 3)
        val result3 = getMaxNegativePnL(nums3, 6)
        assertEquals(3, result3)
    }

    fun getMaxNegativePnL(pnls: IntArray, n: Int): Int {
        if (pnls.size <= 2) {
            return 0
        }

        var maxLosses = 0
        var left = pnls.size - 1

        while (left > 0) {
            pnls[left] = pnls[left] * -1
            maxLosses++
            if (!isPnLPositive(pnls)) {
                pnls[left] = pnls[left] * -1
                maxLosses--
            }
            left--
        }

        return maxLosses
    }

    fun getMaxNegativePnL1(pnls: IntArray, n: Int): Int {
        if (pnls.size <= 2) {
            return 0
        }

        var maxLosses = 0
        var left = 1

        while (left < pnls.size) {
            pnls[left] = pnls[left] * -1
            maxLosses++
            if (!isPnLPositive(pnls)) {
                pnls[left] = pnls[left] * -1
                maxLosses--
            }
            left++
        }

        return maxLosses
    }

    fun isPnLPositive(pnls: IntArray): Boolean {
        var currentSum = pnls[0]
        for (i in 1..<pnls.size) {
            currentSum += pnls[i]
            if (currentSum <= 0)
                return false
        }
        return true
    }

    @Test
    fun reverseWords_isCorrect() {
        val s = "the sky is blue"
        val result = reverseWords(s)
        assertEquals("blue is sky the", result)

        val s2 = "  hello world  "
        val result2 = reverseWords(s2)
        assertEquals("world hello", result2)

        val s3 = "a good   example"
        val result3 = reverseWords(s3)
        assertEquals("example good a", result3)
    }

    fun reverseWords(s: String): String {
        var result = ""
        val words = mutableListOf<String>()
        var wordsIndex = -1
        var cameFromNonChar = true
        s.forEach {
            if (it != ' ') {
                if (cameFromNonChar) {
                    words.add(it.toString())
                    cameFromNonChar = false
                    wordsIndex++
                } else {
                    words[wordsIndex] = words[wordsIndex] + it.toString()
                }
            } else {
                cameFromNonChar = true
            }
        }

        var left = 0
        var right = words.size - 1

        while (left < right) {
            val temp = words[left]
            words[left] = words[right]
            words[right] = temp
            left++
            right--
        }

        words.forEachIndexed { i, word ->
            result += word
            if (i < words.size - 1) {
                result += " "
            }
        }

        return result
    }

    @Test
    fun isPalindrome_isCorrect() {
        val s = "A man, a plan, a canal: Panama"
        val result = isPalindrome(s)
        assertTrue(result)

        val s2 = "race a car"
        val result2 = isPalindrome(s2)
        assertFalse(result2)

        val s3 = " "
        val result3 = isPalindrome(s3)
        assertTrue(result3)
    }

    fun isPalindrome(s: String): Boolean {
        val sanitizedString = s.lowercase()
        var left = 0
        var right = sanitizedString.length - 1
        while (left < right) {
            if (!sanitizedString[left].isLetterOrDigit()) {
                left++
            } else if (!sanitizedString[right].isLetterOrDigit()) {
                right--
            } else {
                if (sanitizedString[left] != sanitizedString[right]) {
                    return false
                }
                left++
                right--
            }
        }
        return true
    }

    @Test
    fun threeSum_isCorrect() {
        intArrayOf(-4, -1, -1, 0, 1, 2)
        val num1 = intArrayOf(-1, 0, 1, 2, -1, -4)
        val result = threeSum(num1)
        val expected = listOf(listOf(-1, -1, 2).sorted(), listOf(-1, 0, 1).sorted())
        assertEquals(expected.size, result.size)
        result.forEach { list ->
            assertTrue(expected.contains(list.sorted()))
        }
    }

    fun threeSum(nums: IntArray): List<List<Int>> {
        if (nums.size == 3) {
            if (nums[0] + nums[1] + nums[2] == 0) {
                return listOf(nums.toList())
            }
            return listOf()
        }
        nums.sort()
        if (nums[0] == 0) {
            return listOf()
        }

        val result = mutableSetOf<List<Int>>()

        for (i in 0 until nums.size - 2) {
            var left = i + 1
            var right = nums.size - 1
            while (left < right) {
                val sum = nums[i] + nums[left] + nums[right]
                if (sum == 0) {
                    val tempList = listOf(nums[i], nums[left], nums[right]).sorted()
                    result.add(tempList)
                    left++
                    right--
                } else if (sum < 0) {
                    left++
                } else {
                    right--
                }
            }
        }

        return result.toList()
    }

    @Test
    fun minSubArrayLen_isCorrect() {
        val nums = intArrayOf(2, 3, 1, 2, 4, 3)
        val result = minSubArrayLen(7, nums)
        assertEquals(2, result)
    }

    fun minSubArrayLen(target: Int, nums: IntArray): Int {
        var minLen = Int.MAX_VALUE
        var left = 0
        var sum = 0
        for (right in 0 until nums.size) {
            sum += nums[right]
            while (sum >= target) {
                val currentLen = right - left + 1
                minLen = min(currentLen, minLen)
                sum -= nums[left++]
            }
        }
        return if (minLen == Int.MAX_VALUE) 0 else minLen
    }

    @Test
    fun minWindow_isCorrect() {
        val s = "ADOBECODEBANC"
        val t = "ABC"
        val result = minWindow(s, t)
        assertEquals("BANC", result)
        val anagrams = mutableSetOf<Set<String>>()

        s.toCharArray().toSet()
    }

    fun minWindow(s: String, t: String): String {
        if (s == t)
            return s

        if (s.length < t.length)
            return ""

        val tMap = mutableMapOf<Char, Int>()
        val windowMap = mutableMapOf<Char, Int>()
        t.forEach {
            tMap[it] = tMap.getOrDefault(it, 0) + 1
        }

        var minLen = Int.MAX_VALUE
        var minStart = 0
        var left = 0
        var count = 0
        for (right in s.indices) {
            val charRight = s[right]
            windowMap[charRight] = windowMap.getOrDefault(charRight, 0) + 1

            if (tMap[charRight]?.let { (windowMap[charRight] ?: 0) <= it } == true) {
                count++
            }

            while (count == t.length) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1
                    minStart = left
                }
                val charLeft = s[left++]
                windowMap[charLeft] = windowMap[charLeft]?.minus(1) ?: 0
                if (tMap[charLeft]?.let { (windowMap[charLeft] ?: 0) < it } == true) {
                    count--
                }
            }
        }

        return if (minLen == Int.MAX_VALUE) "" else s.substring(minStart, minStart + minLen)
    }

    fun subContainsAll(t: String, s: String): Boolean {
        val tList = t.toMutableList()
        s.forEach { c ->
            if (tList.contains(c)) {
                tList.remove(c)
            } else {
                return false
            }
        }
        return true
    }

    @Test
    fun isValidSudoku_isCorrect() {
        val board = arrayOf(
            charArrayOf('5', '3', '.', '.', '7', '.', '.', '.', '.'),
            charArrayOf('6', '.', '.', '1', '9', '5', '.', '.', '.'),
            charArrayOf('.', '9', '8', '.', '.', '.', '.', '6', '.'),
            charArrayOf('.', '9', '8', '.', '.', '.', '.', '6', '.'),
            charArrayOf('.', '9', '8', '.', '.', '.', '.', '6', '.'),
            charArrayOf('.', '9', '8', '.', '.', '.', '.', '6', '.'),
            charArrayOf('.', '9', '8', '.', '.', '.', '.', '6', '.'),
            charArrayOf('.', '9', '8', '.', '.', '.', '.', '6', '.'),
            charArrayOf('.', '9', '8', '.', '.', '.', '.', '6', '.')
        )
        assertFalse(isValidSudoku(board))
    }

    fun isValidSudoku(board: Array<CharArray>): Boolean {
        val columnSet = mutableSetOf<Pair<Int, Char>>()
        val subBoxSet = mutableSetOf<Pair<Int, Char>>()
        board.forEachIndexed { rowIndex, row ->
            val rowSet = mutableSetOf<Char>()
            row.forEachIndexed { columnIndex, cell ->

                val subBoxIndex = (rowIndex / 3) * 3 + (columnIndex / 3)

                if (cell.isDigit()) {
                    if (rowSet.contains(cell) || columnSet.contains(
                            Pair(
                                columnIndex,
                                cell
                            )
                        ) || subBoxSet.contains(Pair(subBoxIndex, cell))
                    ) {
                        return false
                    } else {
                        rowSet.add(cell)
                        columnSet.add(Pair(columnIndex, cell))
                        subBoxSet.add(Pair(subBoxIndex, cell))
                    }
                }
            }
        }

        return true
    }

    @Test
    fun findMaximumQuality_isCorrect() {
        assertEquals(8, findMaximumQuality(intArrayOf(1, 2, 3, 4, 5), 2))
        assertEquals(7, findMaximumQuality(intArrayOf(2, 2, 1, 5, 3), 2))
    }

    fun findMaximumQuality(packets: IntArray, channels: Int): Int {
        packets.sortDescending() // Sort in descending order

        var totalQuality = 0.0
        val channelLists = Array(channels) { mutableListOf<Int>() }
        for (i in 0 until channels - 1) {
            channelLists[i].add(packets[i])
            totalQuality += packets[i]
        }
        for (i in channels - 1 until packets.size) {
            channelLists[channels - 1].add(packets[i])
        }
        val last = channelLists[channels - 1]
        totalQuality +=
            if (last.size % 2 == 0) {
                (last[last.size / 2] + last[(last.size / 2) - 1]) / 2.0
            } else {
                last[last.size / 2].toDouble()
            }

        return ceil(totalQuality).toInt() // Round up to the next integer
    }

    @Test
    fun twoSum_isCorrect() {
        val queue: Queue<Pair<Int, Int>> = LinkedList<Pair<Int, Int>>()


        val nums = intArrayOf(2, 7, 11, 15)
        val result = twoSum2(nums, 9)
        assertArrayEquals(intArrayOf(1, 0), result)

        val nums2 = intArrayOf(3, 2, 4)
        val result2 = twoSum2(nums2, 6)
        assertArrayEquals(intArrayOf(1, 2), result2)

        val nums3 = intArrayOf(-1, -2, -3, -4, -5)
        val result3 = twoSum2(nums3, -8)
        assertArrayEquals(intArrayOf(2, 4), result3)

        val nums4 = intArrayOf(-1, -2, -3, -5, -4)
        val result4 = twoSum2(nums4, -8)
        assertArrayEquals(intArrayOf(2, 3), result4)
    }

    fun twoSum2(nums: IntArray, target: Int): IntArray {
        val map = mutableMapOf<Int, Int>()
        for (i in nums.indices) {
            val other = target - nums[i]
            if (map.containsKey(other)) {
                return intArrayOf(i, map[other]!!)
            }
            map[nums[i]] = i
        }
        return intArrayOf()
    }

    fun twoSum(nums: IntArray, target: Int): IntArray {
        var left = 0
        var right = nums.size - 1
        while (left <= right) {
            if (left == right) {
                right--
                left = 0
                continue
            }
            val sum = nums[left] + nums[right]
            if (sum == target) {
                return intArrayOf(left, right)
            } else {
                left++
            }
        }
        return intArrayOf()
    }

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
    fun rightSideView_isCorrect() {
        val root = TreeNode(4)
        root.left = TreeNode(3).apply {
            left = TreeNode(1).apply {
                right = TreeNode(2)
            }
        }
        root.right = TreeNode(5).apply {
            right = TreeNode(6)
        }

        val result = rightSideView(root)
        assertArrayEquals(intArrayOf(4, 5, 6, 2), result.toIntArray())
    }

    fun rightSideView(root: TreeNode?): List<Int> {
        if (root == null)
            return listOf<Int>()

        val list = mutableListOf<Int>()

        val q = LinkedList<TreeNode>()
        q.add(root)

        while (q.isNotEmpty()) {
            val size = q.size
            var rightValue = -1
            for (i in 0 until size) {
                val polled = q.poll()
                rightValue = polled?.`val`!!
                if (polled.left != null) q.add(polled.left!!)
                if (polled.right != null) q.add(polled.right!!)
            }
            list.add(rightValue)
        }

        return list
    }

    class ListNode(var `val`: Int) {
        var next: ListNode? = null
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
}