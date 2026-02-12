import { Component, OnInit } from '@angular/core';
import { Router, RouterLink } from '@angular/router';

import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';

import { AuthenticationService } from '../../services/authentication/authentication';

@Component({
  standalone: true,
  // REFERENCE: https://stackoverflow.com/questions/78168666/how-can-i-solve-this-error-usging-routerlink-in-angular-17-2/78168794
  imports: [RouterLink, MatButtonModule, MatToolbarModule],
  selector: 'app-header',
  styleUrl: './header.css',
  templateUrl: './header.html'
})
export class HeaderComponent implements OnInit {
  username: string | null;

  constructor(private router: Router, private authenticationService: AuthenticationService) {
    this.username = null;
  }

  ngOnInit(): void {
    this.username = localStorage.getItem('username');
  }

  /** REFERENCE: https://github.com/isa-asistent/Vezbe-2025/tree/main/vezbe4/spring-security-front-app */
  logOff(): void {
    this.authenticationService.logOff();
    this.username = null;

    this.router.navigateByUrl('/').then(() => { window.location.reload(); });
  }
}
