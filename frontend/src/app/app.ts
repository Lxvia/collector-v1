import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { KeycloakService } from './services/keycloak.service';
import { inject } from '@angular/core';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class AppComponent {
  keycloak = inject(KeycloakService);
}
