import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AdvertComponent } from './advert/advert.component';

import { routes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes)]
};
