import { Component } from '@angular/core';
import { CreateAdvertModel } from '../Models/createAdvertModel';
import { CreateAdvertService } from '../Services/advertsService.service';
import { Subscription } from 'rxjs';
import { NgFor } from '@angular/common';
import { NgModel } from '@angular/forms';
import { ModelsService } from '../Services/models.services';
import { Router } from '@angular/router';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-show-adverts',
  standalone: true,
  providers: [CreateAdvertService, ModelsService],
  imports: [NgFor,NgIf],
  templateUrl: './show-adverts.component.html',
  styleUrl: './show-adverts.component.css'
})
export class ShowAdvertsComponent {

  currentPage: number = 0;
  models: string[];
  numberOfAdsPerPage = "3"
  brands: string[];
  selectedBrand: string = "";
  adverts : CreateAdvertModel[];
  subscription: Subscription;
  totalPages: string = "0";
  filterBrand: string = null;
  filterModel: string = null;
  filterOrderSelect: string = "";
  filterFromPrice: string = "";
  filterToPrice: string = "";
  fromYear: string = "";
  toYear: string = "";
  fromYearHtmlVal: string = "";
  toYearHtmlVal: string = "";
  opeanDateFilter: boolean = false;


  closeDatePickerDiscardButton() {
    this.fromYear="";
    this.toYear = "";
    this.fromYearHtmlVal="";
    this.toYearHtmlVal="";
    this.opeanDateFilter=false;
  }
  toYearInput($event: Event) {
    if((event.target as HTMLSelectElement).value != ""){
      var value = (event.target as HTMLSelectElement).value + "-12-31"
      this.toYear = value
      this.toYearHtmlVal = (event.target as HTMLSelectElement).value;
    }else{
      this.toYear = ""
      this.toYearHtmlVal = "";
    }
  }
  startingYearInput($event: Event) {
    if( (event.target as HTMLSelectElement).value != ""){
      var value = (event.target as HTMLSelectElement).value + "-01-01"
      this.fromYear = value;
      this.fromYearHtmlVal = (event.target as HTMLSelectElement).value;
    }else{
      this.fromYear = "";
      this.fromYearHtmlVal = "";
    }
  }

  closeDatePicker() {
    this.opeanDateFilter = false;
    console.log(this.fromYear +" to year: " +this.toYear)
  }

  openDatePicker() {
    this.opeanDateFilter = true;
    }

  ngOnInit(): void {

  //   if(!localStorage?.getItem('jwtToken')){
  //     alert("You must be logged in to view Adverts");
  //     this.router.navigateByUrl('cars-advert-website/login');
  //  }

    this.getBrands();
    this.getNumberOfTotalPages();
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

getNumberOfTotalPages(){
  this.subscription = this.createAdvertService.getPagesCount(this.numberOfAdsPerPage).subscribe((data:string) => {
    this.totalPages = data;
    console.log("number of pages: " + this.totalPages);
  });
}
  getAdvertsWithPagination(){
    this.subscription = this.createAdvertService.getAdvertsWithPagination("0",this.numberOfAdsPerPage).subscribe((data:CreateAdvertModel[])=>{
      this.adverts = data
      console.log("@@@@@@@@@@" + this.adverts.length);
      console.log(data);
    },(error =>{
      if(error.status === 403){
        alert("Your credentials are not valid.");
        localStorage.removeItem('jwtToken');
        this.router.navigate(['cars-advert-website/login']);
      }
    }));
  }

  onOptionSelect(event: Event){
    this.numberOfAdsPerPage = (event.target as HTMLSelectElement).value; 
    //this.getNumberOfTotalPages();
    //this.getAdvertsWithPagination();
  }
  getBrands(){
    return this.createAdvertService.getBrands().subscribe((data:string[])=>{
      console.log(' brands data from service' + data)
      this.brands = data
    },(error =>{
      if(error.status === 403){
        alert("Your credentials are not valid");
        localStorage.removeItem('jwtToken');
        this.router.navigate(['cars-advert-website/login']);
      }
    }))
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
    this.currentPage=0;
    this.subscription = 
      this.createAdvertService.
      getAdvertsWithPaginationAndFilter(this.currentPage.toString(),this.numberOfAdsPerPage,this.filterBrand,this.filterModel,this.filterOrderSelect,this.filterToPrice,this.filterFromPrice,this.fromYear,this.toYear).subscribe((data:CreateAdvertModel[])=>{
        this.adverts = data
    },(error =>{
      if(error.status === 403){
        alert("Your credentials are not valid");
        localStorage.removeItem('jwtToken');
        this.router.navigate(['cars-advert-website/login']);
      }
    }));

    this.getTotalNumberOfPagesWithFilter();
  }

  getTotalNumberOfPagesWithFilter(){
    this.subscription = 
    this.createAdvertService.
      getPagesCountWithFilter(this.numberOfAdsPerPage,this.filterBrand,this.filterModel,this.filterToPrice,this.filterFromPrice).subscribe((data:string)=>{
        this.totalPages = data
    });
  }

  onAdvertClicked(advretId: string) {
    this.router.navigateByUrl('cars-advert-website/viewAdverts/getSingleAdvert/'+advretId);
  }

  nextPageEvent() {
    if(this.currentPage < parseInt(this.totalPages)-1){
      this.currentPage++;

      this.createAdvertService.
      getAdvertsWithPaginationAndFilter(this.currentPage.toString(),this.numberOfAdsPerPage,this.filterBrand,this.filterModel,this.filterOrderSelect,this.filterToPrice,this.filterFromPrice,this.fromYear,this.toYear).subscribe((data:CreateAdvertModel[])=>{
        this.adverts = data
    });
    }
  }

  previousPageEvent() {
    if(this.currentPage > 0){
      this.currentPage--;

      this.createAdvertService.
      getAdvertsWithPaginationAndFilter(this.currentPage.toString(),this.numberOfAdsPerPage,this.filterBrand,this.filterModel,this.filterOrderSelect,this.filterToPrice,this.filterFromPrice,this.fromYear,this.toYear).subscribe((data:CreateAdvertModel[])=>{
        this.adverts = data
    });
    }
  }

  lastPageEvent() {
    this.currentPage = parseInt(this.totalPages)-1;

    this.createAdvertService.
      getAdvertsWithPaginationAndFilter(this.currentPage.toString(),this.numberOfAdsPerPage,this.filterBrand,this.filterModel,this.filterOrderSelect,this.filterToPrice,this.filterFromPrice,this.fromYear,this.toYear).subscribe((data:CreateAdvertModel[])=>{
        this.adverts = data
    });
    }
  
  firstPageEvent() {
    this.currentPage = 0;

    this.createAdvertService.
      getAdvertsWithPaginationAndFilter(this.currentPage.toString(),this.numberOfAdsPerPage,this.filterBrand,this.filterModel,this.filterOrderSelect,this.filterToPrice,this.filterFromPrice,this.fromYear,this.toYear).subscribe((data:CreateAdvertModel[])=>{
        this.adverts = data
    });
  }
}
