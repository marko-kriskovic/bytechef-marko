dependencies {
    api(project(":server:libs:hermes:hermes-data-storage:hermes-data-storage-api"))

    implementation("org.springframework:spring-context")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.data:spring-data-relational")
    implementation(project(":server:libs:core:commons:commons-util"))

    testImplementation(project(":server:libs:configs:liquibase-config"))
    testImplementation(project(":server:libs:test:test-int-support"))
}