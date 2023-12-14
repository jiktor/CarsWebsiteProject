import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CreateAdvertModel } from "../Models/createAdvertModel";

@Injectable()
export class ModelsService{

    constructor(private httpClient: HttpClient){}

    public getModels(forBrand: string):Observable<string[]>{

        const headers = new HttpHeaders({
            'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
          });

          //todo трябва да оправя url
          return this.httpClient.get<string[]>
                                    ('http://localhost:8080/cars-advert-website/createAdvert/getModels/'+forBrand,
                                    {headers});
    }
}