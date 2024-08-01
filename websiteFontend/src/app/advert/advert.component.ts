import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { CommonModule, NgFor, NgIf } from '@angular/common';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, Subscription } from 'rxjs';
import { CreateAdvertService } from '../Services/advertsService.service';
import { CreateAdvertModel } from '../Models/createAdvertModel';
import { trigger, transition, style, animate } from '@angular/animations';
import { OwnerModel } from '../Models/OwnerModel';
@Component({
  selector: 'app-advert',
  standalone: true,
  providers: [CreateAdvertService],
  imports: [CommonModule, NgIf,NgFor],
  templateUrl: './advert.component.html',
  styleUrl: './advert.component.css'
})
export class AdvertComponent {

    currentSlide =0;


    dummy:boolean = true;
    advertId: number;
    subscription: Subscription;
    previousAdsSubscription: Subscription;
    routeSub: Subscription;
    advert: CreateAdvertModel;
    advertHeading: string;
    isFullScreen = false;
    owner: OwnerModel;
    recentlyViewedAdverts: CreateAdvertModel[] ;

    constructor(private route: ActivatedRoute,
                private advertsService: CreateAdvertService,
                private router: Router) 
                {                 
                }
  // ngOnChanges(changes: SimpleChanges): void {
  //   this.getPrevioslyViewedAds();
  // }

  ngOnInit(): void {
    
    this.routeSub = this.route.paramMap.subscribe(params => {
      this.advertId = +params.get('advertId');
      // Perform additional logic if needed
      this.subscription = this.advertsService.getSingleAdvert(this.advertId).subscribe((data:CreateAdvertModel)=>{
        this.advert = data
      });

      this.subscription = this.advertsService.getAdvertismentOwner(this.advertId).subscribe((data:OwnerModel)=>{
        this.owner = data
      });
    });
    
    this.getPrevioslyViewedAds(this.advertId);
  }

   getPrevioslyViewedAds(advertId): void{
    this.recentlyViewedAdverts = [];
    this.previousAdsSubscription =
      this.advertsService.getPreviouslyViewdAdverts(advertId).subscribe((data:CreateAdvertModel[])=>{
        this.recentlyViewedAdverts = data
        });

        this.recentlyViewedAdverts.forEach((elemnt) =>{
          console.log("advert id: "+elemnt.id);
        })
   }

  onAdvertClicked(advertId: string) {
    this.ngOnInit()
    this.router.navigate(['/cars-advert-website/viewAdverts/getSingleAdvert', advertId]);
    
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
