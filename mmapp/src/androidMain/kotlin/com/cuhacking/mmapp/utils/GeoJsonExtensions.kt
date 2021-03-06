package com.cuhacking.mmapp.utils

import android.annotation.SuppressLint
import com.google.gson.JsonObject
import io.github.dellisd.spatialk.geojson.*
import kotlinx.serialization.json.*
import com.mapbox.geojson.BoundingBox as MapboxBoundingBox
import com.mapbox.geojson.Feature as MapboxFeature
import com.mapbox.geojson.FeatureCollection as MapboxFeatureCollection
import com.mapbox.geojson.Geometry as MapboxGeometry
import com.mapbox.geojson.GeometryCollection as MapboxGeometryCollection
import com.mapbox.geojson.LineString as MapboxLineString
import com.mapbox.geojson.MultiLineString as MapboxMultiLineString
import com.mapbox.geojson.MultiPoint as MapboxMultiPoint
import com.mapbox.geojson.MultiPolygon as MapboxMultiPolygon
import com.mapbox.geojson.Point as MapboxPoint
import com.mapbox.geojson.Polygon as MapboxPolygon

internal fun BoundingBox.toMapbox(): MapboxBoundingBox = when (this.northeast.altitude) {
    null -> MapboxBoundingBox.fromLngLats(
        southwest.longitude,
        southwest.latitude,
        northeast.longitude,
        northeast.latitude
    )
    else -> MapboxBoundingBox.fromLngLats(
        southwest.longitude,
        southwest.latitude,
        southwest.altitude!!,
        northeast.longitude,
        northeast.latitude,
        northeast.altitude!!
    )
}

internal fun Position.toMapbox(): MapboxPoint = when (val alt = altitude) {
    null -> MapboxPoint.fromLngLat(longitude, latitude)
    else -> MapboxPoint.fromLngLat(longitude, latitude, alt)
}

internal fun Point.toMapbox(): MapboxPoint = when (val alt = coordinates.altitude) {
    null -> MapboxPoint.fromLngLat(coordinates.longitude, coordinates.latitude, bbox?.toMapbox())
    else -> MapboxPoint.fromLngLat(coordinates.longitude, coordinates.latitude, alt, bbox?.toMapbox())
}

internal fun MultiPoint.toMapbox(): MapboxMultiPoint =
    MapboxMultiPoint.fromLngLats(coordinates.map(Position::toMapbox), bbox?.toMapbox())

internal fun LineString.toMapbox(): MapboxLineString =
    MapboxLineString.fromLngLats(coordinates.map(Position::toMapbox), bbox?.toMapbox())

internal fun MultiLineString.toMapbox(): MapboxMultiLineString = MapboxMultiLineString.fromLngLats(
    coordinates.map { it.map(Position::toMapbox) },
    bbox?.toMapbox()
)

internal fun Polygon.toMapbox(): MapboxPolygon = MapboxPolygon.fromLngLats(
    coordinates.map { it.map(Position::toMapbox) },
    bbox?.toMapbox()
)

internal fun MultiPolygon.toMapbox(): MapboxMultiPolygon = MapboxMultiPolygon.fromLngLats(
    coordinates.map { polygon -> polygon.map { ring -> ring.map(Position::toMapbox) } },
    bbox?.toMapbox()
)

internal fun GeometryCollection.toMapbox(): MapboxGeometryCollection =
    MapboxGeometryCollection.fromGeometries(geometries.map(Geometry::toMapbox), bbox?.toMapbox())

internal fun Geometry.toMapbox(): MapboxGeometry = when (this) {
    is Point -> this.toMapbox()
    is MultiPoint -> this.toMapbox()
    is LineString -> this.toMapbox()
    is MultiLineString -> this.toMapbox()
    is Polygon -> this.toMapbox()
    is MultiPolygon -> this.toMapbox()
    is GeometryCollection -> this.toMapbox()
}

@SuppressLint("NewApi")
internal fun Feature.toMapbox(): MapboxFeature {
    val props = JsonObject()
    properties.forEach { (key, value) ->
        when {
            value is JsonNull -> props.add(key, null)
            value.jsonPrimitive.booleanOrNull != null -> props.addProperty(
                key,
                value.jsonPrimitive.boolean
            )
            value.jsonPrimitive.contentOrNull != null -> props.addProperty(
                key,
                value.jsonPrimitive.content
            )
            value.jsonPrimitive.doubleOrNull != null -> props.addProperty(
                key,
                value.jsonPrimitive.double
            )
            value.jsonPrimitive.longOrNull != null -> props.addProperty(
                key,
                value.jsonPrimitive.long
            )
            value.jsonPrimitive.floatOrNull != null -> props.addProperty(
                key,
                value.jsonPrimitive.float
            )
            value.jsonPrimitive.intOrNull != null -> props.addProperty(key, value.jsonPrimitive.int)
        }
    }
    return MapboxFeature.fromGeometry(geometry?.toMapbox(), props, id, bbox?.toMapbox())
}

internal fun FeatureCollection.toMapbox(): MapboxFeatureCollection =
    MapboxFeatureCollection.fromFeatures(features.map(Feature::toMapbox), bbox?.toMapbox())
