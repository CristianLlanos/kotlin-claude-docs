plugins {
    kotlin("jvm") version "1.9.25"
    `java-gradle-plugin`
    `maven-publish`
}

group = "com.cristianllanos"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(kotlin("stdlib"))
}

gradlePlugin {
    plugins {
        create("claudeDocs") {
            id = "com.cristianllanos.claude-docs"
            implementationClass = "com.cristianllanos.claudedocs.ClaudeDocsPlugin"
        }
    }
}
