import { HttpClient, HttpHeaders, HttpParams } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { CreateAdvertModel } from "../Models/createAdvertModel";
import { OwnerModel } from "../Models/OwnerModel";

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

    getSingleAdvert(advertId): Observable<CreateAdvertModel>{

      const headers = new HttpHeaders({
        'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
      });

      const params = new HttpParams()
      .set('advertId', advertId)

      return this.httpClient.get<CreateAdvertModel>('http://localhost:8080/cars-advert-website/viewAdverts/getSingleAdvert',{headers,params});
    }

    //doniq servis se izpolzwa -> veroqtno go izpolzwam pyrwiq pyt pri otwarqne na stranica -> toest moje da se mahne
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
    
    getAdvertsWithPaginationAndFilter(pageNumber: string, pageSize: string, brand: string, model:string, orderSelect: string, toPrice: string, fromPrice: string, fromYear: string, toYear: string): Observable<CreateAdvertModel[]>{

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
      .set('toPrice', toPrice)
      .set('fromDate',fromYear)
      .set('toDate',toYear);

      return this.httpClient
                  .get<CreateAdvertModel[]>('http://localhost:8080/cars-advert-website/viewAdverts/getAdverts',
                                    {headers,params});

    }


//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!dolniq service e realbiq kojto se izpolwa !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!//
  createAdvertFormData(advertDto : FormData): Observable<HttpClient>{

    const headers = new HttpHeaders({
        'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
      });
      
   return  this.httpClient
                .post<HttpClient>('http://localhost:8080/cars-advert-website/createAdvert/saveAdvert',
                advertDto,
                 {headers});
}

//used initially when loading page with adverts to show count of pages without filter
getPagesCount(adsPerPage: string): Observable<string>{

  const headers = new HttpHeaders({
    'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
  });
  console.log('headr sent from service'+headers)

  const params = new HttpParams()
      .set('adsPerPage', adsPerPage)

  return this.httpClient
              .get<string>('http://localhost:8080/cars-advert-website/viewAdverts/getAdvertsCountWithFilter',
                                {headers,params});
  
}

getPagesCountWithFilter(pageSize: string, brand: string, model:string, toPrice: string, fromPrice: string): Observable<string>{

  const headers = new HttpHeaders({
    'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
  });
  console.log('headr sent from service'+headers)

  const params = new HttpParams()
      .set('pageSize', pageSize.toString())
      .set('sortField','price')
      .set('brand',brand)
      .set('model',model)
      .set('fromPrice', fromPrice)
      .set('toPrice', toPrice);

  return this.httpClient
              .get<string>('http://localhost:8080/cars-advert-website/viewAdverts/getAdvertsCountWithFilter',
                                {headers,params});
  
}

getAdvertismentOwner (advertId: number): Observable<OwnerModel>{

  const headers = new HttpHeaders({
    'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
  });

  const params = new HttpParams()
      .set('advertId', advertId)

  return this.httpClient
              .get<OwnerModel>('http://localhost:8080/cars-advert-website/viewAdverts/getOwnerOfSingleAdvert',
                                {headers,params});
  
}

getPreviouslyViewdAdverts (advertId): Observable<CreateAdvertModel[]>{

  const headers = new HttpHeaders({
    'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
  });

  const params = new HttpParams()
      .set('advertId', advertId)

  return this.httpClient
  .get<CreateAdvertModel[]>('http://localhost:8080/cars-advert-website/viewAdverts/getRecentlyViewed',
                    {headers,params});

}

getAdvertsByOwner(pageNumber,pageSize): Observable<CreateAdvertModel[]>{

  const headers = new HttpHeaders({
    'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
  });

  const params = new HttpParams()
  .set('pageNumber', pageNumber)
  .set('pageSize',pageSize);

  return this.httpClient.get<CreateAdvertModel[]>('http://localhost:8080/cars-advert-website/viewAdverts/getAdvertsByUser',{headers,params});
}

getAdvertsByOwnerCountPages(pageSize): Observable<string>{

  const headers = new HttpHeaders({
    'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
  });

  const params = new HttpParams()
  .set('pageSize',pageSize);

  return this.httpClient.get<string>('http://localhost:8080/cars-advert-website/viewAdverts/getAdvertsByUserPagesCount',{headers,params});
}

deleteAdvertById(id): Observable<any>{

  const headers = new HttpHeaders({
    'Authorization': 'Bearer '+localStorage.getItem('jwtToken'),
  });

  const params = new HttpParams()
  .set('advertId',id);

  return this.httpClient.delete('http://localhost:8080/cars-advert-website/viewAdverts/deleteAdvert',{headers,params});
}
    
}