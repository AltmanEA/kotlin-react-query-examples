plugins {
    kotlin("js") version "1.5.31"
}

group = "ru.altmanea.edu"
version = "0.1"

repositories {
    jcenter()
    mavenCentral()
}

fun kotlinw(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target"

val kotlinWrappersVersion = "0.0.1-pre.242-kotlin-1.5.30"

dependencies {
    testImplementation(kotlin("test", "1.5.30"))
    implementation(enforcedPlatform(kotlinw("wrappers-bom:${kotlinWrappersVersion}")))
    implementation(kotlinw("react"))
    implementation(kotlinw("react-dom"))
    implementation(kotlinw("styled"))
    implementation(kotlinw("react-router-dom"))
    implementation(kotlinw("redux"))
    implementation(kotlinw("react-redux"))
    implementation(kotlinw("react-query"))
    implementation(npm("cross-fetch", "3.1.4"))
    implementation(npm("axios", "0.21.4"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:1.5.2")
}

kotlin {
    js(LEGACY) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}