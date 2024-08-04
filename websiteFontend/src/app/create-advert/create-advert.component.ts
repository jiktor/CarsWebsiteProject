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
closeAdditionalFields() {
  this.showAdditionalFields = false;
}
engineTypeOptions: string[] = ["Diesel", "Electric", "Hybrid", "Petrol"];
gearboxTypeOptions: string[] = ["Manual", "Automatic", "Semi-automatic"];
colorOptions: string[] = ["Black","White","Red","Yellow","Blue","Green","Orange","Grey"];
euroEmissionStandardOptions: string[] = ["Euro1","Euro2","Euro3","Euro4","Euro5","Euro6","Euro7"];
showAdditionalFields: boolean = false;
openAditionFields() {
  this.showAdditionalFields = true;
}
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
    gearbox: new FormControl(null),
    color: new FormControl(null),
    emissionStandard: new FormControl(null),
    engineType: new FormControl(null),
    mileage: new FormControl(null),
    horsePower: new FormControl(null),
    location: new FormControl(null),
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
      this.router.navigateByUrl('cars-advert-website/login');
   }
   else{
    this.getBrands();
   }
  }

  onSubmit(createAdvertDto : CreateAdvertModel){
    const formData = new FormData();
    for (const key of Object.keys(this.formData)) {
      formData.append(key, this.formData[key]);
    }
    for (let i = 0; i < this.selectedFiles.length; i++) {
      formData.append('images', this.selectedFiles[i]);
    }

    this.createAdvertService.createAdvertFormData(formData).subscribe({
      next: (response) => {
        // Handle the response if needed
        console.log('Advert saved successfully', response);
        // Navigate to the new route after the advert is successfully saved
        //this.router.navigateByUrl('cars-advert-website/show-adverts');
      },
      error: (error) => {
        // Handle error case
        console.error('Error saving advert', error);
      }
    });

    alert("New advert has been successfuly created")
    this.router.navigateByUrl('cars-advert-website/show-adverts');
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
    },(error =>{
      if(error.status === 403){
        alert("Your credentials have expired");
        localStorage.removeItem('jwtToken');
        this.router.navigate(['cars-advert-website/login']);
      }
    }))
  }

  getModels(forBrand: string){
    this.modelService.getModels(this.createAdvertForm.get('brand').value)
                      .subscribe((data:string[])=>{
                        this.models = data 
                      },(error =>{
                        if(error.status === 403){
                          alert("Your credentials have expired");
                          localStorage.removeItem('jwtToken');
                          this.router.navigate(['cars-advert-website/login']);
                        }
                      }));
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
