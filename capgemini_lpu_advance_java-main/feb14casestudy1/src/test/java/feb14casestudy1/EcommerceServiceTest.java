package feb14casestudy1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.casestudy1.OrderDao;
import com.casestudy1.Payment;
import com.casestudy1.PaymentDao;
import com.casestudy1.Profile;
import com.casestudy1.ProfileDao;
import com.casestudy1.PurchaseOrder;
import com.casestudy1.Service;
import com.casestudy1.User;
import com.casestudy1.UserDao;

public class EcommerceServiceTest {
	
    UserDao userDao = new UserDao();
    OrderDao orderDao = new OrderDao();
    PaymentDao paymentDao = new PaymentDao();
    Service service = new Service();
	
//    @Test
//    public void testSaveUserWithProfile(){
//
//        Profile p = new Profile(100L,"999999","Hyderabad");
//        User u = new User(100L,"Nani","nani@mail.com");
//
//        ProfileDao pd = new ProfileDao();
//        pd.saveProfile(p);
//
//        u.setProfile(p);
//        userDao.saveUser(u);
//
//        User dbUser = userDao.findUser(100L);
//
//        assertNotNull(dbUser);
//        assertEquals("999999", dbUser.getProfile().getPhone());
//    }
    
    
    @Test
    public void testUserWithOrders(){

        User u = new User(200L,"Puneeth","p@mail.com");

        List<PurchaseOrder> list = new ArrayList<>();
        list.add(new PurchaseOrder(201L,"today",100));
        list.add(new PurchaseOrder(202L,"today",200));

        // save orders first
        for(PurchaseOrder o : list){
            orderDao.saveOrder(o);
        }

        u.setPurchaseOrder(list);
        userDao.saveUser(u);

        User dbUser = userDao.findUser(200L);

        assertEquals(2, dbUser.getPurchaseOrder().size());
    }
    
    
    @Test
    public void testPaymentLinkedToOrder(){

        PurchaseOrder o = new PurchaseOrder(300L,"today",500);
        orderDao.saveOrder(o);

        Payment p = new Payment(301L,"UPI",500);
        p.setPurchaseOrder(o);

        paymentDao.savePayment(p);

        Payment db = paymentDao.findPayment(301L);

        assertEquals(500, db.getPurchaseOrder().getAmount());
    }

    @Test
    public void testUpdateOrderAmount(){

        PurchaseOrder o = new PurchaseOrder(400L,"today",100);
        orderDao.saveOrder(o);

        o.setAmount(999);
        orderDao.saveOrder(o);   // or updateOrder()

        PurchaseOrder db = orderDao.findOrder(400L);

        assertEquals(999, db.getAmount());
    }


    @Test
    public void testDeleteUser(){

        User u = new User(500L,"DeleteMe","del@mail");
        userDao.saveUser(u);

        userDao.deleteUser(500L);

        User db = userDao.findUser(500L);

        assertNull(db);
    }


    @Test
    public void testFullWorkflow(){

        Profile p = new Profile(600L,"888","Address");
        User u = new User(600L,"Workflow","wf@mail");

        new ProfileDao().saveProfile(p);
        service.registerUserWithProfile(u,p);

        List<PurchaseOrder> orders = new ArrayList<>();
        orders.add(new PurchaseOrder(601L,"today",1000));

        for(PurchaseOrder o:orders){
            orderDao.saveOrder(o);
        }

        service.addOrdersToUser(600L,orders);

        User db = service.fetchUserWithOrders(600L);

        assertEquals(1, db.getPurchaseOrder().size());
    }
    
}
