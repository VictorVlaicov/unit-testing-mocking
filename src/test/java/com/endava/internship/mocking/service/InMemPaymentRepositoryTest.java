package com.endava.internship.mocking.service;

import com.endava.internship.mocking.model.Payment;
import com.endava.internship.mocking.repository.InMemPaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InMemPaymentRepositoryTest {

    private InMemPaymentRepository paymentRepository;

    private final static List<Payment> payments = Stream.of(
                new Payment(1, 1D, "msg1"),
                new Payment(2, 2D, "msg2"),
                new Payment(3, 3D, "msg3"),
                new Payment(4, 4D, "msg4")
            ).collect(Collectors.toList());

    @BeforeEach
    void sepUp() {
        paymentRepository = new InMemPaymentRepository();
    }

    @Test
    void testSave() {
        // Given

        // When
        Payment savedPayment = paymentRepository.save(payments.get(0));

        // Then
        assertThat(savedPayment).isEqualToComparingFieldByField(payments.get(0));
    }
    @Test
    void shouldReturnExceptionWhenSaveNullPayment() {
        // Given

        // When
        Executable nullPayment = () -> paymentRepository.save(null);

        // Then
        assertThrows(IllegalArgumentException.class, nullPayment);
    }

    @Test
    void shouldReturnExceptionWhenPaymentExist() {
        // Given
        paymentRepository.save(payments.get(0));

        // When
        Executable existPayment = () -> paymentRepository.save(payments.get(0));

        // Then
        assertThrows(IllegalArgumentException.class, existPayment);
    }

    @Test
    void shouldReturnExceptionWhenNullPaymentId() {
        // Given

        // When
        Executable nullIdPayment = () -> paymentRepository.findById(null);

        // Then
        assertThrows(IllegalArgumentException.class, nullIdPayment);
    }

    @Test
    void shouldReturnPaymentFindById() {
        // Given
        paymentRepository.save(payments.get(0));
        paymentRepository.save(payments.get(1));

        // When
        Optional<Payment> findPayment = paymentRepository.findById(payments.get(0).getPaymentId());

        // Then
        assertTrue(findPayment.isPresent());
        assertThat(findPayment.get()).isEqualToComparingFieldByField(payments.get(0));
    }

    @Test
    void shouldReturnNullFindById() {
        // Given
        paymentRepository.save(payments.get(0));
        paymentRepository.save(payments.get(1));

        // When
        Optional<Payment> nullPayment = paymentRepository.findById(payments.get(2).getPaymentId());

        assertFalse(nullPayment.isPresent());
    }

    @Test
    void shouldReturnEmptyList() {
        // Given

        // When
        List<Payment> emptyList = paymentRepository.findAll();

        // Then
        assertTrue(emptyList.isEmpty());
    }

    @Test
    void shouldReturnAllPayments() {
        // Given
        paymentRepository.save(payments.get(0));
        paymentRepository.save(payments.get(1));
        paymentRepository.save(payments.get(2));

        // When
        List<Payment> allPaymentsList = paymentRepository.findAll();

        // Then
        assertThat(allPaymentsList)
                .containsOnly(payments.get(0), payments.get(1), payments.get(2));
    }

    @Test
    void shouldReturnExceptionWhenNullPaymentIdInEditMessage() {
        // Given

        // When
        Executable nullIdPayment = () -> paymentRepository.editMessage(null, "msg");

        // Then
        assertThrows(NoSuchElementException.class, nullIdPayment);
    }

    @Test
    void shouldReturnPaymentWithNewMessage() {
        // Given
        Payment payment = payments.get(0);
        paymentRepository.save(payment);
        String newMsg = "new msg";

        // When
        Payment newMsgPayment = paymentRepository.editMessage(payment.getPaymentId(), newMsg);
        payment.setMessage(newMsg);
        // Then
        assertThat(newMsgPayment).isEqualToComparingFieldByField(payment);
    }
}
