package com.boots.service;

import com.boots.entity.MedicineCatalog;
import com.boots.entity.Role;
import com.boots.repository.MedicineCatalogRepository;
import com.boots.repository.RoleRepository;
import com.boots.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public List<Role> allRole() {
        return roleRepository.findAll();
    }

    @Transactional
    public void add(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    public boolean checkId(int id) {
        Optional<Role> roleFromDb = roleRepository.findById(id);
        return !roleFromDb.isPresent();
    }

    public Role getById(int id) {

        Optional<Role> roleFromDb = roleRepository.findById(id);
        return roleFromDb.orElse(new Role());

    }
}
