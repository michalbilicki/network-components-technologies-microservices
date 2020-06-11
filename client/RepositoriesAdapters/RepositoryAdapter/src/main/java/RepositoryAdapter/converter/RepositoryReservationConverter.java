package RepositoryAdapter.converter;

import DataModel.ClientEnt;
import DataModel.ReservationEnt;
import DataModel.SportsFacilityEnt;
import DomainModel.Reservation;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class RepositoryReservationConverter {

    public static Reservation convertTo(ReservationEnt obj) {
        if (obj == null)
            return null;
        LocalDateTime start = convertDateToLocalDateTime(obj.getStartDate());
        LocalDateTime end = convertDateToLocalDateTime(obj.getEndDate());
        return new Reservation(obj.getId(), obj.getClient().getId(), start, end, obj.getFacility().getId(), obj.isActive());
    }

    public static ReservationEnt convertFrom(Reservation obj, ClientEnt clientEnt, SportsFacilityEnt sportsFacilityEnt) {
        if (obj == null)
            return null;
//        ClientEnt clientEnt = clientRepository.get(obj.getClientId());
//        SportsFacilityEnt sportsFacilityEnt = sportsFacilityRepository.get(obj.getSportsFacilityId());
        return new ReservationEnt(
                obj.getId(),
                clientEnt,
                sportsFacilityEnt,
                convertLocalDateTimeToDate(obj.getStart()),
                convertLocalDateTimeToDate(obj.getEnd()),
                obj.isActive()
        );
    }

    private static LocalDateTime convertDateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(),
                ZoneId.systemDefault());
    }

    private static Date convertLocalDateTimeToDate(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }

}
