package com.cuhacking.mmapp.layers

import com.cuhacking.mmapp.expressions.Expression
import kotlin.js.JsExport

/**
 * A generic Layer with no particular styling applied.
 * All layers implement this interface. On platform-specific implementations, the classes for different layers implement
 * this interface and inherit from the platform-specific layer class.
 *
 * @property id The id of this layer, must be unique
 * @property sourceId The id of the data source from which this layer's data comes from
 * @property properties Style expressions applied to this layer
 * @property filter A predicate expression applied to the data to filter which data to display
 */
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
