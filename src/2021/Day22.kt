fun main() {
    data class Cube(val x: Pair<Long, Long>, val y: Pair<Long, Long>, val z: Pair<Long, Long>) {
        private val negatedCubes = mutableListOf<Cube>()
        fun volume(): Long =
            (x.second - x.first + 1) * (y.second - y.first + 1) * (z.second - z.first + 1) - negatedCubes.sumOf { it.volume() }

        fun negate(cube: Cube) {
            val overlap = calculateOverlap(cube) ?: return
            negatedCubes.forEach { it.negate(overlap) }
            negatedCubes.add(overlap)
        }
        fun calculateOverlap(other: Cube): Cube? {
            if (this.x.first > other.x.second || this.x.second < other.x.first ||
                this.y.first > other.y.second || this.y.second < other.y.first ||
                this.z.first > other.z.second || this.z.second < other.z.first
            ) return null
            val newX = listOf(this.x.first, this.x.second, other.x.first, other.x.second).sorted().take(3).drop(1).zipWithNext().single()
            val newY = listOf(this.y.first, this.y.second, other.y.first, other.y.second).sorted().take(3).drop(1).zipWithNext().single()
            val newZ = listOf(this.z.first, this.z.second, other.z.first, other.z.second).sorted().take(3).drop(1).zipWithNext().single()
            return Cube(newX, newY, newZ)
        }
    }
    data class Instruction(val on: Boolean, val cube: Cube)

    fun readInstructions(input: List<String>): List<Instruction> = input.map { line ->
        Instruction(
            line.split(" ")[0] == "on",
            Cube(
                Pair(line.split(" ")[1].split(",")[0].split("=")[1].split("..")[0].toLong(), line.split(" ")[1].split(",")[0].split("=")[1].split("..")[1].toLong()),
                Pair(line.split(" ")[1].split(",")[1].split("=")[1].split("..")[0].toLong(), line.split(" ")[1].split(",")[1].split("=")[1].split("..")[1].toLong()),
                Pair(line.split(" ")[1].split(",")[2].split("=")[1].split("..")[0].toLong(), line.split(" ")[1].split(",")[2].split("=")[1].split("..")[1].toLong())
            )
        )
    }

    fun part1(input: List<String>): Int {
        val space = MutableList(101) { MutableList(101) { MutableList(101) { false } } }
        val instructions = readInstructions(input)
        for (i in instructions) {
            for (x in i.cube.x.first..i.cube.x.second) {
                val xi = x + 50
                if (xi in 0..100) {
                    for (y in i.cube.y.first..i.cube.y.second) {
                        val yi = y + 50
                        if (yi in 0..100) {
                            for (z in i.cube.z.first..i.cube.z.second) {
                                val zi = z + 50
                                if (zi in 0..100) {
                                    space[xi.toInt()][yi.toInt()][zi.toInt()] = i.on
                                } else break
                            }
                        } else break
                    }
                } else break
            }
        }
        return space.flatten().flatten().count { it }
    }

    fun part2(input: List<String>): Long {
        val instructions = readInstructions(input)
        val onCubes = mutableListOf<Cube>()
        instructions.forEach { instruction ->
            onCubes.forEach { cube ->
                cube.negate(instruction.cube)
            }
            if (instruction.on) onCubes.add(instruction.cube)
        }
        return onCubes.sumOf { it.volume() }
    }

    val input = readInput("Day22")
    val testInput = readInput("Day22_test")
    check(part1(testInput) == 474140)
    check(part2(testInput) == 2758514936282235)
    println(part1(input))
    println(part2(input))
}
