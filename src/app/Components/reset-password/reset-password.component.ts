import { HttpParams, HttpRequest } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from 'src/app/Services/login.service';


@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
   token: string; 
   password: string;
   confirmPassword: string;
   public form: FormGroup;

   constructor(private route: ActivatedRoute, private loginService: LoginService,
    private router: Router
    ){}

   ngOnInit(): void {
    this.form = new FormGroup({
      password: new FormControl(null, Validators.required),
      confirmPassword: new FormControl(null, Validators.required),
    }); 
    this.route.queryParams.subscribe(
      (params) => {
        this.token = params['token'];
        console.log(this.token)
      }
     ); 
   }

   resetPwd()
   {

    if(this.form.valid) {
      if(this.password !== this.confirmPassword) {
        alert('pwd not equals');
        return;
      }
      this.loginService
          .resetPassword(this.token, this.password)
          .subscribe({
            next: () => this.router.navigate(['/login']) 
          });
    }

  }
}
