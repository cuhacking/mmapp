package com.cuhacking.mmapp.expressions

public class ColorExpression internal constructor(public val hexColor: String) : Expression()

public fun colorLiteral(hexColor: String): Expression = ColorExpression(hexColor)
