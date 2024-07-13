import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AdvertComponent } from './advert/advert.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { RegisterComponent } from './register/register.component';
import { CreateAdvertComponent } from './create-advert/create-advert.component';
import { ShowAdvertsComponent } from './show-adverts/show-adverts.component';

export const routes: Routes = [
    {path: 'cars-advert-website/login', component: LoginComponent},
    {path: 'cars-advert-website/advert', component: AdvertComponent},
    {path: 'cars-advert-website/show-adverts', component: ShowAdvertsComponent},
    {path: 'cars-advert-website/register', component: RegisterComponent},
    {path:'cars-advert-website/createAdvert', component: CreateAdvertComponent},
    {path:'cars-advert-website/viewAdverts/getSingleAdvert/:advertId', component: AdvertComponent},
    {path: '**', component: ErrorPageComponent}
];
