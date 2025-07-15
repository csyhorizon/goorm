plugins {
    id("java")
}

group = "org.chinoel"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.springframework:spring-context:6.1.14")
    implementation("org.springframework:spring-tx:6.1.14")
    implementation("org.aspectj:aspectjweaver:1.9.19")
    implementation("org.aspectj:aspectjrt:1.9.19")

    implementation ("com.h2database:h2:2.1.214")
    implementation ("org.springframework:spring-jdbc:5.3.29")
    compileOnly ("org.projectlombok:lombok:1.18.20")
    annotationProcessor ("org.projectlombok:lombok:1.18.20")

}

tasks.test {
    useJUnitPlatform()
}