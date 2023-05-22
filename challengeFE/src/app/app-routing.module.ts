import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './main/main.component';
import { FavoritosComponent } from './favoritos/favoritos.component';


const routes: Routes = [
  { path: '', component: MainComponent },
  { path: 'favoritos', component: FavoritosComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
