fun main() {
    data class Instruction(val on: Boolean, val x: IntRange, val y: IntRange, val z: IntRange)
    fun readInstructions(input: List<String>): List<Instruction> = input.map { line ->
        Instruction(
            line.split(" ")[0] == "on",
            line.split(" ")[1].split(",")[0].split("=")[1].split("..")[0].toInt()..line.split(" ")[1].split(",")[0].split("=")[1].split("..")[1].toInt(),
            line.split(" ")[1].split(",")[1].split("=")[1].split("..")[0].toInt()..line.split(" ")[1].split(",")[1].split("=")[1].split("..")[1].toInt(),
            line.split(" ")[1].split(",")[2].split("=")[1].split("..")[0].toInt()..line.split(" ")[1].split(",")[2].split("=")[1].split("..")[1].toInt()
        )
    }

    fun part1(input: List<String>): Int {
        val cube = MutableList(101) { MutableList(101) { MutableList(101) { false } } }
        val instructions = readInstructions(input)
        for (i in instructions) {
            for (x in i.x) {
                val xi = x + 50
                if (xi in 0..100) {
                    for (y in i.y) {
                        val yi = y + 50
                        if (yi in 0..100) {
                            for (z in i.z) {
                                val zi = z + 50
                                if (zi in 0..100) {
                                    cube[xi][yi][zi] = i.on
                                } else break
                            }
                        } else break
                    }
                } else break
            }
        }
        return cube.flatten().flatten().count { it }
    }

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
            val newX = listOf(this.x.first, this.x.second, other.x.first, other.x.second).sorted()
            val newY = listOf(this.y.first, this.y.second, other.y.first, other.y.second).sorted()
            val newZ = listOf(this.z.first, this.z.second, other.z.first, other.z.second).sorted()
            return Cube(Pair(newX[1], newX[2]), Pair(newY[1], newY[2]), Pair(newZ[1], newZ[2]))
        }
    }

    data class Instruction2(val on: Boolean, val cube: Cube)

    fun readInstructions2(input: List<String>): List<Instruction2> = input.map { line ->
        Instruction2(
            line.split(" ")[0] == "on",
            Cube(
                Pair(line.split(" ")[1].split(",")[0].split("=")[1].split("..")[0].toLong(), line.split(" ")[1].split(",")[0].split("=")[1].split("..")[1].toLong()),
                Pair(line.split(" ")[1].split(",")[1].split("=")[1].split("..")[0].toLong(), line.split(" ")[1].split(",")[1].split("=")[1].split("..")[1].toLong()),
                Pair(line.split(" ")[1].split(",")[2].split("=")[1].split("..")[0].toLong(), line.split(" ")[1].split(",")[2].split("=")[1].split("..")[1].toLong())
            )
        )
    }

    fun part2(input: List<String>): Long {
        val instructions = readInstructions2(input)
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
