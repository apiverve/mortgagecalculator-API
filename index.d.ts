declare module '@apiverve/mortgagecalculator' {
  export interface mortgagecalculatorOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface mortgagecalculatorResponse {
    status: string;
    error: string | null;
    data: any;
    code?: number;
  }

  export default class mortgagecalculatorWrapper {
    constructor(options: mortgagecalculatorOptions);

    execute(callback: (error: any, data: mortgagecalculatorResponse | null) => void): Promise<mortgagecalculatorResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: mortgagecalculatorResponse | null) => void): Promise<mortgagecalculatorResponse>;
    execute(query?: Record<string, any>): Promise<mortgagecalculatorResponse>;
  }
}
