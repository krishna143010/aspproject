import { UserModel } from "./UserModel"

export interface FundManagerModel{
        "fmid": BigInteger | null,
        "fmName":string | null | undefined,
        "activeStatus": boolean| null | undefined,
        "deleteStatus": boolean| null | undefined,
        "userInfo": UserModel| null | undefined,
    }
