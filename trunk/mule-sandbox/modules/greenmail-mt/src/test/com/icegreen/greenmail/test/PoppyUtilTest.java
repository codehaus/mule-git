/*
 * Copyright (c) 2006 Your Corporation. All Rights Reserved.
 */
package com.icegreen.greenmail.test;

import com.icegreen.greenmail.util.GreenMailUtil;
import junit.framework.TestCase;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

/**
 * @author Wael Chatila
 * @version $Id: PoppyUtilTest.java 2860 2006-08-30 15:28:53Z holger $
 * @since Jan 29, 2006
 */
public class PoppyUtilTest extends TestCase {
    public void testMimeMessageLoading() throws MessagingException {
        MimeMessage message = GreenMailUtil.instance().newMimeMessage(sampleEmail);
        assertEquals("wassup", message.getSubject());
    }

    public void testGetBody() throws MessagingException, IOException {
        MimeMessage message = GreenMailUtil.instance().newMimeMessage(sampleEmail);
        String body = GreenMailUtil.instance().getBody(message);
        assertEquals("Yo wassup Bertil", body.trim());
    }

    final String sampleEmail = "From - Thu Jan 19 00:30:34 2006\r\n" +
            "X-Account-Key: account245\r\n" +
            "X-UIDL: 11332317636080.2607.mail5,S=833\r\n" +
            "X-Mozilla-Status: 0001\r\n" +
            "X-Mozilla-Status2: 00000000\r\n" +
            "Return-Path: <bertil@surstomming.com>\r\n" +
            "Delivered-To: eivar@blastigen.com\r\n" +
            "Received: (qmail 2376 invoked from network); 19 Jan 2006 02:01:05 -0000\r\n" +
            "Received: from unknown (HELO [192.168.0.5]) (hej@66.245.216.76)\r\n" +
            "\tby mail5.hotmail.com with (RC4-MD5 encrypted) SMTP; Wed, 18 Jan 2006 18:01:05 -0800\r\n" +
            "Message-ID: <43CEF322.7080702@hotmail.com>\r\n" +
            "Date: Wed, 18 Jan 2006 18:02:10 -0800\r\n" +
            "From: Wael Chatila <wael@localhost.com>\r\n" +
            "User-Agent: Mozilla Thunderbird 1.0.7 (Windows/20050923)\r\n" +
            "X-Accept-Language: en-us, en\r\n" +
            "MIME-Version: 1.0\r\n" +
            "To: Bertil <bertil@localhost.com>\r\n" +
            "Subject: wassup\r\n" +
            "Content-Type: text/plain; charset=ISO-8859-1; format=flowed\r\n" +
            "Content-Transfer-Encoding: 7bit\r\n" +
            "\r\n" +
            "Yo wassup Bertil\r\n";
}
