package com.senla.hotel.dao.impl;

import com.senla.hotel.dao.EntityDao;
import com.senla.hotel.dao.ServiceDao;
import com.senla.hotel.domain.Service;
import com.senla.hotel.exception.DAOException;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Query;

import static com.senla.hotel.dao.constant.TableColumns.*;

@Component
@Scope("singleton")
public class ServiceDaoImpl extends EntityDao<Service, Long> implements ServiceDao {

}
