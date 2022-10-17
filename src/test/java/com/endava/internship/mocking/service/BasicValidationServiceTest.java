package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Status;
import com.endava.internship.mocking.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


class BasicValidationServiceTest {

    private final BasicValidationService basicValidationService = new BasicValidationService();

    @Test
    void testInvalidAmount() {
        // Given

        // When
        Executable nullAmount = () -> basicValidationService.validateAmount(null);
        Executable zeroAmount = () -> basicValidationService.validateAmount(0D);
        Executable negativeAmount = () -> basicValidationService.validateAmount(-10D);

        // Then
        assertThrows(IllegalArgumentException.class, nullAmount);
        assertThrows(IllegalArgumentException.class, zeroAmount);
        assertThrows(IllegalArgumentException.class, negativeAmount);
    }

    @Test
    void testValidAmount(){
        //Given

        //When
        Executable firstAmount = () -> basicValidationService.validateAmount(100D);
        Executable secondAmount = () -> basicValidationService.validateAmount(01D);
        //Then
        assertDoesNotThrow(firstAmount);
        assertDoesNotThrow(secondAmount);
    }
    @Test
    void testInvalidPaymentId() {
        // Given

        // When
        Executable nullPaymentId = () -> basicValidationService.validatePaymentId(null);

        // Then
        assertThrows(IllegalArgumentException.class, nullPaymentId);
    }

    @Test
    void testValidPaymentId(){
        //Given

        //When
        Executable paymentId = () -> basicValidationService.validatePaymentId(
                new UUID(4096L,10000000L));
        //Then
        assertDoesNotThrow(paymentId);
    }
    @Test
    void testInvalidUserId() {
        // Given

        // When
        Executable nullUserId = () -> basicValidationService.validateUserId(null);
        // Then
        assertThrows(IllegalArgumentException.class, nullUserId);
    }

    @Test
    void testValidUserId(){
        //Given

        //When
        Executable userId = () -> basicValidationService.validateUserId(8);
        //Then
        assertDoesNotThrow(userId);
    }

    @Test
    void testInvalidUser() {
        // Given
        User user = new User(1, "Name", Status.INACTIVE);

        // When
        Executable inactiveUser = () -> basicValidationService.validateUser(user);

        // Then
        assertThrows(IllegalArgumentException.class, inactiveUser);
    }

    @Test
    void testValidUser(){
        //Given
        User user = new User(1,"Name",Status.ACTIVE);
        //When
        Executable activeUser = () -> basicValidationService.validateUser(user);
        //Then
        assertDoesNotThrow(activeUser);
    }
    @Test
    void testInvalidateMessage() {
        // Given
        String message = null;

        // When
        Executable nullMessage = () -> basicValidationService.validateMessage(message);

        // Then
        assertThrows(IllegalArgumentException.class, nullMessage);
    }

    @Test
    void testValidMessage(){
        // Given
        String message ="message";

        // When
        Executable someMessage = () -> basicValidationService.validateMessage(message);

        // Then
        assertDoesNotThrow(someMessage);
    }
}
