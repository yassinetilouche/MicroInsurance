import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ContractModel } from '../Models/ContractModel';
@Injectable({
  providedIn: 'root'
})
export class ContractService {
  readonly Api_Url ="http://localhost:8081"
  readonly Endpoint_Getcontract ="/foryou/Contract/afficherAll"
  readonly Endpoint_Addcontract ="/foryou/Contract/ajouterContract"
  readonly Endpoint_Editcontract ="/foryou/Contract/ModifierContract"
  readonly Endpoint_Deletecontract ="/foryou/Contract/SupprimerContractById"
  readonly Endpoint_FindcontractByid ="/foryou/Contract/afficherContractById/"

  constructor(private httpClient : HttpClient) { }
  getContracts(){
    return this.httpClient.get<ContractModel[]>(this.Api_Url+this.Endpoint_Getcontract)
  }
  AddContracts(contract:ContractModel){
    return this.httpClient.post<string>(this.Api_Url+this.Endpoint_Addcontract,contract)
  }
  FindContractByid(id:string){
    let ids:number = +id
    return this.httpClient.get<ContractModel>(this.Api_Url+this.Endpoint_FindcontractByid+ids)
  }
  EditContracts(contract:ContractModel){
    return this.httpClient.put(this.Api_Url+this.Endpoint_Editcontract,contract)
  }
  DeleteContracts(id:number){
    this.httpClient.delete(this.Api_Url+this.Endpoint_Deletecontract+id)
  }

}

