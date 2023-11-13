import { UserModel } from "./UserModel"

export interface FundManagerModel{
        "fmid": BigInteger,
        "fmName": string,
        "activeStatus": boolean,
        "deleteStatus": boolean,
        "userInfo": UserModel
    }
