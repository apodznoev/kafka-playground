plugins {
    kotlin("jvm") version "1.9.22"
}

kotlin {
    jvmToolchain(21)
}


group = "de.avpod"
version = "1.0.0"

val kafkaApiVersion = "3.6.1"
val kotlinLoggingVersion = "6.0.3"
val slf4jVersion = "2.0.12"
val logbackVersion = "1.5.2"
val jacksonVersion = "2.16.+"


dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    implementation("io.github.oshai:kotlin-logging-jvm:$kotlinLoggingVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.apache.kafka:kafka-clients:$kafkaApiVersion")
    implementation("org.apache.kafka:kafka-clients:$kafkaApiVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest.attributes["Main-Class"] = "de.avpod.ArenaManagerKt"
    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree) // OR .map { zipTree(it) }
    from(dependencies)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}


repositories {
    mavenCentral()
}