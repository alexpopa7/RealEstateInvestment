package com.proiect.licentam.services;

import com.proiect.licentam.model.*;
import com.proiect.licentam.repository.*;
import com.proiect.licentam.utils.ExportPdf;
import com.proiect.licentam.sorters.SortByValuePoints;
import com.proiect.licentam.sorters.SortZoneByValuePoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RealEstateService {

    private HomeRepository homeRepository;
    private ZoneRepository zoneRepository;
    private TypeRepository typeRepository;
    private StatusRepository statusRepository;
    private AddressRepository addressRepository;
    private List<Home> homeList;
    private ExportPdf exportPdf = new ExportPdf();
    private static final double composedInteresedRate = 4.0;
    private static final double mediumInflation = 2.8;
    private static final String ultraCentralZone = "Ultra Central";
    private static final String centralZone = "Central";
    private static final String mediumZone = "Medium quarter";
    private static final String periferieZone = "Periphery";
    private static final String metroZone = "Metropolitan area";
    private static final String statusNotSold = "Not Sold";



    @Autowired
    public RealEstateService(HomeRepository homeRepository, ZoneRepository zoneRepository, TypeRepository typeRepository, StatusRepository statusRepository, AddressRepository addressRepository) {
        this.homeRepository = homeRepository;
        this.zoneRepository = zoneRepository;
        this.typeRepository = typeRepository;
        this.statusRepository = statusRepository;
        this.addressRepository = addressRepository;
        this.homeList = homeListConverter(homeRepository.findAll());
    }

    public List<Home> getHomeList() {
        return homeList;
    }

    private List<Home> homeListConverter(List<HomeDao> homeDaoList){

        List<Home> toBeReturnedHomes = new ArrayList<>();
        for(HomeDao homeDao : homeDaoList){
            Home h = homeConverter(homeDao);
            toBeReturnedHomes.add(h);
        }
        return toBeReturnedHomes;
    }

    public void saveHome(Home toBeSavedHome) {
        HomeDao homeDaoToBeSaved = homeDaoConverter(toBeSavedHome);
        HomeDao homeDao = homeRepository.save(homeDaoToBeSaved);
        if (homeDao != null) {
            homeList.add(homeConverter(homeDao));
        }
    }

    public void updateHome(Home toBeUpdatedHome) {
        HomeDao homeDaoToBeSaved = homeDaoConverter(toBeUpdatedHome);
        HomeDao homeDao = homeRepository.save(homeDaoToBeSaved);
        if (homeDao != null) {
            homeList.remove(toBeUpdatedHome);
            homeList.add(homeConverter(homeDao));
        }
    }

    public Optional<HomeDao> findHomeById(Home toBeFoundHome) {
        HomeDao toBeFoundDao = homeDaoConverter(toBeFoundHome);
        Optional<HomeDao> foundHome = homeRepository.findById(toBeFoundDao.getId());
        if (foundHome.isPresent()) {
            return foundHome;
        }
        return foundHome;
    }

    public boolean deleteHome(Home toBeDeletedHome) {
        homeRepository.deleteById(toBeDeletedHome.getId());
        if (!findHomeById(toBeDeletedHome).isPresent()) {
            homeList.remove(toBeDeletedHome);
            return true;
        }
        return false;
    }


    public Home homeConverter (HomeDao homeDao){
        Home home = new Home();
        home.setId(homeDao.getId());
        home.setValuePoints(homeDao.getValuePoints());
        home.setPrice(homeDao.getPrice());
        home.setUser(homeDao.getUser());
        home.setBuildYear(homeDao.getBuildYear());
        home.setStatus(homeDao.getStatus().getStatus());
        home.setZoneType(homeDao.getZone().getType());
        home.setType(homeDao.getType().getType());
        if(homeDao.getAddress().getNumber() != null){
            home.setAddress(homeDao.getAddress().getStreet());
            home.setNumber(homeDao.getAddress().getNumber());
        }else {
            home.setAddress(homeDao.getAddress().getStreet());
        }
        home.setConstructedArea(homeDao.getConstructedArea());
        return home;
    }


    public HomeDao homeDaoConverter (Home home){
        HomeDao homeDao = new HomeDao();
        homeDao.setBuildYear(home.getBuildYear());
        homeDao.setConstructedArea(home.getConstructedArea());
        homeDao.setId(home.getId());
        homeDao.setPrice(home.getPrice());
        homeDao.setValuePoints(home.getValuePoints());
        Address existingAddresse = addressRepository.findByStreetAndNumber(home.getAddress(), home.getNumber());
        if(existingAddresse == null){
            Address address = new Address();
            if(home.getNumber() == null){
                address.setStreet(home.getAddress());
                homeDao.setAddress(address);
            }else {
                address.setStreet(home.getAddress());
                address.setNumber(home.getNumber());
                homeDao.setAddress(address);
            }
        }else {
            homeDao.setAddress(existingAddresse);
        }
        Type type = typeRepository.findByType(home.getType());
        homeDao.setType(type);
        Zone zone = zoneRepository.findByZone(home.getZoneType());
        homeDao.setZone(zone);
        homeDao.setUser(home.getUser());
        Status status = statusRepository.findByStatus(home.getStatus());
        homeDao.setStatus(status);
        return homeDao;
    }

    public ArrayList<Double> calculateCredit(Home home) {
        ArrayList<Double> list = new ArrayList<>();
        double credit10 = (double) home.getPrice() * (Math.pow((1.0 + (composedInteresedRate) / 100), 10));
        double credit20 = (double) home.getPrice() * (Math.pow((1.0 + (composedInteresedRate) / 100), 20));
        double credit30 = (double) home.getPrice() * (Math.pow((1.0 + (composedInteresedRate) / 100), 30));
        list.add(credit10);
        list.add(credit20);
        list.add(credit30);
        return list;
    }

    public double calculateCostumCredit(Home home, Integer years, double interesedRate) {
        double credit = (double) home.getPrice() * (Math.pow((1.0 + (interesedRate) / 100), years));
        return credit;
    }

    public double calculatePrice(Home home, Integer afterXyears) {
        double priceGrowthForOneYear = home.getPrice() * (mediumInflation / 100);
        double totalNewPrice = home.getPrice() + (priceGrowthForOneYear * afterXyears);
        return totalNewPrice;
    }

    public double calculateReturnOfInvestment(Home home, Integer oneMonthRent) {
        double returnOfInvestement = ((double) (oneMonthRent * 12) / (double) home.getPrice());
        return returnOfInvestement;
    }

    private List<Home> intersectLists(List<Home> firstListToIntersect, List<Home> secondListToIntersect) {

        if (firstListToIntersect.size() != 0 && secondListToIntersect.size() != 0) {
            List<Home> result = firstListToIntersect.stream()
                    .distinct()
                    .filter(secondListToIntersect::contains)
                    .collect(Collectors.toList());
            return result;
        } else if (firstListToIntersect.size() == 0 && secondListToIntersect.size() != 0) {
            return secondListToIntersect;
        } else if (firstListToIntersect.size() != 0 && secondListToIntersect.size() == 0) {
            return firstListToIntersect;
        }
        return new ArrayList<>();
    }

    public List<Home> getSpecificHomes(String homeType, String homeZone, Integer yearFrom, Integer yearTo, Integer priceFrom, Integer priceTo) {

        List<Home> typeHomeList;
        if (homeType.equals(" ")) {
            typeHomeList = new ArrayList<>();
        } else {
            typeHomeList = homeList.stream().filter(o -> o.getType().equals(homeType)).collect(Collectors.toList());
        }

        List<Home> zoneHomeList;
        if (homeZone.equals(" ")) {
            zoneHomeList = new ArrayList<>();
        } else {
            zoneHomeList = homeList.stream().filter(o -> o.getZoneType().equals(homeZone)).collect(Collectors.toList());
        }

        List<Home> yearHomeList;
        if (yearFrom == -1 && yearTo == -1) {
            yearHomeList = new ArrayList<>();
        } else if (yearFrom == -1 && yearTo != -1) {
            yearHomeList = homeList.stream().filter(o -> o.getBuildYear() <= yearTo).collect(Collectors.toList());
        } else if (yearFrom != -1 && yearTo == -1) {
            yearHomeList = homeList.stream().filter(o -> o.getBuildYear() >= yearFrom).collect(Collectors.toList());
        } else {
            yearHomeList = homeList.stream().filter(o -> ((o.getBuildYear() >= yearFrom) && (o.getBuildYear() <= yearTo))).collect(Collectors.toList());
        }

        List<Home> priceHomeList;
        if (priceFrom == -1 && priceTo == -1) {
            priceHomeList = new ArrayList<>();
        } else if (priceFrom == -1 && priceTo != -1) {
            priceHomeList = homeList.stream().filter(o -> (o.getPrice() / o.getConstructedArea()) <= priceTo).collect(Collectors.toList());
        } else if (priceFrom != -1 && priceTo == -1) {
            priceHomeList = homeList.stream().filter(o -> (o.getPrice() / o.getConstructedArea()) >= priceFrom).collect(Collectors.toList());
        } else {
            priceHomeList = homeList.stream().filter(o -> (((o.getPrice() / o.getConstructedArea()) >= priceFrom) && ((o.getPrice() / o.getConstructedArea()) <= priceTo))).collect(Collectors.toList());
        }

        List<Home> toBeReturnedHomes = intersectLists(intersectLists(intersectLists(typeHomeList, zoneHomeList), yearHomeList), priceHomeList);
        return toBeReturnedHomes;

//        List<Home> temporaryHomeList = homeList.stream().filter(o -> homeType == null || o.getType().equals(homeType)).
//                filter(o -> o.getZoneType().equals(homeZone)).
//                filter(o -> (((o.getPrice() / o.getConstructedArea()) >= priceFrom) && ((o.getPrice() / o.getConstructedArea()) <= priceTo))).
//                filter(o -> ((o.getBuildYear() >= yearFrom) && (o.getBuildYear() <= yearTo))).
//                collect(Collectors.toList());
//        return temporaryHomeList;
    }

    public List<Home> getMyHomes(User user) {
        List<Home> temporaryHomeList = homeList.stream().filter(o -> (o.getUser().getId().equals(user.getId()))).
                collect(Collectors.toList());
        return temporaryHomeList;
    }

    public boolean exportHomesToPdf(List<Home> homeList) {
        if (exportPdf.exportPDf(homeList)) {
            return true;
        }
        return false;
    }

    public String getBestZone(List<Home> homeList) {

        ZoneValuePoint ultraCentralVP = new ZoneValuePoint(ultraCentralZone, 0);
        ZoneValuePoint centralVP = new ZoneValuePoint(centralZone, 0);
        ZoneValuePoint cartierMediuVP = new ZoneValuePoint(mediumZone, 0);
        ZoneValuePoint periferieVP = new ZoneValuePoint(periferieZone, 0);
        ZoneValuePoint zonaMetropolitanaVP = new ZoneValuePoint(metroZone, 0);

        List<Home> valuableHomesList = homeList.stream().filter(o -> o.getValuePoints() >= 2).collect(Collectors.toList());

        for (Home h : valuableHomesList) {
            if (h.getZoneType().equals(ultraCentralVP.getZoneName())) {
                ultraCentralVP.setNrOfTotalValuePoints(ultraCentralVP.getNrOfTotalValuePoints() + h.getValuePoints());
            }
            if (h.getZoneType().equals(centralVP.getZoneName())) {
                centralVP.setNrOfTotalValuePoints(centralVP.getNrOfTotalValuePoints() + h.getValuePoints());
            }
            if (h.getZoneType().equals(cartierMediuVP.getZoneName())) {
                cartierMediuVP.setNrOfTotalValuePoints(cartierMediuVP.getNrOfTotalValuePoints() + h.getValuePoints());
            }
            if (h.getZoneType().equals(periferieVP.getZoneName())) {
                periferieVP.setNrOfTotalValuePoints(periferieVP.getNrOfTotalValuePoints() + h.getValuePoints());
            }
            if (h.getZoneType().equals(zonaMetropolitanaVP.getZoneName())) {
                zonaMetropolitanaVP.setNrOfTotalValuePoints(zonaMetropolitanaVP.getNrOfTotalValuePoints() + h.getValuePoints());
            }
        }
        List<ZoneValuePoint> zoneValuePointList = new ArrayList<>();
        zoneValuePointList.add(ultraCentralVP);
        zoneValuePointList.add(centralVP);
        zoneValuePointList.add(cartierMediuVP);
        zoneValuePointList.add(periferieVP);
        zoneValuePointList.add(zonaMetropolitanaVP);
        Collections.sort(zoneValuePointList, new SortZoneByValuePoints());

        return zoneValuePointList.get(0).getZoneName();
    }

    public List<Home> getBestZoneHomes() {
        String bestZone = getBestZone(homeList);
        List<Home> temporaryHomeList = homeList.stream().filter(o -> o.getZoneType().equals(bestZone)).collect(Collectors.toList());
        temporaryHomeList.sort(new SortByValuePoints());
        return temporaryHomeList;
    }

    private void getAverageBuiltYear(List<Home> homeList) {
        double builtYearAverage;
        double builtYears = 0.0;
        for (Home h : homeList) {
            builtYears += h.getBuildYear();
        }
        builtYearAverage = builtYears / homeList.size();

        for (Home h : homeList)
            if (h.getBuildYear() >= builtYearAverage) {
                h.setValuePoints(h.getValuePoints() + 1);
            }
    }

    private void getAverageSurfacePrice(List<Home> homeList) {
        double averageSurfacePrice;
        double surfacePrices = 0.0;
        for (Home h : homeList) {
            surfacePrices += (double) h.getPrice() / h.getConstructedArea();
        }
        averageSurfacePrice = surfacePrices / homeList.size();

        for (Home h : homeList)
            if (h.getPrice() / h.getConstructedArea() <= averageSurfacePrice * 1.3) {
                h.setValuePoints(h.getValuePoints() + 1);
            }
    }

    private void getBestZones(List<Home> homeList) {
        for (Home h : homeList) {
            if (h.getZoneType().equals(ultraCentralZone) || h.getZoneType().equals(centralZone)) {
                h.setValuePoints(h.getValuePoints() + 1);
            }
        }
    }

    private List<Home> setValuePointsHomeList(List<Home> homeList) {

        //not sold filter
        List<Home> temporaryList = homeList.stream().filter(o -> o.getStatus().equals(statusNotSold)).
                collect(Collectors.toList());

        //average built year filter & valuepoints ++
        getAverageBuiltYear(temporaryList);

        //average surface price & valuepoints++ for averagePrices + 30%
        getAverageSurfacePrice(temporaryList);

        getBestZones(temporaryList);

        return temporaryList;
    }

    private void resetValuePointsToNull(List<Home> homeList) {
        for (Home h : homeList) {
            h.setValuePoints(0);
        }
    }

    public List<Home> getUndervaluedHomes() {

        resetValuePointsToNull(homeList);
        for (Home h : homeList) {
            HomeDao homeDao = homeDaoConverter(h);
            homeRepository.save(homeDao);
        }

        List<Home> valuedHomeList = setValuePointsHomeList(homeList);
        for (Home h : homeList) {
            HomeDao homeDao = homeDaoConverter(h);
            homeRepository.save(homeDao);
        }

        List<Home> toBeSortedList = valuedHomeList.stream().filter(o -> o.getValuePoints() >= 2).
                collect(Collectors.toList());

        Collections.sort(toBeSortedList, new SortByValuePoints());

        return toBeSortedList;
    }

}

