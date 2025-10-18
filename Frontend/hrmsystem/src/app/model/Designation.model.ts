import { Department } from "./Department.model";


export interface Designation {
    id: number;
    name?: string;   // make optional to fix the error
    department?: Department;
}