package com.ukim.finki.domashna2.service;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.TextSearchRequest;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.ukim.finki.domashna2.model.WineryInfo;
import com.ukim.finki.domashna2.model.WineryReview;
import com.ukim.finki.domashna2.repository.WineryRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class WineryDataExporterService implements CommandLineRunner {
    @Autowired
    private WineryService wineryService;

    @Autowired
    private WineryRepository wineryRepository;


    @Override
    public void run(String... args) throws Exception {
        if (wineryRepository.count() == 0) {
            Dotenv dotenv = Dotenv.configure().load();
            String apiKey = dotenv.get("GOOGLE_MAPS_API_KEY");
            GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build();
            LatLng[] locations = {
                new LatLng(41.25303, 21.27507),
                new LatLng(41.52297, 22.35999),
                new LatLng(41.93242, 21.69960),
                new LatLng(42.14813, 22.27066),
                new LatLng(41.83265, 20.95572),
                new LatLng(41.45005, 22.22156),
                new LatLng(41.75242, 22.55919),
                new LatLng(42.11101, 22.18597),
                new LatLng(41.81062, 21.90612),
                new LatLng(41.53209, 21.58333),
                new LatLng(41.07832, 20.96742),
                new LatLng(41.69503, 20.89885),
                new LatLng(42.05295, 21.33640),
                new LatLng(41.09672, 21.60100)
            };
            int radius = 50000;

            List<WineryInfo> allWineryData = new ArrayList<>();

            for (LatLng location : locations) {
                try {
                    TextSearchRequest request = PlacesApi.textSearchQuery(context, "wineries")
                            .location(location)
                            .radius(radius);
                    PlacesSearchResponse response = request.await();
                    for (PlacesSearchResult result : response.results) {
                        PlaceDetails detailedResult = PlacesApi.placeDetails(context, result.placeId).await();
                        List<WineryReview> reviews = Utility.getReviews(detailedResult);
                        System.out.println("Getting info for: "+ detailedResult.name);
                        WineryInfo winery = new WineryInfo(
                                detailedResult.name,
                                detailedResult.formattedAddress,
                                String.valueOf(detailedResult.geometry.location),
                                detailedResult.rating,
                                detailedResult.userRatingsTotal,
                                Utility.getPhoneNumber(detailedResult),
                                detailedResult.placeId,
                                Utility.getOpeningTime(detailedResult),
                                Utility.getWebsite(detailedResult),
                                reviews
                        );
                        if (LocationFilter.shouldIncludeWinery(result)) {
                            wineryService.saveWineryToDB(winery);
                            allWineryData.add(winery);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Info gathered");
        }else{
            System.out.println("Info has already been gathered");
        }
    }
}
