package ImageHoster.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import ImageHoster.model.Comment;

@Repository
public class CommentRepository {
	
/**
	 Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
*/
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;
    
    public Comment createComment(Comment comment) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(comment);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return comment;
    }
    
    
  /**  The method creates an instance of EntityManager
    Executes JPQL typedQuery to fetch all the comments from the database based on Image id
    Returns the list of all the comments fetched from the database*/
    public List<Comment> getAllComments(Integer imageId, String imageTitle) {
        EntityManager em = emf.createEntityManager();
        try {
        	TypedQuery<Comment> typedQuery = em.createQuery("SELECT c from Comment c where c.image.id =:imageId", Comment.class).setParameter("imageId", imageId);
            List<Comment> resultList = typedQuery.getResultList();
            return resultList;
        } catch (NoResultException nre) {
            return null;
        }
    }
    
   
}
