import { Component } from '@angular/core';
import { FormControl, FormGroup, AbstractControl, ReactiveFormsModule, Validators} from '@angular/forms';
import { CreateAdvertModel } from '../Models/createAdvertModel';
import { NgFor, NgIf } from '@angular/common';
import { routes } from '../app.routes';
import { Router } from '@angular/router';
import { CreateAdvertService } from '../Services/createModel.service';
import { ModelsService } from '../Services/models.services';


@Component({
  selector: 'app-create-advert',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, NgFor],
  providers: [CreateAdvertService,ModelsService],
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
  models: string[];
  prevBrand: string;


  constructor(private router: Router,
              private createAdvertService: CreateAdvertService,
              private modelService: ModelsService){}

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

    
onBrandSelect() {
    if(this.createAdvertForm?.get('brand').value 
    && this.createAdvertForm?.get('brand').value!=this.prevBrand){
        this.getModels(this.createAdvertForm?.get('brand').value );
    }
}

  getBrands(){
    return this.createAdvertService.getBrands().subscribe((data:string[])=>{
      console.log(' brands data from service' + data)
      this.brands = data
    })
  }

  getModels(forBrand: string){
    this.modelService.getModels(this.createAdvertForm.get('brand').value)
                      .subscribe((data:string[])=>{
                        this.models = data 
                      });
  }

}
