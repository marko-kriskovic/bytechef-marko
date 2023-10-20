/* tslint:disable */
/* eslint-disable */
/**
 * Definition API
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
import type { ControlTypeModel } from './ControlTypeModel';
import {
    ControlTypeModelFromJSON,
    ControlTypeModelFromJSONTyped,
    ControlTypeModelToJSON,
} from './ControlTypeModel';
import type { PropertyTypeModel } from './PropertyTypeModel';
import {
    PropertyTypeModelFromJSON,
    PropertyTypeModelFromJSONTyped,
    PropertyTypeModelToJSON,
} from './PropertyTypeModel';
import type { ValuePropertyModel } from './ValuePropertyModel';
import {
    ValuePropertyModelFromJSON,
    ValuePropertyModelFromJSONTyped,
    ValuePropertyModelToJSON,
} from './ValuePropertyModel';

/**
 * A time property.
 * @export
 * @interface TimePropertyModel
 */
export interface TimePropertyModel extends ValuePropertyModel {
    /**
     * The hour.
     * @type {number}
     * @memberof TimePropertyModel
     */
    hour?: number;
    /**
     * The minute.
     * @type {number}
     * @memberof TimePropertyModel
     */
    minute?: number;
    /**
     * The second.
     * @type {number}
     * @memberof TimePropertyModel
     */
    second?: number;
}

/**
 * Check if a given object implements the TimePropertyModel interface.
 */
export function instanceOfTimePropertyModel(value: object): boolean {
    let isInstance = true;

    return isInstance;
}

export function TimePropertyModelFromJSON(json: any): TimePropertyModel {
    return TimePropertyModelFromJSONTyped(json, false);
}

export function TimePropertyModelFromJSONTyped(json: any, ignoreDiscriminator: boolean): TimePropertyModel {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        ...ValuePropertyModelFromJSONTyped(json, ignoreDiscriminator),
        'hour': !exists(json, 'hour') ? undefined : json['hour'],
        'minute': !exists(json, 'minute') ? undefined : json['minute'],
        'second': !exists(json, 'second') ? undefined : json['second'],
    };
}

export function TimePropertyModelToJSON(value?: TimePropertyModel | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        ...ValuePropertyModelToJSON(value),
        'hour': value.hour,
        'minute': value.minute,
        'second': value.second,
    };
}
