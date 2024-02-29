import { Component } from '@angular/core';
import { CreateAdvertModel } from '../Models/createAdvertModel';
import { CreateAdvertService } from '../Services/advertsService.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-show-adverts',
  standalone: true,
  providers: [CreateAdvertService],
  imports: [],
  templateUrl: './show-adverts.component.html',
  styleUrl: './show-adverts.component.css'
})
export class ShowAdvertsComponent {

  adverts : CreateAdvertModel[];
  subscription: Subscription;

  constructor( private createAdvertService: CreateAdvertService){}

  async ngAfterViewInit(){
    console.log("from ng on in it")
    await this.getAllAdverts();
  }

  showAds(){
    this.adverts.forEach(element => {
      console.log(element)
    });
  }

  async getAllAdverts(){
    this.subscription = this.createAdvertService.getAllAdverts().subscribe((data:CreateAdvertModel[])=>{
      this.adverts = data
    });
  }

}
