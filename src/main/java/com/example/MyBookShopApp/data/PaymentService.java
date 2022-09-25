package com.example.MyBookShopApp.data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class PaymentService {


    @Value("${robokassa.merchant.login}")
    private String merchantLogin;


    @Value("${robokassa.pass.first.test}")
    private String firstTestPass;



    public String getPaymentUrl(List<Book> books) throws NoSuchAlgorithmException {
        Double price = books.stream().mapToDouble(Book::discountPrice).sum();
        MessageDigest md = MessageDigest.getInstance("MD5");
        String invId="5";
        md.update((merchantLogin+":"+price.toString()+":"+invId+":"+firstTestPass).getBytes());
        return "https://auth.robokassa.ru/Merchant/Index.aspx"+
                "?MerchantLogin=" + merchantLogin+
                "&invId="+invId+
                "&Culture=ru"+
                "&encoding=utf-8" +
                "&OutSum="+price.toString()+
                "&SignatureValue=" + DatatypeConverter.printHexBinary(md.digest()).toUpperCase() +
                "&IsTest=1";
    }
    public String getPaymentUrl(Double sum) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String invId="5";
        md.update((merchantLogin+":"+sum.toString()+":"+invId+":"+firstTestPass).getBytes());
        return "https://auth.robokassa.ru/Merchant/Index.aspx"+
                "?MerchantLogin=" + merchantLogin+
                "&invId="+invId+
                "&Culture=ru"+
                "&encoding=utf-8" +
                "&OutSum="+sum.toString()+
                "&SignatureValue=" + DatatypeConverter.printHexBinary(md.digest()).toUpperCase() +
                "&IsTest=1";
    }
}
