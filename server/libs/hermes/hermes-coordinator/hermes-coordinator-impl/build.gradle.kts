dependencies {
    api(project(":server:libs:hermes:hermes-coordinator:hermes-coordinator-api"))

    implementation("org.slf4j:slf4j-api")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework.boot:spring-boot")
    implementation(project(":server:libs:core:autoconfigure-annotations"))
    implementation(project(":server:libs:core:commons:commons-util"))
    implementation(project(":server:libs:core:event:event-listener:event-listener-api"))
    implementation(project(":server:libs:core:message-broker:message-broker-api"))
}