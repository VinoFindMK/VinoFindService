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
import com.ukim.finki.domashna2.service.impl.WineryServiceImpl;
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

    @Autowired
    private WineryServiceImpl wineryServiceImpl;

    @Override
    public void run(String... args) throws Exception {
        if (wineryRepository.count() == 0) {
            Dotenv dotenv = Dotenv.configure().load();
            String apiKey = dotenv.get("GOOGLE_MAPS_API_KEY");
            GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build();
            LatLng[] locations = {
                new LatLng(41.71143,21.13630),
                new LatLng(41.87774,22.29132),
                new LatLng(41.24890,20.97830),
                new LatLng(41.25716,21.86275),
                new LatLng(41.55381,22.68675)
            };
            int radius = 50000;

            List<WineryInfo> allWineryData = new ArrayList<>();

            for (LatLng location : locations) {
                try {
                    String nextPageToken = "";
                    do {
                        TextSearchRequest request = PlacesApi.textSearchQuery(context, "wineries")
                                .location(location)
                                .radius(radius);

                        if (nextPageToken != null && !nextPageToken.isEmpty()) {
                            request = request.pageToken(nextPageToken);
                        }
                        PlacesSearchResponse response = request.await();
                        for (PlacesSearchResult result : response.results) {
                            PlaceDetails detailedResult = PlacesApi.placeDetails(context, result.placeId).await();
                            List<WineryReview> reviews = wineryServiceImpl.getReviews(detailedResult);
                            System.out.println("Getting info for: "+ detailedResult.name);
                            WineryInfo winery = new WineryInfo(
                                    detailedResult.name,
                                    detailedResult.formattedAddress,
                                    String.valueOf(detailedResult.geometry.location),
                                    detailedResult.rating,
                                    detailedResult.userRatingsTotal,
                                    wineryServiceImpl.getPhoneNumber(detailedResult),
                                    detailedResult.placeId,
                                    wineryServiceImpl.getOpeningTime(detailedResult),
                                    wineryServiceImpl.getWebsite(detailedResult),
                                    reviews
                            );
                            if (wineryServiceImpl.shouldIncludeWinery(result)) {
                                wineryService.saveWineryToDB(winery);
                                allWineryData.add(winery);
                            }
                        }
                        nextPageToken = response.nextPageToken;
                        System.out.println(nextPageToken);
                    }  while (nextPageToken != null && !nextPageToken.isEmpty());
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
