import java.lang.Integer.MAX_VALUE
import java.util.PriorityQueue
import kotlin.Comparator
import kotlin.math.min

fun main() {
    data class Node(val row: Int, val col: Int)
    fun getNeighbours(row: Int, col: Int, width: Int): List<Node> {
        return listOfNotNull(
            if (row > 0) Node(row - 1, col) else null,
            if (row < width - 1) Node(row + 1, col) else null,
            if (col > 0) Node(row, col - 1) else null,
            if (col < width - 1) Node(row, col + 1) else null
        )
    }

    fun dijkstra(input: List<List<Int>>): Int {
        // Dijkstra
        val width = input.size
        val scores = MutableList(width) { MutableList(width) { MAX_VALUE } }
        val startNode = Node(0, 0)
        scores[startNode.row][startNode.col] = 0
        val checked = mutableListOf(startNode)
        val compareByScore: Comparator<Node> = compareBy { scores[it.row][it.col] }
        val queue = PriorityQueue(compareByScore)
        for (nb in getNeighbours(startNode.row, startNode.col, width)) {
            queue.add(nb)
            scores[nb.row][nb.col] = input[nb.row][nb.col]
        }
        while (queue.isNotEmpty()) {
            val y = queue.remove()
            checked.add(y)
            for (nb in getNeighbours(y.row, y.col, width)) {
                if (!checked.contains(nb)) {
                    if (!queue.contains(nb)) {
                        queue.add(nb)
                        scores[nb.row][nb.col] = scores[y.row][y.col] + input[nb.row][nb.col]
                    } else {
                        scores[nb.row][nb.col] =
                            min(
                                scores[nb.row][nb.col],
                                scores[y.row][y.col] + input[nb.row][nb.col]
                            )
                    }
                }
            }
        }
        return scores[width - 1][width - 1]
    }

    fun part1(input: List<List<Int>>): Int = dijkstra(input)

    fun part2(input: List<List<Int>>): Int {
        val width = input.size
        val newInput = MutableList(width * 5) { MutableList(width * 5) { 0 } }
        input.forEachIndexed { row, list -> list.forEachIndexed { col, i -> newInput[row][col] = i } }
        newInput.forEachIndexed { row, list ->
            list.forEachIndexed { col, _ ->
                if (col >= width) {
                    newInput[row][col] = (newInput[row][col - width] % 9) + 1
                } else if (row >= width) {
                    newInput[row][col] = (newInput[row - width][col] % 9) + 1
                }
            }
        }
        return dijkstra(newInput)
    }

    val input = readGrid("Day15")
    val testInput = readGrid("Day15_test")
    check(part1(testInput) == 40)
    check(part2(testInput) == 315)
    println(part1(input))
    println(part2(input))
}
