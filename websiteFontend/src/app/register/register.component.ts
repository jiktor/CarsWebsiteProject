import { NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup,ReactiveFormsModule,Validators } from '@angular/forms';
@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule,NgIf],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {
  title ='Registartion form'
  reactiveForm: FormGroup;

  ngOnInit(){
    this.reactiveForm = new FormGroup({
      firstName: new FormControl(null,[this.noSpaceAllowed,Validators.required]),
      lastName: new FormControl(null,[this.noSpaceAllowed,Validators.required]),
      email : new FormControl(null, [Validators.email,Validators.required]),
      password: new FormControl(null,Validators.required),
      confirmPassword: new FormControl(null,Validators.required)
    });
  }

  onSubmit(){
    console.log(this.reactiveForm)
  }
  //custom validation for whitespace
  noSpaceAllowed(control: FormControl){
    if(control.value != null && control.value.indexOf(' ') != -1){
      return {noSpaceAllowed: true}
    }
    return null;
  }
  //custom validation for matching passwords
  // matchingPassword(control: FormControl){
  //   //console.log(control.value)
  //  // console.log((<FormGroup>this.reactiveForm).get('password').value)
  //   if(!control.value != (<FormControl>this.reactiveForm.get('password')).value){
  //     return {notMatchingPassword: true}
  //   }
  //   return null;
  // }
}
