declare module '@apiverve/mortgagecalculator' {
  export interface mortgagecalculatorOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface mortgagecalculatorResponse {
    status: string;
    error: string | null;
    data: MortgageCalculatorData;
    code?: number;
  }


  interface MortgageCalculatorData {
      amount:               number;
      downpayment:          number;
      rate:                 number;
      years:                number;
      totalInterestPaid:    number;
      monthlyPayment:       Payment;
      annualPayment:        Payment;
      amortizationSchedule: AmortizationSchedule[];
  }
  
  interface AmortizationSchedule {
      month:            number;
      interestPayment:  number;
      principalPayment: number;
      remainingBalance: number;
  }
  
  interface Payment {
      total:         number;
      mortgage:      number;
      propertyTax:   number;
      hoa:           number;
      homeInsurance: number;
  }

  export default class mortgagecalculatorWrapper {
    constructor(options: mortgagecalculatorOptions);

    execute(callback: (error: any, data: mortgagecalculatorResponse | null) => void): Promise<mortgagecalculatorResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: mortgagecalculatorResponse | null) => void): Promise<mortgagecalculatorResponse>;
    execute(query?: Record<string, any>): Promise<mortgagecalculatorResponse>;
  }
}
