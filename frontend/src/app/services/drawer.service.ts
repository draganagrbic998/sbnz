import { Injectable } from '@angular/core';
import { MatDrawer } from '@angular/material/sidenav';

@Injectable({
  providedIn: 'root'
})
export class DrawerService {

  constructor() { }

  drawers: MatDrawer[] = [];

  register(drawer: MatDrawer): void{
    this.drawers.push(drawer);
  }

  closeAllExceptOne(exc: MatDrawer): void{
    for (const drawer of this.drawers){
      if (drawer !== exc){
        drawer.close();
      }
    }
  }

}
