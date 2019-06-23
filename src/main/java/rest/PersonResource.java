package rest;

import dto.PersonDto;
import entity.Person;
import entityutils.EmfCreator;
import error.ResourceNotFound;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;


@Path("person")
public class PersonResource {

    @Context
    private UriInfo context;

    public PersonResource() {
    }
    
    private static EntityManagerFactory emf = EmfCreator.getEntityManagerFactory();
    
    @GET
    @Path("onedummy")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDto getOnePerson(){
        return new PersonDto("Kurt","Wonnegut","Lyngby vej 45", "Lyngby","kw@somewhere.com",0);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public PersonDto addPerson(PersonDto person){
        System.out.println("-------------------> "+person.getFirstName());
    
        EntityManager em = emf.createEntityManager();
        Person p = new Person(person);
        try{
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            PersonDto pReturn = new PersonDto();
            pReturn.setId(p.getId());
            pReturn.setFirstName(p.getFirstName());
            return pReturn;
        }finally{
            em.close();
        }
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public PersonDto getPerson(@PathParam("id") int id) throws ResourceNotFound {
        EntityManager em = emf.createEntityManager();
        try{
            Person p = em.find(Person.class,id);
            if(p == null){
                throw new ResourceNotFound("Person with requested id not found");
            }
            return new PersonDto(p);
        }finally{
            em.close();
        }
    }

   
   
}
