package com.bytechef.platform.configuration.web.rest.model;

import java.net.URI;
import java.util.Objects;
import com.bytechef.platform.configuration.web.rest.model.ControlTypeModel;
import com.bytechef.platform.configuration.web.rest.model.PropertyTypeModel;
import com.bytechef.platform.configuration.web.rest.model.ValuePropertyModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * An file entry property type.
 */

@Schema(name = "FileEntryProperty", description = "An file entry property type.")

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-01-15T17:51:13.706642+01:00[Europe/Zagreb]")
public class FileEntryPropertyModel extends ValuePropertyModel {

  @Valid
  private List<@Valid ValuePropertyModel> properties;

  public FileEntryPropertyModel() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public FileEntryPropertyModel(PropertyTypeModel type) {
    super();
  }

  public FileEntryPropertyModel properties(List<@Valid ValuePropertyModel> properties) {
    this.properties = properties;
    return this;
  }

  public FileEntryPropertyModel addPropertiesItem(ValuePropertyModel propertiesItem) {
    if (this.properties == null) {
      this.properties = new ArrayList<>();
    }
    this.properties.add(propertiesItem);
    return this;
  }

  /**
   * The list of valid file entry property types.
   * @return properties
  */
  @Valid 
  @Schema(name = "properties", description = "The list of valid file entry property types.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("properties")
  public List<@Valid ValuePropertyModel> getProperties() {
    return properties;
  }

  public void setProperties(List<@Valid ValuePropertyModel> properties) {
    this.properties = properties;
  }


  public FileEntryPropertyModel controlType(ControlTypeModel controlType) {
    super.controlType(controlType);
    return this;
  }

  public FileEntryPropertyModel label(String label) {
    super.label(label);
    return this;
  }

  public FileEntryPropertyModel placeholder(String placeholder) {
    super.placeholder(placeholder);
    return this;
  }

  public FileEntryPropertyModel advancedOption(Boolean advancedOption) {
    super.advancedOption(advancedOption);
    return this;
  }

  public FileEntryPropertyModel description(String description) {
    super.description(description);
    return this;
  }

  public FileEntryPropertyModel displayCondition(String displayCondition) {
    super.displayCondition(displayCondition);
    return this;
  }

  public FileEntryPropertyModel expressionEnabled(Boolean expressionEnabled) {
    super.expressionEnabled(expressionEnabled);
    return this;
  }

  public FileEntryPropertyModel hidden(Boolean hidden) {
    super.hidden(hidden);
    return this;
  }

  public FileEntryPropertyModel name(String name) {
    super.name(name);
    return this;
  }

  public FileEntryPropertyModel required(Boolean required) {
    super.required(required);
    return this;
  }

  public FileEntryPropertyModel type(PropertyTypeModel type) {
    super.type(type);
    return this;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FileEntryPropertyModel fileEntryProperty = (FileEntryPropertyModel) o;
    return Objects.equals(this.properties, fileEntryProperty.properties) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(properties, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FileEntryPropertyModel {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
