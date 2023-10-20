dependencies {
    api(project(":server:libs:core:message-broker:message-broker-api"))

    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("jakarta.jms:jakarta.jms-api")
    implementation("org.slf4j:slf4j-api")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-jms")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
}