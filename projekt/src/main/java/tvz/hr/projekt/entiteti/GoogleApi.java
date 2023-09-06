package tvz.hr.projekt.entiteti;


import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;

public class GoogleApi {

    private static final double EARTH_RADIUS = 6371;
    GeoApiContext context = new GeoApiContext.Builder()
            .apiKey(apiKey)
            .build();

    String address1;
    String address2;

    public GoogleApi(String address1, String address2) {
        this.address1 = address1;
        this.address2 = address2;


    }

    public GoogleApi() {

    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }



    public void setAddress2(String address2) {
        this.address2 = address2;
    }

        public double calculateDistance() {
            try {
                GeocodingResult[] results1 = GeocodingApi.geocode(context, address1).await();
                GeocodingResult[] results2 = GeocodingApi.geocode(context, address2).await();

                double lat1 = results1[0].geometry.location.lat;
                double lon1 = results1[0].geometry.location.lng;

                double lat2 = results2[0].geometry.location.lat;
                double lon2 = results2[0].geometry.location.lng;

                double latDiff = Math.toRadians(lat2 - lat1);
                double lonDiff = Math.toRadians(lon2 - lon1);

                double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                        + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(lonDiff / 2) * Math.sin(lonDiff / 2);

                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                double distance = EARTH_RADIUS * c;

                return distance;
            } catch (ApiException | InterruptedException | IOException e) {
                e.printStackTrace();
                return -1;
            }
        }


    }

