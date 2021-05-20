public Booking findBestBooking(Request req, Driver[] drivers)
{
    Booking cheapestBooking = new Booking(drivers[0], req);
    Booking currBooking;

    for(Driver driver : drivers)
    {
        currBooking= new Booking(driver, req);
        cheapestBooking = currBooking.compareTo(cheapestBooking) < 0 ? currBooking : cheapestBooking ;
    }

    return cheapestBooking;
}
