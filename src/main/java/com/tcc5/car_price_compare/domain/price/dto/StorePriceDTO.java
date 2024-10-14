package com.tcc5.car_price_compare.domain.price.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO (Data Transfer Object) representing the data returned by the scraping microservice.
 * It contains the details of a store's price offer for a specific vehicle.
 *
 * This DTO is sent in the response body of the endpoint that retrieves store prices for a vehicle.
 * It provides the vehicle ID, the store where the price was found, the price itself, the URL of the deal,
 * and the timestamp indicating when the scraping was performed.
 *
 * @param vehicleId   The unique identifier (UUID) of the vehicle for which the prices were scraped.
 * @param store       The name of the store offering the price (e.g., OLX, Mercado Livre).
 * @param price       The price of the vehicle as listed by the store.
 * @param mileageInKm The mileage of the vehicle in kilometers.
 * @param dealUrl     The URL of the specific deal or offer on the store's website.
 * @param imageUrl    The URL of the vehicle image
 * @param scrapedAt   The date and time when the price was scraped from the store.
 */
public record StorePriceDTO (
        UUID vehicleId,
        String store,
        Double price,
        Double mileageInKm,
        String dealUrl,
        String imageUrl,
        LocalDateTime scrapedAt
) {
}
