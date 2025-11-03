package co.edu.uniquindio.logisticsapp.service;

import co.edu.uniquindio.logisticsapp.model.Delivery;
import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;

public class LoginService {


    public boolean loginUser(String email){
        LogisticsRepository repo = LogisticsRepository.getInstance();

        for (User u : repo.getUserList()){
            if(u.getEmail().equalsIgnoreCase(email)){
                    System.out.println("Bienvenido Usuario " + u.getEmail());
                    return true;
                }
            }
        System.out.println("Email no encontrado");
        return false;
    }

    public boolean loginDelivery(String email){
        LogisticsRepository repo = LogisticsRepository.getInstance();

        for (Delivery d : repo.getDeliveriesList()){
            if(d.getEmail().equalsIgnoreCase(email)){
                System.out.println("Bienvenido Repartidor " + d.getEmail());
                return true;
            }
        }
        System.out.println("Email no encontrado");
        return false;
    }

    /*public boolean loginAdmin(String email){
        LogisticsRepository repo = LogisticsRepository.getInstance();

        for (User u : repo.getUserList()){
            if(u.getEmail().equalsIgnoreCase(email)){
                System.out.println("Bienvenido Usuario " + u.getEmail());
            }
            return true;
        }
        System.out.println("Email no encontrado");
        return false;
    }*/



    private boolean esAdmin (User usuario){

        return usuario.getEmail().toLowerCase().contains("admin");
    }

    private boolean esDelivery(User usuario){

        return usuario.getEmail().toLowerCase().contains("delivery");
    }

}
