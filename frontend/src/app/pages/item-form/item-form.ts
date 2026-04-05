import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ItemService } from '../../services/itemService';

@Component({
  selector: 'app-item-form',
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './item-form.html',
  styleUrl: './item-form.scss'
})
export class ItemFormComponent {
  itemForm;

  constructor(
    private fb: FormBuilder,
    private itemService: ItemService,
    private router: Router
  ) {
    this.itemForm = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      price: [null, [Validators.required, Validators.min(0.01)]]
    });
  }

  onSubmit(): void {
    if (this.itemForm.invalid) {
      this.itemForm.markAllAsTouched();
      return;
    }

    this.itemService.createItem(this.itemForm.value as any).subscribe({
      next: () => {
        this.router.navigate(['/']);
      },
      error: (error) => {
        console.error('Erreur lors de la création', error);
      }
    });
  }
}
