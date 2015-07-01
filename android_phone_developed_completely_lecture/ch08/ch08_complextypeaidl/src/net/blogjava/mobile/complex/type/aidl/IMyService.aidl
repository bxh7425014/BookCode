package net.blogjava.mobile.complex.type.aidl;
import net.blogjava.mobile.complex.type.aidl.Product;

interface IMyService  
{  
    Map getMap(in String country, in Product product);
    Product getProduct();     
}          