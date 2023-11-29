import { AccountModel } from "./AccountModel";
import { ClientModel } from "./ClinetModel";
import { FundManagerModel } from "./FundManagerModel";

export class TransactionModel{
        "transId": BigInteger | null;
        "fromClientId":ClientModel;
        "toClientId":ClientModel;
        "fromAccountId": AccountModel;
        "toAccountId": AccountModel;
        "remarks": string;
        "date": Date;
        "amount": BigInteger;
    }
