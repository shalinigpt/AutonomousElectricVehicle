package com.example.vehicle.handlers;

import com.example.vehicle.external.VehicleConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.example.vehicle.model.*;

import java.util.*;

@RestController
public class ElectricVehicleController {

	private static final Logger logger = LoggerFactory.getLogger(ElectricVehicleController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	VehicleConsumerService vehicleConsumerService;

	@GetMapping(value = "/welcome")
	public String testCall() {
		return "Hello, World";
	}

	//{ "transactionId": "043020211 //A unique numerical value", "vin": "W1K2062161F0014 //vehicle identification number", "source": "source name",
	// "destination": "destination name", "distance": "100 //distance between the source and destination in miles", "currentChargeLevel": "1 //current charge level in percentage ,
	// 0<=charge<=100", "isChargingRequired": "true/false //whether the vehicle has to stop for charging?.If true populate charging stations", "chargingStations": [ "s1", "s2" ],
	// "errors": [ { "Id": 8888, "description": "Unable to reach the destination with the current charge level" }, { "id": 9999, "description": "Technical Exception" } ] }

	@PostMapping(value = "/vehicle_reachDestination")
	public ElectricVehicle getCurrentChargeLevel(@RequestBody ElectricVehicle electricVehicle) {

		ElectricVehicle vehicle = electricVehicle;
		// fetching current charge level for a vin
		int currentChargelevel = vehicleConsumerService.getCurrentChargeLevel(vehicle.getVin());
		if(currentChargelevel != 0) {
			vehicle.setCurrentChargeLevel(currentChargelevel);
		} else{
			vehicle.setErrors(Arrays.asList(new ErrorDetails(9999, "Technical Exception")));
		}

		// fetching distance between source and destination
		int distance = vehicleConsumerService.getDistance(vehicle.getSource(), vehicle.getDestination());
		if(distance != 0){
			vehicle.setDistance(distance);
		} else{
			vehicle.setErrors(Arrays.asList(new ErrorDetails(9999, "Technical Exception")));
		}

		boolean isChargingRequired = isChargingRequired(vehicle.getCurrentChargeLevel(), vehicle.getDistance());

		if(isChargingRequired){
			// fetching charging stations
			List<ChargingStation> chargingStations = vehicleConsumerService.getChargingStations(vehicle.getSource(), vehicle.getDestination());
			if(chargingStations != null){
				vehicle.setChargingStations(chargingStations);
				vehicle.setTransactionId();
				vehicle.setChargingRequired(isChargingRequired);
				// our code for analyzing that vehicle can reach the destination or not
				canReachDestination(vehicle);
				//minRefuelStops(vehicle);
				System.out.println(vehicle.toString());
				return vehicle;
			} else{
				vehicle.setErrors(Arrays.asList(new ErrorDetails(9999, "Technical Exception")));
			}
		} else {
			vehicle.setTransactionId();
			vehicle.setChargingRequired(isChargingRequired);
			System.out.println(vehicle.toString());
			return vehicle;
		}
		return vehicle;
	}

	public boolean isChargingRequired(int currentChargeLevel, int distance){
		if(currentChargeLevel >= distance){
			return false;
		} else{
			return true;
		}
	}



	public void canReachDestination(ElectricVehicle vehicle){
		int currentChargeLevel = vehicle.getCurrentChargeLevel();
		int actualDistance = vehicle.getDistance();
		int remainingDistance = actualDistance;
		List<ChargingStation> actualChargingStns = vehicle.getChargingStations();
		List<ChargingStation> selectedStList = new ArrayList<ChargingStation>();
		ChargingStation chargingSt = null;
		int distanceCovered = 0;
		int totalDistanceCovered =0;
		while(isChargingRequired(currentChargeLevel, remainingDistance)){
			List<ChargingStation> reachableStList = new ArrayList<ChargingStation>();
			for(int i=0; i<actualChargingStns.size(); i++){
				System.out.println("actualChargingStns "+actualChargingStns.get(i).toString());
				if(actualChargingStns.get(i).getDistance() <= currentChargeLevel + totalDistanceCovered){
					reachableStList.add(vehicle.getChargingStations().get(i));
				}
			}
			if(reachableStList.size() == 0){
				vehicle.setChargingStations(null);
				vehicle.setErrors(Arrays.asList(new ErrorDetails(8888, "Unable to reach the destination with the current fuel level")));
				break;
			} else {
				int canCoverDistanceMax = 0;
				int canCoverDistanceMaxIndex = 0;
				for (int i=0; i<reachableStList.size();i++) {
					System.out.println("reachableStList "+reachableStList.get(i).toString());
					int remCharge = currentChargeLevel - reachableStList.get(i).getDistance();
					int vehicleCharge = remCharge+reachableStList.get(i).getLimit();
					vehicleCharge = (vehicleCharge > 100) ? 100 : vehicleCharge;
					int canCoverDistance = vehicleCharge + reachableStList.get(i).getDistance();
					if(canCoverDistance > canCoverDistanceMax){
						canCoverDistanceMax = canCoverDistance;
						canCoverDistanceMaxIndex = i;
					}
				}
				chargingSt = reachableStList.get(canCoverDistanceMaxIndex);
				System.out.println("chargingSt "+chargingSt.toString());
				selectedStList.add(chargingSt); //s2, s4, s6
				distanceCovered = chargingSt.getDistance() - totalDistanceCovered; // 8, //17, //20
				totalDistanceCovered = totalDistanceCovered + distanceCovered;//8, //25, //45
				remainingDistance = actualDistance - totalDistanceCovered; //92, //75, //55
				currentChargeLevel = currentChargeLevel - distanceCovered + chargingSt.getLimit();//42, //65, //145
				currentChargeLevel = (currentChargeLevel) < 100 ? (currentChargeLevel) : 100;
				for(int i=0; i< actualChargingStns.size(); i++){
					if(actualChargingStns.get(i).getDistance() <= chargingSt.getDistance()){
						actualChargingStns.remove(i);
					}
				}
//				System.out.println(distanceCovered +""+totalDistanceCovered+""+remainingDistance+""+currentChargeLevel);
			}
		}
		vehicle.setChargingStations(selectedStList);
	}



}




