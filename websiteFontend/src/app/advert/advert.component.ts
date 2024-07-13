import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { CreateAdvertService } from '../Services/advertsService.service';
import { CreateAdvertModel } from '../Models/createAdvertModel';
import { trigger, transition, style, animate } from '@angular/animations';
@Component({
  selector: 'app-advert',
  standalone: true,
  providers: [CreateAdvertService],
  imports: [CommonModule],
  templateUrl: './advert.component.html',
  styleUrl: './advert.component.css'
})
export class AdvertComponent {

    currentSlide =0;


    advertId: number;
    subscription: Subscription;
    routeSub: Subscription;
    advert: CreateAdvertModel;
    advertHeading: string;
    isFullScreen = false;

    constructor(private route: ActivatedRoute,
                private advertsService: CreateAdvertService) 
                { }

  ngOnInit(): void {
    this.routeSub = this.route.paramMap.subscribe(params => {
      this.advertId = +params.get('advertId');
      // Perform additional logic if needed
      this.subscription = this.advertsService.getSingleAdvert(this.advertId).subscribe((data:CreateAdvertModel)=>{
        this.advert = data
        console.log("@@@@@@@@@@" + this.advert.price);
      });
    });
    console.log("the Id of the advert: "+this.advertId);
    // Use carId to fetch the car details from a service or API
    //this.advertHeading = this.advert.brand + " " + this.advert.model;
    console.log("!!!!"+this.advertHeading);
  }

  
  toggleFullScreen() {
    this.isFullScreen = !this.isFullScreen;
  }

  nextSlide() {
    this.currentSlide = (this.currentSlide + 1) % this.advert.images.length;
  }

  prevSlide() {
    this.currentSlide = (this.currentSlide - 1 + this.advert.images.length) % this.advert.images.length;
  }
}
