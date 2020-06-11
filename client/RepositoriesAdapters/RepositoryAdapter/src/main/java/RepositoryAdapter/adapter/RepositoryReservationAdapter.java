package RepositoryAdapter.adapter;

import ApplicationPorts.Infrastructure.*;
import DataModel.ClientEnt;
import DataModel.SportsFacilityEnt;
import DomainModel.Reservation;
import DataModel.ReservationEnt;
import Repository.ClientRepository;
import Repository.ReservationRepository;
import Repository.SportsFacilityRepository;
import RepositoryAdapter.converter.RepositoryReservationConverter;
import exceptions.RepositoryException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@ApplicationScoped
public class RepositoryReservationAdapter implements ReservationPort {

    @Inject
    private ReservationRepository reservationRepository;

    @Inject
    private ClientRepository clientRepository;

    @Inject
    private SportsFacilityRepository sportsFacilityRepository;

    @Override
    public Reservation get(UUID id) throws RepositoryException {
        ReservationEnt reservationEnt = reservationRepository.get(id.toString());
        return RepositoryReservationConverter.convertTo(reservationEnt);
    }

    @Override
    public List<Reservation> getAll() {
        List<ReservationEnt> reservationEnts = reservationRepository.getAll();
        List<Reservation> reservations = new ArrayList<>();
        reservationEnts.forEach(reservationEnt -> {
            reservations.add(RepositoryReservationConverter.convertTo(reservationEnt));
        });
        return reservations;
    }

    @Override
    public void remove(Reservation arg) throws RepositoryException {
        ClientEnt clientEnt = clientRepository.get(arg.getClientId());
        SportsFacilityEnt sportsFacilityEnt = sportsFacilityRepository.get(arg.getSportsFacilityId());
        reservationRepository.remove(RepositoryReservationConverter.convertFrom(arg, clientEnt, sportsFacilityEnt));
    }

    @Override
    public void remove(UUID id) throws RepositoryException {
        reservationRepository.remove(id.toString());
    }

    @Override
    public void add(Reservation arg) throws RepositoryException {
        ClientEnt clientEnt = clientRepository.get(arg.getClientId());
        SportsFacilityEnt sportsFacilityEnt = sportsFacilityRepository.get(arg.getSportsFacilityId());
        reservationRepository.add(RepositoryReservationConverter.convertFrom(arg, clientEnt, sportsFacilityEnt));
    }

    @Override
    public void deactivateReservation(Reservation reservation) throws RepositoryException {
        ReservationEnt reservationEnt = reservationRepository.get(reservation.getId().toString());
        reservationEnt.setActive(false);
    }

    @Override
    public List<Reservation> getFiltered(Predicate<Reservation> predicate) {
        List<ReservationEnt> reservationEnts = reservationRepository.getAll();
        List<Reservation> reservations = new ArrayList<>();
        for (ReservationEnt item : reservationEnts) {
            reservations.add(RepositoryReservationConverter.convertTo(item));
        }
        return reservations.stream().filter(predicate).collect(Collectors.toList());
    }

}
