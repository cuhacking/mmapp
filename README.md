# <u>M</u>ultiplatform <u>MAP</u> <u>P</u>roject

Kotlin Multiplatform Mapbox library.

### Supported Mapbox Versions

|[JS](https://github.com/mapbox/mapbox-gl-js)|[Android](https://github.com/mapbox/mapbox-gl-native-android)|[iOS](https://github.com/mapbox/mapbox-gl-native-ios)|
|:----:|:----:|:----:|
|2.2.0|9.6.1|6.3.0|

## mmapp Library
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/com.cuhacking.mmapp/mmapp?server=https%3A%2F%2Foss.sonatype.org%2F)

### Data Sources

Currently, only GeoJSON sources are supported. A `GeoJsonSource` can be created in common code and supplied with a 
[collection of features](https://dellisd.github.io/spatial-k/geojson/#featurecollection).

```kotlin
// in commonMain
val featureCollection: FeatureCollection = getFeatures()
val dataSource = GeoJsonSource("some-data", featureCollection)
```

In Android code, the `GeoJsonSource` can be used just like a regular Mapbox source.
```kotlin
// in androidMain
mapView.getMapAsync { map ->
    map.setStyle(Style.DARK) { style ->
        style.addSource(dataSource)
    }
}
```

In JavaScript, the data source must be converted to a plain JS object before being passed to Mapbox. This can be done 
with the `.toJsObject()` method.
```jsx
// in your JS code
map.addSource(dataSource.getId(), dataSource.toJsObject())

// Alternatively in React, using a package like react-map-gl
<Source {...dataSource.toJsObject()} />
```

In iOS, the raw `MGLShapeSource` instance must be obtained from the data source using the `internalSource` property.
```swift
// in your iOS code
let source = DataSourceKt.exampleDataSource
mapView.style?.addSource(source.internalSource)
```

The eventual goal is to allow the common-defined `GeoJsonSource` to be used as though it were a natively-defined object
on all platforms, just as it is in the Android example. Due to current limitations with Kotlin/JS and Kotlin/Native, 
this isn't possible yet.

### Layers

Mapbox layers can be constructed using a DSL which provides a type-safe method to specify the style properties.

```kotlin
// in commonMain
val exampleLayer = fillLayer(id = "example-layer", sourceId = "some-data") {
    fillColor("#FF0000")
    fillOutlineColor("#00FF00")
}
```

The same rules as the `GeoJsonSource` apply to using the layer on each platform.

```kotlin
// in Android
mapView.getMapAsync { map ->
    map.setStyle(Style.DARK) { style ->
        style.addSource(exampleLayer)
    }
}
```

```js
// in plain Js
map.addLayer(exampleLayer.toJsObject())

// alternatively, in React using react-map-gl
<Layer {...exampleLayer.toJsObject()} />
```

```swift
// in iOS code
let layer = DataSourceKt.exampleLayer.getMglLayer(source: source.internalSource)
mapView.style?.addLayer(layer)
```

### Expressions

Currently, only literals are supported.

```kotlin
literal("String Literal")
literal(65) // Number Literal
colorLiteral("#FFFFFF") // Color literals, to enable iOS support
```

## Gradle Plugin
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/com.cuhacking.mmapp/gradle-plugin?server=https%3A%2F%2Foss.sonatype.org%2F)

Since Mapbox's Android and iOS SDKs require special authentication to download, an *optional* gradle plugin is available
to simplify the configuration of multiplatform projects with Mapbox dependencies. The mmapp library can still be used
without this plugin, provided you manually configure your setup.

This plugin will automatically add mapbox dependencies to your multiplatform targets for supported platforms (JS,
Android, and iOS), and also set up authentication for the Android and iOS SDKs.

> Note: Because the Mapbox-iOS-SDK cocoapod requires modifying a user's `.netrc` file, an extra line of local
> configuration must be added to enable the automatic configuration of authentication to download the iOS dependency.
> See below.

To use the plugin, apply it to your project:

```kotlin
plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")

    // mmapp plugin
    id("com.cuhacking.mmapp")
}
```

The version of Mapbox dependencies can be overridden using the plugin configuration.

```kotlin
mmapp {
    androidSdkVersion = "9.6.1"
    iosSdkVersion = "~> 6.3.0"
    jsVersion = "2.2.0"
}
```

Lastly, add your Mapbox download key to your `local.properties` file.

```properties
mapbox.download.key=YOUR-DOWNLOAD-KEY-HERE
# Add this if you want the plugin to automatically configure your ~/.netrc file
mmapp.config.netrc=true
```
