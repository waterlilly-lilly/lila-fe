plugins {
    kotlin("js") version "1.6.10"
}

group = "dev.waterlilly"
version = "1.0.0"

repositories {
    mavenCentral()
}



kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}