/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.vnpay.common;

import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author TGDD
 */
public class Config {

    public static String vnp_PayUrl = "http://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String vnp_ReturnUrl = "http://localhost:9999/shop/vnpay_return.jsp";
    public static String vnp_TmnCode = "6F2RDZVN";
    public static String secretKey = "P9D6K30DHY8I3N14TBFLY8NJL7XWH5Y4";
    public static String vnp_ApiUrl = "http://sandbox.vnpayment.vn/merchant_webapi/merchant.html";

    //sử dụng để tạo mã hash từ một chuỗi dữ liệu.
    public static String md5(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            digest = "";
        } catch (NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }
    //trả về giá trị băm SHA-256 của chuỗi đó dưới dạng chuỗi hex
    public static String Sha256(String message) {
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }
            digest = sb.toString();
        } catch (UnsupportedEncodingException ex) {
            digest = "";
        } catch (NoSuchAlgorithmException ex) {
            digest = "";
        }
        return digest;
    }

    //Util for VNPAY
    //sắp xếp các trường theo thứ tự tăng dần của tên trường và tạo một chuỗi từ các trường và giá trị đó
    //sử dụng hmacSHA512 để tạo mã hash SHA-512 cho chuỗi đã tạo kèm theo khóa bí mật (secretKey)
    public static String hashAllFields(Map fields) {
        List fieldNames = new ArrayList(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                sb.append(fieldName);
                sb.append("=");
                sb.append(fieldValue);
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        return hmacSHA512(secretKey,sb.toString());
    }
    //nhận vào một chuỗi key và một chuỗi data, sau đó sử dụng thuật toán HMAC-SHA512 để tạo mã hash SHA-512 cho dữ liệu data sử dụng khóa key
    public static String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }
    //nhận vào một đối tượng HttpServletRequest và trả về địa chỉ IP của người gửi yêu cầu.
    public static String getIpAddress(HttpServletRequest request) {
        String ipAdress;
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }
//nhận vào một số nguyên len đại diện cho độ dài của số ngẫu nhiên cần tạo
    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
}

