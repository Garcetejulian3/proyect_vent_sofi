package com.vent_entra_sf.proyect_vent_sofi.controller;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.vent_entra_sf.proyect_vent_sofi.model.Compra;
import com.vent_entra_sf.proyect_vent_sofi.model.EstadoCompra;
import com.vent_entra_sf.proyect_vent_sofi.model.EstadoTicket;
import com.vent_entra_sf.proyect_vent_sofi.model.Ticket;
import com.vent_entra_sf.proyect_vent_sofi.repository.CompraRepository;
import com.vent_entra_sf.proyect_vent_sofi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
    @RequestMapping("/api/pagos")
    public class PagoWebhookController {
        @Autowired
        private EmailService emailService;

        @Autowired
        private CompraRepository compraRepository;

        @Value("${mp.access.token}")
        private String mpToken;

    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(@RequestParam("data_id") String paymentId)
            throws MPException, MPApiException {

        // 🔐 1️⃣ Autenticamos contra Mercado Pago
        // Sin esto, no podemos consultar el pago
        MercadoPagoConfig.setAccessToken(mpToken);

        // 📦 2️⃣ Creamos cliente de pagos
        PaymentClient client = new PaymentClient();

        // 🔎 3️⃣ Consultamos el pago REAL en Mercado Pago
        // El webhook solo manda el ID → aquí buscamos los datos completos
        Payment payment = client.get(Long.parseLong(paymentId));

        // ✅ 4️⃣ Solo actuamos si el pago fue APROBADO
        // Mercado Pago puede enviar estados como:
        // pending / rejected / approved / cancelled
        if ("approved".equals(payment.getStatus())) {

            // 🧾 5️⃣ Obtenemos el ID de compra de NUESTRO sistema
            // externalReference = dato que nosotros enviamos al crear la preferencia
            Long compraId = Long.valueOf(payment.getExternalReference());

            // 🔎 6️⃣ Buscamos la compra en la base de datos
            Compra compra = compraRepository.findById(compraId)
                    .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

            // 💰 7️⃣ Marcamos la compra como PAGADA
            compra.setEstadoCompra(EstadoCompra.PAGADO);

            // 🎟 8️⃣ Recorremos los tickets de esa compra
            for (Ticket ticket : compra.getTickets()) {

                // ✅ Ticket ahora es válido
                ticket.setEstadoTicket(EstadoTicket.VALIDO);

                // 🔑 9️⃣ Obtenemos el código YA generado
                // 👉 NO se genera aquí
                // 👉 NO se modifica aquí
                String codigo = ticket.getCodigoVerificacion();

                // 📧 🔟 Enviamos el código por correo
                emailService.enviarCodigo(compra.getEmailComprador(), codigo);
            }

            // 💾 11️⃣ Guardamos cambios en BD
            compraRepository.save(compra);
        }

        // 👍 12️⃣ Respondemos OK a Mercado Pago
        // Si no respondes OK → Mercado Pago reintenta
        return ResponseEntity.ok("OK");
    }


    }
