import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthorizationService } from '../Services/authorization.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authorizationService: AuthorizationService, private router: Router) {}  
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
        
      const allowedRole = next.data['allowedRole'];
      const isAuthorized = this.authorizationService.isAuthorized(allowedRole);

      if (!isAuthorized) {
        this.router.navigate(['/homepage']);
      }
      
      return isAuthorized;

  }
  canActivateChild(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
     
      const allowedRole = next.data["allowedRole"];
      const isAuthorized = this.authorizationService.isAuthorized(allowedRole);

      if (!isAuthorized) {
        this.router.navigate(['/accessdenied']);
      }
      
    return isAuthorized;
  
  }  
}
