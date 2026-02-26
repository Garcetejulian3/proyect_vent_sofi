package com.vent_entra_sf.proyect_vent_sofi.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.vent_entra_sf.proyect_vent_sofi.model.Compra;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MercadoPagoService {

    @Value("${mp.access.token}")
    private String mpToken;

    public String createPreferencia(Compra compra) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(mpToken);

        PreferenceItemRequest item =
                PreferenceItemRequest.builder()
                        .title("Entrada de Evento")
                        .quantity(1)
                        .unitPrice(compra.getTotal())
                        .currencyId("ARS")
                        .build();

        // BACK URLS
        PreferenceBackUrlsRequest backUrls =
                PreferenceBackUrlsRequest.builder()
                        .success("https://nonmodal-abandonable-vanesa.ngrok-free.dev/success")
                        .failure("https://nonmodal-abandonable-vanesa.ngrok-free.dev/failure")
                        .pending("https://nonmodal-abandonable-vanesa.ngrok-free.dev/pending")
                        .build();

        PreferenceRequest preferenceRequest =
                PreferenceRequest.builder()
                        .items(List.of(item))
                        .backUrls(backUrls)

                // Clave del Sistema
                        .externalReference(compra.getIdCompra().toString())

                        .notificationUrl("https://nonmodal-abandonable-vanesa.ngrok-free.dev/api/mp/webhook")
                        .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        return preference.getInitPoint();
    }
}
