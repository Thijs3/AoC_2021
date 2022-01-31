fun main() {
    fun readStartingPositions(input: List<String>) = input.map { it.split(": ")[1].toInt() }
    data class State(val score1: Int, val score2: Int, val pos1: Int, val pos2: Int, val turn1: Boolean)

    class Player(var score: Int, var position: Int) {
        fun move(moves: Int) {
            position = (position + moves) % 10
            score += position + 1
        }

        fun hasWon() = score >= 1000
    }

    class Dice(var score: Int, var rolls: Int) {
        fun roll(): Int {
            rolls += 3
            var moves = 3
            repeat(3) { moves = (moves + score); score = (score + 1) % 100 }
            return moves
        }
    }

    fun play(player1: Player, player2: Player): Int {
        val dice = Dice(0, 0)
        while (!player1.hasWon() && !player2.hasWon()) {
            player1.move(dice.roll())
            if (player1.hasWon()) break
            player2.move(dice.roll())
            if (player2.hasWon()) break
        }
        // val winningPlayer: Player = if (player1.hasWon()) player1 else player2
        val losingPlayer: Player = if (player1.hasWon()) player2 else player1
        return losingPlayer.score * dice.rolls
    }

    fun part1(input: List<String>): Int {
        val startingPositions = readStartingPositions(input)
        val player1 = Player(0, startingPositions[0] - 1)
        val player2 = Player(0, startingPositions[1] - 1)
        return play(player1, player2)
    }

    fun Pair<Long, Long>.times(amount: Long) =
        (first * amount) to (second * amount)

    fun part2(input: List<String>): Long {
        val startingPositions = readStartingPositions(input)
        val stepsPerTurn = mapOf(3 to 1L, 4 to 3L, 5 to 6L, 6 to 7L, 7 to 6L, 8 to 3L, 9 to 1L)
        fun solve(state: State): Pair<Long, Long> =
            stepsPerTurn.entries.fold(0L to 0L) {
                acc, stepsPerTurn ->
                val (steps, times) = stepsPerTurn
                var newScore1 = state.score1
                var newScore2 = state.score2
                var newPos1 = state.pos1
                var newPos2 = state.pos2
                if (state.turn1) {
                    newPos1 = (state.pos1 + steps) % 10
                    if (newPos1 == 0) newPos1 = 10
                    newScore1 = state.score1 + newPos1
                } else {
                    newPos2 = (state.pos2 + steps) % 10
                    if (newPos2 == 0) newPos2 = 10
                    newScore2 = state.score2 + newPos2
                }
                val (s1, s2) =
                    when {
                        newScore1 >= 21 -> times to 0L
                        newScore2 >= 21 -> 0L to times
                        else -> solve(State(newScore1, newScore2, newPos1, newPos2, !state.turn1)).times(times)
                    }
                (acc.first + s1) to (acc.second + s2)
            }
        val answer = solve(State(0, 0, startingPositions[0], startingPositions[1], true))
        return maxOf(answer.first, answer.second)
    }

    val input = readInput("Day21")
    val testInput = readInput("Day21_test")
    check(part1(testInput) == 739785)
    check(part2(testInput) == 444356092776315L)
    println(part1(input))
    println(part2(input))
}
