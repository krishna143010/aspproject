import { ClientModel } from "./ClinetModel";
import { FundManagerModel } from "./FundManagerModel";

export class AccountModel{
        "accountId": BigInteger;
        "clients":ClientModel;
        "accountName": string;
        "accountNumber": BigInt;
        "upiId": string;
        "status": boolean;
    }
