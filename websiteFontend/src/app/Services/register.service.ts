import { HttpClient, HttpClientModule } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { JwtAuth } from "../Models/JwtAuth";

@Injectable()
export class RegisterService{

    constructor(private httpClient: HttpClient){}

    register(user: {firstName:string,lastName:string,email:string,password:string}): Observable<JwtAuth>{
       return  this.httpClient
                    .post<JwtAuth>('http://localhost:8080/cars-advert-website/authentication/register',
                     user);
    }

}