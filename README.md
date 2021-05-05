# <u>M</u>ultiplatform <u>MAP</u> <u>P</u>roject

Kotlin Multiplatform Mapbox library.

### Supported Mapbox Versions

|[JS](https://github.com/mapbox/mapbox-gl-js)|[Android](https://github.com/mapbox/mapbox-gl-native-android)|[iOS](https://github.com/mapbox/mapbox-gl-native-ios)|
|:----:|:----:|:----:|
|2.2.0|9.6.1|6.3.0|

## mmapp Library

TODO

## Gradle Plugin

Since Mapbox's Android and iOS SDKs require special authentication to download, a gradle plugin is available to simplify
the configuration of multiplatform projects with Mapbox dependencies.

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