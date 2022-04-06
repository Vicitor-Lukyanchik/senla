package com.senla.hotel.dao.impl;

import com.senla.hotel.dao.EntityDao;
import com.senla.hotel.dao.LodgerDao;
import com.senla.hotel.domain.Lodger;
import com.senla.hotel.exception.DAOException;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

import java.util.List;

import static com.senla.hotel.dao.constant.TableColumns.*;

@Repository
public class LodgerDaoImpl extends EntityDao<Lodger, Long> implements LodgerDao {

}
