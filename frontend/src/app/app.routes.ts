import { Routes } from '@angular/router';
import { ItemsListComponent } from './pages/items-list/items-list';
import { ItemFormComponent } from './pages/item-form/item-form';

export const routes: Routes = [
    { path: '', component: ItemsListComponent },
    { path: 'create', component: ItemFormComponent }
];
