package com.cuhacking.mmapp.expressions

public open class Expression internal constructor(public val operator: String?, public vararg val arguments: Expression) {
    internal constructor() : this(null)
}
