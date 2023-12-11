import { NgIf } from '@angular/common';
import { LoginService } from '../Services/login.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, AbstractControl, FormGroup,ReactiveFormsModule, FormGroupDirective} from '@angular/forms';
import { JwtAuth } from '../Models/JwtAuth';
import { WidgetErrorComponent } from '../error-widget/error-widget.component';
import { LoginModel } from '../Models/LoginModel';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule,NgIf, WidgetErrorComponent],
  providers: [HttpClientModule, LoginService],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
  title = "Login Form"
  loginForm: FormGroup;
  jwtToken = new JwtAuth();
  err : Error | null = null;

  constructor(private loginService: LoginService,
              private router: Router){}

  ngOnInit(){
    this.loginForm = new FormGroup({
      email : new FormControl(null, null),
      password: new FormControl(null,null),
    });
  }

  onSubmit(loginDto: LoginModel){
    this.Login(loginDto);       
  }

  ngDoCheck(){
    if(localStorage?.getItem('jwtToken')) this.router.navigateByUrl('advert');
  }

  Login(loginDTO: LoginModel){
    this.loginService.login(loginDTO).subscribe((jwtToken) =>{
      localStorage.setItem('jwtToken',jwtToken.token);
      console.log(localStorage.getItem('jwtToken'))
    },(error) => {
      this.err = error.error;
    })
  }
}
