fun main() {
    data class MutableGrid(val input: MutableList<MutableList<Pair<Int, Boolean>>>) {
        val grid = input
        operator fun get(cell: Pair<Int, Int>) = grid[cell.first][cell.second]
        operator fun set(cell: Pair<Int, Int>, value: Pair<Int, Boolean>) { grid[cell.first][cell.second] = value }
        fun getRow(row: Int) = grid[row]
        fun getColumn(col: Int) = grid.map { row -> row[col] }
    }

    fun findNeighbours(row: Int, col: Int, grid: List<List<Int>>) = listOfNotNull(
        if (row > 0) row - 1 to col else null,
        if (row < grid.size - 1) row + 1 to col else null,
        if (col > 0) row to col - 1 else null,
        if (col < grid[0].size - 1) row to col + 1 else null
    )

    fun isLocalMinimum(row: Int, col: Int, grid: List<List<Int>>): Boolean {
        val cell = grid[row][col]
        val neighbours = findNeighbours(row, col, grid)
        return neighbours.none { neighbour -> grid[neighbour.first][neighbour.second] <= cell  }
    }

    fun getNeighbours(row: Int, col: Int, grid: MutableGrid) = listOfNotNull(
        if (row > 0) row - 1 to col else null,
        if (row < grid.getColumn(0).size - 1) row + 1 to col else null,
        if (col > 0) row to col - 1 else null,
        if (col < grid.getRow(0).size - 1) row to col + 1 else null
    )

    fun findLocalMinima(grid: List<List<Int>>): List<Pair<Int, Int>> =
        grid.flatMapIndexed { row, cols -> cols.mapIndexed { col, _ -> row to col }.filter { isLocalMinimum(it.first, it.second, grid) } }

    fun findBasinSizes(input: List<List<Pair<Int, Boolean>>>, minima: List<Pair<Int, Int>>): List<Int> {
        val res = mutableListOf<Int>()
        val grid = MutableGrid(input.map { it.toMutableList() }.toMutableList())
        fun expandBasin(current: Pair<Int, Int>): Int {
            var intermediate = 1
            val neighbours = getNeighbours(current.first, current.second, grid)
            for (neighbour in neighbours) {
                if (!grid[neighbour].second) if (grid[current].first <= grid[neighbour].first && grid[neighbour].first < 9) {
                    grid[neighbour] = grid[neighbour].first to true
                    intermediate += expandBasin(neighbour)
                }
            }
            return intermediate
        }
        for (minimum in minima) {
            res.add(expandBasin(minimum))
        }
        return res
    }

    fun convertGridToPairs(grid: List<List<Int>>): List<List<Pair<Int, Boolean>>> =
        grid.map { it.map { cell -> cell to false } }

    fun part1(input: List<List<Int>>): Int =
        input.mapIndexed { row, cols ->
            cols.filterIndexed { col, _ ->
                isLocalMinimum(row, col, input)
            }
        }
            .sumOf { fr ->
                fr.sumOf { it + 1 }
            }

    fun part2(input: List<List<Int>>): Int {
        val result: List<Int> = findBasinSizes(convertGridToPairs(input), findLocalMinima(input))
            .sortedBy { it }
            .takeLast(3)
        return result[0] * result[1] * result[2]
    }

    val input = readGrid("Day09")
    val testInput = readGrid("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)
    println(part1(input))
    println(part2(input))
}
