package com.projectx.clients.email;

public record EmailRequest(String ownerRef, String emailFrom, String emailTo, String emailSubject, String text) {
}
