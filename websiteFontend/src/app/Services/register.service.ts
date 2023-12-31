import { HttpClient, HttpClientModule } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { JwtAuth } from "../Models/JwtAuth";
import { RegisterModel } from "../Models/RegisterModel";

@Injectable()
export class RegisterService{

    constructor(private httpClient: HttpClient){}

    register(registerDto : RegisterModel): Observable<JwtAuth>{
       return  this.httpClient
                    .post<JwtAuth>('http://localhost:8080/cars-advert-website/authentication/register',
                     registerDto);
    }

}