import { Component } from '@angular/core';
import { FormControl, FormGroup, AbstractControl, ReactiveFormsModule, Validators} from '@angular/forms';
import { CreateAdvertModel } from '../Models/createAdvertModel';
import { NgFor, NgIf } from '@angular/common';
import { routes } from '../app.routes';
import { Router } from '@angular/router';
import { CreateAdvertService } from '../Services/createModel.service';

@Component({
  selector: 'app-create-advert',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, NgFor],
  providers: [CreateAdvertService],
  templateUrl: './create-advert.component.html',
  styleUrl: './create-advert.component.css'
})
export class CreateAdvertComponent {

  createAdvertForm: FormGroup = new FormGroup({
    brand: new FormControl(null,[Validators.required]),
    model: new FormControl(null,[Validators.required]),
    engine: new FormControl(null,[Validators.required]),
    price: new FormControl(null,[Validators.required]),
    date: new FormControl(null,[Validators.required]),
    description: new FormControl(null),
  })
  err: Error | null = null;
  brands: string[];

  //todo да се инжектират услугите
  constructor(private router: Router,
              private createAdvertService: CreateAdvertService){}

  ngOnInit(){

   if(!localStorage?.getItem('jwtToken')){
      alert("You must be logged in to create new advert");
      this.router.navigateByUrl('login');
   }

    this.getBrands();
  }

  onSubmit(createAdvertDto : CreateAdvertModel){
    //todo service.http.post(model)
    console.log(createAdvertDto);
  }

  getBrands(){
    return this.createAdvertService.getBrands().subscribe((data:string[])=>{
      console.log(' brands data from service')
      this.brands = data
    })
  }

}
