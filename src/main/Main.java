package main;

import dao.DomicilioDAOImpl;
import dao.GenericDAO;
import dao.PersonaDAOImpl;
import model.Domicilio;
import model.Persona;
import service.DomicilioServiceImpl;
import service.GenericService;
import service.PersonaServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 Iniciando aplicación...");

        GenericDAO<Domicilio> domicilioDAO = new DomicilioDAOImpl();
        GenericDAO<Persona> personaDAO = new PersonaDAOImpl();

        GenericService<Domicilio> domicilioService = new DomicilioServiceImpl(domicilioDAO);
        GenericService<Persona> personaService = new PersonaServiceImpl(personaDAO, domicilioService);

        try {

            System.out.println("\n📍 Creando domicilio...");
            Domicilio dom1 = new Domicilio("San Martin", 1200, 5500);
            domicilioService.save(dom1);

            System.out.println("\n👤 Creando persona...");
            Persona p1 = new Persona("Carlos", 25, dom1);
            personaService.save(p1);

            System.out.println("\n📍 Creando domicilio...");
            Domicilio dom2  = new Domicilio("Las Heras",2301 , 5519);
            domicilioService.save(dom2);

            System.out.println("\n👤 Creando persona...");
            Persona p2 = new Persona("Agustin", 21, dom2);
            personaService.save(p2);
            System.out.println("###########################################################");
            System.out.println("\n📋 Listado de personas:");
            List<Persona> personas = personaService.findAll();
            for (Persona p : personas) {
                System.out.println("🔹 " + p);
            }
            System.out.println("###########################################################");
            System.out.println("\n👤 Buscamos una persona con id [1] ...");
            System.out.println(personaService.findById(1));

            System.out.println("\n👤 Borramos la persona buscada ...");
            personaService.delete(1);
            System.out.println("###########################################################");
            System.out.println("\n📋 Listado de personas actualizado...:");
            List<Persona> personasActualizado = personaService.findAll();
            for (Persona p : personasActualizado) {
                System.out.println("🔹 " + p);
            }


        } catch (Exception e) {
            System.err.println("❌ Error en la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}