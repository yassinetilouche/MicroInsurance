import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ContractModel } from 'src/app/Models/ContractModel';
import { ContractService } from 'src/app/Services/contract.service';

@Component({
  selector: 'app-modif-contract',
  templateUrl: './modif-contract.component.html',
  styleUrls: ['./modif-contract.component.css']
})
export class ModifContractComponent implements OnInit {
  contractid !: any;
  contract : ContractModel =new ContractModel();
  constructor(private c: ContractService, private _router:Router,private route: ActivatedRoute){
    if (this.route.snapshot.paramMap.get('id'))
    this.contractid=this.route.snapshot.paramMap.get('id')
  }
  ngOnInit(): void {
    this.FindContractByid()
  }
  FindContractByid(){
    return this.c.FindContractByid(this.contractid).subscribe((data:ContractModel)=>{
      this.contract=data
      console.log(this.contract)
    });
  }

  EditContracts(){
    return this.c.EditContracts(this.contract).subscribe(()=>this._router.navigateByUrl("/menu/listcontract"));
  }


}
