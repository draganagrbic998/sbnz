import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { StorageService } from '../services/storage/storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private storageService: StorageService,
    private router: Router
  ){}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const roles = route.data.roles as string[];
    if (!roles.includes(this.storageService.getUser()?.role)){
      this.router.navigate([environment.loginRoute]);
      return false;
    }
    return true;
  }

}
