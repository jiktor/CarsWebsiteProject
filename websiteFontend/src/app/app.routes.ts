import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AdvertComponent } from './advert/advert.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { RegisterComponent } from './register/register.component';
import { CreateAdvertComponent } from './create-advert/create-advert.component';

export const routes: Routes = [
    {path: 'login', component: LoginComponent},
    {path: 'advert', component: AdvertComponent},
    {path: 'register', component: RegisterComponent},
    {path:'createAdvert', component: CreateAdvertComponent},
    {path: '**', component: ErrorPageComponent}
];
