fun main() {

    /*
    op 1-3: set x to 0
    opv 4: div z by 1 or 26!!!!!
    op 5-7: set x to 1
    op 8-11: set y to 26
    op 12: multiply z by 26 (y)
    op 13-16: set y to w + NUMBER
    op 17: add y to z
     */
    data class Instruction(val op: String, val variable: String, val value: String)
    data class State(var w: Int, var x: Int, var y: Int, var z: Int)
    data class Cache(val op: Int, val state: State)
    fun parseInstructions(input: List<String>): List<Instruction> = input.map { Instruction(it.split(" ").first(), it.split(" ")[1], it.split(" ").last()) }
    fun part1(input: List<String>): Int {
        println(input)
        val userInputRaw = "9"
        val instructions = parseInstructions(input)
        val seen = mutableListOf(Cache(0, State(0, 0, 0, 0)))
        for (i in 9999999999999 downTo 0) {
            val s = State(0, 0, 0, 0)
            var c = 0
            val asString = i.toString()
            val userInput = (userInputRaw + asString).toCharArray()
            instructions.forEachIndexed { index, it ->
                var variable = 0
                when (it.variable) {
                    "w" -> variable += s.w
                    "x" -> variable += s.x
                    "y" -> variable += s.y
                    "z" -> variable += s.z
                }
                var value = 0
                when (it.value) {
                    "w" -> value += s.w
                    "x" -> value += s.x
                    "y" -> value += s.y
                    "z" -> value += s.z
                    else -> value += it.value.toInt()
                }
                when (it.op) {
                    "inp" -> value = userInput[c].toString().toInt()
                    "mul" -> value *= variable
                    "add" -> value += variable
                    "div" -> value = variable / value
                    "mod" -> value = variable % value
                    else -> if (value == variable) value = 1 else value = 0
                }
                if (it.op == "inp") c += 1
                when (it.variable) {
                    "w" -> s.w = value
                    "x" -> s.x = value
                    "y" -> s.y = value
                    else -> s.z = value
                }
                if (index == 13) println("here")
                if (s.z == 0 && index==13) {
                    println("answer $i")
                    return@forEachIndexed
            }
            }
            // println("state $s")
        }
        // println(instructions)
        // println("end state $s")
        return 0
    }
    fun part2(input: List<Int>): Int =
        input.windowed(3)
            .map { window -> window.sum() }
            .zipWithNext()
            .count {
                it.second > it.first
            }

    val input = readInput("Day24")
    val testInput = readInput("Day24_test")
    // check(part1(testInput) == 5)
    println(part1(testInput))
    // println(part1(input))
    // println(part2(input))
}
