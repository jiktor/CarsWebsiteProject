import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { CreateAdvertService } from '../Services/advertsService.service';
import { CreateAdvertModel } from '../Models/createAdvertModel';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-my-adverts',
  standalone: true,
  providers: [CreateAdvertService],
  imports: [NgFor],
  templateUrl: './my-adverts.component.html',
  styleUrls: ['./my-adverts.component.css']
})
export class MyAdvertsComponent implements OnInit, OnDestroy {
  myAdverts: CreateAdvertModel[] = [];
  subscription: Subscription = new Subscription();
  subscriptionNumberPages: Subscription;
  numberOfPages: string = "0";
  currentPage: number = 0;

  constructor(
    private createAdvertService: CreateAdvertService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadAdverts();
    this.loadNumberOfPages();
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    if (this.subscriptionNumberPages) {
      this.subscriptionNumberPages.unsubscribe();
    }
  }

  loadAdverts(): void {
    this.subscription.add(
      this.createAdvertService.getAdvertsByOwner(this.currentPage, 5).subscribe(
        (data: CreateAdvertModel[]) => {
          this.myAdverts = data;
        },
        error => this.handleError(error)
      )
    );
  }

  loadNumberOfPages(): void {
    this.subscriptionNumberPages = this.createAdvertService.getAdvertsByOwnerCountPages(5).subscribe(
      (data: string) => {
        this.numberOfPages = data;
        console.log(data);
      },
      error => this.handleError(error)
    );
  }

  nextPage(): void {
    if (this.currentPage + 1 < parseInt(this.numberOfPages, 10)) {
      this.currentPage++;
      this.loadAdverts();
    } else {
      alert("No more pages");
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadAdverts();
    } else {
      alert("This is already the first page");
    }
  }

  deleteAdvert(id: string): void {
    this.subscription.add(
      this.createAdvertService.deleteAdvertById(id).subscribe(
        () => {

        }));
        alert("Advertisement was successfully deleted");
        this.loadAdverts();
        this.loadNumberOfPages();
  }

  private handleError(error: any): void {
    if (error.status === 403) {
      alert("Your credentials are not valid.");
      localStorage.removeItem('jwtToken');
      this.router.navigate(['cars-advert-website/login']);
    } else {
      console.error('An error occurred:', error);
      alert('An error occurred. Please try again later.');
    }
  }
}