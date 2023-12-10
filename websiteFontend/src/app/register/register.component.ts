import { NgIf } from '@angular/common';
import { HttpClient, HttpClientModule, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup,ReactiveFormsModule,ValidationErrors,ValidatorFn,Validators } from '@angular/forms';
import { RegisterService } from '../Services/register.service';
import { JwtAuth } from '../Models/JwtAuth';
import { RegisterModel } from '../Models/RegisterModel';
import { WidgetErrorComponent } from '../error-widget/error-widget.component';
@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule,NgIf, WidgetErrorComponent],
  providers: [HttpClientModule, RegisterService],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {
  title ='Registartion form'
  reactiveForm: FormGroup;
  jwtToken = new JwtAuth();
  err : Error | null = null;
  constructor(private registerService: RegisterService){

  }

  ngOnInit(){
    this.reactiveForm = new FormGroup({
      firstName: new FormControl(null,[this.noSpaceAllowed,Validators.required]),
      lastName: new FormControl(null,[this.noSpaceAllowed,Validators.required]),
      email : new FormControl(null, [Validators.email,Validators.required]),
      password: new FormControl(null,Validators.required),
      confirmPassword: new FormControl(null,[Validators.required])
    },{
      validators: this.passwordMatchValdiator
    });
  }
  onSubmit(registerDto: RegisterModel){
      this.Register(registerDto);       
  }
  //custom validation for whitespace
  noSpaceAllowed(control: FormControl){
    if(control.value != null && control.value.indexOf(' ') != -1){
      return {noSpaceAllowed: true}
    }
    return null;
  }

   //custom validation for matching passwords
 passwordMatchValdiator (control: AbstractControl){
    return control.get('password')?.value === control.get('confirmPassword')?.value
    ? null : {passwordMismatchError: true}
  }
  Register(registerDto: RegisterModel){
        this.registerService.register(registerDto).subscribe((jwtToken)=>{
          localStorage.setItem('jwtToken', jwtToken.token);
          console.log(localStorage.getItem('jwtToken'));
      },(error) =>{
          this.err = error.error;
      });
  }
}