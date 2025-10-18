import { NgModule, provideBrowserGlobalErrorListeners, provideZonelessChangeDetection } from '@angular/core';
import { BrowserModule, provideClientHydration, withEventReplay } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Dashboard } from './component/dashboard/dashboard';
import { Sidebar } from './component/sidebar/sidebar';
import { Login } from './component/auth/login/login';
import { Logout } from './component/auth/logout/logout';
import { EmpProfile } from './component/profiles/emp-profile/emp-profile';
import { AdminProfile } from './component/profiles/admin-profile/admin-profile';
import { DeptHeadProfile } from './component/profiles/dept-head-profile/dept-head-profile';
import { AccountantProfile } from './component/profiles/accountant-profile/accountant-profile';
import { AddEmp } from './component/employee/add-emp/add-emp';
import { provideHttpClient, withFetch, withInterceptorsFromDi } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Clock } from './component/pages/clock/clock';
import { ViewEmp } from './component/employee/view-emp/view-emp';
import { AddDept } from './component/pages/department/add-dept/add-dept';
import { ViewDept } from './component/pages/department/view-dept/view-dept';
import { AddDesignation } from './component/pages/designation/add-designation/add-designation';
import { ViewDesignation } from './component/pages/designation/view-designation/view-designation';
import { ViewDeptHead } from './component/deptHead/view-dept-head/view-dept-head';
import { AddDeptHead } from './component/deptHead/add-dept-head/add-dept-head';
import { ViewLeave } from './component/pages/leave/view-leave/view-leave';
import { ViewAdvance } from './component/pages/advance/view-advance/view-advance';
import { AddHoliday } from './component/pages/holiday/add-holiday/add-holiday';
import { ViewHoliday } from './component/pages/holiday/view-holiday/view-holiday';
import { AttendanceView } from './component/pages/attendance/attendance-view/attendance-view';
import { AttendanceByDept } from './component/pages/attendance/attendance-by-dept/attendance-by-dept';
import { ViewEmpDetails } from './component/employee/view-emp-details/view-emp-details';
import { ApprovedLeaves } from './component/pages/leave/approved-leaves/approved-leaves';
import { AddSalaryToEmp } from './component/pages/salary/add-salary-to-emp/add-salary-to-emp';
import { AddAdvance } from './component/pages/advance/add-advance/add-advance';
import { CreateMonthlySalary } from './component/pages/salary/create-monthly-salary/create-monthly-salary';
@NgModule({
  declarations: [
    App,
    Dashboard,
    Sidebar,
    Login,
    Logout,
    EmpProfile,
    AdminProfile,
    DeptHeadProfile,
    AccountantProfile,
    AddEmp,
    Clock,
    ViewEmp,
    AddDept,
    ViewDept,
    AddDesignation,
    ViewDesignation,
    AddDeptHead,
    ViewDeptHead,
    ViewLeave,
    ViewAdvance,
    AddHoliday,
    ViewHoliday,
    AttendanceView,
    AttendanceByDept,
    ViewEmpDetails,
    ApprovedLeaves,
    AddSalaryToEmp,
    AddAdvance,
    CreateMonthlySalary
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZonelessChangeDetection(),
    provideClientHydration(withEventReplay()),
    provideHttpClient(
      withFetch(),
      withInterceptorsFromDi()
    )
  ],
  bootstrap: [App]
})
export class AppModule { }
