fun main() {
    fun <T> isEqual(first: List<T>, second: List<T>): Boolean = first.zip(second).all { (x, y) -> x == y }
    fun getCopy(array: MutableList<MutableList<Char>>): MutableList<MutableList<Char>> {
        val copy = MutableList(array.size) { MutableList(array[0].size) { 'a' } }
        for (i in array.indices) {
            for (j in array[i].indices) {
                copy[i][j] = array[i][j]
            }
        }
        return copy
    }

    fun readGridChars(input: List<String>): MutableList<MutableList<Char>> =
        input.map { it.toCharArray().toMutableList() }.toMutableList()

    fun step(grid: MutableList<MutableList<Char>>): MutableList<MutableList<Char>> {
        val new = getCopy(grid)
        val width = grid[0].size - 1
        val height = grid.size - 1
        new.forEachIndexed { row, chars ->
            chars.forEachIndexed { col, _ ->
                if (grid[row][col] == '>') {
                    if (col == width) {
                        if (grid[row][0] == '.') {
                            new[row][0] = '>'
                            new[row][col] = '.'
                        }
                    } else if (grid[row][col + 1] == '.') {
                        new[row][col + 1] = '>'
                        new[row][col] = '.'
                    }
                }
            }
        }
        val temp = getCopy(new)
        new.forEachIndexed { row, chars ->
            chars.forEachIndexed { col, _ ->
                if (temp[row][col] == 'v') {
                    if (row == height) {
                        if (temp[0][col] == '.') {
                            new[0][col] = 'v'
                            new[row][col] = '.'
                        }
                    } else if (temp[row + 1][col] == '.') {
                        new[row + 1][col] = 'v'
                        new[row][col] = '.'
                    }
                }
            }
        }
        return new
    }
    fun part1(input: List<String>): Int {
        var grid = readGridChars(input)
        var steps = 0
        while (true) {
            val new = step(grid)
            steps += 1
            if (isEqual(grid, new)) break
            grid = getCopy(new)
        }
        return steps
    }

    val input = readInput("Day25")
    val testInput = readInput("Day25_test")
    check(part1(testInput) == 58)
    println(part1(input))
}
