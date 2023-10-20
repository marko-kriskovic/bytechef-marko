/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.4.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.bytechef.hermes.definition.registry.web.rest;

import com.bytechef.hermes.definition.registry.web.rest.model.ActionDefinitionModel;
import com.bytechef.hermes.definition.registry.web.rest.model.ComponentDefinitionBasicModel;
import com.bytechef.hermes.definition.registry.web.rest.model.ComponentDefinitionWithBasicActionsModel;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.http.codec.multipart.Part;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-03-02T18:38:21.432374+01:00[Europe/Zagreb]")
@Validated
@Tag(name = "action-definitions", description = "the action-definitions API")
public interface ComponentDefinitionsApi {

    /**
     * GET /component-definitions/{componentName}/{componentVersion}/actions/{actionName} : Get an action of a component definition.
     * Get an action of a component definition.
     *
     * @param componentName The name of the component. (required)
     * @param componentVersion The version of the component to get. (required)
     * @param actionName The name of the action to get. (required)
     * @return Successful operation. (status code 200)
     */
    @Operation(
        operationId = "getActionDefinition",
        summary = "Get an action of a component definition.",
        description = "Get an action of a component definition.",
        tags = { "action-definitions" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ActionDefinitionModel.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/component-definitions/{componentName}/{componentVersion}/actions/{actionName}",
        produces = { "application/json" }
    )
    default Mono<ResponseEntity<ActionDefinitionModel>> getActionDefinition(
        @Parameter(name = "componentName", description = "The name of the component.", required = true, in = ParameterIn.PATH) @PathVariable("componentName") String componentName,
        @Parameter(name = "componentVersion", description = "The version of the component to get.", required = true, in = ParameterIn.PATH) @PathVariable("componentVersion") Integer componentVersion,
        @Parameter(name = "actionName", description = "The name of the action to get.", required = true, in = ParameterIn.PATH) @PathVariable("actionName") String actionName,
        @Parameter(hidden = true) final ServerWebExchange exchange
    ) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"performFunction\" : \"{}\", \"outputSchema\" : [ { \"metadata\" : { \"key\" : \"{}\" }, \"hidden\" : true, \"name\" : \"name\", \"description\" : \"description\", \"advancedOption\" : true, \"label\" : \"label\", \"placeholder\" : \"placeholder\", \"displayOption\" : { \"hide\" : { \"key\" : [ \"{}\", \"{}\" ] }, \"show\" : { \"key\" : [ \"{}\", \"{}\" ] } }, \"required\" : true }, { \"metadata\" : { \"key\" : \"{}\" }, \"hidden\" : true, \"name\" : \"name\", \"description\" : \"description\", \"advancedOption\" : true, \"label\" : \"label\", \"placeholder\" : \"placeholder\", \"displayOption\" : { \"hide\" : { \"key\" : [ \"{}\", \"{}\" ] }, \"show\" : { \"key\" : [ \"{}\", \"{}\" ] } }, \"required\" : true } ], \"exampleOutput\" : \"{}\", \"display\" : { \"subtitle\" : \"subtitle\", \"icon\" : \"icon\", \"description\" : \"description\", \"label\" : \"label\", \"category\" : \"category\", \"tags\" : [ \"tags\", \"tags\" ] }, \"name\" : \"name\", \"properties\" : [ { \"metadata\" : { \"key\" : \"{}\" }, \"hidden\" : true, \"name\" : \"name\", \"description\" : \"description\", \"advancedOption\" : true, \"label\" : \"label\", \"placeholder\" : \"placeholder\", \"displayOption\" : { \"hide\" : { \"key\" : [ \"{}\", \"{}\" ] }, \"show\" : { \"key\" : [ \"{}\", \"{}\" ] } }, \"required\" : true }, { \"metadata\" : { \"key\" : \"{}\" }, \"hidden\" : true, \"name\" : \"name\", \"description\" : \"description\", \"advancedOption\" : true, \"label\" : \"label\", \"placeholder\" : \"placeholder\", \"displayOption\" : { \"hide\" : { \"key\" : [ \"{}\", \"{}\" ] }, \"show\" : { \"key\" : [ \"{}\", \"{}\" ] } }, \"required\" : true } ] }";
                result = ApiUtil.getExampleResponse(exchange, mediaType, exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }


    /**
     * GET /component-definitions/{name}/{version} : Get a component definition.
     * Get a component definition.
     *
     * @param name The name of the component to get. (required)
     * @param version The version of the component to get. (required)
     * @return Successful operation. (status code 200)
     */
    @Operation(
        operationId = "getComponentDefinition",
        summary = "Get a component definition.",
        description = "Get a component definition.",
        tags = { "component-definitions" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation.", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ComponentDefinitionWithBasicActionsModel.class))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/component-definitions/{name}/{version}",
        produces = { "application/json" }
    )
    default Mono<ResponseEntity<ComponentDefinitionWithBasicActionsModel>> getComponentDefinition(
        @Parameter(name = "name", description = "The name of the component to get.", required = true, in = ParameterIn.PATH) @PathVariable("name") String name,
        @Parameter(name = "version", description = "The version of the component to get.", required = true, in = ParameterIn.PATH) @PathVariable("version") Integer version,
        @Parameter(hidden = true) final ServerWebExchange exchange
    ) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"display\" : { \"subtitle\" : \"subtitle\", \"icon\" : \"icon\", \"description\" : \"description\", \"label\" : \"label\", \"category\" : \"category\", \"tags\" : [ \"tags\", \"tags\" ] }, \"name\" : \"name\", \"resources\" : { \"documentationUrl\" : \"documentationUrl\" }, \"connection\" : { \"authorizationRequired\" : true, \"display\" : { \"subtitle\" : \"subtitle\", \"icon\" : \"icon\", \"description\" : \"description\", \"label\" : \"label\", \"category\" : \"category\", \"tags\" : [ \"tags\", \"tags\" ] }, \"authorizations\" : [ { \"display\" : { \"subtitle\" : \"subtitle\", \"icon\" : \"icon\", \"description\" : \"description\", \"label\" : \"label\", \"category\" : \"category\", \"tags\" : [ \"tags\", \"tags\" ] }, \"name\" : \"name\", \"properties\" : [ { \"metadata\" : { \"key\" : \"{}\" }, \"hidden\" : true, \"name\" : \"name\", \"description\" : \"description\", \"advancedOption\" : true, \"label\" : \"label\", \"placeholder\" : \"placeholder\", \"displayOption\" : { \"hide\" : { \"key\" : [ \"{}\", \"{}\" ] }, \"show\" : { \"key\" : [ \"{}\", \"{}\" ] } }, \"required\" : true }, { \"metadata\" : { \"key\" : \"{}\" }, \"hidden\" : true, \"name\" : \"name\", \"description\" : \"description\", \"advancedOption\" : true, \"label\" : \"label\", \"placeholder\" : \"placeholder\", \"displayOption\" : { \"hide\" : { \"key\" : [ \"{}\", \"{}\" ] }, \"show\" : { \"key\" : [ \"{}\", \"{}\" ] } }, \"required\" : true } ] }, { \"display\" : { \"subtitle\" : \"subtitle\", \"icon\" : \"icon\", \"description\" : \"description\", \"label\" : \"label\", \"category\" : \"category\", \"tags\" : [ \"tags\", \"tags\" ] }, \"name\" : \"name\", \"properties\" : [ { \"metadata\" : { \"key\" : \"{}\" }, \"hidden\" : true, \"name\" : \"name\", \"description\" : \"description\", \"advancedOption\" : true, \"label\" : \"label\", \"placeholder\" : \"placeholder\", \"displayOption\" : { \"hide\" : { \"key\" : [ \"{}\", \"{}\" ] }, \"show\" : { \"key\" : [ \"{}\", \"{}\" ] } }, \"required\" : true }, { \"metadata\" : { \"key\" : \"{}\" }, \"hidden\" : true, \"name\" : \"name\", \"description\" : \"description\", \"advancedOption\" : true, \"label\" : \"label\", \"placeholder\" : \"placeholder\", \"displayOption\" : { \"hide\" : { \"key\" : [ \"{}\", \"{}\" ] }, \"show\" : { \"key\" : [ \"{}\", \"{}\" ] } }, \"required\" : true } ] } ], \"resources\" : { \"documentationUrl\" : \"documentationUrl\" }, \"componentName\" : \"componentName\", \"version\" : 0, \"properties\" : [ { \"metadata\" : { \"key\" : \"{}\" }, \"hidden\" : true, \"name\" : \"name\", \"description\" : \"description\", \"advancedOption\" : true, \"label\" : \"label\", \"placeholder\" : \"placeholder\", \"displayOption\" : { \"hide\" : { \"key\" : [ \"{}\", \"{}\" ] }, \"show\" : { \"key\" : [ \"{}\", \"{}\" ] } }, \"required\" : true }, { \"metadata\" : { \"key\" : \"{}\" }, \"hidden\" : true, \"name\" : \"name\", \"description\" : \"description\", \"advancedOption\" : true, \"label\" : \"label\", \"placeholder\" : \"placeholder\", \"displayOption\" : { \"hide\" : { \"key\" : [ \"{}\", \"{}\" ] }, \"show\" : { \"key\" : [ \"{}\", \"{}\" ] } }, \"required\" : true } ] }, \"actions\" : [ { \"display\" : { \"subtitle\" : \"subtitle\", \"icon\" : \"icon\", \"description\" : \"description\", \"label\" : \"label\", \"category\" : \"category\", \"tags\" : [ \"tags\", \"tags\" ] }, \"name\" : \"name\" }, { \"display\" : { \"subtitle\" : \"subtitle\", \"icon\" : \"icon\", \"description\" : \"description\", \"label\" : \"label\", \"category\" : \"category\", \"tags\" : [ \"tags\", \"tags\" ] }, \"name\" : \"name\" } ], \"version\" : 6 }";
                result = ApiUtil.getExampleResponse(exchange, mediaType, exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }


    /**
     * GET /component-definitions/{name} : Get all component definition versions of a component definition.
     * Get all component definition versions of a component definition.
     *
     * @param name The name of the component to get. (required)
     * @return Successful operation. (status code 200)
     */
    @Operation(
        operationId = "getComponentDefinitionVersions",
        summary = "Get all component definition versions of a component definition.",
        description = "Get all component definition versions of a component definition.",
        tags = { "component-definitions" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation.", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ComponentDefinitionBasicModel.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/component-definitions/{name}",
        produces = { "application/json" }
    )
    default Mono<ResponseEntity<Flux<ComponentDefinitionBasicModel>>> getComponentDefinitionVersions(
        @Parameter(name = "name", description = "The name of the component to get.", required = true, in = ParameterIn.PATH) @PathVariable("name") String name,
        @Parameter(hidden = true) final ServerWebExchange exchange
    ) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "[ { \"display\" : { \"subtitle\" : \"subtitle\", \"icon\" : \"icon\", \"description\" : \"description\", \"label\" : \"label\", \"category\" : \"category\", \"tags\" : [ \"tags\", \"tags\" ] }, \"name\" : \"name\", \"version\" : 0 }, { \"display\" : { \"subtitle\" : \"subtitle\", \"icon\" : \"icon\", \"description\" : \"description\", \"label\" : \"label\", \"category\" : \"category\", \"tags\" : [ \"tags\", \"tags\" ] }, \"name\" : \"name\", \"version\" : 0 } ]";
                result = ApiUtil.getExampleResponse(exchange, mediaType, exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }


    /**
     * GET /component-definitions : Get all component definitions.
     * Get all component definitions.
     *
     * @param connectionDefinitions Use for filtering components which define connection definitions. (optional)
     * @param connectionInstances Use for filtering components which have connection instances created. (optional)
     * @return Successful operation. (status code 200)
     */
    @Operation(
        operationId = "getComponentDefinitions",
        summary = "Get all component definitions.",
        description = "Get all component definitions.",
        tags = { "component-definitions" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation.", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ComponentDefinitionBasicModel.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/component-definitions",
        produces = { "application/json" }
    )
    default Mono<ResponseEntity<Flux<ComponentDefinitionBasicModel>>> getComponentDefinitions(
        @Parameter(name = "connectionDefinitions", description = "Use for filtering components which define connection definitions.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "connectionDefinitions", required = false) Boolean connectionDefinitions,
        @Parameter(name = "connectionInstances", description = "Use for filtering components which have connection instances created.", in = ParameterIn.QUERY) @Valid @RequestParam(value = "connectionInstances", required = false) Boolean connectionInstances,
        @Parameter(hidden = true) final ServerWebExchange exchange
    ) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "[ { \"display\" : { \"subtitle\" : \"subtitle\", \"icon\" : \"icon\", \"description\" : \"description\", \"label\" : \"label\", \"category\" : \"category\", \"tags\" : [ \"tags\", \"tags\" ] }, \"name\" : \"name\", \"version\" : 0 }, { \"display\" : { \"subtitle\" : \"subtitle\", \"icon\" : \"icon\", \"description\" : \"description\", \"label\" : \"label\", \"category\" : \"category\", \"tags\" : [ \"tags\", \"tags\" ] }, \"name\" : \"name\", \"version\" : 0 } ]";
                result = ApiUtil.getExampleResponse(exchange, mediaType, exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }

}
