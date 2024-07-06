import { Component } from '@angular/core';
import { CreateAdvertModel } from '../Models/createAdvertModel';
import { CreateAdvertService } from '../Services/advertsService.service';
import { Subscription } from 'rxjs';
import { NgFor } from '@angular/common';
import { NgModel } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-show-adverts',
  standalone: true,
  providers: [CreateAdvertService],
  imports: [NgFor],
  templateUrl: './show-adverts.component.html',
  styleUrl: './show-adverts.component.css'
})
export class ShowAdvertsComponent {
  numberOfAdsPerPage = "3"
  brands: string[];
  selectedBrand: string = "";
  adverts : CreateAdvertModel[];
  subscription: Subscription;

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
                private router: Router){}

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
    });
  }

  getImageUrl(blob: Blob): string {
    if (blob instanceof Blob) {
        const reader = new FileReader();
        reader.readAsDataURL(blob);
        reader.onloadend = () => {
            return reader.result as string;
        };
    } else {
        console.error('Parameter is not a Blob:', blob);
        return ''; // or any default value
    }
    return "";
}
  onOptionSelect(event: Event){
    this.numberOfAdsPerPage = (event.target as HTMLSelectElement).value; 
    this.getAdvertsWithPagination();
  }
  getBrands(){
    return this.createAdvertService.getBrands().subscribe((data:string[])=>{
      console.log(' brands data from service' + data)
      this.brands = data
    })
  }
  onBrandSelect(event: Event){
    this.selectedBrand = (event.target as HTMLSelectElement).value;
    console.log(this.selectedBrand)
  }

}
