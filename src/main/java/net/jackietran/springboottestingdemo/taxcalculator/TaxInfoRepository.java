package net.jackietran.springboottestingdemo.taxcalculator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxInfoRepository extends JpaRepository<TaxInfo, String> {
}
