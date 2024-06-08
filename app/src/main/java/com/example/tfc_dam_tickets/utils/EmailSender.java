package com.example.tfc_dam_tickets.utils;

import android.content.Context;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.example.tfc_dam_tickets.R;
import com.example.tfc_dam_tickets.model.Client;
import com.example.tfc_dam_tickets.model.Ticket;
import com.example.tfc_dam_tickets.model.User;
import com.example.tfc_dam_tickets.persistence.UserPersistence;

import java.util.List;

public class EmailSender {
    public static void sendEmail(Context context, String toEmail, String subject, String body) {

        String signature = context.getString(R.string.signature);

        //BackgroundMail.newBuilder(context) ORIGINAL LIBRARY
        CustomBackgroundMail.newBuilder(context)
                .withUsername(SenderEmailInfo.EMAIL)
                .withPassword(SenderEmailInfo.PASSWORD)
                .withMailto(toEmail)
                .withType(BackgroundMail.TYPE_PLAIN)
                .withSubject(subject)
                .withBody(body + signature)
                .send();
    }

    public static void notifyAdmins(Context context, User user, Ticket ticket, Client client) {
        List<User> adminUsers = new UserPersistence(context).getTecUsers();

        String subject = "Nuevo Ticket - #" + ticket.getTicketId();
        String body = context.getString(
                R.string.new_ticket_notification,
                ticket.getTicketId(),
                ticket.getCatId(),
                ticket.getClientId(),
                ticket.getUserOpen(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getTsOpen() != null ? ticket.getTsOpen().toString() : "N/A",
                user.getEmail(),
                user.getName(),
                user.getLastName(),
                user.getPhoneNum(),
                user.getType(),
                client.getClientId(),
                client.getName(),
                client.getNif(),
                client.getStreet(),
                client.getZipCode(),
                client.getProvince(),
                client.getMunicipality()
        );

        for (User adminUser : adminUsers) {
            sendEmail(context, adminUser.getEmail(), subject, body);
        }
    }



}
