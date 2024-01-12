dependencies {
    implementation("org.springframework:spring-context")
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.cloud:spring-cloud-commons")
    implementation(project(":server:libs:core:commons:commons-util"))
    implementation(project(":server:libs:platform:platform-component:platform-component-registry:platform-component-registry-api"))

    implementation(project(":ee:server:libs:core:commons:commons-discovery"))
    implementation(project(":ee:server:libs:core:commons:commons-rest-client"))
}