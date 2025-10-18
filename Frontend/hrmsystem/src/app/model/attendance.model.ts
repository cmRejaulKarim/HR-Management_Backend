export interface AttendanceDTO {
    id: number;
    empId: number;
    date: string;
    checkIn: string;
    checkOut: string;
    totalWorkingTime: number;
    overtimeHours: number;
    lateCount: number;
    absent: number;
}

export interface attendModel {
    id?: number;
    empId?: number;             // employee_id foreign key
    date?: string;              // yyyy-MM-dd
    checkIn?: string;           // ISO time string (e.g. "09:15:00")
    checkOut?: string;          // ISO time string
    totalWorkingTime?: number; // decimal hours
    overtimeHours?: number;     // decimal hours if > 1
    lateCount?: number;         // late arrival count
    absent?: boolean;            // absent count
}