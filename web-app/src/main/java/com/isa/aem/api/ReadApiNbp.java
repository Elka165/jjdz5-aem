package com.isa.aem.api;

import com.isa.aem.api.nbp.CurrencyRates;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class ReadApiNbp {
    private static final String BASE_URL =
            "http://api.nbp.pl/api/exchangerates/tables/A/2018-11-02/2018-11-15?format=json";

    public static List<CurrencyRates> callREST() {
        Client client = ClientBuilder.newClient();
        try {
            return ClientBuilder.newClient().target(BASE_URL)
                    .request()
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .get().readEntity(new GenericType<List<CurrencyRates>>() {});
        } finally {
            client.close();
        }
    }

    public static String callREST2() {
        Client client = ClientBuilder.newClient();
        try {
            return ClientBuilder.newClient().target(BASE_URL)
                    .request()
                    .accept(MediaType.APPLICATION_JSON_TYPE)
                    .get().readEntity(String.class);
        } finally {
            client.close();
        }
    }

}
