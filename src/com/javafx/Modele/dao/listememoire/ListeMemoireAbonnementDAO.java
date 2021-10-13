package com.javafx.Modele.dao.listememoire;

import com.javafx.Modele.dao.AbonnementDAO;
import com.javafx.Modele.metier.Abonnement;
import com.javafx.Modele.metier.Client;
import com.javafx.Modele.metier.Revue;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ListeMemoireAbonnementDAO implements AbonnementDAO {

    private static ListeMemoireAbonnementDAO instance;

    private List<Abonnement> donnees;


    public static ListeMemoireAbonnementDAO getInstance()
    {
        if (instance == null) {
            instance = new ListeMemoireAbonnementDAO();
        }

        return instance;
    }

    private ListeMemoireAbonnementDAO()
    {
        this.donnees = new ArrayList<Abonnement>();

        this.donnees.add(new Abonnement(1, LocalDate.parse("23/04/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalDate.parse("24/04/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy")), new Client(1), new Revue(1)));
        this.donnees.add(new Abonnement(2, LocalDate.parse("23/04/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
            LocalDate.parse("24/04/2021", DateTimeFormatter.ofPattern("dd/MM/yyyy")), new Client(2), new Revue(2)));
    }


    @Override
    public boolean create(Abonnement objet)
    {
        objet.setId(3);
        // Ne fonctionne que si l'objet métier est bien fait...
        while (this.donnees.contains(objet))
        {
            objet.setId(objet.getId() + 1);
        }
        boolean ok = this.donnees.add(objet);

        return ok;
    }

    @Override
    public boolean update(Abonnement objet)
    {
        int idx = -1;

        for (Abonnement abonnement : this.donnees) 
        {
            if (abonnement.getId() == objet.getId()) 
            {
                idx = this.donnees.indexOf(abonnement);    
            }    
        }

        if (idx == -1) 
        {
            throw new IllegalArgumentException("Tentative de modification d'un objet inexistant");
        } 
        else 
        {

            this.donnees.set(idx, objet);
        }

        return true;
    }

    @Override
    public boolean delete(Abonnement objet)
    {
        int idx = -1;

        for (Abonnement abonnement : this.donnees) 
        {
            if (abonnement.getId() == objet.getId()) 
            {
                idx = this.donnees.indexOf(abonnement);    
            }    
        }

        if (idx == -1) 
        {
            throw new IllegalArgumentException("Tentative de modification d'un objet inexistant");
        } 
        else 
        {
            this.donnees.remove(idx);
        }

        return true;
    }

    @Override
    public Abonnement getById(int id)
    {
        int idx = -1;

		for (Abonnement abonnement : this.donnees) 
		{
			if (abonnement.getId() == id) 
			{
				idx = this.donnees.indexOf(abonnement);
			}
		}

		if (idx == -1) 
		{
			throw new IllegalArgumentException("Aucun objet ne possède cet identifiant");
		} 
		else 
		{
			return this.donnees.get(idx);
		}
    }

    @Override
    public List<Abonnement> findAll()
    {
        return this.donnees;
    }

    @Override
    public ArrayList<Abonnement> getByDate(LocalDate dateDebut, LocalDate dateFin)
    {
        ArrayList<Abonnement> listeAbonnement = new ArrayList<Abonnement>();
        
		for (Abonnement abonnement : this.donnees) 
		{
			if (abonnement.getDateDebut().equals(dateDebut) && abonnement.getDateFin().equals(dateFin)) 
			{
				listeAbonnement.add(this.donnees.get(this.donnees.indexOf(abonnement)));
			}
		}

		if (listeAbonnement.size() == 0) 
		{
			throw new IllegalArgumentException("Aucun objet ne possède ces dates");
		} 
		else 
		{
			return listeAbonnement;
		}
    }

    @Override
    public ArrayList<Abonnement> getByNomPrenom(String nom, String prenom) throws SQLException 
    {
        ArrayList<Abonnement> listeAbonnement = new ArrayList<Abonnement>();
        ListeMemoireClientDAO listeMemoireClient = ListeMemoireClientDAO.getInstance();

		for (Abonnement abonnement : this.donnees) 
		{
            int idClient = abonnement.getIdClient();
            Client client = listeMemoireClient.getById(idClient);

			if (client.getPrenom().equals(prenom) && client.getNom().equals(nom)) 
			{
				listeAbonnement.add(this.donnees.get(this.donnees.indexOf(abonnement)));
			}
		}

		if (listeAbonnement.size() == 0) 
		{
			throw new IllegalArgumentException("Aucun objet ne possède ce prenom et ce nom");
		} 
		else 
		{
			return listeAbonnement;
		}
    }
}
