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
import type { ExecutionErrorModel } from './ExecutionErrorModel';
import {
    ExecutionErrorModelFromJSON,
    ExecutionErrorModelFromJSONTyped,
    ExecutionErrorModelToJSON,
} from './ExecutionErrorModel';
import type { WebhookModel } from './WebhookModel';
import {
    WebhookModelFromJSON,
    WebhookModelFromJSONTyped,
    WebhookModelToJSON,
} from './WebhookModel';

/**
 * Represents an execution of a workflow.
 * @export
 * @interface JobModel
 */
export interface JobModel {
    /**
     * The created by.
     * @type {string}
     * @memberof JobModel
     */
    readonly createdBy?: string;
    /**
     * The created date.
     * @type {Date}
     * @memberof JobModel
     */
    readonly createdDate?: Date;
    /**
     * The index of the step on the job's workflow on which the job is working on right now.
     * @type {number}
     * @memberof JobModel
     */
    readonly currentTask?: number;
    /**
     * The time execution entered end status COMPLETED, STOPPED, FAILED
     * @type {Date}
     * @memberof JobModel
     */
    endDate?: Date;
    /**
     * 
     * @type {ExecutionErrorModel}
     * @memberof JobModel
     */
    error?: ExecutionErrorModel;
    /**
     * The id of a job.
     * @type {string}
     * @memberof JobModel
     */
    readonly id?: string;
    /**
     * The key-value map of the inputs passed to the job when it was created.
     * @type {{ [key: string]: object; }}
     * @memberof JobModel
     */
    readonly inputs?: { [key: string]: object; };
    /**
     * The job's human-readable name.
     * @type {string}
     * @memberof JobModel
     */
    readonly label?: string;
    /**
     * The last modified by.
     * @type {string}
     * @memberof JobModel
     */
    readonly lastModifiedBy?: string;
    /**
     * The last modified date.
     * @type {Date}
     * @memberof JobModel
     */
    readonly lastModifiedDate?: Date;
    /**
     * The key-value map of the outputs returned.
     * @type {{ [key: string]: object; }}
     * @memberof JobModel
     */
    readonly outputs?: { [key: string]: object; };
    /**
     * The id of the parent task that created this job. Required for sub-flows.
     * @type {number}
     * @memberof JobModel
     */
    readonly parentTaskExecutionId?: number;
    /**
     * The priority value.
     * @type {number}
     * @memberof JobModel
     */
    readonly priority: number;
    /**
     * The time of when the job began.
     * @type {Date}
     * @memberof JobModel
     */
    readonly startDate: Date;
    /**
     * The job's status.
     * @type {string}
     * @memberof JobModel
     */
    readonly status: JobModelStatusEnum;
    /**
     * The list of the webhooks configured.
     * @type {Array<WebhookModel>}
     * @memberof JobModel
     */
    readonly webhooks?: Array<WebhookModel>;
    /**
     * 
     * @type {string}
     * @memberof JobModel
     */
    readonly workflowId?: string;
}


/**
 * @export
 */
export const JobModelStatusEnum = {
    Created: 'CREATED',
    Started: 'STARTED',
    Stopped: 'STOPPED',
    Failed: 'FAILED',
    Completed: 'COMPLETED'
} as const;
export type JobModelStatusEnum = typeof JobModelStatusEnum[keyof typeof JobModelStatusEnum];


/**
 * Check if a given object implements the JobModel interface.
 */
export function instanceOfJobModel(value: object): boolean {
    let isInstance = true;
    isInstance = isInstance && "priority" in value;
    isInstance = isInstance && "startDate" in value;
    isInstance = isInstance && "status" in value;

    return isInstance;
}

export function JobModelFromJSON(json: any): JobModel {
    return JobModelFromJSONTyped(json, false);
}

export function JobModelFromJSONTyped(json: any, ignoreDiscriminator: boolean): JobModel {
    if ((json === undefined) || (json === null)) {
        return json;
    }
    return {
        
        'createdBy': !exists(json, 'createdBy') ? undefined : json['createdBy'],
        'createdDate': !exists(json, 'createdDate') ? undefined : (new Date(json['createdDate'])),
        'currentTask': !exists(json, 'currentTask') ? undefined : json['currentTask'],
        'endDate': !exists(json, 'endDate') ? undefined : (new Date(json['endDate'])),
        'error': !exists(json, 'error') ? undefined : ExecutionErrorModelFromJSON(json['error']),
        'id': !exists(json, 'id') ? undefined : json['id'],
        'inputs': !exists(json, 'inputs') ? undefined : json['inputs'],
        'label': !exists(json, 'label') ? undefined : json['label'],
        'lastModifiedBy': !exists(json, 'lastModifiedBy') ? undefined : json['lastModifiedBy'],
        'lastModifiedDate': !exists(json, 'lastModifiedDate') ? undefined : (new Date(json['lastModifiedDate'])),
        'outputs': !exists(json, 'outputs') ? undefined : json['outputs'],
        'parentTaskExecutionId': !exists(json, 'parentTaskExecutionId') ? undefined : json['parentTaskExecutionId'],
        'priority': json['priority'],
        'startDate': (new Date(json['startDate'])),
        'status': json['status'],
        'webhooks': !exists(json, 'webhooks') ? undefined : ((json['webhooks'] as Array<any>).map(WebhookModelFromJSON)),
        'workflowId': !exists(json, 'workflowId') ? undefined : json['workflowId'],
    };
}

export function JobModelToJSON(value?: JobModel | null): any {
    if (value === undefined) {
        return undefined;
    }
    if (value === null) {
        return null;
    }
    return {
        
        'endDate': value.endDate === undefined ? undefined : (value.endDate.toISOString()),
        'error': ExecutionErrorModelToJSON(value.error),
    };
}
