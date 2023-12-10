import { HttpClient, HttpClientModule } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { JwtAuth } from "../Models/JwtAuth";
import { LoginModel } from "../Models/LoginModel";

@Injectable()
export class LoginService{

    constructor(private httpClient: HttpClient){}

    login(LoginModel): Observable<JwtAuth>{
       return  this.httpClient
                    .post<JwtAuth>('http://localhost:8080/cars-advert-website/authentication/authenticate',
                     LoginModel);
    }

}