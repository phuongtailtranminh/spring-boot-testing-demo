package net.jackietran.springboottestingdemo.taxcalculator;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
