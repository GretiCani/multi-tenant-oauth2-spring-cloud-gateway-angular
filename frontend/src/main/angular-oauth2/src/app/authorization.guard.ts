import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { AppComponent } from './app.component';

@Injectable({
  providedIn: 'root'
})
export class AuthorizationGuard implements CanActivate {

  helper = new JwtHelperService();
  constructor(private appComponent: AppComponent, private router: Router) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
      const allowedRoles = next.data.allowedRoles;
      const isAuthorized = this.isAuthorized(allowedRoles);
    console.log(next.data.allowedRoles);
    
      if (!isAuthorized) {
        this.router.navigate(['access-denied']);
      }
    
    return isAuthorized
  }

  isAuthorized(allowedRoles: string[]): boolean {
    // check if the list of allowed roles is empty, if empty, authorize the user to access the page
    if (allowedRoles == null || allowedRoles.length === 0) {
      return true;
    }
  
    // get token from local storage or state management
  
      // decode token to read the payload details
    const decodeToken = this.helper.decodeToken(this.appComponent.accessToken)['realm_access']['roles'];
    console.log('Const',decodeToken);
    
  
  // check if it was decoded successfully, if not the token is not valid, deny access
    if (!decodeToken) {
      console.log('Invalid token');
      return false;
    }
  
    const intersection = decodeToken.filter(x => allowedRoles.includes(x));

  // check if the user roles is in the list of allowed roles, return true if allowed and false if not allowed
    return intersection.length>0;
  }
  
}
