package pl.lapciakbilicki.RepositoryAdapter.adapter;

import pl.lapciakbilicki.ApplicationCore.DomainModel.Reservation;
import pl.lapciakbilicki.ApplicationPorts.Interfaces.Infrastructure.*;
import pl.lapciakbilicki.DataModel.reservation.ReservationEnt;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.RepositoryException;
import pl.lapciakbilicki.RepositoriesAdapter.Repository.repository.ReservationRepository;
import pl.lapciakbilicki.RepositoryAdapter.conventer.RepositoryReservationConverter;
import pl.lapciakbilicki.RepositoryAdapter.conventer.exception.RepositoryConverterException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Named("RepositoryReservationAdapter")
@RequestScoped
public class RepositoryReservationAdapter
        implements GetPort<Reservation>,
        RemovePort<Reservation>,
        AddPort<Reservation>,
        ReservationDeactivationPort,
        FilterPort<Reservation> {

    @Inject
    private ReservationRepository reservationRepository;

    @Inject
    private RepositoryReservationConverter repositoryReservationConverter;

    @Override
    public Reservation get(UUID id) {
        ReservationEnt reservationEnt = reservationRepository.get(id.toString());
            return repositoryReservationConverter.convertTo(reservationEnt);
    }

    @Override
    public List<Reservation> getAll() {
        List<ReservationEnt> reservationEnts = reservationRepository.getAll();
        List<Reservation> reservations = new ArrayList<>();

        reservationEnts.forEach(reservationEnt -> {
            reservations.add(repositoryReservationConverter.convertTo(reservationEnt));
        });
        return reservations;
    }

    @Override
    public void remove(Reservation arg) throws RepositoryException {
        reservationRepository.remove(repositoryReservationConverter.convertFrom(arg));
    }

    @Override
    public void remove(UUID id) throws RepositoryException {
        reservationRepository.remove(id.toString());
    }

    @Override
    public void add(Reservation arg) throws RepositoryException {
        reservationRepository.add(repositoryReservationConverter.convertFrom(arg));
    }

    @Override
    public void deactivationReservation(Reservation reservation) {
        ReservationEnt reservationEnt = reservationRepository.get(reservation.getId().toString());
        reservationEnt.setActive(false);
    }

    @Override
    public List<Reservation> getFiltered(Predicate<Reservation> predicate) {
        List<ReservationEnt> reservationEnts = reservationRepository.getAll();
        List<Reservation> reservations = new ArrayList<>();
        reservationEnts.forEach(reservationEnt -> {
                reservations.add(repositoryReservationConverter.convertTo(reservationEnt));
        });

        return reservations.stream().filter(predicate).collect(Collectors.toList());
    }

}
