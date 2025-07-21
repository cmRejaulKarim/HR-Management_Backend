import { NgModule, provideBrowserGlobalErrorListeners, provideZonelessChangeDetection } from '@angular/core';
import { BrowserModule, provideClientHydration, withEventReplay } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Login } from './component/auth/login/login';
import { Registration } from './component/auth/registration/registration';
import { UserProfile } from './component/auth/user-profile/user-profile';
import { AddAttendance } from './component/pages/Attendance/add-attendance/add-attendance';
import { ViewAttendance } from './component/pages/Attendance/view-attendance/view-attendance';
import { AddAdvance } from './component/pages/Advance/add-advance/add-advance';
import { ViewAdvance } from './component/pages/Advance/view-advance/view-advance';
import { AddLeave } from './component/pages/leave/add-leave/add-leave';
import { ViewLeave } from './component/pages/leave/view-leave/view-leave';
import { AddSalary } from './component/pages/salary/add-salary/add-salary';
import { ViewSalary } from './component/pages/salary/view-salary/view-salary';

@NgModule({
  declarations: [
    App,
    Login,
    Registration,
    UserProfile,
    AddAttendance,
    ViewAttendance,
    AddAdvance,
    ViewAdvance,
    AddLeave,
    ViewLeave,
    AddSalary,
    ViewSalary
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZonelessChangeDetection(),
    provideClientHydration(withEventReplay())
  ],
  bootstrap: [App]
})
export class AppModule { }
