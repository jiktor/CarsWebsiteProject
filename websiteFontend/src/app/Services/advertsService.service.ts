import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
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

    getAllAdverts(): Observable<CreateAdvertModel[]>{

      const headers = new HttpHeaders({
        'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
      });
      console.log('headr sent from service'+headers)

      return this.httpClient
                  .get<CreateAdvertModel[]>('http://localhost:8080/cars-advert-website/viewAdverts/all',
                                    {headers});
      
    }

    getAdvertsWithPagination(pageNumber: string, pageSize): Observable<CreateAdvertModel[]>{

      const headers = new HttpHeaders({
        'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
      });
      

      const params = new HttpParams()
      .set('pageNumber', pageNumber)
      .set('pageSize', pageSize.toString())
      .set('sortField','price')

      return this.httpClient
                  .get<CreateAdvertModel[]>('http://localhost:8080/cars-advert-website/viewAdverts/getAdverts',
                                    {headers,params});
      
    }
    getAdvertsWithPaginationAndFilter(pageNumber: string, pageSize: string, brand: string, model:string, orderSelect: string, toPrice: string, fromPrice: string): Observable<CreateAdvertModel[]>{

      const headers = new HttpHeaders({
        'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
      });

      const params = new HttpParams()
      .set('pageNumber', pageNumber)
      .set('pageSize', pageSize.toString())
      .set('sortField','price')
      .set('brand',brand)
      .set('model',model)
      .set('sortOrder', orderSelect)
      .set('fromPrice', fromPrice)
      .set('toPrice', toPrice);

      return this.httpClient
                  .get<CreateAdvertModel[]>('http://localhost:8080/cars-advert-website/viewAdverts/getAdverts',
                                    {headers,params});

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
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
  createAdvertFormData(advertDto : FormData): Observable<HttpClient>{

    const headers = new HttpHeaders({
        'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
      });
      
   return  this.httpClient
                .post<HttpClient>('http://localhost:8080/cars-advert-website/createAdvert/save',
                advertDto,
                 {headers});
}

getPagesCount(adsPerPage: string): Observable<string>{

  const headers = new HttpHeaders({
    'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
  });
  console.log('headr sent from service'+headers)

  const params = new HttpParams()
      .set('adsPerPage', adsPerPage)

  return this.httpClient
              .get<string>('http://localhost:8080/cars-advert-website/viewAdverts/countPages',
                                {headers,params});
  
}
    
}