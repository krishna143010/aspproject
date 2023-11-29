import { FundManagerModel } from "./FundManagerModel";

export class ClientModel{
        "clientId": BigInteger | null;
        "fundManager":FundManagerModel | null | undefined;
        "clientName": string| '';
        "activeStatus": boolean| null | undefined
    }
