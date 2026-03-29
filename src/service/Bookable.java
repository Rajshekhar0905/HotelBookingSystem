package service;

import exception.InvalidCheckoutDateException;
import exception.RoomAlreadyBookedException;

import java.time.LocalDate;

/**
 * =========================================================
 *  OOP CONCEPT: INTERFACE  (Abstraction)
 * =========================================================
 * Defines the CONTRACT that any bookable entity must follow.
 * Any class implementing this MUST provide concrete logic for
 * book() and cancel().
 *
 * This is ABSTRACTION — we define WHAT to do, not HOW.
 */
public interface Bookable {
    void book(LocalDate checkIn, LocalDate checkOut)
            throws RoomAlreadyBookedException, InvalidCheckoutDateException;

    void cancel();

    boolean isAvailable();
}
