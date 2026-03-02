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


    @Async
    public void enviarCodigo(String destino, String codigo) {
        try {

            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setFrom("garcetejulian3@gmail.com");
            mensaje.setTo(destino);
            mensaje.setSubject("Código de verificación - Compra realizada");
            mensaje.setText("Tu código de verificación es: " + codigo + "\n\n" +
                    "Usa este código para acceder a tu entrada en el evento.");

            mailSender.send(mensaje);


        } catch (Exception e) {
            e.printStackTrace();
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