fun main() {

    data class Edge(val source: String, val dest: String)
    data class Node(val label: String, var visited: Boolean, val isSmall: Boolean, val neighbours: List<String>)
    fun findNeighbours(label: String, edges: List<Edge>): List<String> {
        val neighbours = mutableListOf<String>()
        for (edge in edges) {
            if (edge.source == label) neighbours.add(edge.dest)
            if (edge.dest == label) neighbours.add(edge.source)
        }
        return neighbours.toSet().toList().filterNot { it == "start" }
    }

    fun findPaths(nodes: List<Node>, label: String): Int {
        var count = 0
        if (label == "end") {
            return 1
        }
        for (node in nodes) {
            if (node.label == label) {
                if (node.isSmall) node.visited = true
                for (neighbourLabel in node.neighbours) {
                    val copyNodes = nodes.map { it.copy() }
                    if (!nodes.find { it.label == neighbourLabel }!!.visited) count += findPaths(copyNodes, neighbourLabel)
                }
            }
        }
        return count
    }

    fun findPaths2(nodes: List<Node>, label: String, joker: Boolean): Int {
        var count = 0
        if (label == "end") {
            return 1
        }
        for (node in nodes) {
            if (node.label == label) {
                if (node.isSmall) node.visited = true
                for (neighbourLabel in node.neighbours) {
                    val copyNodes = nodes.map { it.copy() }
                    if (!nodes.find { it.label == neighbourLabel }!!.visited) count += findPaths2(copyNodes, neighbourLabel, joker)
                    else if (!joker) count += findPaths2(copyNodes, neighbourLabel, true)
                }
            }
        }
        return count
    }

    fun part1(input: List<String>): Int {
        val edges = input.map { Edge(it.split("-")[0], it.split("-")[1]) }
        val rawNodes = edges.flatMap { listOf(it.source, it.dest) }.toSet().toList()
        val nodes = rawNodes.map { Node(it, false, it == it.lowercase(), findNeighbours(it, edges)) }
        return findPaths(nodes, "start")
    }

    fun part2(input: List<String>): Int {
        val edges = input.map { Edge(it.split("-")[0], it.split("-")[1]) }
        val rawNodes = edges.flatMap { listOf(it.source, it.dest) }.toSet().toList()
        val nodes = rawNodes.map { Node(it, false, it == it.lowercase(), findNeighbours(it, edges)) }
        return findPaths2(nodes, "start", false)
    }

    val input = readInput("Day12")
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 226)
    check(part2(testInput) == 3509)
    println(part1(input))
    println(part2(input))
}
