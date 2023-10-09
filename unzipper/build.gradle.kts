plugins {
    kotlin("jvm")
}

dependencies {
    // Coroutines
    implementation(libs.kotlin.coroutines.coreJvm)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.okio)
}
