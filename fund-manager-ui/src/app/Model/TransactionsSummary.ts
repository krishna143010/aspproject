import { AccountSummary } from "./AccountSummary";
import { ClientSummary } from "./ClientSummary";

export class TransactionsSummary {
    availableFund!: string;
    clientSummaries!: ClientSummary[];
    accountSummaries!: AccountSummary[];
  }