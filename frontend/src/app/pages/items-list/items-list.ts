import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ItemService } from '../../services/itemService';
import { Item } from '../../models/item';

@Component({
  selector: 'app-items-list',
  imports: [CommonModule, RouterModule],
  templateUrl: './items-list.html',
  styleUrl: './items-list.scss'
})
export class ItemsListComponent implements OnInit {
  items: Item[] = [];
  isLoading = true;
  errorMessage = '';

  constructor(
    private itemService: ItemService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadItems();
  }

  loadItems(): void {
    this.isLoading = true;
    this.errorMessage = '';

    this.itemService.getItems().subscribe({
      next: (data) => {
        this.items = data;
        this.isLoading = false;
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('Erreur lors du chargement des items', error);
        this.errorMessage = 'Impossible de charger les objets.';
        this.isLoading = false;
        this.cdr.detectChanges();
      }
    });
  }

  deleteItem(id?: number): void {
    if (!id) return;

    this.itemService.deleteItem(id).subscribe({
      next: () => {
        this.loadItems();
      },
      error: (error) => {
        console.error('Erreur lors de la suppression', error);
      }
    });
  }

  updateStatus(id: number | undefined, status: string): void {
    if (!id) return;

    this.itemService.updateStatus(id, status).subscribe({
      next: () => {
        this.loadItems();
      },
      error: (error) => {
        console.error('Erreur lors du changement de statut', error);
      }
    });
  }
}