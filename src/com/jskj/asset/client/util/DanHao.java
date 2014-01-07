/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jskj.asset.client.util;

import java.util.Date;

/**
 *
 * @author tt
 */
public class DanHao {
    public static String getDanHao(String type){
        
        return type + (new Date().getTime());
        
    }
    
    //测试用main函数
//    public static void main(String[] args){
//        System.out.println(DanHao.getDanHao("cgsq"));
//    }
}
