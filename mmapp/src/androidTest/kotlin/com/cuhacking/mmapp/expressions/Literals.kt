package com.cuhacking.mmapp.expressions

import com.mapbox.mapboxsdk.style.expressions.Expression

public actual object Literals {
    public actual fun number(number: Number): Any = Expression.literal(number)

    public actual fun string(string: String): Any = Expression.literal(string)
}
