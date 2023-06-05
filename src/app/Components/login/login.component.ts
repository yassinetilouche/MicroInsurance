import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Login } from 'src/app/Models/Login';
import { LoginService } from 'src/app/Services/login.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public loginForm: FormGroup;
  username: string ='';
  password: string = '';

  constructor(
    private loginService: LoginService,
    private router: Router,
    private toastrService: ToastrService
  ){}

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      username: new FormControl(null, Validators.required),
      password: new FormControl(null, Validators.required)
    })
  }

  logIn()
  {
    if(this.loginForm.valid) {
      this.loginService.login(new Login(
        this.username,
        this.password
      )).subscribe({
        next: (response) => {
            localStorage.setItem('token', response.token)
            localStorage.setItem('role', response.role);
            this.toastrService.success('Login', 'Connected successfully');
            this.router.navigate(['/homepage']);
        },
        complete: () => {
        },
        error:  (error) => this.toastrService.error('Login', 'Invalid password')
      })
    }
  }
}
