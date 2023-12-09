import { HttpClient, HttpClientModule } from "@angular/common/http";
import { Injectable } from "@angular/core";

@Injectable()
export class RegisterService{

    constructor(private httpClient: HttpClient){}

    register(user: {firstName:string,lastName:string,email:string,password:string}){
       return  this.httpClient
                    .post('http://localhost:8080/cars-advert-website/authentication/register',
                     user)
    }

}