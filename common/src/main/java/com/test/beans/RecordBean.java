/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.test.beans;

import java.io.Serializable;

public class RecordBean implements Serializable {
    public String account_id;
    public String amount;
    public String card_number;
    public String card_type;
    public String customer_id;
    public String customer_zipcode;
    public String email;
    public String first_name;
    public String gender;
    public String is_married;
    public String last_name;
    public String merchant;
    public String rewards_earned;
    public String transaction_id;
    public String transaction_zipcode;
    public String tx_time;

//    private static String numDigits(int x) {
//        Random rng = new Random();
//        String ans = "";
//        for (int i = 0; i < x; i++) {
//            ans += rng.nextInt(10);
//        }
//        return ans;
//    }
//
    public static RecordBean generate() {
//        Random rng = new Random();
        RecordBean data = new RecordBean();
//        data.account_id = numDigits(9);
//        data.customer_id = numDigits(10);
//        data.amount = "" + Math.abs(rng.nextGaussian() * 1000);
//        data.country = "United States";
//        data.date = "11/5/2016";
//        data.merchant_name = "Yodoo";
//        data.rewards_earned = data.amount.substring(0, data.amount.length() - 2) + "." + data.amount.substring(data.amount.length() - 2);
//        data.transaction_id = numDigits(9);
//        data.transaction_row_id = "" + rng.nextInt(1000);
//        data.zipcode = numDigits(5);
        return data;
    }
}