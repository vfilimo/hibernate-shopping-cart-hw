package mate.academy.service.impl;

import java.util.List;
import mate.academy.dao.ShoppingCartDao;
import mate.academy.dao.TicketDao;
import mate.academy.lib.Inject;
import mate.academy.lib.Service;
import mate.academy.model.MovieSession;
import mate.academy.model.ShoppingCart;
import mate.academy.model.Ticket;
import mate.academy.model.User;
import mate.academy.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;
    @Inject
    private TicketDao ticketDao;

    @Override
    public void addSession(MovieSession movieSession, User user) {
        ShoppingCart shoppingCart = shoppingCartDao.getByUser(user).orElseThrow();
        Ticket ticket = ticketDao.add(new Ticket(movieSession, user));
        shoppingCart.getTickets().add(ticket);
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        return shoppingCartDao.getByUser(user).orElseThrow();
    }

    @Override
    public void registerNewShoppingCart(User user) {
        shoppingCartDao.add(new ShoppingCart(List.of(), user));
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        ShoppingCart shoppingCartFromDao = shoppingCartDao.getByUser(shoppingCart.getUser()).get();
        shoppingCartFromDao.getTickets().clear();
        shoppingCartDao.update(shoppingCartFromDao);
    }
}
