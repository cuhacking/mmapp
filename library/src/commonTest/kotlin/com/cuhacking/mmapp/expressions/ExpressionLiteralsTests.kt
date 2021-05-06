package com.cuhacking.mmapp.expressions

import com.cuhacking.mmapp.test.assertMapboxEquivalent
import kotlin.test.Test

public expect object Literals {
    public fun number(number: Number): Any

    public fun string(string: String): Any
}

class ExpressionLiteralTests {
    @Test
    fun testLiteralNumberMapping() {
        assertMapboxEquivalent(literal(64), Literals.number(64))
    }

    @Test
    fun testLiteralStringMapping() {
        assertMapboxEquivalent(literal("Hello World"), Literals.string("Hello World"))
    }
}