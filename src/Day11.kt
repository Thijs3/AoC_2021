typealias Cell = Pair<Int, Int>
fun main() {
    data class Grid(private val input: MutableList<MutableList<Int>>) {
        private val cells = input
        private val width = cells.size
        val flashedThisRound = MutableList(width) { MutableList(width) { false } }
        var flashes = 0
        var stepNumber = 0
        var firstSynced: Int = 0
        var synced = false
        fun getNeighbours(row: Int, col: Int) = listOfNotNull(
            if (row != 0) Cell(row - 1, col) else null,
            if (row != width - 1) Cell(row + 1, col) else null,
            if (col != 0) Cell(row, col - 1) else null,
            if (col != width - 1) Cell(row, col + 1) else null,
            if (row != 0 && col != 0) Cell(row - 1, col - 1) else null,
            if (row != 0 && col != width - 1) Cell(row - 1, col + 1) else null,
            if (row != width - 1 && col != 0) Cell(row + 1, col - 1) else null,
            if (row != width - 1 && col != width - 1) Cell(row + 1, col + 1) else null
        )
        fun resetFlashes() {
            for (rc in getAllIndices()) {
                flashedThisRound[rc.first][rc.second] = false
            }
        }
        fun flatFlashed() = flashedThisRound.flatten()
        fun increaseCells() {
            for (rc in getAllIndices()) {
                cells[rc.first][rc.second] += 1
            }
        }
        fun setFlashedToZero() {
            val flashed = getAllIndices().filter { flashedThisRound[it.first][it.second] }
            for (flash in flashed) {
                cells[flash.first][flash.second] = 0
            }
        }
        fun flash(row: Int, col: Int) {
            flashedThisRound[row][col] = true
            flashes += 1
            val neighbours = getNeighbours(row, col)
            for (neighbour in neighbours) {
                val r = neighbour.first
                val c = neighbour.second
                cells[r][c] += 1
                if (cells[r][c] > 9 && !flashedThisRound[r][c]) {
                    flash(r, c)
                }
            }
        }
        fun getAllCells(): List<Int> = cells.flatten()
        fun getAllIndices(): List<Cell> = cells.mapIndexed { r, row ->
            row.mapIndexed { c, _ ->
                Cell(r, c)
            }
        }
            .flatten()
        fun step() {
            stepNumber += 1
            increaseCells()
            for (cell in getAllIndices()) {
                if (!flashedThisRound[cell.first][cell.second] && cells[cell.first][cell.second] > 9) {
                    flash(cell.first, cell.second)
                }
            }
            setFlashedToZero()
            if (flatFlashed().find { !it } == null) {
                firstSynced = stepNumber
                synced = true
            }
            resetFlashes()
        }
    }

    fun part1(input: MutableList<MutableList<Int>>): Int {
        val grid = Grid(input)
        for (i in 0..99) {
            grid.step()
        }
        return grid.flashes
    }

    fun part2(input: MutableList<MutableList<Int>>): Int {
        val grid2 = Grid(input)
        while (!grid2.synced) {
            grid2.step()
        }
        return grid2.firstSynced
    }

    val input = readMutableGrid("Day11")
    val inputCopy = readMutableGrid("Day11")
    val testInput = readMutableGrid("Day11_test")
    check(part1(testInput) == 1656)
    println(part1(input))
    println(part2(inputCopy))
}
