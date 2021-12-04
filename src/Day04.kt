fun main() {

    data class Board(private val cells: MutableList<Pair<Int, Boolean>>) {
        fun markValue(value: Int) {
            cells.forEachIndexed { index, cell ->
                if (cell.first == value) {
                    cells[index] = cell.first to true
                }
            }
        }

        fun sumOfUnmarkedCells(): Int =
            cells.filter { (_, mark) -> !mark }
                .sumOf { (value, _) -> value }

        fun hasFullRowMarked(): Boolean =
            cells.windowed(5, 5)
                .any {
                    it
                        .all { (_, mark) -> mark }
                }

        fun hasFullColumnMarked(): Boolean {
            val result = MutableList(5) { 0 }
            cells.forEachIndexed { index, pair ->
                if (pair.second) result[index % 5] += 1
            }
            return result.contains(5)
        }

        fun hasWon(): Boolean =
            hasFullRowMarked() || hasFullColumnMarked()
    }

    fun readBoards(inputLines: List<String>): List<Board> =
        inputLines
            .drop(1)
            .flatMap { it.trim().split("\\s+".toRegex()) }
            .filter { it.isNotEmpty() }
            .windowed(25, 25)
            .map { board ->
                Board(board.map { it.toInt() to false }.toMutableList())
            }

    fun part1(input: List<String>): Int {
        val numbers: List<Int> = readIntsCommaSeparated(input)
        val boards: List<Board> = readBoards(input)

        for (number in numbers) {
            for (board in boards) {
                board.markValue(number)
                if (board.hasWon())
                    return number * board.sumOfUnmarkedCells()
            }
        }
        return -1
    }

    fun part2(input: List<String>): Int {
        val numbers: List<Int> = readIntsCommaSeparated(input)
        val boards: List<Board> = readBoards(input)

        for (number in numbers) {
            for (board in boards) {
                board.markValue(number)
                if (boards.count { it.hasWon() } == boards.size) {
                    return number * board.sumOfUnmarkedCells()
                }
            }
        }
        return -1
    }

    val input = readInput("Day04")
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 1550)
    check(part2(testInput) == 24300)
    println(part1(input))
    println(part2(input))
}
