export class StockData {
    "name": string;
    "advance": {
      "declines": string;
      "advances": string;
      "unchanged": string;
    };
    "timestamp": string;
    "data": StockInfo[];
  }
  export class StockInfo {
    "priority": number;
    "symbol": string;
    "identifier": string;
    "open": number;
    "dayHigh": number;
    "dayLow": number;
    "lastPrice": number;
    "previousClose": number;
    "change": number;
    "pChange": number;
    "ffmc": number;
    "yearHigh": number;
    "yearLow": number;
    "totalTradedVolume": number;
    "totalTradedValue": number;
    "lastUpdateTime": string;
    "nearWKH": number;
    "nearWKL": number;
    "perChange365d": number;
    "date365dAgo": string;
    "chart365dPath": string;
    "date30dAgo": string;
    "perChange30d": number;
    "chart30dPath": string;
    "chartTodayPath": string;
  }