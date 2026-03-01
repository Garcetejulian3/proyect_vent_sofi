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

import java.util.Map;


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
    public ResponseEntity<String> webhook(@RequestBody Map<String, Object> body) {

        try {

            // 1️⃣ Validar que venga el tipo
            String type = (String) body.get("type");
            if (type == null || !"payment".equals(type)) {
                System.out.println("⚠️ Webhook: Tipo de evento ignorado");
                return ResponseEntity.ok("Evento ignorado");
            }

            // 2️⃣ Validar que venga data
            Object dataObj = body.get("data");
            if (!(dataObj instanceof Map)) {
                System.out.println("⚠️ Webhook: Sin data válida");
                return ResponseEntity.ok("Sin data válida");
            }

            Map<String, Object> data = (Map<String, Object>) dataObj;

            Object idObj = data.get("id");
            if (idObj == null) {
                System.out.println("⚠️ Webhook: Sin payment id");
                return ResponseEntity.ok("Sin payment id");
            }

            String paymentId = idObj.toString();
            System.out.println("🔔 Webhook recibido para payment: " + paymentId);

            // 3️⃣ Autenticación Mercado Pago
            MercadoPagoConfig.setAccessToken(mpToken);
            PaymentClient client = new PaymentClient();

            // 4️⃣ Obtener pago real
            Payment payment = client.get(Long.parseLong(paymentId));

            if (payment == null) {
                System.out.println("⚠️ Webhook: Pago no encontrado");
                return ResponseEntity.ok("Pago no encontrado");
            }

            System.out.println("💳 Estado del pago: " + payment.getStatus());

            // 5️⃣ Solo procesar si está aprobado
            if (!"approved".equals(payment.getStatus())) {
                System.out.println("⚠️ Webhook: Pago no aprobado, estado: " + payment.getStatus());
                return ResponseEntity.ok("Pago no aprobado");
            }

            // 6️⃣ Obtener ID de compra desde externalReference
            if (payment.getExternalReference() == null) {
                System.out.println("⚠️ Webhook: Sin referencia externa");
                return ResponseEntity.ok("Sin referencia externa");
            }

            Long compraId = Long.valueOf(payment.getExternalReference());
            System.out.println("🛒 Compra ID: " + compraId);

            Compra compra = compraRepository.findById(compraId)
                    .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

            // 7️⃣ Evitar reprocesar si ya está pagada
            if (compra.getEstadoCompra() == EstadoCompra.PAGADO) {
                System.out.println("⚠️ Webhook: Compra ya procesada");
                return ResponseEntity.ok("Compra ya procesada");
            }

            // 8️⃣ Actualizar estados
            System.out.println("✅ Actualizando estado de compra y tickets...");
            compra.setEstadoCompra(EstadoCompra.PAGADO);

            for (Ticket ticket : compra.getTickets()) {
                ticket.setEstadoTicket(EstadoTicket.VALIDO);
            }

            // 9️⃣ Guardar primero en base de datos
            compraRepository.save(compra);
            System.out.println("✅ Compra guardada en BD");

            // 🔟 Enviar emails después de guardar
            System.out.println("📧 Iniciando envío de correos...");
            for (Ticket ticket : compra.getTickets()) {
                try {
                    System.out.println("📨 Enviando correo para ticket: " + ticket.getIdTicket());
                    emailService.enviarCodigo(
                            compra.getEmailComprador(),
                            ticket.getCodigoVerificacion()
                    );
                } catch (Exception emailError) {
                    // ❌ NO fallar toda la transacción si falla un email
                    System.out.println("⚠️ Error enviando email para ticket: " +
                            ticket.getIdTicket());
                    emailError.printStackTrace();
                    // En producción, podrías guardar esto en una tabla de errores
                    // o en un servicio de logs
                }
            }

            System.out.println("✅✅✅ WEBHOOK PROCESADO EXITOSAMENTE");
            return ResponseEntity.ok("OK");

        } catch (MPApiException | MPException e) {
            System.out.println("❌ ERROR DE API MERCADO PAGO");
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error API Mercado Pago: " +
                    e.getMessage());

        } catch (Exception e) {
            System.out.println("❌ ERROR REAL EN WEBHOOK");
            e.printStackTrace();  // ✅ Imprime el stack trace completo
            return ResponseEntity.status(500).body("Error procesando webhook: " +
                    e.getMessage());
        }
    }

}