import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { importProvidersFrom } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

// Polyfills
// (window as any).global = window; // Polyfill for `global`
// (window as any).process = require('process/browser'); // Polyfill for `process`
// (window as any).Buffer = require('buffer').Buffer; // Polyfill for `Buffer`

bootstrapApplication(AppComponent, {
  providers: [importProvidersFrom(HttpClientModule)],
}).catch((err: any) => console.error(err));
