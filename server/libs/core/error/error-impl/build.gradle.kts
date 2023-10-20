dependencies {
    api(project(":server:libs:core:error:error-api"))

    implementation("org.slf4j:slf4j-api")
    implementation("org.springframework:spring-beans")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-core")
    implementation("org.springframework.boot:spring-boot")
    implementation(project(":server:libs:core:message-broker:message-broker-api"))
}