import { Component, signal, WritableSignal } from '@angular/core';
import { RouterOutlet } from '@angular/router';

import { HeaderComponent } from './components/header/header';

@Component({
  standalone: true,
  imports: [HeaderComponent, RouterOutlet],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html'
})
export class AppComponent {
  protected readonly title: WritableSignal<string> = signal('jutjubic-frontend');
}
