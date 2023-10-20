
/*
 * Copyright 2021 <your company/name>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bytechef.helios.configuration.web.rest;

import com.bytechef.category.domain.Category;
import com.bytechef.category.web.rest.model.CategoryModel;
import com.bytechef.helios.configuration.facade.ProjectFacade;
import com.bytechef.helios.configuration.facade.ProjectInstanceFacade;
import com.bytechef.helios.configuration.web.rest.config.ProjectRestTestConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author Ivica Cardic
 */
@Disabled
@ContextConfiguration(classes = ProjectRestTestConfiguration.class)
@WebMvcTest(value = ProjectCategoryController.class)
public class ProjectCategoryControllerIntTest {

    @MockBean
    private ProjectInstanceFacade projectInstanceFacade;

    @MockBean
    private ProjectFacade projectFacade;

    @Autowired
    private MockMvc mockMvc;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        this.webTestClient = MockMvcWebTestClient
            .bindTo(mockMvc)
            .build();
    }

    @Test
    public void testGetProjectCategories() {
        try {
            when(projectFacade.getProjectCategories()).thenReturn(List.of(new Category(1, "name")));

            this.webTestClient
                .get()
                .uri("/automation/project-categories")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(CategoryModel.class)
                .hasSize(1);
        } catch (Exception exception) {
            Assertions.fail(exception);
        }
    }
}