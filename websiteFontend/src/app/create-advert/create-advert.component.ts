import { Component, ElementRef } from '@angular/core';
import { FormControl, FormGroup, AbstractControl, ReactiveFormsModule, Validators, FormArray} from '@angular/forms';
import { CreateAdvertModel } from '../Models/createAdvertModel';
import { NgFor, NgIf } from '@angular/common';
import { routes } from '../app.routes';
import { Router } from '@angular/router';
import { CreateAdvertService } from '../Services/advertsService.service';
import { ModelsService } from '../Services/models.services';
import { HttpClient, HttpResponse } from '@angular/common/http';


@Component({
  selector: 'app-create-advert',
  standalone: true,
  imports: [ReactiveFormsModule, NgIf, NgFor],
  providers: [CreateAdvertService,ModelsService],
  templateUrl: './create-advert.component.html',
  styleUrl: './create-advert.component.css'
})
export class CreateAdvertComponent {
  formData: any = {};
  selectedFiles: File[] = [];

  createAdvertForm: FormGroup = new FormGroup({
    brand: new FormControl(null,[Validators.required]),
    model: new FormControl(null,[Validators.required]),
    engine: new FormControl(null,[Validators.required]),
    price: new FormControl(null,[Validators.required]),
    dateOfManufacturing: new FormControl(null,[Validators.required]),
    description: new FormControl(null),
    images: new FormArray([
      new FormControl(null,Validators.required)
    ]),
  })
  err: Error | null = null;
  brands: string[];
  models: string[];
  prevBrand: string;
  http: any;


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
    const formData = new FormData();
    for (const key of Object.keys(this.formData)) {
      formData.append(key, this.formData[key]);
    }
    for (let i = 0; i < this.selectedFiles.length; i++) {
      formData.append('images', this.selectedFiles[i]);
    }

    this.createAdvertService.createAdvertFormData(formData).subscribe()
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

  createAdvert(advertDto: CreateAdvertModel){
      this.createAdvertService.createAdvert(advertDto).subscribe((response) =>{
        console.log(response);
      })
  }
  
  // addAnotherImage(){
  //   (<FormArray>this.createAdvertForm.get('images')).push(new FormControl(null,Validators.required))
  // }
  // deleteImageInput(i: number){
  //   (<FormArray>this.createAdvertForm.get('images')).removeAt(i);
  // }

  onFileSelected(event: any): void {
    if(event.target.files){
      console.log(event.target.files)
      this.selectedFiles = event.target.files;
    }
  }
}
