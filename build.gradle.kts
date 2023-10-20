/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    `java-library`
    `maven-publish`
    `jacoco`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation("org.mockito:mockito-core:4.11.0")
}

group = "com.agie"
version = "1.0-SNAPSHOT"
description = "inte-checkout-system"
java.sourceCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    filter {
        includeTestsMatching("com.agie.*")
        includeTestsMatching("*Test")
    }
}

jacoco {
    toolVersion = "0.8.9"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required = true
        csv.required = false
        html.required = true
    }
    // java classes
    classDirectories.setFrom(
        files(
            "${project.rootDir}/src/main/java",
            "${project.rootDir}/src/test/java"
        )
    )
    // execution data
    executionData.setFrom(
        files(
            "${project.buildDir}/jacoco/jacoco.exec"
        )
    )

    // source files
    sourceDirectories.setFrom(
        files(
            "${project.rootDir}/src/main/java",
            "${project.rootDir}/src/test/java"
        )
    )
}