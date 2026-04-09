import Keycloak from 'keycloak-js';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class KeycloakService {
    private keycloak: Keycloak;

    constructor() {
        this.keycloak = new Keycloak({
            url: 'http://localhost:8180',
            realm: 'collector',
            clientId: 'collector-backend'
        });
    }

    async init(): Promise<void> {
        try {
            await this.keycloak.init({
                onLoad: 'check-sso',
                checkLoginIframe: false,
                silentCheckSsoFallback: false
            });
        } catch (error) {
            console.warn('Keycloak init failed, continuing as unauthenticated', error);
        }
    }

    login(): void {
        this.keycloak.login();
    }

    logout(): void {
        this.keycloak.logout({ redirectUri: 'http://localhost:4200' });
    }

    getToken(): string | undefined {
        return this.keycloak.token;
    }

    isLoggedIn(): boolean {
        return this.keycloak.authenticated ?? false;
    }

    getUsername(): string | undefined {
        return this.keycloak.tokenParsed?.['preferred_username'];
    }

    hasRole(role: string): boolean {
        return this.keycloak.hasRealmRole(role);
    }
}