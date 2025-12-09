using System;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json;

namespace APIVerve.API.MortgageCalculator
{
    /// <summary>
    /// Query options for the Mortgage Calculator API
    /// </summary>
    public class MortgageCalculatorQueryOptions
    {
        /// <summary>
        /// The loan amount (e.g., 200000)
        /// Example: 200000
        /// </summary>
        [JsonProperty("amount")]
        public string Amount { get; set; }

        /// <summary>
        /// The interest rate (e.g., 4.5)
        /// Example: 4.5
        /// </summary>
        [JsonProperty("rate")]
        public string Rate { get; set; }

        /// <summary>
        /// The loan term in years (e.g., 30)
        /// Example: 30
        /// </summary>
        [JsonProperty("years")]
        public string Years { get; set; }

        /// <summary>
        /// The down payment amount (e.g., 15000)
        /// Example: 15000
        /// </summary>
        [JsonProperty("downpayment")]
        public string Downpayment { get; set; }

        /// <summary>
        /// The annual property tax amount (e.g., 2000)
        /// Example: 2000
        /// </summary>
        [JsonProperty("annual_propertytax")]
        public string Annual_propertytax { get; set; }

        /// <summary>
        /// The annual home insurance amount (e.g., 1200)
        /// Example: 1200
        /// </summary>
        [JsonProperty("annual_homeinsurance")]
        public string Annual_homeinsurance { get; set; }

        /// <summary>
        /// The annual HOA amount (e.g., 500)
        /// Example: 500
        /// </summary>
        [JsonProperty("annual_hoa")]
        public string Annual_hoa { get; set; }
    }
}
