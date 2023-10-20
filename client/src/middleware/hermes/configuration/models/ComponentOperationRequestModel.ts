/* tslint:disable */
/* eslint-disable */
/**
 * Core Workflow API
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { exists, mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface ComponentOperationRequestModel
 */
export interface ComponentOperationRequestModel {
    /**
     * The connection id.
     * @type {number}
     * @memberof ComponentOperationRequestModel
     */
    connectionId: number;
    /**
     * The parameters of an action.
     * @type {{ [key: string]: object; }}
     * @memberof ComponentOperationRequestModel
     */
    parameters: { [key: string]: object; };
}

/**
 * Check if a given object implements the ComponentOperationRequestModel interface.
 */
export function instanceOfComponentOperationRequestModel(value: object): boolean {
    let isInstance = true;
    isInstance = isInstance && "connectionId" in value;
    isInstance = isInstance && "parameters" in value;

    return isInstance;
}

export function ComponentOperationRequestModelFromJSON(json: any): ComponentOperationRequestModel {
    return ComponentOperationRequestModelFromJSONTyped(json, false);
}

export function ComponentOperationRequestModelFromJSONTyped(json: any, ignoreDiscriminator: boolean): ComponentOperationRequestModel {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'connectionId': json['connectionId'],
        'parameters': json['parameters'],
    };
}

export function ComponentOperationRequestModelToJSON(value?: ComponentOperationRequestModel | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'connectionId': value.connectionId,
        'parameters': value.parameters,
    };
}
