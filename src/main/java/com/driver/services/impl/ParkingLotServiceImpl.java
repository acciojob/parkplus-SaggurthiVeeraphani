package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Spot spot = new Spot();
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        if(numberOfWheels <= 2){
            spot.setSpotType(SpotType.TWO_WHEELER);
        }
        if(numberOfWheels >2 && numberOfWheels <= 4){
            spot.setSpotType(SpotType.FOUR_WHEELER);
        }
        else if(numberOfWheels > 4){
            spot.setSpotType(SpotType.OTHERS);
        }
        spot.setPricePerHour(pricePerHour);
        spot.setOccupied(false);
        spot.setParkingLot(parkingLot);

        List<Spot> spotList = parkingLot.getSpotList();
        spotList.add(spot);
        parkingLot.setSpotList(spotList);

//        spotRepository1.save(spot);

        parkingLotRepository1.save(parkingLot);


        return spot;
    }

    @Override
    public void deleteSpot(int spotId) {
//        Spot spot = spotRepository1.findById(spotId).get();
//        ParkingLot parkingLot = spot.getParkingLot();
//        List<Spot> spotList = parkingLot.getSpotList();
//        for(Spot spot1:spotList){
//            if(spot1 == spot){
//                spotList.remove(spot1);
//            }
//        }
//        parkingLot.setSpotList(spotList);
//        parkingLotRepository1.save(parkingLot);//save in parent class
////        spotRepository1.delete(spot);//remove in spot repository;
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
      ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
      List<Spot> spotList = parkingLot.getSpotList();
      Spot updatedSpot = new Spot();
      for(Spot spot: spotList){
          if(spot.getId()==spotId){
              spot.setPricePerHour(pricePerHour);
              updatedSpot = spot;
          }
      }
      parkingLot.setSpotList(spotList);
      parkingLotRepository1.save(parkingLot);
      return updatedSpot;

    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
        parkingLotRepository1.delete(parkingLotRepository1.findById(parkingLotId).get());
    }
}
