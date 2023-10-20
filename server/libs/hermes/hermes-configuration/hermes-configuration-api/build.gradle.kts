dependencies {
    api(project(":server:libs:atlas:atlas-configuration:atlas-configuration-api"))
    api(project(":server:libs:hermes:hermes-connection:hermes-connection-api"))
    api(project(":server:libs:hermes:hermes-definition-registry:hermes-definition-registry-api"))

    implementation("org.springframework.data:spring-data-relational")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation(project(":server:libs:core:commons:commons-data"))
    implementation(project(":server:libs:core:commons:commons-util"))
    implementation(project(":server:libs:core:evaluator"))
}