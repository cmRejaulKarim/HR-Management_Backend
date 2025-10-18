export interface AttendanceMonthlySummary {
    empId: number;
    year: number;
    month: number;
    totalWorkingDays: number;
    presents: number;
    absents: number;
    lates: number;
    overtimeHours: number;
}