// Converter.java

// To use this code, add the following Maven dependency to your project:
//
//
//     com.fasterxml.jackson.core     : jackson-databind          : 2.9.0
//     com.fasterxml.jackson.datatype : jackson-datatype-jsr310   : 2.9.0
//
// Import this package:
//
//     import com.apiverve.data.Converter;
//
// Then you can deserialize a JSON string with
//
//     MortgageCalculatorData data = Converter.fromJsonString(jsonString);

package com.apiverve.mortgagecalculator.data;

import java.io.IOException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class Converter {
    // Date-time helpers

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_INSTANT)
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetDateTime parseDateTimeString(String str) {
        return ZonedDateTime.from(Converter.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
    }

    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
            .parseDefaulting(ChronoField.YEAR, 2020)
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetTime parseTimeString(String str) {
        return ZonedDateTime.from(Converter.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
    }
    // Serialize/deserialize helpers

    public static MortgageCalculatorData fromJsonString(String json) throws IOException {
        return getObjectReader().readValue(json);
    }

    public static String toJsonString(MortgageCalculatorData obj) throws JsonProcessingException {
        return getObjectWriter().writeValueAsString(obj);
    }

    private static ObjectReader reader;
    private static ObjectWriter writer;

    private static void instantiateMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                String value = jsonParser.getText();
                return Converter.parseDateTimeString(value);
            }
        });
        mapper.registerModule(module);
        reader = mapper.readerFor(MortgageCalculatorData.class);
        writer = mapper.writerFor(MortgageCalculatorData.class);
    }

    private static ObjectReader getObjectReader() {
        if (reader == null) instantiateMapper();
        return reader;
    }

    private static ObjectWriter getObjectWriter() {
        if (writer == null) instantiateMapper();
        return writer;
    }
}

// MortgageCalculatorData.java

package com.apiverve.mortgagecalculator.data;

import com.fasterxml.jackson.annotation.*;

public class MortgageCalculatorData {
    private long amount;
    private long downpayment;
    private double rate;
    private long years;
    private double totalInterestPaid;
    private Payment monthlyPayment;
    private Payment annualPayment;
    private AmortizationSchedule[] amortizationSchedule;

    @JsonProperty("amount")
    public long getAmount() { return amount; }
    @JsonProperty("amount")
    public void setAmount(long value) { this.amount = value; }

    @JsonProperty("downpayment")
    public long getDownpayment() { return downpayment; }
    @JsonProperty("downpayment")
    public void setDownpayment(long value) { this.downpayment = value; }

    @JsonProperty("rate")
    public double getRate() { return rate; }
    @JsonProperty("rate")
    public void setRate(double value) { this.rate = value; }

    @JsonProperty("years")
    public long getYears() { return years; }
    @JsonProperty("years")
    public void setYears(long value) { this.years = value; }

    @JsonProperty("total_interest_paid")
    public double getTotalInterestPaid() { return totalInterestPaid; }
    @JsonProperty("total_interest_paid")
    public void setTotalInterestPaid(double value) { this.totalInterestPaid = value; }

    @JsonProperty("monthly_payment")
    public Payment getMonthlyPayment() { return monthlyPayment; }
    @JsonProperty("monthly_payment")
    public void setMonthlyPayment(Payment value) { this.monthlyPayment = value; }

    @JsonProperty("annual_payment")
    public Payment getAnnualPayment() { return annualPayment; }
    @JsonProperty("annual_payment")
    public void setAnnualPayment(Payment value) { this.annualPayment = value; }

    @JsonProperty("amortization_schedule")
    public AmortizationSchedule[] getAmortizationSchedule() { return amortizationSchedule; }
    @JsonProperty("amortization_schedule")
    public void setAmortizationSchedule(AmortizationSchedule[] value) { this.amortizationSchedule = value; }
}

// AmortizationSchedule.java

package com.apiverve.mortgagecalculator.data;

import com.fasterxml.jackson.annotation.*;

public class AmortizationSchedule {
    private long month;
    private double interestPayment;
    private double principalPayment;
    private double remainingBalance;

    @JsonProperty("month")
    public long getMonth() { return month; }
    @JsonProperty("month")
    public void setMonth(long value) { this.month = value; }

    @JsonProperty("interest_payment")
    public double getInterestPayment() { return interestPayment; }
    @JsonProperty("interest_payment")
    public void setInterestPayment(double value) { this.interestPayment = value; }

    @JsonProperty("principal_payment")
    public double getPrincipalPayment() { return principalPayment; }
    @JsonProperty("principal_payment")
    public void setPrincipalPayment(double value) { this.principalPayment = value; }

    @JsonProperty("remaining_balance")
    public double getRemainingBalance() { return remainingBalance; }
    @JsonProperty("remaining_balance")
    public void setRemainingBalance(double value) { this.remainingBalance = value; }
}

// Payment.java

package com.apiverve.mortgagecalculator.data;

import com.fasterxml.jackson.annotation.*;

public class Payment {
    private double total;
    private double mortgage;
    private long propertyTax;
    private long hoa;
    private long homeInsurance;

    @JsonProperty("total")
    public double getTotal() { return total; }
    @JsonProperty("total")
    public void setTotal(double value) { this.total = value; }

    @JsonProperty("mortgage")
    public double getMortgage() { return mortgage; }
    @JsonProperty("mortgage")
    public void setMortgage(double value) { this.mortgage = value; }

    @JsonProperty("property_tax")
    public long getPropertyTax() { return propertyTax; }
    @JsonProperty("property_tax")
    public void setPropertyTax(long value) { this.propertyTax = value; }

    @JsonProperty("hoa")
    public long getHoa() { return hoa; }
    @JsonProperty("hoa")
    public void setHoa(long value) { this.hoa = value; }

    @JsonProperty("home_insurance")
    public long getHomeInsurance() { return homeInsurance; }
    @JsonProperty("home_insurance")
    public void setHomeInsurance(long value) { this.homeInsurance = value; }
}