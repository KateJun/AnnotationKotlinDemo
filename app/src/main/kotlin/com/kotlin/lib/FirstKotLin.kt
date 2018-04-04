package com.kotlin.lib


/**
 * Created by Administrator on 2017/2/8.
 */
class FirstKotLin {

    private var const: Int = 1
    val name: String = "test"


    fun syso(a: Any?): Unit {
        try {
            val temp = (a) ?: fail("error ")
            println("temp = ${temp.javaClass.name}")
        } catch (e: Exception) {
            val b=null
            b
            println("error----${b}--" + e.message)
        }

        println("i am kotlin $const")
        const += 1
    }

    fun assetInt(): Boolean {
        println("cont = $const")
        println("const === 5 ? ${const === 5} const == 5 ? ${const == 5}")
        return (const !in 3..5)

        // if (const in 3..5) const==5 else false


    }

    fun fail(msg: String): Nothing {
        throw IllegalArgumentException(msg)
    }


    fun parseInts(str: Any): Int? {

        var a: Int? = try {
            Integer.parseInt(str.toString())
        } catch (e: NumberFormatException) {
            -1
        } finally {
            println("finally") //对返回值无影响
        }
        return a
    }


    fun doRequest(): Unit {
        val response =  ComonResponse("d")

        println(response.toString())
    }
}