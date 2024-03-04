import { Component } from '@angular/core';
import { CreateAdvertModel } from '../Models/createAdvertModel';
import { CreateAdvertService } from '../Services/advertsService.service';
import { Subscription } from 'rxjs';
import { NgFor } from '@angular/common';
import { NgModel } from '@angular/forms';

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

  ngOnInit(): void {
  }

  onAdvertClick(_t6: any) {
  throw new Error('Method not implemented.');
  }

  adverts : CreateAdvertModel[];
  subscription: Subscription;

  constructor( private createAdvertService: CreateAdvertService){}

  async ngAfterViewInit(){
   this.getAdvertsWithPagination();
  }

  // async getAllAdverts(){
  //   this.subscription = this.createAdvertService.getAllAdverts().subscribe((data:CreateAdvertModel[])=>{
  //     this.adverts = data
  //   });
  // }

  getAdvertsWithPagination(){
    this.subscription = this.createAdvertService.getAdvertsWithPagination("1",this.numberOfAdsPerPage).subscribe((data:CreateAdvertModel[])=>{
      this.adverts = data
    });
    this.adverts.forEach(ad => {
      console.log(ad)
    })
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

}
