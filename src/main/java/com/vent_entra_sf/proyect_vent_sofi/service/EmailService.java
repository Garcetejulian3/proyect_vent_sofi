package com.vent_entra_sf.proyect_vent_sofi.service;

import com.vent_entra_sf.proyect_vent_sofi.dto.ticket.TicketResponseDTO;
import com.vent_entra_sf.proyect_vent_sofi.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    /**
     * Envía el código de verificación por email
     * Método asincrónico para no bloquear el webhook
     */
    @Async
    public void enviarCodigo(String destino, String codigo) {
        try {
            System.out.println("📧 Intentando enviar mail a: " + destino);
            System.out.println("📧 Desde email: " + emailFrom);

            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom(emailFrom);  // ✅ CORRECTO: Usa el valor inyectado
            mensaje.setTo(destino);
            mensaje.setSubject("Código de verificación - Compra realizada");
            mensaje.setText("Tu código de verificación es: " + codigo + "\n\n" +
                    "Usa este código para acceder a tu entrada en el evento.");

            mailSender.send(mensaje);

            System.out.println("✅ MAIL ENVIADO OK A: " + destino);

        } catch (Exception e) {
            System.out.println("❌ ERROR EN ENVIO DE MAIL A: " + destino);
            e.printStackTrace();  // ✅ Imprime el error real completo
            throw new RuntimeException("Fallo al enviar correo a " + destino, e);
        }
    }

    public TicketResponseDTO mapToDTO(Ticket ticket) {
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setCodigo(ticket.getCodigoVerificacion());
        dto.setEstado(ticket.getEstadoTicket().name());
        return dto;
    }

    public List<TicketResponseDTO> mapToDTOList(List<Ticket> tickets) {
        return tickets.stream()
                .map(this::mapToDTO)
                .toList();
    }
}