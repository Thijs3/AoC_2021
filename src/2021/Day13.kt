fun main() {
    fun readMarked(input: List<String>): List<Cell> =
        input.filter { it.contains(",") }
            .map { Cell(it.split(",")[0].toInt(), it.split(",")[1].toInt()) }

    fun readInstructions(input: List<String>): List<Pair<Char, Int>> =
        input.filter { it.contains("=") }.map { row ->
            Pair(row.split("=")[0].last(), row.split("=")[1].toInt())
        }

    fun foldX(marked: List<Cell>, split: Int): List<Cell> {
        val output = mutableListOf<Cell>()
        for (item in marked) {
            if (item.first >= split) { // maybe geq????
                output.add(Pair(split - (item.first - split), item.second))
            } else output.add(item)
        }
        return output.toSet().toList()
    }

    fun foldY(marked: List<Cell>, split: Int): List<Cell> {
        val output = mutableListOf<Cell>()
        for (item in marked) {
            if (item.second >= split) {
                output.add(Pair(item.first, split - (item.second - split)))
            } else output.add(item)
        }
        return output.toSet().toList()
    }

    fun prettyPrint(grid: List<List<String>>, title: String) {
        println("$title GRID:")
        for (row in grid) {
            for (item in row) {
                if (item == "#") print("⬜️")
                else print("⬛️")
            }
            println()
        }
    }

    fun part1(input: List<String>): Int {
        val marked = readMarked(input)
        val i = readInstructions(input)[0]
        val newMarked = if (i.first == 'x') foldX(marked, i.second) else foldY(marked, i.second)
        return newMarked.size
    }

    fun part2(input: List<String>): List<List<String>> {
        val marked = readMarked(input)
        val instructions = readInstructions(input)
        val finalMarked = instructions.fold(marked) { acc, pair ->
            if (pair.first == 'x') foldX(acc, pair.second) else foldY(acc, pair.second)
        }
        val width = finalMarked.maxOf { it.first + 1 }
        val height = finalMarked.maxOf { it.second + 1 }
        val grid = MutableList(height) { MutableList(width) { "." } }
        for (mark in finalMarked) grid[mark.second][mark.first] = "#"
        return grid
    }

    val input = readInput("Day13")
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    println(part1(input))
    prettyPrint(part2(testInput), "TEST")
    prettyPrint(part2(input), "RESULT")
}
