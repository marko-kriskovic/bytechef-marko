dependencies {
    api(project(":server:libs:helios:helios-connection:helios-connection-api"))

    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
    implementation(project(":server:libs:core:commons:commons-util"))
    implementation(project(":server:libs:helios:helios-configuration:helios-configuration-api"))
    implementation(project(":server:libs:hermes:hermes-definition-registry:hermes-definition-registry-api"))

    testImplementation("org.springframework.data:spring-data-jdbc")
    testImplementation(project(":server:libs:configs:liquibase-config"))
    testImplementation(project(":server:libs:core:category:category-service"))
    testImplementation(project(":server:libs:core:commons:commons-data"))
    testImplementation(project(":server:libs:core:encryption:encryption-api"))
    testImplementation(project(":server:libs:core:tag:tag-service"))
    testImplementation(project(":server:libs:helios:helios-configuration:helios-configuration-service"))
    testImplementation(project(":server:libs:hermes:hermes-connection:hermes-connection-service"))
    testImplementation(project(":server:libs:hermes:hermes-definition-registry:hermes-definition-registry-service"))
    testImplementation(project(":server:libs:test:test-int-support"))
}