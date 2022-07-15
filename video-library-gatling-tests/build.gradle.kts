plugins {
    id("java")
    id("io.gatling.gradle") version("3.8.2")
}

repositories {
    mavenLocal()
    mavenCentral()
}

gatling {
    // WARNING: options below only work when logback config file isn't provided
    logLevel = "WARN" // logback root level
    logHttp = io.gatling.gradle.LogHttp.NONE // set to 'ALL' for all HTTP traffic in TRACE, 'FAILURES' for failed HTTP traffic in DEBUG
}

dependencies {
    gatling("org.apache.commons:commons-lang3:3.12.0")
}
