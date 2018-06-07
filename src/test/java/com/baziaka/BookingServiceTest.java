package com.baziaka;

import com.baziaka.domain.Event;
import com.baziaka.domain.Ticket;
import com.baziaka.domain.User;
import com.baziaka.domain.auditorium.Auditorium;
import com.baziaka.domain.discount.Discount;
import com.baziaka.domain.discount.DiscountType;
import com.baziaka.domain.seat.Seat;
import com.baziaka.domain.seat.SeatType;
import com.baziaka.repository.DiscountRepository;
import com.baziaka.repository.SeatRepository;
import com.baziaka.repository.TicketRepository;
import com.baziaka.service.BookingService;
import com.baziaka.service.crud.DiscountService;
import com.baziaka.service.crud.EventService;
import com.baziaka.service.crud.UserService;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.*;

public class BookingServiceTest {

    private BookingService bookingService;
    private DiscountService discountService;

    @Mock
    private EventService eventService;

    @Mock
    private UserService userService;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private DiscountRepository discountRepository;

    @Mock
    private Event event;

    @Mock
    private Seat seat;

    @Mock
    private User user;

    private List<Seat> seats;

    @Mock
    private List<Ticket> tickets;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        discountService = new DiscountService(discountRepository);
        bookingService = new BookingService(ticketRepository, seatRepository,
                discountService, eventService, userService);

        seats = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            seats.add(new Seat());
        }
        seats.add(new Seat(SeatType.Vip));

        when(discountRepository.findByDiscountType(DiscountType.BIRTHDAY))
                .thenReturn(new Discount(DiscountType.BIRTHDAY, 0.5d));

        when(discountRepository.findByDiscountType(DiscountType.TENTH_TICKET))
                .thenReturn(new Discount(DiscountType.TENTH_TICKET, 0.5d));

        when(discountRepository.findByDiscountType(DiscountType.NONE))
                .thenReturn(new Discount(DiscountType.NONE, 0d));

        when(seat.getBookingForSeat())
                .thenReturn(Collections.emptyList());

        when(event.getBasicPrice())
                .thenReturn(100d);

        when(event.getDate())
                .thenReturn(new DateTime());

        Auditorium auditorium = mock(Auditorium.class);

        when(event.getAuditorium())
                .thenReturn(auditorium);

        when(auditorium.getSeats())
                .thenReturn(seats);
    }

    @Test
    public void testBirthdayDiscount() {

        when(user.getBirthday())
                .thenReturn(new LocalDate());

        Discount discount = discountService.getDiscount(user);

        Assert.assertEquals(DiscountType.BIRTHDAY, discount.getDiscountType());
    }

    @Test
    public void testTenthTicketDiscount() {

        doReturn(9)
                .when(tickets).size();

        when(user.getBirthday())
                .thenReturn(new LocalDate(1));

        when(user.getTickets())
                .thenReturn(tickets);

        Discount discount = discountService.getDiscount(user);

        Assert.assertEquals(DiscountType.TENTH_TICKET, discount.getDiscountType());
    }

    @Test
    public void testNoneDiscount() {

        doReturn(5)
                .when(tickets).size();

        when(user.getBirthday())
                .thenReturn(new LocalDate(1));

        when(user.getTickets())
                .thenReturn(tickets);

        Discount discount = discountService.getDiscount(user);

        Assert.assertEquals(DiscountType.NONE, discount.getDiscountType());
    }

    @Test
    public void testTicketPriceCalculating() {
        Assert.assertEquals(Double.valueOf(1020),
                bookingService.getTicketsPrice(event, seats));
    }
}
