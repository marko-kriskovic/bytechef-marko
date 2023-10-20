dependencies {
    api(project(":server:libs:hermes:hermes-worker:hermes-worker-api"))

    implementation("org.slf4j:slf4j-api")
    implementation("org.springframework:spring-context")
    implementation("org.springframework.boot:spring-boot")
    implementation(project(":server:libs:core:autoconfigure-annotations"))
    implementation(project(":server:libs:core:commons:commons-util"))
    implementation(project(":server:libs:core:message-broker:message-broker-api"))
    implementation(project(":server:libs:hermes:hermes-definition-registry:hermes-definition-registry-api"))
}