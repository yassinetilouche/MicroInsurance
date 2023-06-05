import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../Models/User';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseUri = environment.baseUri;

  constructor(private httpClient: HttpClient) { }

  register(user: User)
  {
    return this.httpClient.post<any>(
      `${this.baseUri}/foryou/register/${user.role}`,
      user
    )
  }

  list()
  {
    return this.httpClient.get<User[]>(
      `${this.baseUri}/foryou/User/afficherUser`
    );
  }
}
