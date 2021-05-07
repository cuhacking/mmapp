package com.cuhacking.mmapp.layers

import com.cuhacking.mmapp.expressions.Expression
import kotlin.js.JsExport

@JsExport
public interface Layer {
    public val id: String
    public val sourceId: String
    public val properties: Map<String, Expression>
    public val filter: Expression?
}

public enum class Visibility(public val value: String) {
    VISIBLE("visible"),
    NONE("none")
}
