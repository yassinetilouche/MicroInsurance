import { PaymentFormality } from "./PaymentFormality"
import { PaymentType } from "./PaymentType"

export class ContractModel{
  contract_id!:number
  creationDate!:Date
  startDate!:Date
  exprirationDate!:Date
  ceilingAmount!:number
  installementsnbr!:number
  duration!:number
  lastRenewalDate!:Date
  paymentFormality!:PaymentFormality
  paymentDate!:Date
  paymentType!:PaymentType
  renewable!: Boolean
}
