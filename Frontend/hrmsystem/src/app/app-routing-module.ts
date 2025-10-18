import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Login } from './component/auth/login/login';
import { AddEmp } from './component/employee/add-emp/add-emp';
import { EmpProfile } from './component/profiles/emp-profile/emp-profile';
import { Clock } from './component/pages/clock/clock';
import { ViewEmp } from './component/employee/view-emp/view-emp';
import { AddDept } from './component/pages/department/add-dept/add-dept';
import { AddDesignation } from './component/pages/designation/add-designation/add-designation';
import { AddDeptHead } from './component/deptHead/add-dept-head/add-dept-head';
import { ViewDept } from './component/pages/department/view-dept/view-dept';
import { ViewLeave } from './component/pages/leave/view-leave/view-leave';
import { ViewHoliday } from './component/pages/holiday/view-holiday/view-holiday';
import { AddHoliday } from './component/pages/holiday/add-holiday/add-holiday';
import { AttendanceView } from './component/pages/attendance/attendance-view/attendance-view';
import { AttendanceByDept } from './component/pages/attendance/attendance-by-dept/attendance-by-dept';
import { Logout } from './component/auth/logout/logout';
import { DeptHeadProfile } from './component/profiles/dept-head-profile/dept-head-profile';
import { AdminProfile } from './component/profiles/admin-profile/admin-profile';
import { AccountantProfile } from './component/profiles/accountant-profile/accountant-profile';
import { AddSalaryToEmp } from './component/pages/salary/add-salary-to-emp/add-salary-to-emp';
import { ViewAdvance } from './component/pages/advance/view-advance/view-advance';

const routes: Routes = [
  {path: '', component:Login},
  {path: 'login', component:Login},
  {path: 'clock', component:Clock},
  {path: 'logout', component:Logout},
  { path:'register',component:AddEmp},
  {path:'viewallemp',component:ViewEmp},
  
  {path:'empprofile',component:EmpProfile},
  {path:'deptheadprofile',component:DeptHeadProfile},
  {path:'accountantprofile',component:AccountantProfile},
  {path:'adminprofile',component:AdminProfile},

  {path:'addsal',component:AddSalaryToEmp},
  {path:'advsal',component:ViewAdvance},
  {path:'addDept',component:AddDept},
  {path:'addDes',component:AddDesignation},
  {path:'deptheadadd',component:AddDeptHead},
  {path:'dept',component:ViewDept},
  {path:'leave',component:ViewLeave},
  {path:'holidayview',component:ViewHoliday},
  {path:'holidayadd',component:AddHoliday},
  {path:'attendancedash',component:AttendanceView},
  {path:'attendancebydept',component:AttendanceByDept},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
