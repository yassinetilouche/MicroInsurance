import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from 'src/app/Services/user.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {
  firstName: string;
  lastName: string;
  username: string;
  email: string;
  phoneNumber: string;
  birthdate: Date;
  password: string;
  confirmPassword: string;
  address:string;
  role: string;
  public signUpForm: FormGroup;

  constructor(
    private userService: UserService,
    private router: Router
  ){}

  ngOnInit(): void {
    this.signUpForm = new FormGroup({
      firstName:new FormControl(null, Validators.required),
      lastName:new FormControl(null, Validators.required),
      username:new FormControl(null, Validators.required),
      email:new FormControl(null, Validators.required),
      phoneNumber:new FormControl(null, Validators.required),
      birthdate: new FormControl(null, Validators.required),
      password: new FormControl(null, Validators.required),
      confirmPassword:new FormControl(null, Validators.required),
      address: new FormControl(null, Validators.required),
      role: new FormControl(null, Validators.required)
    });
  }

  signUp()
  {

   if(this.signUpForm.valid) { 

    if(this.password !== this.confirmPassword) {
      alert('pwd not equals');
      return;
    }
    this.userService.register(
      {
        username: this.username,
        firstName: this.firstName,
        lastName: this.lastName,
        phone: this.phoneNumber,
        address: this.address,
        role: this.role,
        password: this.password,
        email: this.email,
        birthDate: this.birthdate
      }
    ).subscribe({
      next: () => this.router.navigate(['/login'])
    })
  }
  }
}
