import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink,RouterLinkActive, Router } from '@angular/router';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'websiteFontend';
  logged: boolean = false;
  firstName = localStorage?.getItem('firstName');
  lastName = localStorage?.getItem('lastName');

  constructor(private router: Router){}

  ngDoCheck(){
    console.log("lyfecyclehook")
    this.firstName=localStorage?.getItem('firstName');
    this.lastName=localStorage?.getItem('lastName');

    if(localStorage?.getItem('jwtToken')){
      this.logged = true;
    }else{
      this.logged=false;
    }
  }
  
  onLogout(){
    localStorage.clear()
    this.logged = false;
    this.router.navigateByUrl('cars-advert-website/advert')
  }
}
