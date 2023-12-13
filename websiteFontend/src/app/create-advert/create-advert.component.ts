import { Component } from '@angular/core';
import { FormControl, FormGroup, AbstractControl, ReactiveFormsModule, Validators} from '@angular/forms';
import { CreateAdvertModel } from '../Models/createAdvertModel';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-create-advert',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf],
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

  //todo да се инжектират услугите
  constructor(){}

  ngOnInIt(){
   
  }

  onSubmit(createAdvertDto : CreateAdvertModel){
    //todo service.http.post(model)
    console.log(createAdvertDto);
  }

}
