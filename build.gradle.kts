plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false

    kotlin("jvm") version "2.3.20"
    id("com.google.devtools.ksp") version "2.3.7" apply false
    id("com.google.dagger.hilt.android") version "2.59.2" apply false
}
