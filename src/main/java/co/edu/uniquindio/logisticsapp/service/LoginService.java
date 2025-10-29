package co.edu.uniquindio.logisticsapp.service;

import co.edu.uniquindio.logisticsapp.model.User;
import co.edu.uniquindio.logisticsapp.repository.LogisticsRepository;

public class LoginService {


    public boolean login(String email){
        LogisticsRepository repo = LogisticsRepository.getInstance();

        for (User u : repo.getUsers()){
            if(u.getEmail().equalsIgnoreCase(email)){
                if(esAdmin(u)){
                    System.out.println("Bienvenido Admin " + u.getEmail());
                }
                else if (esCourier(u)) {
                    System.out.println("Bienvenido Dealer " + u.getEmail());
                }else{
                    System.out.println("Bienvenido Usuario " + u.getEmail());
                }
                return true;
            }
        }
        System.out.println("Email no encontrado");
        return false;
    }

    private boolean esAdmin (User usuario){
        return usuario.getEmail().toLowerCase().contains("admin");
    }

    private boolean esCourier (User usuario){
        return usuario.getEmail().toLowerCase().contains("dealer");
    }

}
