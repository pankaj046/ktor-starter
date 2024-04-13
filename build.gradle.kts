
val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposeVersion: String by project
val koinVersion: String by project
val emailVersion: String by project
val sqliteVersion: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
}

group = "app.pankaj"
version = "0.0.1"

application {
    mainClass.set("application.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")

    //DATABASE
    implementation("org.jetbrains.exposed:exposed-core:$exposeVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposeVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposeVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposeVersion")
    implementation("org.xerial:sqlite-jdbc:$sqliteVersion")

    //AUTH
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt:$ktorVersion")

    implementation("io.ktor:ktor-server-cors:$ktorVersion")

    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    //TESTING
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")

    //DI
    implementation("io.insert-koin:koin-ktor:$koinVersion")
    implementation("io.insert-koin:koin-core:$koinVersion")

    //EMAIL
    implementation("jakarta.mail:jakarta.mail-api:$emailVersion")

    implementation("io.ktor:ktor-server-status-pages:$kotlinVersion")

}
