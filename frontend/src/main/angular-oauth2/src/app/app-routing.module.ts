import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccessDeniedComponent } from './access-denied/access-denied.component';
import { AuthorizationGuard } from './authorization.guard';
import { Comp1Component } from './comp1/comp1.component';

const routes: Routes = [
  {
    path: 'app1',
    component: Comp1Component,
    canActivate: [AuthorizationGuard],
    data: {
      allowedRoles: ['admin']
    }
  },
  {
    path: 'access-denied',
    component: AccessDeniedComponent,
    data: {}
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
