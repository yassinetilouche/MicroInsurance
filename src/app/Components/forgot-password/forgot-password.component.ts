import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LoginService } from 'src/app/Services/login.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent  implements OnInit{
  public forgotPasswordForm: FormGroup;
  email: string;
  isSent : boolean = false;

  constructor(
    private loginService: LoginService,
  ){}

  ngOnInit(): void {
    this.forgotPasswordForm = new FormGroup({
      email: new FormControl(null, Validators.required)
    }) 
  }

  forgotPassword()
  {
      if(this.forgotPasswordForm.valid) {
        this.loginService.forgotPassword(this.email)
        .subscribe({
          next: () => this.isSent = true
         })
      }
  }
}
