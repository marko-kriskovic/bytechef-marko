/* tslint:disable */
/* eslint-disable */
/**
 * The Automation Configuration API
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
import type { CategoryModel } from './CategoryModel';
import {
    CategoryModelFromJSON,
    CategoryModelFromJSONTyped,
    CategoryModelToJSON,
} from './CategoryModel';
import type { TagModel } from './TagModel';
import {
    TagModelFromJSON,
    TagModelFromJSONTyped,
    TagModelToJSON,
} from './TagModel';

/**
 * A group of workflows that make one logical project.
 * @export
 * @interface ProjectModel
 */
export interface ProjectModel {
    /**
     * 
     * @type {CategoryModel}
     * @memberof ProjectModel
     */
    category?: CategoryModel;
    /**
     * The created by.
     * @type {string}
     * @memberof ProjectModel
     */
    readonly createdBy?: string;
    /**
     * The created date.
     * @type {Date}
     * @memberof ProjectModel
     */
    readonly createdDate?: Date;
    /**
     * The description of a project.
     * @type {string}
     * @memberof ProjectModel
     */
    description?: string;
    /**
     * The id of a project.
     * @type {number}
     * @memberof ProjectModel
     */
    readonly id?: number;
    /**
     * The last modified by.
     * @type {string}
     * @memberof ProjectModel
     */
    readonly lastModifiedBy?: string;
    /**
     * The last modified date.
     * @type {Date}
     * @memberof ProjectModel
     */
    readonly lastModifiedDate?: Date;
    /**
     * The name of a project.
     * @type {string}
     * @memberof ProjectModel
     */
    name: string;
    /**
     * The published date.
     * @type {Date}
     * @memberof ProjectModel
     */
    publishedDate?: Date;
    /**
     * The version of a project.
     * @type {number}
     * @memberof ProjectModel
     */
    projectVersion?: number;
    /**
     * The status of a project.
     * @type {string}
     * @memberof ProjectModel
     */
    status?: ProjectModelStatusEnum;
    /**
     * 
     * @type {Array<TagModel>}
     * @memberof ProjectModel
     */
    tags?: Array<TagModel>;
    /**
     * The workflow ids belonging to this project.
     * @type {Array<string>}
     * @memberof ProjectModel
     */
    workflowIds?: Array<string>;
    /**
     * 
     * @type {number}
     * @memberof ProjectModel
     */
    version?: number;
}


/**
 * @export
 */
export const ProjectModelStatusEnum = {
    Published: 'PUBLISHED',
    Unpublished: 'UNPUBLISHED'
} as const;
export type ProjectModelStatusEnum = typeof ProjectModelStatusEnum[keyof typeof ProjectModelStatusEnum];


/**
 * Check if a given object implements the ProjectModel interface.
 */
export function instanceOfProjectModel(value: object): boolean {
    let isInstance = true;
    isInstance = isInstance && "name" in value;

    return isInstance;
}

export function ProjectModelFromJSON(json: any): ProjectModel {
    return ProjectModelFromJSONTyped(json, false);
}

export function ProjectModelFromJSONTyped(json: any, ignoreDiscriminator: boolean): ProjectModel {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'category': !exists(json, 'category') ? undefined : CategoryModelFromJSON(json['category']),
        'createdBy': !exists(json, 'createdBy') ? undefined : json['createdBy'],
        'createdDate': !exists(json, 'createdDate') ? undefined : (new Date(json['createdDate'])),
        'description': !exists(json, 'description') ? undefined : json['description'],
        'id': !exists(json, 'id') ? undefined : json['id'],
        'lastModifiedBy': !exists(json, 'lastModifiedBy') ? undefined : json['lastModifiedBy'],
        'lastModifiedDate': !exists(json, 'lastModifiedDate') ? undefined : (new Date(json['lastModifiedDate'])),
        'name': json['name'],
        'publishedDate': !exists(json, 'publishedDate') ? undefined : (new Date(json['publishedDate'])),
        'projectVersion': !exists(json, 'projectVersion') ? undefined : json['projectVersion'],
        'status': !exists(json, 'status') ? undefined : json['status'],
        'tags': !exists(json, 'tags') ? undefined : ((json['tags'] as Array<any>).map(TagModelFromJSON)),
        'workflowIds': !exists(json, 'workflowIds') ? undefined : json['workflowIds'],
        'version': !exists(json, '__version') ? undefined : json['__version'],
    };
}

export function ProjectModelToJSON(value?: ProjectModel | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'category': CategoryModelToJSON(value.category),
        'description': value.description,
        'name': value.name,
        'publishedDate': value.publishedDate === undefined ? undefined : (value.publishedDate.toISOString()),
        'projectVersion': value.projectVersion,
        'status': value.status,
        'tags': value.tags === undefined ? undefined : ((value.tags as Array<any>).map(TagModelToJSON)),
        'workflowIds': value.workflowIds,
        '__version': value.version,
    };
}

