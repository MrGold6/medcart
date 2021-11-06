package com.boots.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "medicine_catalog")
public class MedicineCatalog {

        @Id
        @Column(name = "ATX")
        private String ATX;

        @Column(name = "name")
        private String name;

        public String getATX() {
            return ATX;
        }

        //???
        public void setATX(String ATX) {
            this.ATX = ATX;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
}
