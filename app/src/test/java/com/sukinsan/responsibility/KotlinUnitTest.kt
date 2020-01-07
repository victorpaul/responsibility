package com.sukinsan.responsibility

import org.junit.Test

import org.junit.Assert.*
import kotlin.concurrent.thread
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class KotlinUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    fun threadSafeMethod(mod: String){
        for (i in 0..10) {
            print(mod)
        }

    }

    @Test
    fun chech_synchronized_method() {

        thread(start = true) {
            this.threadSafeMethod( "a")
        }
        thread(start = true) {
            this.threadSafeMethod("b")
        }
        thread(start = true) {
            this.threadSafeMethod( "c")
        }
        thread(start = true) {
            this.threadSafeMethod("d")
        }

        Thread.sleep(100)

    }

}
