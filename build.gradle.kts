plugins {
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.serialization") version "2.0.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:2.3.12") // Ktor core
    implementation("io.ktor:ktor-server-netty:2.3.12") // Netty server engine
    implementation("io.ktor:ktor-server-content-negotiation:2.3.12") // Content negotiation plugin
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.12") // JSON serialization
    implementation("ch.qos.logback:logback-classic:1.2.10") // Logback for logging

    implementation("io.ktor:ktor-client-core:2.3.12")  // Ktor client core
    implementation("io.ktor:ktor-client-cio:2.3.12")   // CIO engine for Ktor client (HTTP engine)
    implementation("io.ktor:ktor-client-content-negotiation:2.3.12")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0") // Add this line for JSON serialization

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}