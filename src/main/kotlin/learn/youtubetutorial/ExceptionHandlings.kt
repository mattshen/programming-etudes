package learn.youtubetutorial

fun main(args: Array<String>) {

    val divisor = 0

    try {
        if (divisor == 0) {
            throw IllegalArgumentException("Cannot Divide by Zero")
        } else {
            println("5 / $divisor = ${5/divisor}")
        }
    } catch (e: IllegalArgumentException) {
        println("${e.message}")
    }

}