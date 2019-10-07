package com.github.DoneIn2Hours;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


class DayPortfolio {
    String security;
    float quantity;

    DayPortfolio(String security, float quantity) {
        this.security = security;
        this.quantity = quantity;
    }
}

class DaySecurityPrice {
    String security;
    float price;

    DaySecurityPrice(String security, float price) {
        this.security = security;
        this.price = price;
    }
}

public class FindMeeting {

    public static void main(String[] args) {

/*        int[] minutesInWeekIndx = new int[7 * 24 * 60];
        int[] markedBusyIndx = convertDaytoWeekMintIndx(minutesInWeekIndx, "Mon 10:00-12:23");

        for(int i : markedBusyIndx)
            System.out.print(i);
        System.out.println(convertToDayMintIndx("10:20"));*/

        System.out.println("asd");

        String date = "20190506";

        float netHoldingForADay = holdingForGivenDay(date);

    }

    public static float holdingForGivenDay(String date) {

        ArrayList<DayPortfolio> dayPortfolio = getDayPortfolio(date);
        ArrayList<DaySecurityPrice> daySecurityPrice = getDaySecurityPrice(date);

        Float dayPortfolioPrice = 0f;

        for (DayPortfolio portfolio : dayPortfolio) {

            for (DaySecurityPrice securityPrice : daySecurityPrice) {

                if(portfolio.security.equals(securityPrice.security)){

                    System.out.println(securityPrice.price  + " " + portfolio.quantity);

                    dayPortfolioPrice = dayPortfolioPrice + securityPrice.price * portfolio.quantity;

                }

            }

        }

        System.out.println(dayPortfolioPrice);

        return dayPortfolioPrice;

    }

    public static ArrayList<DayPortfolio> getDayPortfolio(String date) {

        ArrayList<DayPortfolio> dayPortfolio = new ArrayList<DayPortfolio>();

        Gson gson = new Gson();

        String holdingServiceUrl = "https://api.myjson.com/bins/10ysxg";

        while (holdingServiceUrl != null) {

            BufferedReader br = getResponseFromRestUrl(holdingServiceUrl);

            HoldingService holdingService = gson.fromJson(br, HoldingService.class);

            List<DateHolding> dateHoldings = holdingService.getData();

            for (DateHolding dateHolding : dateHoldings) {
                if (dateHolding.getDate().equals(date)) {
                    String security = dateHolding.getSecurity();
                    float quantity = dateHolding.getQuantity();

                    dayPortfolio.add(new DayPortfolio(security, quantity));

                }
            }

            holdingServiceUrl = holdingService.getNextPage();

            System.out.println(holdingServiceUrl);
        }


        return dayPortfolio;

    }


    public static ArrayList<DaySecurityPrice> getDaySecurityPrice(String date) {

        ArrayList<DaySecurityPrice> daySecurityPrice = new ArrayList<DaySecurityPrice>();

        Gson gson = new Gson();

        String securityPriceUrl = "https://api.myjson.com/bins/6ycbo";

        while (securityPriceUrl != null) {

            BufferedReader br = getResponseFromRestUrl(securityPriceUrl);

            PricingService pricingService = gson.fromJson(br, PricingService.class);

            List<SecurityMarketPrice> securityMarketPrice = pricingService.getData();

            for (SecurityMarketPrice daySecurityMarketPrice : securityMarketPrice) {
                if (daySecurityMarketPrice.getDate().equals(date)) {
                    String security = daySecurityMarketPrice.getSecurity();
                    float price = daySecurityMarketPrice.getPrice();

                    daySecurityPrice.add(new DaySecurityPrice(security, price));

                }
            }

            securityPriceUrl = pricingService.getNextPage();

            System.out.println(securityPriceUrl);
        }


        return daySecurityPrice;

    }



    public static BufferedReader getResponseFromRestUrl(String restUrl) {

        BufferedReader br = null;

        try {
            //URL url = new URL("http://api.myjson.com/bins/1eleys");
            // URL url = new URL("https://api.myjson.com/bins/10ysxg");
            URL url = new URL(restUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("=> " + conn.getResponseCode());
            }

            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            br = new BufferedReader(in);
            /*            String ou;
            while ((ou = br.readLine()) != null){
                System.out.println(ou);
            }*/


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null)
                return br;
            else
                return null;
        }
    }




    /*

    public static int convertToDayMintIndx(String str) {

        String[] hhmm = str.split(":");
        int hh = Integer.parseInt(hhmm[0]);
        int mm = Integer.parseInt(hhmm[1]);

        return hh * 60 + mm;
    }

    public static int[] convertDaytoWeekMintIndx(int[] minutesInWeekIndx, String str) {

        String[] dayStartEnd = str.split(" ");
        String day = dayStartEnd[0];
        String[] startEnd = dayStartEnd[1].split("-");

        int start = convertToDayMintIndx(startEnd[0]);
        int end = convertToDayMintIndx(startEnd[1]);

        int[] markedBusy;

        switch (day) {
            case "Mon":
                markedBusy = markBusy(0, start, end, minutesInWeekIndx);
                break;
            case "Tue":
                markedBusy = markBusy(1, start, end, minutesInWeekIndx);
                break;
            case "Wed":
                markedBusy = markBusy(2, start, end, minutesInWeekIndx);
                break;
            case "Thu":
                markedBusy = markBusy(3, start, end, minutesInWeekIndx);
                break;
            case "Fri":
                markedBusy = markBusy(4, start, end, minutesInWeekIndx);
                break;
            case "Sat":
                markedBusy = markBusy(5, start, end, minutesInWeekIndx);
                break;
            default:
                markedBusy = markBusy(6, start, end, minutesInWeekIndx);
        }

        return markedBusy;

    }

    public static int[] markBusy(int dayInx, int start, int end, int[] minutesInWeekIndx) {
        int startIndx = dayInx * 24 * 60 + start;
        int endIndx = dayInx * 24 * 60 + end;

        for (int indx = startIndx; indx <= endIndx; indx++) {
            minutesInWeekIndx[indx] = 1;
        }

        return minutesInWeekIndx;
    }*/

}
