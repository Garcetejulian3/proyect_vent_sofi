package com.vent_entra_sf.proyect_vent_sofi.service;

import com.vent_entra_sf.proyect_vent_sofi.dto.ticket.TicketResponseDTO;
import com.vent_entra_sf.proyect_vent_sofi.model.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void enviarCodigo(String destino, String codigo) {
        try {
            System.out.println("Intentando enviar mail a: " + destino);

            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom(System.getenv("EMAIL_USER")); // 👈 IMPORTANTE
            mensaje.setTo(destino);
            mensaje.setSubject("Código de verificación - Compra realizada");
            mensaje.setText("Código: " + codigo);

            mailSender.send(mensaje);

            System.out.println("MAIL ENVIADO OK");
        } catch (Exception e) {
            System.out.println("ERROR REAL EN ENVIO DE MAIL");
            e.printStackTrace();
            throw e; // 👈 forzamos que explote
        }
    }

    public TicketResponseDTO mapToDTO(Ticket ticket){
        TicketResponseDTO dto = new TicketResponseDTO();

        dto.setCodigo(ticket.getCodigoVerificacion());
        dto.setEstado(ticket.getEstadoTicket().name());

        return dto;
    }

    public List<TicketResponseDTO> mapToDTOList(List<Ticket>tickets){
        return tickets.stream()
                .map(this::mapToDTO)
                .toList();
    }

}
