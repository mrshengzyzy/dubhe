package space.zyzy.dubhe.test;

import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        BookingPassenger p1 = new BookingPassenger(1L);
        BookingPassenger p11 = new BookingPassenger(11L);
        BookingPassenger p2 = new BookingPassenger(22L);
        BookingPassenger p22 = new BookingPassenger(2L);
        BookingPassenger p3 = new BookingPassenger(3L);
        BookingPassenger p33 = new BookingPassenger(33L);

        List<BookingPassenger> b1 = new ArrayList<BookingPassenger>() {{
            add(p1);
            add(p11);
        }};
        List<BookingPassenger> b2 = new ArrayList<BookingPassenger>() {{
            add(p1);
            add(p2);
            add(p22);
        }};
        List<BookingPassenger> b3 = new ArrayList<BookingPassenger>() {{
            add(p3);
            add(p33);
        }};

        List<List<BookingPassenger>> totalList = new ArrayList<List<BookingPassenger>>() {{
            add(b1);
            add(b2);
            add(b3);
        }};
        BookingPassenger infPsg = new BookingPassenger(1L);

        for (List<BookingPassenger> passengerList : totalList) {
            for (BookingPassenger bookingPassenger : passengerList) {
                if (bookingPassenger.getPersonId().equals(infPsg.getPersonId())) {
                    passengerList.add(infPsg);
                    break;
                }
            }
        }

        System.out.println(totalList);

    }

    static class BookingPassenger {

        private Long personId;

        public BookingPassenger(Long personId) {
            this.personId = personId;
        }

        public Long getPersonId() {
            return personId;
        }

        public void setPersonId(Long personId) {
            this.personId = personId;
        }

        @Override
        public String toString() {
            return String.valueOf(personId);
        }
    }
}