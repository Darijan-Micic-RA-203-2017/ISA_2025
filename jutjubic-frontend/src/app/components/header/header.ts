import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';

@Component({
  standalone: true,
  imports: [MatButtonModule, MatToolbarModule],
  selector: 'app-header',
  styleUrl: './header.css',
  templateUrl: './header.html'
})
export class HeaderComponent implements OnInit {
  username: string | null;

  constructor(private router: Router) {
    this.username = null;
  }

  ngOnInit(): void {
    this.username = localStorage.getItem('username');
  }

  /** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
  logOff(): void {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('username');
    localStorage.removeItem('exp');

    this.username = null;

    this.router.navigateByUrl('/log-on').then(() => { window.location.reload(); });
  }
}
