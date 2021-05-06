package com.cuhacking.mmapp.sources

import io.github.dellisd.spatialk.geojson.Feature
import io.github.dellisd.spatialk.geojson.FeatureCollection
import io.github.dellisd.spatialk.geojson.Geometry

public expect class GeoJsonSource(id: String, features: FeatureCollection?) {
    public val id: String

    public fun setGeoJson(json: String)

    public fun setGeoJson(feature: Feature)

    public fun setGeoJson(geometry: Geometry)

    public fun setGeoJson(featureCollection: FeatureCollection)
}
