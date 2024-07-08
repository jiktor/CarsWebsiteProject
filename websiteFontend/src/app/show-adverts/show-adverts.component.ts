import { Component } from '@angular/core';
import { CreateAdvertModel } from '../Models/createAdvertModel';
import { CreateAdvertService } from '../Services/advertsService.service';
import { Subscription } from 'rxjs';
import { NgFor } from '@angular/common';
import { NgModel } from '@angular/forms';
import { ModelsService } from '../Services/models.services';
import { Router } from '@angular/router';

@Component({
  selector: 'app-show-adverts',
  standalone: true,
  providers: [CreateAdvertService, ModelsService],
  imports: [NgFor],
  templateUrl: './show-adverts.component.html',
  styleUrl: './show-adverts.component.css'
})
export class ShowAdvertsComponent {

  models: string[];
  numberOfAdsPerPage = "3"
  brands: string[];
  selectedBrand: string = "";
  adverts : CreateAdvertModel[];
  subscription: Subscription;
  filterBrand: string = null;
  filterModel: string = null;
  filterOrderSelect: string = "";
  filterFromPrice: string = "";
  filterToPrice: string = "";

  ngOnInit(): void {

    if(!localStorage?.getItem('jwtToken')){
      alert("You must be logged in to view Adverts");
      this.router.navigateByUrl('login');
   }

    this.getBrands();
  }

  onAdvertClick(_t6: any) {
    throw new Error('Method not implemented.');
  }

  constructor( private createAdvertService: CreateAdvertService,
                private router: Router,
                private modelService: ModelsService){}

  async ngAfterViewInit(){
   this.getAdvertsWithPagination();
  }

  // async getAllAdverts(){
  //   this.subscription = this.createAdvertService.getAllAdverts().subscribe((data:CreateAdvertModel[])=>{
  //     this.adverts = data
  //   });
  // }

  getAdvertsWithPagination(){
    this.subscription = this.createAdvertService.getAdvertsWithPagination("0",this.numberOfAdsPerPage).subscribe((data:CreateAdvertModel[])=>{
      this.adverts = data
      console.log("@@@@@@@@@@" + this.adverts.length);
      console.log(data);
    });
  }

//   getImageUrl(blob: Blob): string {
//     if (blob instanceof Blob) {
//         const reader = new FileReader();
//         reader.readAsDataURL(blob);
//         reader.onloadend = () => {
//             return reader.result as string;
//         };
//     } else {
//         console.error('Parameter is not a Blob:', blob);
//         return ''; // or any default value
//     }
//     return "";
// }
  onOptionSelect(event: Event){
    this.numberOfAdsPerPage = (event.target as HTMLSelectElement).value; 
    //this.getAdvertsWithPagination();
  }
  getBrands(){
    return this.createAdvertService.getBrands().subscribe((data:string[])=>{
      console.log(' brands data from service' + data)
      this.brands = data
    })
  }
  getModels(forBrand:string){
    this.modelService.getModels(forBrand)
                      .subscribe((data:string[])=>{
                        this.models = data 
                      });
  }
  onBrandSelect(event: Event){
    this.selectedBrand = (event.target as HTMLSelectElement).value;
    console.log(this.selectedBrand)
    this.filterBrand = this.selectedBrand;
    this.getModels(this.selectedBrand);
    console.log(this.models);

    if(this.selectedBrand == "null")
      this.models = [];
    //clear previously selected model
    this.filterModel = null;
  }

  onModelSelect(event: Event) {
    this.filterModel = (event.target as HTMLSelectElement).value;
    console.log(this.filterModel);
  }

  onOrderSelect(event: Event) {
    this.filterOrderSelect = (event.target as HTMLSelectElement).value;
    console.log(this.filterOrderSelect);
  }

  OnToPriceSelect(event: Event) {
    this.filterToPrice = (event.target as HTMLSelectElement).value;
  }

  OnFromPriceSelect(event: Event) {
    this.filterFromPrice = (event.target as HTMLSelectElement).value;
    console.log("Value for From price !!!"+this.filterFromPrice)
  }
    

  applyFilter() {
    this.subscription = 
      this.createAdvertService.
      getAdvertsWithPaginationAndFilter("0",this.numberOfAdsPerPage,this.filterBrand,this.filterModel,this.filterOrderSelect,this.filterToPrice,this.filterFromPrice).subscribe((data:CreateAdvertModel[])=>{
        this.adverts = data
    });
  }
}
