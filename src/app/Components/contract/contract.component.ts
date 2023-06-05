import { Component, OnInit } from '@angular/core';
import { ContractService } from 'src/app/Services/contract.service';
import { ContractModel } from 'src/app/Models/ContractModel';

@Component({
  selector: 'app-contract',
  templateUrl: './contract.component.html',
  styleUrls: ['./contract.component.css']
})
export class ContractComponent implements OnInit  {
  contracts: ContractModel[]=[];
  constructor(private contractService : ContractService) { }
  ngOnInit(): void {
    this.contractService.getContracts().subscribe((datas) => {
      this.contracts = datas;
        });
  }
}
