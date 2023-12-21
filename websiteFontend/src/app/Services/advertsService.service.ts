import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CreateAdvertModel } from "../Models/createAdvertModel";

@Injectable()
export class CreateAdvertService{

    constructor(private httpClient: HttpClient){}

    getBrands(): Observable<string[]>{

      const headers = new HttpHeaders({
          'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
        });
      console.log('headr sent from service'+headers)

      return this.httpClient
                  .get<string[]>('http://localhost:8080/cars-advert-website/createAdvert/getBrands',
                                    {headers});
    }

    createAdvert(advertDto : CreateAdvertModel): Observable<HttpClient>{

      const headers = new HttpHeaders({
          'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
        });
        
     return  this.httpClient
                  .post<HttpClient>('http://localhost:8080/cars-advert-website/createAdvert/save',
                  advertDto,
                   {headers});
  }
    
}