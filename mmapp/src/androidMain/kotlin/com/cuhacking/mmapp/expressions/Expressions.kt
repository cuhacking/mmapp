package com.cuhacking.mmapp.expressions

import com.mapbox.mapboxsdk.style.expressions.Expression as MapboxExpression
import com.mapbox.mapboxsdk.style.expressions.Expression.ExpressionLiteral as MapboxExpressionLiteral

@Suppress("SpreadOperator")
internal fun Expression.toMapbox(): MapboxExpression = when (this) {
    is ColorExpression -> MapboxExpressionLiteral(hexColor)
    is ExpressionLiteral -> MapboxExpressionLiteral(literal)
    else -> MapboxExpression(operator!!, *arguments.map(Expression::toMapbox).toTypedArray())
}