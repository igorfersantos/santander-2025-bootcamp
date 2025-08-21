plugins {
    id("java")
    id("com.github.ben-manes.versions") version "0.52.0"
}

group = "br.com.dio"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.mysql:mysql-connector-j:9.4.0")
    implementation("org.flywaydb:flyway-core:11.11.1")
    implementation("org.flywaydb:flyway-mysql:11.11.1")
    implementation("net.datafaker:datafaker:2.4.4")
    implementation("org.slf4j:slf4j-simple:1.7.36")

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

}
